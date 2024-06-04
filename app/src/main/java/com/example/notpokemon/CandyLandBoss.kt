package com.example.notpokemon

class CandyLandBoss (override var attack: Attack) : Creature(attack) {
    override var creatureName = "Krazy Kandy"
    override var creatureType = "Sugar"
    override var maxHealthPoints = 3500.0
    override var imageResource: Int = R.drawable.creature_glorb
}