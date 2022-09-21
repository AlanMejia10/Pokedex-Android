package com.alanxmejia.pokedex.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alanxmejia.pokedex.databinding.ActivityMainBinding
import com.alanxmejia.pokedex.model.APIService
import com.alanxmejia.pokedex.model.RetroFitHelper
import com.alanxmejia.pokedex.model.pokemondm.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

        val retro: Retrofit = RetroFitHelper.getRetroFit()
        val service: APIService = retro.create(APIService::class.java)
        val respcall: Call<PokemonResponse> = service.getPokemon()

        respcall.enqueue(object : Callback<PokemonResponse>{
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                if(response.isSuccessful){
                    val poke: PokemonResponse? = response.body()
                    Log.i("pokemonAPI", "$poke")
                    Log.i("pokemonAPI", "${poke?.name}")
                    binding.pokemonName.text = poke?.name
                }else{
                    Log.i("apierror", "algo salio mal")
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        }
}