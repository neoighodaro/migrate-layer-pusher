package com.neo.layersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.layer.sdk.messaging.Conversation
import kotlinx.android.synthetic.main.activity_conversations_list.*


class ConversationsListActivity : AppCompatActivity(), ConversionListAdapter.OnConversationSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations_list)

        val list = LayerSampleApp.instance.layerClient.conversations
        val conversationAdapter = ConversionListAdapter(this)

        conversationAdapter.setList(list)

        with(recyclerViewConversation){
            layoutManager = LinearLayoutManager(this@ConversationsListActivity)
            adapter = conversationAdapter
        }


    }

    override fun conversationSelected(conversation: Conversation) {
        LayerSampleApp.instance.conversation = conversation
        startActivity(Intent(this,ChatMessagesActivity::class.java))
    }

}
