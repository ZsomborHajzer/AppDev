package com.example.notpokemon.creatures

import com.example.notpokemon.creatures.attacks.Attack
import com.example.notpokemon.R

class ButterPig(override var attack: Attack) : Creature(attack) {
    override var creatureName = "ButterPig"
    override var creatureType = "Sugar"
    override var maxHealthPoints = 200.0
    override var imageResource: Int = R.drawable.creature_glorb

}