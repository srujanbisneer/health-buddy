package com.healthbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.healthbuddy.databinding.ActivityBmicalculatorBinding

class Bmicalculator : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private  val b by lazy {
        ActivityBmicalculatorBinding.inflate(layoutInflater)
    }

    lateinit var spin: Spinner
    var type1= arrayOf("little or no exercise","exercise 1–3 days/week","exercise 3–5 days/week",
        "exercise 6–7 days/week","hard exercise 6–7 days/week")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        spin=findViewById(R.id.amrspinner)
        val ad = ArrayAdapter<Any?>(this, android.R.layout.simple_dropdown_item_1line, type1)
        spin.adapter = ad
        spin.onItemSelectedListener = this


        b.btncalculate.setOnClickListener {
            var h=b.etheight.text.toString().toFloat()
            var w=b.etweigth.text.toString().toInt()
            var a=b.etage.text.toString().toInt()
            var hh=(h*h)/10000
            var rr=w/hh


            if(b.etsbp.text.toString().toInt()<120 && b.etdbp.text.toString().toInt()<80){
                b.tvBp.setText("your Blood pressure Normal")
            }else if(b.etsbp.text.toString().toInt() >=129 && b.etsbp.text.toString().toInt()<120  &&b.etdbp.text.toString().toInt()<80 ){
                b.tvBp.setText("your Blood pressure Elevated")
            }else if(b.etsbp.text.toString().toInt() >=130   &&b.etdbp.text.toString().toInt()>=80 ) {
                b.tvBp.setText("your Blood pressure high")
            }else if((b.etsbp.text.toString().toInt()<120 && b.etdbp.text.toString().toInt()<80)){
                b.tvBp.setText("your Blood pressure Low")
            }
            if(rr<18.5){
                b.tvbmi.setText("BMI $rr ")
                b.tvclass.setText("underweight")
            }else if(rr>18.5 && rr<24.9){
                b.tvbmi.setText("BMI $rr ")
                b.tvclass.setText("Normal")
            }else if(rr>25 && rr<29.9){
                b.tvbmi.setText("BMI $rr")
                b.tvclass.setText("overweight")
            }else if(rr>30 && rr<34.9){
                b.tvbmi.setText("BMI $rr")
                b.tvclass.setText("obesity(Class1)")
            }else if(rr>35 && rr<39.9){
               b. tvbmi.setText("BMI $rr")
                b.tvclass.setText("obesity(Class2)")
            }else if(rr>40.9){
               b. tvbmi.setText("BMI $rr")
                b.tvclass.setText("Extremeobesity")
            }



            if(b.rdmale.isChecked){
                val bmr= 66.47 + (13.75 *w) + (5.003 * h) - (6.755 * a)
                if(b.tvspinn.text=="little or no exercise"){
                    val r1=bmr* 1.2
                    b.tvcal.setText("Calories: $r1" )
                }else if(b.tvspinn.text=="exercise 1–3 days/week"){
                    val r1=bmr*1.375
                    b.tvcal.setText(" Calories: $r1" )
                }else if(b.tvspinn.text=="exercise 3–5 days/week"){
                    val r1=bmr*1.55
                    b.tvcal.setText("Calories: $r1" )
                }else if(b.tvspinn.text=="exercise 6–7 days/week"){
                    val r1=bmr*1.725
                    b.tvcal.setText("Calories: $r1" )
                }else if(b.tvspinn.text=="hard exercise 6–7 days/week"){
                    val r1=bmr*1.9
                    b.tvcal.setText("Calories: $r1" )
                }
            }else if(b.rdfemale.isChecked){
                var bmr=(655.1 + (9.563 *w) + (1.850 *h) - (4.676 * a))
                if(b.tvspinn.text=="little or no exercise"){
                    val r1=bmr* 1.2
                    b.tvcal.setText("Calories: $r1" )
                }else if(b.tvspinn.text=="exercise 1–3 days/week"){
                    val r1=bmr*1.375
                    b.tvcal.setText(" Calories: $r1" )
                }else if(b.tvspinn.text=="exercise 3–5 days/week"){
                    val r1=bmr*1.55
                    b.tvcal.setText("Calories: $r1" )
                }else if(b.tvspinn.text=="exercise 6–7 days/week"){
                    val r1=bmr*1.725
                    b.tvcal.setText("Calories: $r1" )
                }else if(b.tvspinn.text=="hard exercise 6–7 days/week"){
                    val r1=bmr*1.9
                    b.tvcal.setText("Calories: $r1" )
                }
            }


        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if(p0!!.id == R.id.amrspinner){
            if(type1[p2]=="exercise 1–3 days/week"){
               b. tvspinn.setText("exercise 1–3 days/week")

            }else if(type1[p2]=="exercise 3–5 days/week"){
                b.tvspinn.setText("exercise 3–5 days/week")
            }else if(type1[p2]=="exercise 6–7 days/week"){
                b.tvspinn.setText("exercise 6–7 days/week")
            }else if(type1[p2]=="hard exercise 6–7 days/week"){
                b.tvspinn.setText("hard exercise 6–7 days/week")
            }else if(type1[p2]=="little or no exercise"){
                b.tvspinn.setText("little or no exercise")
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}