package com.neo.layersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.layer.sdk.listeners.LayerProgressListener
import com.layer.sdk.messaging.MessagePart

class ChatMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_messages)

        val messageText = "Hi! How are you"
        val messagePart = LayerSampleApp.instance.layerClient.newMessagePart("text/plain", messageText.toByteArray())

        LayerSampleApp.instance.conversation.send(messagePart?.message, object : LayerProgressListener {
            override fun onProgressUpdate(p0: MessagePart?, p1: LayerProgressListener.Operation?, p2: Long) {

            }

            override fun onProgressStart(p0: MessagePart?, p1: LayerProgressListener.Operation?) {

            }

            override fun onProgressComplete(p0: MessagePart?, p1: LayerProgressListener.Operation?) {

            }

            override fun onProgressError(p0: MessagePart?, p1: LayerProgressListener.Operation?, p2: Throwable?) {

            }

        })

    }

}