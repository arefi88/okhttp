package com.example.testokhttpandretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    companion object {
       const val TAG ="MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val okHttpClient = OkHttpClient()
        val gson= Gson()
        val userReq=UserReq("")
        val req=gson.toJson(userReq)
        val requestBody= RequestBody.create("application/json;charset=utf-8".toMediaType(),req)
//       val requestBody=RequestBody.create("application/json;charset=utf-8".toMediaType(),"{\"blog\":\"mohserafshar.ir\"}")
       val request=Request.Builder()
            .url("https://api.github.com/users/mohsenafshar")
           //.addHeader("key","token")
           .post(requestBody)
            .build()

       val executor = Executors.newSingleThreadExecutor()

        executor.submit {
            try {
                Log.d(TAG,"onCreate:${Thread.currentThread().name}")
                val response= okHttpClient.newCall(request).execute()
                Log.d(TAG,"onCreate:${response.body?.string()}")
                val result = response.body?.string()
               val user=gson.fromJson(result,User::class.java)
                Log.d(TAG,"onCreate: ${user.login}")
            }catch (e: IOException){
                Log.d(TAG,"onCreate: ${e.message}")
            }

        }

       //getUserAsync()
    }
    private fun getUserAsync() {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.github.com/users/mohsenafshar")
            .build()
        Log.d(TAG,"getUserDataAsync: before enqueue")
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG,"onCreate: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG,"onCreate: ${response.body?.string()}")
            }

        })
        Log.d(TAG,"getUserDataAsync: after enqueue")
    }
}