package com.healthbuddy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.healthbuddy.databinding.ActivityWaterlevelBinding

class waterlevelActivity : AppCompatActivity() {
    private  val b by lazy {
        ActivityWaterlevelBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


        b.btnwaterlevel.setOnClickListener {
            var weight=b.etweight.text.toString()
            var height=b.etheight.text.toString()
            var waterlevel=b.etwater.text.toString()


            if(weight.isEmpty()){
                b.etweight.error="Enter your weight"
            }else if(height.isEmpty()){
                b.etheight.error="Enter your height"
            }else if(waterlevel.isEmpty()){
                b.etwater.error="Enter your water intake"
            } else{
              val result= calculateWaterIntake(weight.toDouble(), height.toDouble(), waterlevel.toDouble())
                val suggestedWaterIntake = calculateWaterIntake(weight.toDouble(), height.toDouble())

                b.tvsuggestions.text=result+"\n"+
                        "Suggested daily water intake: %.2f liters".format(suggestedWaterIntake)
            }
        }

    }

    fun calculateWaterIntake(weight: Double, height: Double): Double {
        // General recommendation: 30-35 ml of water per kg of body weight
        val waterPerKg = 35  // Using 35 ml per kg of body weight as the upper range
        val waterIntakeInMl = weight * waterPerKg  // in milliliters
        val waterIntakeInLiters = waterIntakeInMl / 1000  // convert to liters

        // You can also use height to modify the result slightly if you wish.
        // A simplistic adjustment might be based on a height factor (though it's not as common as weight).
        val heightFactor = height / 150  // normalize height (considering 150cm as base)
        val adjustedWaterIntake = waterIntakeInLiters * heightFactor

        return adjustedWaterIntake
    }


    fun calculateWaterIntake(weight: Double, height: Double, actualIntake: Double): String {
        // Calculate the ideal water intake (in liters) based on weight (30 to 35 ml per kg)
        val idealIntakeLow = (weight * 30) / 1000  // 30 ml per kg
        val idealIntakeHigh = (weight * 35) / 1000  // 35 ml per kg

        // Provide suggestions based on comparison with actual intake
        return when {
            actualIntake < idealIntakeLow -> {
                 "You should drink more water. Your ideal intake is between $idealIntakeLow liters and $idealIntakeHigh liters per day."
            }
            actualIntake in idealIntakeLow..idealIntakeHigh -> {
                "You're drinking an adequate amount of water. Keep it up!"
            }
            else -> {
                "You may be drinking more water than needed. Your ideal intake is between $idealIntakeLow liters and $idealIntakeHigh liters per day."
            }
        }
    }


}