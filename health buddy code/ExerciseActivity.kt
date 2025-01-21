package com.healthbuddy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthbuddy.databinding.ActivityExerciseBinding
import com.healthbuddy.databinding.CardfitnessBinding

class ExerciseActivity : AppCompatActivity() {
    private val b by lazy {
        ActivityExerciseBinding.inflate(layoutInflater)
    }
    var link = ArrayList<String>()
    var title = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)

        link.add("https://youtu.be/sWfNosruPPw?si=NY4Xvx7cay506270")
        link.add("https://youtu.be/7EX1Xnvvk5c?si=1pxStLuCPsfQ6ko1")
        link.add("https://youtu.be/r4MzxtBKyNE?si=ed2lF9ZC1lHnio8M")
        link.add("https://youtu.be/r4MzxtBKyNE?si=qUzDgOlKHqCwheN9")
        link.add("https://youtu.be/rT7DgCr-3pg?si=NQTfoI66AMshaYtC")
        link.add("https://youtu.be/eGo4IYlbE5g?si=0CagkmWJyDvU0MRv")
        link.add("https://youtu.be/rep-qVOkqgk?si=tz76nIlYAcd7zQxa")
        link.add("https://youtu.be/_UvmPNGtlPM?si=M-Ep1k9epF9cvNNd")
        link.add("https://youtu.be/wrwwXE_x-pQ?si=u9Rck-jtYnrAHqoD")
        link.add("https://youtu.be/IODxDxX7oi4?si=S9mQx8DvdGNkXHVt")
        link.add("https://youtu.be/M2rwvNhTOu0?si=3NDM9UeyIrH9vGsF")
        link.add("https://youtu.be/4re6CJ0XNF8?si=t5LW7GswYiZwJRCv")
        link.add("https://youtu.be/QZEqB6wUPxQ?si=FvCt5aDqqvV5opRz")
        link.add("https://youtu.be/kBWAon7ItDw?si=IhK2woKO_eVoAZAL")
        title.add("How to Reduce your Depression")
        title.add("How to manage stress and anxiety")
        title.add("Squat")
        title.add("Deadlift")
        title.add("Bench Press")
        title.add("Pullup")
        title.add("Face Pull")
        title.add("Banded External Rotation")
        title.add("Lunge")
        title.add ("Pushup")
        title.add("Overhead Press")
        title.add("The Lying Triceps Extension")
        title.add("Barbell Curl")
        title.add("Barbell Row")

        b.cycle.let {
            it.adapter=fitnessadapter(this,link,title)
            it.layoutManager= LinearLayoutManager(this)
        }


    }

    class fitnessadapter(var context: Context, var links: ArrayList<String>, var title:ArrayList<String>):
        RecyclerView.Adapter<fitnessadapter.DataViewHolder>(){

        class DataViewHolder(val view: CardfitnessBinding) : RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
            return DataViewHolder(CardfitnessBinding.inflate(LayoutInflater.from(context),parent,false))
        }

        override fun onBindViewHolder(holder: DataViewHolder, @SuppressLint("RecyclerView") position:Int) {
            with(holder.view){

                tvname.text=title[position]
                imgyoutube.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(links[position]))
                    context.startActivity(intent)

                }

            }


        }

        override fun getItemCount() = title.size
    }
}