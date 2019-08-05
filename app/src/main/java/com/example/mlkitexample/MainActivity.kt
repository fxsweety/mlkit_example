package com.example.mlkitexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        face_detection?.setOnClickListener {
            val intent = Intent(this, FaceDetection::class.java)
            startActivity(intent)
        }
        vision_edge?.setOnClickListener{
            val intent = Intent(this, StillImageActivity::class.java)
            startActivity(intent)
        }
        translate?.setOnClickListener{
            val intent = Intent(this, TranslateActivity ::class.java)
            startActivity(intent)
        }
        bar_code?.setOnClickListener{
            val intent = Intent(this, BarCodeScanning::class.java)
            startActivity(intent)
        }
        smart_reply?.setOnClickListener{
            val intent = Intent(this, SmartReplyActivity::class.java)
            startActivity(intent)
        }
        }
}
