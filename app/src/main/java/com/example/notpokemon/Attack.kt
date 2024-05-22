package com.example.notpokemon

abstract class Attack (private val attackName: String, private val attackType: String, private var damage: Int) {

    fun getAttackName(): String {
        return attackName
    }

    fun getAttackType(): String {
        return attackType
    }

    fun getDamage(): Int {
        return damage
    }

    //Basic attack function. Can be overridden and customised.
    fun doAttack(attackingCreature: Creature, opposingCreature: Creature) {
        val newHealthPoints = opposingCreature.healthPoints - getDamage()
        if (newHealthPoints < 0) { //in case of overkill
            opposingCreature.healthPoints = 0
        } else {
            opposingCreature.healthPoints = newHealthPoints
        }
        println("${attackingCreature.getName()} used ${getAttackName()} and dealt ${getDamage()} damage to ${opposingCreature.getName()}")
    }
}