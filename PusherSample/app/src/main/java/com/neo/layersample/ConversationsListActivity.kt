package com.neo.layersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pusher.chatkit.rooms.Room
import kotlinx.android.synthetic.main.activity_conversations_list.*


class ConversationsListActivity : AppCompatActivity(), ConversionListAdapter.OnConversationSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations_list)

        val list = LayerSampleApp.instance.currentUser.rooms
        val conversationAdapter = ConversionListAdapter(this)

        conversationAdapter.setList(list)

        with(recyclerViewConversation){
            layoutManager = LinearLayoutManager(this@ConversationsListActivity)
            adapter = conversationAdapter
        }


    }

    override fun conversationSelected(room: Room) {
        LayerSampleApp.instance.room = room
        startActivity(Intent(this,ChatMessagesActivity::class.java))
    }



}
