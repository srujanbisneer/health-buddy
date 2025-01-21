package com.healthbuddy

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.Style
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grihagully.model.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.healthbuddy.databinding.ActivityMainBinding
import com.healthbuddy.databinding.CardloginBinding
import com.ymts0579.model.model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val b by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private  val bind by lazy {
        CardloginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)


        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type", "")!!

        when(type){

            "Users"->{
                startActivity(Intent(this, UserDashboard::class.java))
                finish()
            }

            else->{
                b.linearregister.setOnClickListener {
                    startActivity(Intent(this,Register::class.java))
                    finish()
                }
            }
        }








        b.button.setOnClickListener {
            BottomSheetDialog(this,R.style.TransparentDialog).apply {
                (bind.root.parent as? ViewGroup)?.removeView(bind.root)
                setContentView(bind.root)


                bind.btnsignin.setOnClickListener {
                    val num=bind.etnum.text.toString().trim()
                    val pass=bind.etpassword.text.toString().trim()

                    if(num.isEmpty()){
                        bind.etnum.error="Enter your Contact Number"
                    }else if(pass.isEmpty()){
                        bind.etpassword.error="Enter your Password"
                    }else if(num.count()!=10){
                        bind.etnum.error="Enter your Contact Number properly"
                    }else{
                        CoroutineScope(Dispatchers.IO).launch {
                            RetrofitClient.instance.login(num,pass,"login")
                                .enqueue(object: Callback<LoginResponse> {
                                    override fun onResponse(
                                        call: Call<LoginResponse>, response: Response<LoginResponse>
                                    ) {
                                        if(!response.body()?.error!!){
                                            val type=response.body()?.user
                                            if (type!=null) {
                                                getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).edit().apply {
                                                    putString("num",type.num)
                                                    putString("pass",type.pass)
                                                    putString("email",type.email)
                                                    putString("name",type.name)
                                                    putString("address",type.address)
                                                    putString("city",type.city)
                                                    putString("type","Users")
                                                    putInt("id",type.id)
                                                    putString("path",type.path)
                                                    apply()
                                                }
                                                startActivity(Intent(this@MainActivity, UserDashboard::class.java))
                                                finish()
                                                dismiss()




                                                Toast.makeText(this@MainActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                                            }
                                        }else{
                                            Toast.makeText(this@MainActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                                        }

                                    }

                                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                        Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()


                                    }

                                })
                        }
                    }
                }

                show()
            }
        }

        if(intent.getStringExtra("type").toString()=="click"){
            b.button.performClick()
        }



    }
}