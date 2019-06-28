package com.neo.layersample

import android.app.Application
import com.layer.sdk.LayerClient
import com.layer.sdk.messaging.Conversation

class LayerSampleApp : Application(){

    lateinit var layerClient: LayerClient
    lateinit var conversation: Conversation
    var userId: String = ""

    override fun onCreate() {
        super.onCreate()
        LayerClient.applicationCreated(this)
        instance = this

    }

    companion object {
        lateinit var instance:LayerSampleApp
    }

}