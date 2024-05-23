package com.example.notpokemon

class ButterPig(override var attack: Attack) : Creature(attack) {
    override var creatureName = "ButterPig"
    override var creatureType = "Fire"
    override var maxHealthPoints = 200


}