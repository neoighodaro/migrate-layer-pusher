package com.neo.layersample

import android.app.Application
import com.pusher.chatkit.CurrentUser
import com.pusher.chatkit.rooms.Room

class LayerSampleApp : Application(){

    lateinit var currentUser: CurrentUser
    lateinit var room: Room

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        lateinit var instance:LayerSampleApp
    }

}