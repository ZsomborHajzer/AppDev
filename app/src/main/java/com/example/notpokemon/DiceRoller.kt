package com.example.notpokemon

import kotlin.random.Random

class DiceRoller {
    companion object{
        /**
         * rolls a 6 sided dice, landing between 1 and 6 inclusive
         */
        fun rollD6() : Int{
            return Random.nextInt(6)+1;
        }
    }
}