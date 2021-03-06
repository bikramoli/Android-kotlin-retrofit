package com.example.myapplication.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private  var instance: Retrofit?=null

    fun getInstance():Retrofit{
        if (instance == null)
            instance = Retrofit.Builder()
                .baseUrl("http://172.20.10.8:8080/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return instance!!

    }
}