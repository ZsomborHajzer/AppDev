package com.example.notpokemon.animations

import com.example.notpokemon.creatures.Creature
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.playerObjects.Fighter

class AnimationCreator(val fight: Fight) {
    init {
        Companion.instance = this
    }

    fun createAttackAnimation(attackingCreature: Creature, defendingCreature: Creature, damage: Double):ExecuteAttackAnimation{
        return ExecuteAttackAnimation(fight, attackingCreature, defendingCreature, damage)
    }
    fun createDeathAnimation():DeathAnimation{
        return DeathAnimation(fight)
    }
    fun createSwitchTeamAnimation(futureAttacker: Fighter, futureDefender: Fighter):SwitchTeamAnimation{
        return SwitchTeamAnimation(fight, futureAttacker, futureDefender)
    }

    // singleton
    companion object{
        lateinit var instance: AnimationCreator
        fun attackAnimation(attackingCreature: Creature, defendingCreature: Creature, damage:Double): ExecuteAttackAnimation{
            return instance.createAttackAnimation(attackingCreature, defendingCreature, damage)
        }

        fun deathAnimation(): DeathAnimation{
            return instance.createDeathAnimation()
        }

        fun switchTeamAnimation(futureAttacker: Fighter, futureDefender: Fighter): SwitchTeamAnimation{
            return instance.createSwitchTeamAnimation(futureAttacker, futureDefender)
        }
    }
}