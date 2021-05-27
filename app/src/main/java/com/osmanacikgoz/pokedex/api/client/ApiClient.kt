package com.osmanacikgoz.pokedex.api.client

import com.osmanacikgoz.pokedex.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object{
        fun getClient() : Retrofit{
            return  Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}