package com.example.notpokemon

//Generic Creature class
class Creature(private var name: String, private var type: String, private var healthPoints: Int, private var attackName: String) {

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getHealthPoints(): Int {
        return healthPoints
    }

    fun setHealthPoints(newHealthPoints: Int) {
        healthPoints = newHealthPoints
    }

    fun getAttackName(): String {
        return attackName
    }

    //Basic attack function. We can maybe turn the class into an abstract and override it or something
    fun attack(opposingCreature: Creature) {
        val damage = (0..50).random() //does random damage
        val newHealthPoints = opposingCreature.getHealthPoints() - damage
        if (newHealthPoints < 0) { //in case of overkill
            opposingCreature.setHealthPoints(0)
        } else {
            opposingCreature.setHealthPoints(newHealthPoints)
        }
        println("${getName()} used ${getAttackName()} and dealt $damage damage to ${opposingCreature.getName()}")
    }
}
