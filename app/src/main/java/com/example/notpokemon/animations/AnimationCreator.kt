package com.example.notpokemon.animations

import com.example.notpokemon.Creature
import com.example.notpokemon.Fight
import com.example.notpokemon.Fighter

class AnimationCreator(val fight: Fight) {
    init {
        Companion.instance = this
    }

    fun createAttackAnimation(attackingCreature:Creature, defendingCreature:Creature, damage: Double):ExecuteAttackAnimation{
        return ExecuteAttackAnimation(fight, attackingCreature, defendingCreature)
    }
    fun createDeathAnimation():DeathAnimation{
        return DeathAnimation(fight)
    }
    fun createSwitchTeamAnimation(futureAttacker:Fighter, futureDefender:Fighter):SwitchTeamAnimation{
        return SwitchTeamAnimation(fight, futureAttacker, futureDefender)
    }

    // singleton
    companion object{
        lateinit var instance: AnimationCreator
        fun attackAnimation(attackingCreature:Creature, defendingCreature:Creature, damage:Double): ExecuteAttackAnimation{
            return instance.createAttackAnimation(attackingCreature, defendingCreature)
        }

        fun deathAnimation(): DeathAnimation{
            return instance.createDeathAnimation()
        }

        fun switchTeamAnimation(futureAttacker:Fighter, futureDefender:Fighter): SwitchTeamAnimation{
            return instance.createSwitchTeamAnimation(futureAttacker, futureDefender)
        }
    }
}