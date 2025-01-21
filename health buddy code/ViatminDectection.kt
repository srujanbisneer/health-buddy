package com.healthbuddy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.healthbuddy.databinding.ActivityViatminDectectionBinding

import com.healthbuddy.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.text.DecimalFormat


class ViatminDectection : AppCompatActivity() {
    private val b by lazy {
        ActivityViatminDectectionBinding.inflate(layoutInflater)
    }
    private val model by lazy {
        Model.newInstance(this)
    }

    private val activity =   registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
        it.data?.data?.let { uri ->
            contentResolver.openInputStream(uri)?.use {
                BitmapFactory.decodeStream(it)
            }?.let {
                b.imageview.setImageBitmap(it)
                functions(it)
            }
        }
    }




    private val camera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.data?.extras?.let { it1 ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it1.getParcelable("data", Bitmap::class.java)
                } else {
                    it1.getParcelable("data")
                }?.let {
                    b.imageview.setImageBitmap(it)
                    functions(it)

                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(b.root)

        val image = BitmapFactory.decodeResource(
            resources, R.drawable.blue
        )

        functions(image)

        b.imgcamera.setOnClickListener {
            camera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        b.imggallery.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                activity.launch(this)
            }
        }

    }

    private fun functions(image: Bitmap) {
        val real = Bitmap.createScaledBitmap(image, 224, 224, true)
        val buffer = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
        val image = TensorImage(DataType.UINT8)
        image.load(real)
        buffer.loadBuffer(image.buffer)
        val out = model.process(buffer)
        val array = arrayOf(
            "Acral Lentiginous Melanoma",
            "Beaus Line",
            "Blue Finger",
            "Clubbing",
            "Koilonychia",
            "lip and tongue",
            "Muehrckes Lines",
            "Onychogryphosis",
            "Pitting",
            "Terry Nail"
        )
        var num = 0.0f
        var string = ""
        var number = 0f
        val k = out.outputFeature0AsTensorBuffer.floatArray

        k.forEachIndexed { index, fl: Float ->

            if (num < fl) {
                num = fl
                string = array[index]
            }
            number += fl

        }
        val decimal = DecimalFormat("##.##")
        b.tvusername.text = "$string : (${decimal.format((num / number) * 100)}%)"
        readname(string)

    }

    private fun readname(name: String) {
        if(name== "Acral Lentiginous Melanoma" ){b.tvname.text="Acral lentiginous melanoma is a type of skin cancer that primarily occurs on the palms, soles, and under the nails. Early detection is crucial, as it can be aggressive and often presents as a dark, irregularly shaped lesion. Regular skin examinations and awareness of any changes in pigmentation or texture are essential for prompt diagnosis. Treatment typically involves surgical excision, and advanced cases may require additional therapies like immunotherapy or chemotherapy."}
        else if(name== "Beaus Line"){b.tvname.text="Beau's lines are transverse indentations or grooves that appear on the nails, often indicating a temporary interruption in nail growth. These lines can result from various conditions, including trauma, severe illness, or systemic diseases like diabetes or cardiovascular issues. Identifying the underlying cause is crucial for effective management. Treatment involves addressing the underlying health issue, as the lines will grow out as the nail continues to grow."}
        else if(name== "Blue Finger"){b.tvname.text="Blue finger, or cyanosis, refers to a bluish discoloration of the fingers due to insufficient oxygen in the blood. It can be caused by various conditions, including Raynaud's phenomenon, peripheral artery disease, or cold exposure. Prompt evaluation by a healthcare professional is essential to determine the underlying cause and initiate appropriate treatment. Management may include lifestyle modifications, medications, or interventions to improve blood flow."}
        else if(name== "Clubbing"){b.tvname.text="Clubbing is characterized by the enlargement of the fingertips and a downward curvature of the nails, often associated with chronic hypoxia. It can be indicative of various underlying conditions, such as lung diseases (e.g., cystic fibrosis, lung cancer) or cardiovascular disorders. Identifying and treating the underlying condition is essential for managing clubbing. Regular monitoring and follow-up with a healthcare provider are recommended to track changes in nail shape."}
        else if(name== "Koilonychia"){b.tvname.text="Koilonychia, or spoon-shaped nails, is a condition where the nails curve upwards at the edges, resembling a spoon. It is often associated with iron deficiency anemia, hypothyroidism, or other systemic conditions. Diagnosis typically involves blood tests to identify underlying nutritional deficiencies or systemic diseases. Treatment focuses on correcting the underlying issue, such as iron supplementation for anemia, to restore normal nail shape over time."}
        else if(name== "lip and tongue"){b.tvname.text="Lesions or abnormalities on the lips and tongue can indicate various conditions, including oral thrush, leukoplakia, or infections. Symptoms may include soreness, discoloration, or changes in texture. A thorough examination by a healthcare professional is crucial for accurate diagnosis and treatment. Management may involve antifungal medications, lifestyle modifications, or further investigation if lesions persist or change."}
        else if(name== "Muehrckes Lines"){b.tvname.text="Muehrcke's lines are white transverse lines that appear on the nails, often associated with low serum protein levels or liver disease. Unlike Beau's lines, Muehrcke's lines do not grow out with the nail but remain fixed. Identifying the underlying cause, such as malnutrition or liver dysfunction, is essential for effective treatment. Addressing the root cause can help improve overall health and potentially resolve the appearance of the lines."}
        else if(name== "Onychogryphosis"){b.tvname.text="Onychogryphosis, or ram's horn nails, is characterized by thickened, curved nails that resemble the horns of a ram. It can result from neglect, trauma, or underlying conditions like psoriasis or fungal infections. Proper nail care and hygiene are crucial for management, along with identifying and treating any underlying issues. In severe cases, surgical intervention may be necessary to correct the nail deformity."}
        else if(name== "Pitting"){b.tvname.text="Pitting refers to small depressions or indentations on the surface of the nails, commonly associated with psoriasis, eczema, or alopecia areata. The presence of pits can indicate underlying skin conditions and may vary in severity. Treatment typically involves managing the associated skin condition and may include topical treatments or systemic therapies. Regular follow-up with a healthcare provider is essential for monitoring changes and adjusting treatment as needed."}
        else  if(name== "Terry Nail"){
            b.tvname.text="Terry's nails are characterized by a white appearance with a narrow pink band at the tip, often indicative of systemic conditions such as liver disease, heart failure, or diabetes. The change in nail coloration reflects underlying health issues and requires thorough evaluation. Management focuses on diagnosing and treating the underlying condition, which may improve the appearance of the nails over time. Regular monitoring and consultations with healthcare providers are recommended for ongoing assessment."
        }




    }
}