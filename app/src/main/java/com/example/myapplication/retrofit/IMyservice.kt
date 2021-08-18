package com.example.myapplication.retrofit

import  io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.*

interface IMyservice {
    @POST(value= "user/register")
    @FormUrlEncoded
    fun registerUser(@Field(value="fullname") fullname:String,
                      @Field( value="email") email: String,
                     @Field(value = "mobile") mobile: String,
                     @Field(value = "password") password: String,
                     @Field(value = "address") address: String,
                     ):Observable<String>


    @POST(value= "user/login")
    @FormUrlEncoded
    fun loginUser(
                     @Field( value="email") email: String,
                     @Field(value = "password") password: String):Observable<String>


}