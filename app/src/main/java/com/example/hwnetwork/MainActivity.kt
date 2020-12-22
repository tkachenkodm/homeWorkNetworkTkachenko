package com.example.hwnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()

        val getFacts = retrofit.create(GetCatFacts::class.java)
        getFacts.getCatFacts().enqueue(
                object: Callback<List<CatFact>> {
                    override fun onResponse(call: Call<List<CatFact>>, response: Response<List<CatFact>>) {
                        if(response.code() == 200){
                            responseText.text = response.body().toString()
                            println(response.body().toString())
                        } else {
                            println("Request failed")
                        }
                    }

                    override fun onFailure(call: Call<List<CatFact>>, t: Throwable) {
                        println("Request failed")
                    }
                }
        )
    }
}

interface GetCatFacts {
    @GET("/facts")
    fun getCatFacts(): Call<List<CatFact>>
}