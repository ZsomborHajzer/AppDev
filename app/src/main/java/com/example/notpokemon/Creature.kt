package com.example.notpokemon

import kotlin.math.max

//Generic Creature class
class Creature(private var name: String, private var type: String, private var maxHealthPoints: Int, private var attackName: String) {
    var healthPoints = maxHealthPoints

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getAttackName(): String {
        return attackName
    }

    //Basic attack function. We can maybe turn the class into an abstract and override it or something
    fun attack(opposingCreature: Creature) {
        val damage = (0..50).random() //does random damage
        val newHealthPoints = opposingCreature.healthPoints - damage
        if (newHealthPoints < 0) { //in case of overkill
            opposingCreature.healthPoints = 0
        } else {
            opposingCreature.healthPoints = newHealthPoints
        }
        println("${getName()} used ${getAttackName()} and dealt $damage damage to ${opposingCreature.getName()}")
    }

    fun heal(amount: Int){
        healthPoints += amount
        if(healthPoints > maxHealthPoints){
            healthPoints = maxHealthPoints
        }
    }
}
