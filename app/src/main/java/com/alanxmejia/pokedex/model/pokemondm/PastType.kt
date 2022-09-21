package com.alanxmejia.pokedex.model.pokemondm


import com.google.gson.annotations.SerializedName

data class PastType(
    @SerializedName("generation")
    val generation: Generation,
    @SerializedName("types")
    val types: List<Type>
)