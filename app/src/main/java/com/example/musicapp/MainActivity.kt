package com.example.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var myRecyclerview:RecyclerView

         lateinit var myAdapter:MyAdapter


myRecyclerview=findViewById(R.id.recyclerView)

        val retrofitBuilder=Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData=retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<Mydata?> {
            override fun onResponse(call: Call<Mydata?>, response: Response<Mydata?>) {
//if the API call is success this method is executed
val datalist=response. body()?.data!!
//  val textView=findViewById<TextView>(R.id.helloText)
//                textView.text=datalist.toString()
          myAdapter= MyAdapter(this@MainActivity,datalist)
          myRecyclerview.adapter=myAdapter
           myRecyclerview.layoutManager=LinearLayoutManager(this@MainActivity)

                Log.d("onResponse", "onResponse: "+response.body())
            }

            override fun onFailure(call: Call<Mydata?>, t: Throwable) {
//if the API call is failure then this method is executed
                Log.d("onFailure", "onFailure: "+t.message)

            }
        })
    }


}