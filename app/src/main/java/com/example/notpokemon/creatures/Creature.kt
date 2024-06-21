package com.example.notpokemon.creatures

import com.example.notpokemon.creatures.attacks.Attack
import com.example.notpokemon.R
import com.example.notpokemon.statusEffects.StatusEffect

//Generic Creature class
abstract class Creature(open var attack: Attack) {
    open var creatureType = "Default"
    open var maxHealthPoints = 0.0
    open var creatureName = "Default"
    open var imageResource = R.drawable.creature_harvey
    open var effectStatuses: MutableList<StatusEffect> = mutableListOf()

    var healthPoints = 0.0

    val id = incrementedId

    init {
        maxHealthPoints = defaultHealth
        incrementedId++
        heal(999)
        println("maxhealth: $maxHealthPoints")
        println("health: $healthPoints")
    }

    fun attack(attackedCreature: Creature, damageModifier: Int): Double {
        return this.attack.doAttack(this, attackedCreature, damageModifier)
    }

    fun calculateDamage(attackedCreature: Creature, damageModifier: Int): Double {
        return this.attack.calculateDamage(this, attackedCreature, damageModifier)
    }

    fun takeDamage(damageAmount: Double) {
        if (damageAmount > healthPoints) { //in case of overkill
            healthPoints = 0.0
        } else {
            healthPoints -= damageAmount
        }
    }

    fun heal(amount: Int) {
        healthPoints += amount
        if (healthPoints > maxHealthPoints) {
            healthPoints = maxHealthPoints
        }
    }

    fun addStatusEffect(effect: StatusEffect) {
        effectStatuses.add(effect)
    }

    fun removeStatusEffect(effect: StatusEffect) {
        effectStatuses.remove(effect)
    }

    fun isDead(): Boolean {
        return healthPoints <= 0.0
    }

    companion object {
        var incrementedId = 0
        var defaultHealth = 240.00
    }
}
