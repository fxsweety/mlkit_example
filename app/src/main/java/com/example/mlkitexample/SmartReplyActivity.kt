package com.example.mlkitexample

import android.os.Bundle
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.smartreply.FirebaseTextMessage
import com.google.firebase.ml.naturallanguage.smartreply.SmartReplySuggestionResult
import kotlinx.android.synthetic.main.smart_reply.*

class SmartReplyActivity: BaseActivity() {

    var userId:String ?= "123"
    var conversation = arrayListOf<FirebaseTextMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.smart_reply)

        val smartReply = FirebaseNaturalLanguage.getInstance().smartReply
        enter.setOnClickListener{
            conversation.add(
                FirebaseTextMessage.createForRemoteUser(
                    enter_text.text.toString(), System.currentTimeMillis(), userId))

            smartReply.suggestReplies(conversation)
                .addOnSuccessListener { result ->
                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        // The conversation's language isn't supported, so the
                        // the result doesn't contain any suggestions.
                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS && result.suggestions.size == 3) {
                        text1.text = result.suggestions[0].text
                        text2.text = result.suggestions[1].text
                        text3.text = result.suggestions[2].text
                    }
                }
                .addOnFailureListener {
                    // Task failed with an exception
                    // ...
                }

        }

        text1.setOnClickListener{
            conversation.add( FirebaseTextMessage.createForLocalUser(text1.text.toString(),  System.currentTimeMillis()))
        }

        text2.setOnClickListener{
            conversation.add( FirebaseTextMessage.createForLocalUser(text2.text.toString(),  System.currentTimeMillis()))
        }

        text3.setOnClickListener{
            conversation.add( FirebaseTextMessage.createForLocalUser(text3.text.toString(),  System.currentTimeMillis()))
        }





    }
}