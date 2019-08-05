package com.example.mlkitexample

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.io.File
import android.content.Intent
import android.graphics.Bitmap
import android.app.Activity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions


class FaceDetection : BaseActivity() {
    private var currentPhotoFile: File? = null
    private var imagePreview: ImageView? = null
    private var textView: TextView? = null
    private val CAMERA_REQUEST = 1888
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_still_image)
        imagePreview = findViewById(R.id.image_preview)
        textView = findViewById(R.id.result_text)
        takePhoto()
    }

    fun takePhoto() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val picture =
                data?.extras?.get("data") as Bitmap
                imagePreview?.setImageBitmap(picture)
            val image = FirebaseVisionImage.fromBitmap(picture)
            val options = FirebaseVisionFaceDetectorOptions.Builder()
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setMinFaceSize(0.15f)
                .enableTracking()
                .build()
            val detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options)

            val result = detector.detectInImage(image)
                .addOnSuccessListener { faces ->
                    // Task completed successfully
                    // ...
                    if(faces.isEmpty()) {
                        textView?.text = "No Face Detected"
                    } else {
                        textView?.text = "Face Detected"
                    }
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...

                }
        }
    }
}