package com.example.notpokemon

//Generic Creature class
abstract class Creature(open var attack: Attack) {
    open var creatureType = "Default"
    open var maxHealthPoints = 240.00
    open var creatureName = "Default"
    open var imageResource = R.drawable.creature_harvey

    var healthPoints = maxHealthPoints

    fun takeDamage(damageAmount: Double){
        if(damageAmount > healthPoints){ //in case of overkill
            healthPoints = 0.0
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
