package com.alanxmejia.pokedex.model.pokemondm


import com.google.gson.annotations.SerializedName

data class Generation(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)