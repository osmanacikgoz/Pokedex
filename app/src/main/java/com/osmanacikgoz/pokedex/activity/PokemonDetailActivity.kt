package com.osmanacikgoz.pokedex.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.osmanacikgoz.pokedex.R
import com.osmanacikgoz.pokedex.Stats
import com.osmanacikgoz.pokedex.api.client.ApiClient
import com.osmanacikgoz.pokedex.api.response.PokemonInfo
import com.osmanacikgoz.pokedex.api.services.PokedexServices
import com.osmanacikgoz.pokedex.databinding.ActivityPokemonDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPokemonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pokemon_detail)


        binding.arrow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val detailName = intent.getStringExtra("name")
        binding.pokemonDetailName.text = detailName

        val detailImage = intent.getStringExtra("url")
        Glide.with(this)
            .load(detailImage)
            .listener(
                GlidePalette.with(detailImage)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            binding.background.setBackgroundColor(rgb)
                        }
                    }
            )
            .into(binding.pokemonDetailImage)

        fetchDetailData(detailName!!)
    }

    private fun fetchDetailData(name: String) {
        ApiClient.getClient().create(PokedexServices::class.java)
            .getPokeInfo(name)
            .enqueue(object : Callback<PokemonInfo> {

                override fun onResponse(call: Call<PokemonInfo>, response: Response<PokemonInfo>) {
                    val pokemonDetailResponse = response.body()

                    binding.pokemonDetailNumber.text = pokemonDetailResponse?.id.toString()
                    binding.pokemonWeight.text = pokemonDetailResponse?.weight.toString()
                    binding.pokemonHeight.text = pokemonDetailResponse?.height.toString()

                    if (pokemonDetailResponse?.types?.isNotEmpty() == true && pokemonDetailResponse.types.size == 2) {
                        val pokemonDemageFrom: PokemonInfo.Type? = pokemonDetailResponse.types[0]
                        binding.halfDemageFrom.text = (pokemonDemageFrom?.type?.name)

                        val pokemonDemageTo: PokemonInfo.Type? = pokemonDetailResponse.types[1]
                        binding.halfDemageTo.text = (pokemonDemageTo?.type?.name)

                    } else if (pokemonDetailResponse?.types?.isNotEmpty() == true && pokemonDetailResponse.types.size == 1) {

                        val pokemonDemageFrom: PokemonInfo.Type? =
                            pokemonDetailResponse.types[0]
                        binding.halfDemage.text = (pokemonDemageFrom?.type?.name)
                    }


                    val attackPokemon =
                        pokemonDetailResponse?.stats?.find { it?.stat?.name == Stats.ATTACK.statName }

                    binding.attackProgress.labelText = attackPokemon?.baseStat.toString()
                    binding.attackProgress.progress = attackPokemon?.baseStat?.toFloat() ?: 0f

                    val defensePokemon =
                        pokemonDetailResponse?.stats?.find { it?.stat?.name == Stats.DEFENSE.statName }

                    binding.defansiveProgress.labelText = defensePokemon?.baseStat.toString()
                    binding.defansiveProgress.progress = defensePokemon?.baseStat?.toFloat() ?: 0f

                    val hpPokemon =
                        pokemonDetailResponse?.stats?.find { it?.stat?.name == Stats.HP.statName }

                    binding.hpProgress.labelText = hpPokemon?.baseStat.toString()
                    binding.hpProgress.progress = hpPokemon?.baseStat?.toFloat() ?: 0f

                    val speedPokemon =
                        pokemonDetailResponse?.stats?.find { it?.stat?.name == Stats.SPEED.statName }

                    binding.spdProgress.labelText = speedPokemon?.baseStat.toString()
                    binding.spdProgress.progress = speedPokemon?.baseStat?.toFloat() ?: 0f

                    binding.expProgress.labelText = pokemonDetailResponse?.baseExperience.toString()
                    binding.expProgress.progress =
                        pokemonDetailResponse?.baseExperience?.toFloat() ?: 0f


                }

                override fun onFailure(call: Call<PokemonInfo>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }
            })
    }


}