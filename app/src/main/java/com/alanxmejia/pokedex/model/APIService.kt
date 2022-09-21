package com.alanxmejia.pokedex.model

import com.alanxmejia.pokedex.model.pokemondm.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("1")
    fun getPokemon(): Call<PokemonResponse>
}