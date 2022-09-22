package com.alanxmejia.pokedex.Utils

class Utils {
    companion object {
        val minId = 1
        val maxId = 900

         fun getRandomId(): Int {
            return (minId..maxId).random()
        }
    }
}