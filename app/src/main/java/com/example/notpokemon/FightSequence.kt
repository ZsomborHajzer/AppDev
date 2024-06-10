package com.example.notpokemon

import com.example.notpokemon.animations.AnimationCreator
import com.example.notpokemon.animations.DeathAnimation
import com.example.notpokemon.animations.SwitchTeamAnimation

class FightSequence(val fight: Fight): Runnable {
    var attackingPlayer = fight.attackingPlayer
    var defendingPlayer = fight.defendingPlayer
    var attackerIndex = 0
    var defenderIndex = 0
    override fun run() {
        while (attackingPlayer.team.isNotEmpty() && defendingPlayer.team.isNotEmpty()) { //Neither team has "lost" yet
            val attacker = attackingPlayer.team[attackerIndex]
            val defender = defendingPlayer.team[defenderIndex]

            println("${attacker.creatureName} is fighting ${defender.creatureName}!")
            attacker.attack.doAttack(attacker, defender)
            AnimationCreator.attackAnimation().run()

            // Check if the defender's creature is defeated
            if (defender.healthPoints <= 0) {
                println("${defender.creatureName} fainted!")
                AnimationCreator.deathAnimation().run()
                // Remove the defeated creature from the defending player's team
                defendingPlayer.removeCreature(defenderIndex)
            }

            // Switch teams if all creatures from the current attacking team have attacked
            if (shouldSwitch()) {
                switchSides()
            } else {
                attackerIndex++
                defenderIndex++
            }
        }
        onFinish()
    }
    protected fun shouldSwitch(): Boolean{
        return attackerIndex >= attackingPlayer.team.lastIndex
                || defenderIndex >= defendingPlayer.team.lastIndex
    }
    protected fun switchSides(){
        val temp = attackingPlayer
        attackingPlayer = defendingPlayer
        defendingPlayer = temp
        attackerIndex = 0
        defenderIndex = 0
        println("Attacker team: ${attackingPlayer.getName()}, Defender team: ${defendingPlayer.getName()}")
        println("${attackingPlayer.getName()} 's turn!")
        AnimationCreator.switchTeamAnimation(attackingPlayer, defendingPlayer).run()
    }

    private fun onFinish(){
        fight.isFinished = true
        if (attackingPlayer.team.isEmpty()) {
            fight.winner = defendingPlayer
        } else {
            fight.winner = attackingPlayer
        }
    }
}