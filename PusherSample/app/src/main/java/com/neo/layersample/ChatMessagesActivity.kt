package com.neo.layersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pusher.chatkit.rooms.RoomListeners
import com.pusher.util.Result

class ChatMessagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_messages)


        val room = PusherSampleApp.instance.room
        PusherSampleApp.instance.currentUser.subscribeToRoomMultipart(
            roomId = room.id,
            listeners = RoomListeners(
                onMultipartMessage = {
                    runOnUiThread {
                        // add message to adapter
                    }
                }
            ),
            messageLimit = 20, // Optional
            callback = { subscription ->
                // Called when the subscription has started.
                // You should terminate the subscription with subscription.unsubscribe()
                // when it is no longer needed
            }
        )

        PusherSampleApp.instance.currentUser.sendSimpleMessage(
            roomId = PusherSampleApp.instance.room.id,
            messageText = "Hey there!",
            callback = {


        })

        PusherSampleApp.instance.currentUser.fetchMultipartMessages(
            roomId = PusherSampleApp.instance.room.id,
            callback = {
                when (it){
                    is Result.Success -> {
                        var messageList = it.value
                    }

                }

            }
        )

    }

}