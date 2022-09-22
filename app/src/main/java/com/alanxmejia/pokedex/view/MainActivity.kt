package com.alanxmejia.pokedex.view

import android.os.Bundle
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
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var updateCardTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timeBetweenRefresh = 30_000L;
        startTimer(timeBetweenRefresh)
        binding.btnNewPokemon.setOnClickListener {
            updateCardTimer.cancel()
            updateCardTimer.purge()
            asyncDownload(Utils.getRandomId())
            startTimer(timeBetweenRefresh)
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

    private fun startTimer(time: Long) {
        updateCardTimer = Timer("updatePokemonCard", false)
        updateCardTimer.scheduleAtFixedRate(0, time) {
            asyncDownload(Utils.getRandomId())
        }
    }
}