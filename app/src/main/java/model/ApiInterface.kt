package model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("{cardNUmber}")

    fun getCardInfo(
        @Path("cardNUmber") cardNUmber: String?
    ): Call<CardInfo>

    //http://api.weatherapi.com/v1/current.json?key=f84c1a8ad2f04fa79cb85013222909&q=London&aqi=no

    companion object{

        private const val BASE_URL = "https://lookup.binlist.net/"


        fun create() : ApiInterface{

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}