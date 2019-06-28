package com.neo.layersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.pusher.chatkit.AndroidChatkitDependencies
import com.pusher.chatkit.ChatManager
import com.pusher.chatkit.ChatkitTokenProvider
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()))
        .client(
            OkHttpClient.Builder()
                .build())
        .build()
        .create(ApiService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {

            val chatManager = ChatManager(
                instanceLocator = "v1:us1:ffa1e6ad-5dba-40a8-9d2a-a2868a8879bb",
                userId = username.text.toString(),
                dependencies = AndroidChatkitDependencies(
                    tokenProvider = ChatkitTokenProvider(
                        endpoint = "http://10.0.2.2:3000/token",
                        userId = username.text.toString()
                    )
                )
            )

            chatManager.connect { result ->
                when (result) {
                    is com.pusher.util.Result.Success -> {
                        // result.value
                        LayerSampleApp.instance.currentUser = result.value
                        startActivity(Intent(this@LoginActivity, ConversationsListActivity::class.java))
                    }

                    is com.pusher.util.Result.Failure -> {


                    }

                }
            }

        }


    }


}
