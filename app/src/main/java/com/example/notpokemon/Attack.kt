package com.example.notpokemon

abstract class Attack (private var damage: Int) {
    open var attackName = "DefaultAttack"

    fun getDamage(): Int {
        return damage
    }

    //Basic attack function. Can be overridden and customised.
    fun doAttack(attackingCreature: Creature, opposingCreature: Creature) {
        opposingCreature.takeDamage(damage)
        println("${attackingCreature.creatureName} used ${attackingCreature.attack} dealt ${getDamage()} damage to ${opposingCreature.creatureName}")
    }
}