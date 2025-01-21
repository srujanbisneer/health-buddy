package com.healthbuddy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.grihagully.model.RetrofitClient
import com.healthbuddy.databinding.ActivityProfileBinding
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class profile : AppCompatActivity() {
    private val b by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
   var num=""
   var pass=""
   var email=""
   var name=""
   var address=""
   var city=""
   var type=""
   var id=0
   var path=""
    var uri: Uri? = null
    var encoded=""
    private lateinit var p: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
            num=getString("num","").toString()
            pass=getString("pass","").toString()
            email=getString("email","").toString()
            name=getString("name","").toString()
            address=getString("address","").toString()
            city=getString("city","").toString()
            type=getString("type","").toString()
            id=getInt("id",0)
            path=getString("path","").toString()

        }


        b.etname.setText(name)
        b.etmobilee.setText(num)
        b.etaddress.setText(address)
        b.etpassword.setText(pass)
        b.etcity.setText(city)
        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))

        p = builder.create()
        p.show()


        if(path.isNotEmpty()){
            Glide.with(this).load(Uri.parse(path.trim())).into(b.imageadd)
            p.dismiss()
        }

        val activity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.data?.let {
                b.imageadd.setImageURI(it)
                uri = it
            }
        }

        b.imageadd.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type="image/*"
                activity.launch(this)
            }
        }


        b.btnsubmit.setOnClickListener {
            var name1=b.etname.text.toString().trim()
            var num1=b.etmobilee.text.toString().trim()
            var address1=b.etaddress.text.toString().trim()
            var pass1=b.etpassword.text.toString().trim()
            var city1=b.etcity.text.toString().trim()



            if(name1.isEmpty()){
                b.etname.error="Enter your name"
            }else if(num1.isEmpty()){
                b.etmobilee.error="Enter your Number"
            }else if(address1.isEmpty()){
                b.etaddress.error="Enter your Address"
            }else if(pass1.isEmpty()){
                b.etpassword.error="Enter your password"
            }else if(city1.isEmpty()){
                b.etcity.error="Enter your city"
            }else if(num1.isEmpty()){
                b.etmobilee.error="Enter your number properly"
            }else{
                contentResolver.openInputStream(uri!!)?.readBytes()?.let {
                    encoded= Base64.encodeToString(it, Base64.NO_WRAP)}
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.updateprofile(id,name1,num1,address1,city1,pass1,encoded,"update")
                        .enqueue(object: Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                Toast.makeText(this@profile, ""+t.message, Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(this@profile, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                    putString("num",num1)
                                    putString("pass",pass1)
                                    putString("email",email)
                                    putString("name",name1)
                                    putString("address",address1)
                                    putString("city",city1)
                                    putString("type","Users")
                                    putInt("id",id)
                                    putString("path",path)
                                    apply()
                                    finish()
                                    startActivity(Intent(this@profile,MainActivity::class.java).apply {
                                        putExtra("type","click")
                                    })
                                }
                            }
                        })
                }
            }

        }

    }
}