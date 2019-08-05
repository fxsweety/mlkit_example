package com.example.mlkitexample

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage

class BarCodeScanning : BaseActivity() {

    private val CAMERA_REQUEST = 1888
    private var imagePreview: ImageView? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_still_image)
        imagePreview = findViewById(R.id.image_preview)
        textView = findViewById(R.id.result_text)
        takePhoto()

    }

    fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val picture =
                data?.extras?.get("data") as Bitmap
            imagePreview?.setImageBitmap(picture)

            val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build()

            val detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options)

            val image = FirebaseVisionImage.fromBitmap(picture)

            val result = detector.detectInImage(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        when (barcode.valueType) {
                            FirebaseVisionBarcode.TYPE_WIFI -> {
                                val ssid = barcode.wifi!!.ssid
                                val password = barcode.wifi!!.password
                                textView?.text = "RawValue: " + barcode.rawValue + "\n" + "ssid: " + ssid + "\n" +
                                        "password: " + password
                            }
                        }

                    }
                }
                .addOnFailureListener {

                }
        }
    }

}