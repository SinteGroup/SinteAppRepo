package com.example.sinteapp2

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageList = ArrayList<SlideModel>() // Create image list

// imageList.add(SlideModel("String Url" or R.drawable)
// imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title

        imageList.add(
            SlideModel(
                "https://bit.ly/2YoJ77H",
                "The animal population decreased by 58 percent in 42 years."
            )
        )
        imageList.add(
            SlideModel(
                "https://sintegroup.hu/wp-content/uploads/2022/12/udvozlet.png",
                "The animal population decreased by 58 percent in 42 years."
            )
        )

        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        imageSlider.setImageList(imageList)

        val playStopImageView=findViewById<ImageView>(R.id.playStopImageView)

        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                //Log.d("Click", "Double click")
                imageSlider.startSliding(3000)
                playStopImageView.setBackgroundColor(Color.GREEN)
                //Toast.makeText(this@MainActivity, "Start", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(position: Int) {
                //Log.d("Click", "Simple click")
                imageSlider.stopSliding()
                playStopImageView.setBackgroundColor(Color.RED)
                //Toast.makeText(this@MainActivity, "Stop", Toast.LENGTH_LONG).show()
            }

        })
    }
}