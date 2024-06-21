package com.example.sinteapp2

import android.app.Notification
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.inflate
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "MIDrawerActivity"
    private var slideType = 0
    private lateinit var activityDataBinding: DataBindingUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageList = ArrayList<SlideModel>() // Create image list
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        val playStopImageView = findViewById<ImageView>(R.id.playStopImageView)


        var fileokLista=""

        val insertDataQueqe = Volley.newRequestQueue(this)
        val domain="http://195.228.220.2:65535/"
        val url =domain+"script/getFiles.php"

        val insertDataStringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->Log.d("Szerver_response", response)
                                      fileokLista=response
                                      val fileTempCsakhogyLegyen=fileokLista.split("\n")
                                      for (fileBelsoTempCsakhogyBonyiLegyen in fileTempCsakhogyLegyen){
                                          Log.d("Szerver_Belso", domain+"kepek/$fileBelsoTempCsakhogyBonyiLegyen")
                                          imageList.add(
                                              SlideModel(
                                                  domain+"kepek/$fileBelsoTempCsakhogyBonyiLegyen",
                                                  "Nem kell title"
                                              )
                                          )
                                      }
                                        imageSlider.setImageList(imageList)
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
                                      },

            Response.ErrorListener { error-> Log.d("Szerver_error", error.message.toString())
                                    Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG).show()})
        insertDataQueqe.add(insertDataStringRequest)

        val timer = object: CountDownTimer(1800000, 1800000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("Timer____", millisUntilFinished.toString())
                imageList.removeAll(imageList.toSet())
                insertDataQueqe.add(insertDataStringRequest)
            }
            override fun onFinish() {
                Log.d("Timer_Finish", "Finish_onStart")
                super.start()
            }
        }
        timer.start()
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}