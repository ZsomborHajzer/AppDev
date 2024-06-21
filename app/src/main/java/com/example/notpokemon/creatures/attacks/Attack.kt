package com.example.notpokemon.creatures.attacks

import com.example.notpokemon.R
import com.example.notpokemon.creatures.Creature

abstract class Attack() {
    open var name = "DefaultAttack"
    open var baseDamage: Double = 50.0
    open var imageResource = R.drawable.bite_animation
    open var attackMultiplier: Double = 10.0

    //Basic attack function. Can be overridden and customised.
    fun doAttack(
        attackingCreature: Creature,
        opposingCreature: Creature,
        attackModifier: Int
    ): Double {
        val damage = calculateDamage(attackingCreature, opposingCreature, attackModifier)

        opposingCreature.takeDamage(damage)
        println("${attackingCreature.creatureName} used ${name} dealt $damage damage to ${opposingCreature.creatureName}")
        return damage
    }

    fun calculateDamage(
        attackingCreature: Creature,
        opposingCreature: Creature,
        attackModifier: Int
    ): Double {
        val advantage = creatureTypeAdvantageCheck(attackingCreature, opposingCreature)
        var damage = baseDamage + (attackModifier * attackMultiplier)

        // Adjust damage if the attacking creature has an advantage/disadvantage
        if (advantage == 1) {
            damage *= 1.5
        } else if (advantage == 0) {
            damage *= 0.5
        }
        return damage
    }

    /**
     * returns -1 for no advantage or disadvantage,
     * returns 0 for disadvantage,
     * and 1 for advantage
     */
    fun creatureTypeAdvantageCheck(attackingCreature: Creature, opposingCreature: Creature): Int {
        val advantageMap = mapOf(
            "Haunted" to mapOf("Space" to true, "Sugar" to false),
            "Space" to mapOf("Aquatic" to true, "Haunted" to false),
            "Aquatic" to mapOf("Sugar" to true, "Space" to false),
            "Sugar" to mapOf("Haunted" to true, "Aquatic" to false)
        )

        var advantage = -1


        if (advantageMap[attackingCreature.creatureType]?.contains(opposingCreature.creatureType) == true) {
            advantage = 0
            if (advantageMap[attackingCreature.creatureType]?.get(opposingCreature.creatureType) == true) {
                advantage = 1
            }
        }

        return advantage
    }
}
