package com.osmanacikgoz.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.osmanacikgoz.pokedex.api.response.PokemonListResponse
import com.osmanacikgoz.pokedex.databinding.PokemonItemBinding
import com.osmanacikgoz.pokedex.holder.PokemonListHolder

class PokemonListAdapter(
    val pokeList: List<PokemonListResponse.Result?>
) : RecyclerView.Adapter<PokemonListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListHolder {
        val binding: PokemonItemBinding =
            PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonListHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonListHolder, position: Int) {
        holder.bind(pokeList[position])
    }

    override fun getItemCount(): Int {
        return pokeList.size
    }
}