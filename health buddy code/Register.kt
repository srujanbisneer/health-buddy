package com.healthbuddy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grihagully.model.RetrofitClient
import com.healthbuddy.databinding.ActivityRegisterBinding
import com.ymts0579.model.model.DefaultResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private val b by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)


        b.btnsubmit.setOnClickListener {
            val name=b.etname.text.toString().trim()
            val num=b.etmobilee.text.toString().trim()
            val address=b.etaddress.text.toString().trim()
            val email=b.etemail.text.toString().trim()
            val pass=b.etpassword.text.toString().trim()
            val city=b.etcity.text.toString().trim()


            if(name.isEmpty()){
                b.etname.error="Enter your name"
            }else if(num.isEmpty()){
                b.etmobilee.error="Enter your number"
            }else if(address.isEmpty()){
                b.etaddress.error="Enter your address"
            }else if(email.isEmpty()){
                b.etemail.error="Enter your email"
            }else if(pass.isEmpty()){
                b.etpassword.error="Enter your password"
            }else if(num.count()!=10){
                b.etmobilee.error="Enter your number properly"
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    RetrofitClient.instance.register(name,num,email,address,city,pass,"","register")
                        .enqueue(object: Callback<DefaultResponse> {
                            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                Toast.makeText(this@Register, ""+t.message, Toast.LENGTH_SHORT).show()
                            }
                            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                                Toast.makeText(this@Register, "${response.body()!!.message}", Toast.LENGTH_SHORT).show()
                                b.etname.text.clear()
                                b.etmobilee.text.clear()
                                b.etemail.text.clear()
                                b.etaddress.text.clear()
                                b.etcity.text.clear()
                                b.etpassword.text.clear()
                                startActivity(Intent(this@Register,MainActivity::class.java).apply {
                                    putExtra("type","click")
                                })
                            }
                        })
                }

            }
        }

    }
}