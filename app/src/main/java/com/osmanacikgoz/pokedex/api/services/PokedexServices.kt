package com.osmanacikgoz.pokedex.api.services

import com.osmanacikgoz.pokedex.api.response.PokemonInfo
import com.osmanacikgoz.pokedex.api.response.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexServices {

    @GET("pokemon")
    fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ):Call<PokemonListResponse>

    @GET("pokemon/{name}")
    fun getPokeInfo(@Path("name") name:String): Call<PokemonInfo>
}