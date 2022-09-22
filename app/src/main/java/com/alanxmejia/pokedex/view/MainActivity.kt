package com.alanxmejia.pokedex.view

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alanxmejia.pokedex.Utils.Utils
import com.alanxmejia.pokedex.databinding.ActivityMainBinding
import com.alanxmejia.pokedex.model.APIService
import com.alanxmejia.pokedex.model.RetroFitHelper
import com.alanxmejia.pokedex.model.pokemondm.PokemonResponse
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isRepetitive: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        asyncDownload(Utils.getRandomId())

        binding.btnNewPokemon.setOnClickListener {
            asyncDownload(Utils.getRandomId())
        }

    }

    private fun asyncDownload(randPokemonId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit: Retrofit = RetroFitHelper.getRetroFit()
            val service: APIService = retrofit.create(APIService::class.java)
            val serviceResponse: Response<PokemonResponse> =
                service.pokemonInformation("$randPokemonId")

            val body = serviceResponse.body()
            runOnUiThread {
                if (serviceResponse.isSuccessful) {
                    val img = body?.sprites?.other?.officialArtwork?.frontDefault
                        ?: "@drawable/masterball"
                    binding.pokemonName.text = body?.name?.uppercase() ?: "No name"
                    binding.pokemonId.text = "Id: ${body?.id ?: "No id"}"
                    binding.baseExperience.text = "Experience: ${body?.baseExperience ?: 0}"
                    binding.weight.text = "Weight: ${body?.weight ?: 0}"
                    Picasso.get().load(img).into(binding.pokemonImg)
                }
            }
        }
    }

}