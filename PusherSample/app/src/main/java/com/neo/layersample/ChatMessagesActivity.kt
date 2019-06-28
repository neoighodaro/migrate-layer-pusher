package com.neo.layersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ChatMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_messages)

        LayerSampleApp.instance.currentUser.sendSimpleMessage(
            roomId = LayerSampleApp.instance.room.id,
            messageText = "Hey there!",
            callback = {


        })

    }

}