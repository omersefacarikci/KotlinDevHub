package com.omersefacarikci.retrofitcryptoapp.service

import com.omersefacarikci.retrofitcryptoapp.modal.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoAPI {
    // https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    //get, post, update,delete
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData(): Call<List<CryptoModel>>

}