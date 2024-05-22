package com.example.notpokemon

import kotlin.math.max

//Generic Creature class
abstract class Creature(private var name: String, private var type: String, private var maxHealthPoints: Int, private var attack: Attack) {
    var healthPoints = maxHealthPoints

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getAttack(): Attack {
        return attack
    }

    fun heal(amount: Int){
        healthPoints += amount
        if(healthPoints > maxHealthPoints){
            healthPoints = maxHealthPoints
        }
    }
}
