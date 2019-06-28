package com.neo.layersample

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body

interface ApiService {

    fun getIdentityToken(@Body body: RequestBody): Call<String>

}