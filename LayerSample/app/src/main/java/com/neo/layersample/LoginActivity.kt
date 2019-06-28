package com.neo.layersample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.layer.sdk.LayerClient
import com.layer.sdk.exceptions.LayerException
import com.layer.sdk.listeners.LayerAuthenticationListener
import com.layer.sdk.listeners.LayerConnectionListener
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity(), LayerAuthenticationListener, LayerConnectionListener {

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

        LayerSampleApp.instance.layerClient = LayerClient.newInstance(this, "layer:///apps/staging/cf10048c-d9ab-11e5-b6a9-c01d00006542")
        LayerSampleApp.instance.layerClient.registerConnectionListener(this)
        LayerSampleApp.instance.layerClient.registerAuthenticationListener(this)

        loginButton.setOnClickListener {
            LayerSampleApp.instance.layerClient.connect()
        }


    }

    override fun onConnectionConnected(p0: LayerClient?) {
        LayerSampleApp.instance.layerClient.authenticate()
    }

    override fun onConnectionError(p0: LayerClient?, p1: LayerException?) {

    }

    override fun onConnectionDisconnected(p0: LayerClient?) {

    }

    override fun onAuthenticated(p0: LayerClient?, p1: String?) {
        LayerSampleApp.instance.userId = p1!!
        startActivity(Intent(this,ConversationsListActivity::class.java))
    }

    override fun onDeauthenticated(p0: LayerClient?) {

    }

    override fun onAuthenticationError(p0: LayerClient?, p1: LayerException?) {

    }

    override fun onAuthenticationChallenge(p0: LayerClient?, p1: String?) {
        fetchToken(p1!!)
    }

    private fun fetchToken(nonce:String){

        val reqObject = JSONObject()
        reqObject.put("user_id", username.text.toString())
        reqObject.put("nonce", nonce)

        val body =
            RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                reqObject.toString())

        retrofit.getIdentityToken(body).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.code()==200)
                    LayerSampleApp.instance.layerClient.answerAuthenticationChallenge(JSONObject(response.body()!!)
                        .getString("identity_token"))
            }

        })

    }


}
