package com.healthbuddy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.skinsmart.model.logout
import com.healthbuddy.chatbot.ChatActivity
import com.healthbuddy.databinding.ActivityUserDashboardBinding
import com.healthbuddy.model.CurvedBackgroundDrawable

class UserDashboard : AppCompatActivity() {
    private  val b  by lazy {
        ActivityUserDashboardBinding.inflate(layoutInflater)
    }
    var name=""
    var path=""
    private lateinit var p: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.linear.background=CurvedBackgroundDrawable()


        getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE).apply {
            name= getString("name","").toString()
            path=getString("path","").toString()
        }

        val builder = AlertDialog.Builder(this,R.style.TransparentDialog)
        val inflater = this.layoutInflater
        builder.setView(inflater.inflate(R.layout.progressdialog, null))

        p = builder.create()
        p.dismiss()


        if(path.isNotEmpty()){
            Glide.with(this).load(Uri.parse(path.trim())).into(b.imagadd)
            p.dismiss()
        }

        b.tvname.text="Welcome "+name



        b.btnlogout.setOnClickListener { logout() }
        b.btndiet.setOnClickListener { startActivity(Intent(this,ChatActivity::class.java)) }
        b.btnwaterlevel.setOnClickListener { startActivity(Intent(this,waterlevelActivity::class.java)) }
        b.linearbmi.setOnClickListener { startActivity(Intent(this,Bmicalculator::class.java)) }
        b.btnvitamindetection.setOnClickListener { startActivity(Intent(this,ViatminDectection::class.java)) }
        b.imagadd.setOnClickListener { startActivity(Intent(this,profile::class.java)) }
        b.activity.setOnClickListener { startActivity(Intent(this,ExerciseActivity::class.java)) }

    }
}