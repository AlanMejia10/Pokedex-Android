package com.alanxmejia.pokedex.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetroFitHelper {
    fun getRetroFit() : Retrofit {
        return Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/pokemon/").
                addConverterFactory(GsonConverterFactory.create()).build()
    }
}