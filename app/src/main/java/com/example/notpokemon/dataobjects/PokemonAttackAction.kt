package com.example.notpokemon.dataobjects

import com.example.notpokemon.Attack
import com.example.notpokemon.Creature

data class PokemonAttackAction(
    val pokemon : Creature,
    val targetPokemon : Creature,
    val attackMove: Attack,
    val random: Int
)


