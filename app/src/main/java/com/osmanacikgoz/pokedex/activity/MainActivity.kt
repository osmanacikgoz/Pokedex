package com.osmanacikgoz.pokedex.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.osmanacikgoz.pokedex.R
import com.osmanacikgoz.pokedex.adapter.PokemonListAdapter
import com.osmanacikgoz.pokedex.api.client.ApiClient
import com.osmanacikgoz.pokedex.api.response.PokemonListResponse
import com.osmanacikgoz.pokedex.api.services.PokedexServices
import com.osmanacikgoz.pokedex.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var page = 0
    val limit = 20
    val pokemonList: ArrayList<PokemonListResponse.Result?> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fetchPokemonData(0)

    }

    private fun fetchPokemonData(offset: Int) {
        ApiClient.getClient().create(PokedexServices::class.java)
            .fetchPokemonList(limit, offset)
            .enqueue(object : Callback<PokemonListResponse> {

                override fun onResponse(
                    call: Call<PokemonListResponse>,
                    response: Response<PokemonListResponse>
                ) {
                    val pokemonResponse = response.body()

                    pokemonResponse?.results?.let {
                        if (response.isSuccessful && response.body() != null) {
                            pokemonList.addAll(it)
                            setPokemonAdapter(pokemonList)
                            setNestedScroolView()
                        }

                    }
                }

                override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })


    }

    private fun setNestedScroolView() {
        binding.nestedScrool.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v,
                scrollX,
                scrollY,
                oldScrollX,
                oldScrollY,
            ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++

                fetchPokemonData(limit * page)

            }
        })
    }

    private fun setPokemonAdapter(pokemon: List<PokemonListResponse.Result?>) {
        val adapter = PokemonListAdapter(pokemon)
        binding.recyclerView.adapter = adapter

    }


}








