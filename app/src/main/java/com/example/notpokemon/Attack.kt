package com.example.notpokemon

abstract class Attack () {
    open var attackName = "DefaultAttack"
    open var damage: Double = 50.0
    open var imageResource = R.drawable.bite_animation

    //Basic attack function. Can be overridden and customised.
    fun doAttack(attackingCreature: Creature, opposingCreature: Creature, attackModifier:Int) {
        val (advantage, disadvantage) = creatureTypeAdvantageCheck(attackingCreature, opposingCreature)
        var damage = damage + (attackModifier * 10)

        // Adjust damage if the attacking creature has an advantage/disadvantage
        if (advantage) {
            damage *= 1.5
        } else if (disadvantage) {
            damage *= 0.5
        }

        opposingCreature.takeDamage(damage)
        println("${attackingCreature.creatureName} used ${attackName} dealt $damage damage to ${opposingCreature.creatureName}")
    }

    fun creatureTypeAdvantageCheck(attackingCreature: Creature, opposingCreature: Creature): Pair<Boolean, Boolean> {
        val advantageMap = mapOf(
            "Haunted" to mapOf("Space" to true, "Sugar" to false),
            "Space" to mapOf("Aquatic" to true, "Haunted" to false),
            "Aquatic" to mapOf("Sugar" to true, "Space" to false),
            "Sugar" to mapOf("Haunted" to true, "Aquatic" to false)
        )

        var advantage = false
        var disadvantage = false

        advantageMap[attackingCreature.creatureType]?.let {
            when (it[opposingCreature.creatureType]) {
                true -> advantage = true
                false -> disadvantage = true
                else -> {}
            }
        }

        return Pair(advantage, disadvantage)
    }
}
