package com.example.notpokemon.animations

import com.example.notpokemon.Fight

class AnimationCreator(val fight: Fight) {
    init {
        Companion.instance = this
    }

    fun createAttackAnimation():AttackAnimation{
        return AttackAnimation(fight)
    }
    fun createDeathAnimation():DeathAnimation{
        return DeathAnimation(fight)
    }
    fun createSwitchTeamAnimation():SwitchTeamAnimation{
        return SwitchTeamAnimation(fight)
    }

    // singleton
    companion object{
        lateinit var instance: AnimationCreator
        fun attackAnimation(): AttackAnimation{
            return instance.createAttackAnimation()
        }

        fun deathAnimation(): DeathAnimation{
            return instance.createDeathAnimation()
        }

        fun switchTeamAnimation(): SwitchTeamAnimation{
            return instance.createSwitchTeamAnimation()
        }
    }
}