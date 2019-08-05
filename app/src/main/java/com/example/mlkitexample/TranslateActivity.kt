package com.example.mlkitexample

import android.os.Bundle
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.translate.*


class TranslateActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.translate)
        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.EN)
            .setTargetLanguage(FirebaseTranslateLanguage.ES)
            .build()
        val englishSpanishTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)
        englishSpanishTranslator.downloadModelIfNeeded()
            .addOnSuccessListener {
                new_text.text = "Model Downloaded"

            }
            .addOnFailureListener { exception ->
                // Model couldn’t be downloaded or other internal error.
                // ...
                new_text.text = "Model Download Error"
            }
        translate_text.setOnClickListener{


            englishSpanishTranslator.translate(enter_text.text.toString())
                .addOnSuccessListener { translatedText ->
                    // Translation successful.
                    new_text.text = translatedText
                }
                .addOnFailureListener { exception ->
                    // Error.
                    // ...
                    new_text.text = "Not able to translate"
                }
        }

    }
}