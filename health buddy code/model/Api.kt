package com.healthbuddy.model
import com.example.skinsmart.model.Userresponse
import com.ymts0579.model.model.DefaultResponse
import com.ymts0579.model.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("users.php")
    fun register(
        @Field("name")name:String,
        @Field("num")num:String,
        @Field("email")email:String,
        @Field("address")address:String,
        @Field("city")city:String,
        @Field("pass")pass:String,
        @Field("path") path:String,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>




    @FormUrlEncoded
    @POST("users.php")
    fun login(@Field("num")num:String, @Field("pass") pass:String,
              @Field("condition") condition:String): Call<LoginResponse>


    @FormUrlEncoded
    @POST("users.php")
    fun updateprofile(
        @Field("id") id:Int,
        @Field("name")name:String,
        @Field("num")num:String,
        @Field("address")address:String,
        @Field("city")city:String,
        @Field("pass")pass:String,
        @Field("path") path:String,
        @Field("condition") condition:String,
    ): Call<DefaultResponse>





}