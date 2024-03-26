package com.example.newsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class APIManager private constructor(){

    companion object{
        private var INSTANCE:Retrofit?=null

        private fun getInstance():Retrofit{
            if (INSTANCE==null)
                INSTANCE=Retrofit
                    .Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return INSTANCE!!
        }

        fun getNewsServices():NewsServices{
            return getInstance().create(NewsServices::class.java)
        }
    }
}