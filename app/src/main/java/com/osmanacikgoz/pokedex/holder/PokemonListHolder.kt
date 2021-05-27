package com.osmanacikgoz.pokedex.holder

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.osmanacikgoz.pokedex.activity.PokemonDetailActivity
import com.osmanacikgoz.pokedex.api.response.PokemonListResponse
import com.osmanacikgoz.pokedex.databinding.PokemonItemBinding

class PokemonListHolder(val binding: PokemonItemBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(pokeModel: PokemonListResponse.Result?) {

        pokeModel?.let {
            val pokemonName = binding.pokemonName
            pokemonName.text = it.name

            val pokemonImage = binding.pokemonImage
            Glide.with(pokemonImage.context)
                .load(getImageUrl(it.url))
                .listener(
                    GlidePalette.with(getImageUrl(it.url))
                        .use(BitmapPalette.Profile.MUTED_LIGHT)
                        .intoCallBack { palette ->
                            val rgb = palette?.dominantSwatch?.rgb
                            if (rgb != null) {
                                binding.pokemonCardItem.setCardBackgroundColor(rgb)
                            }
                        }.crossfade(true)
                ).into(pokemonImage)
            binding.pokemonImage.setOnClickListener { v ->
                val intent = Intent(binding.root.context, PokemonDetailActivity::class.java)
                intent.putExtra("name", it.name)
                intent.putExtra("url", getImageUrl(it.url))
                binding.root.context.startActivity(intent)

            }
        }

    }

    fun getImageUrl(url: String?): String {
        val index = url?.split("/".toRegex())?.dropLast(1)?.last() ?: "0"
        return "https://pokeres.bastionbot.org/images/pokemon/$index.png"
    }


}