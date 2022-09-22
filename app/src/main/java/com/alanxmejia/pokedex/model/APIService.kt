package com.alanxmejia.pokedex.model

import com.alanxmejia.pokedex.model.pokemondm.PokemonResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {
    @GET("{id}/")
    suspend fun pokemonInformation(@Path("id") id: String): Response<PokemonResponse>
}