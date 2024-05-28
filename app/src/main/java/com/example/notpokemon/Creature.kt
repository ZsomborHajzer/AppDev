package com.example.notpokemon

import kotlin.math.max

//Generic Creature class
abstract class Creature(open var attack: Attack) {
    open var creatureType = "Default"
    open var maxHealthPoints = 100
    open var creatureName = "Default"
    open var imageResource = R.drawable.creature_harvey

    var healthPoints = maxHealthPoints

    fun takeDamage(damageAmount: Int){
        if(damageAmount > healthPoints){ //in case of overkill
            healthPoints = 0
        }
        else{
            healthPoints -= damageAmount
        }
    }

    fun heal(amount: Int){
        healthPoints += amount
        if(healthPoints > maxHealthPoints){
            healthPoints = maxHealthPoints
        }
    }
}
