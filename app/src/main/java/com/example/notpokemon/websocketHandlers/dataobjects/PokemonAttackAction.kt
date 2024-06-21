package com.example.notpokemon.websocketHandlers.dataobjects

import com.example.notpokemon.creatures.attacks.Attack
import com.example.notpokemon.creatures.Creature

data class PokemonAttackAction(
    val pokemon : Creature,
    val targetPokemon : Creature,
    val attackMove: Attack,
    val random: Int
)


