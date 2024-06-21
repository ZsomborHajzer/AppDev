package com.example.notpokemon.battle

import com.example.notpokemon.websocketHandlers.EventHandlers
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.notpokemon.DiceRoller
import com.example.notpokemon.playerObjects.Fighter
import com.example.notpokemon.R
import com.example.notpokemon.animations.AnimationCreator
import com.example.notpokemon.creatures.Creature
import com.example.notpokemon.statusEffects.EffectTrigger
import com.example.notpokemon.views.activities.BoardView

class Fight(val fighter1: Fighter, val fighter2: Fighter) : Fragment(R.layout.fight_layout) {

    var attackingPlayer = fighter1
    var defendingPlayer = fighter2
    var isFinished = false
    lateinit var winner: Fighter
    var creatureIndex = 0

    init {
        instance = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationCreator(this)

        AnimationCreator.switchTeamAnimation(attackingPlayer, defendingPlayer).run()

        EventHandlers.instance.sendReadyToFight()
    }

    fun run() {
        while (attackingPlayer.team.isNotEmpty() && defendingPlayer.team.isNotEmpty()) { //Neither team has "lost" yet
            val attacker = attackingPlayer.team[creatureIndex]
            val defender = defendingPlayer.team[creatureIndex]


            // Switch teams if all creatures from the current attacking team have attacked
            if (shouldSwitch()) {
                applyStatusEffects(attacker)
                switchSides()
            } else {
                creatureIndex++
            }
        }
        onFinish()
    }

    protected fun shouldSwitch(): Boolean {
        return creatureIndex >= attackingPlayer.team.lastIndex
                || creatureIndex >= defendingPlayer.team.lastIndex
    }

    fun switchSides() {
        val temp = attackingPlayer
        attackingPlayer = defendingPlayer
        defendingPlayer = temp
        creatureIndex = 0

        println("Attacker team: ${attackingPlayer.name}, Defender team: ${defendingPlayer.name}")
        println("${attackingPlayer.name} 's turn!")
        AnimationCreator.switchTeamAnimation(attackingPlayer, defendingPlayer).run()
        EventHandlers.instance.sendReadyToFight()
    }

    fun onRequestAttackMove(creatureIndex: Int) {
        val options = ArrayList<String>()
        options.add(attackingPlayer.team[creatureIndex].attack.name)

        val execution: (Int) -> Unit = { moveIndex ->
            EventHandlers.instance.sendCreatureAttacks(
                creatureIndex, creatureIndex, moveIndex,
                DiceRoller.rollD6()
            )
        }

        BoardView.instance.spawnOptionsMenu(execution, options)
    }

    // attackMoveIndex is unused for now due to the creatures only knowing one move
    fun onAttack(
        attackingCreatureIndex: Int,
        defendingCreatureIndex: Int,
        attackMoveIndex: Int,
        attackModifierValue: Int
    ) {
        val attackPokemon = attackingPlayer.team[attackingCreatureIndex]
        val defencePokemon = defendingPlayer.team[defendingCreatureIndex]
        val damage = attackPokemon.calculateDamage(defencePokemon, attackModifierValue)
        AnimationCreator.attackAnimation(attackPokemon, defencePokemon, damage).run()
        attackPokemon.attack(defencePokemon, attackModifierValue)

        notifyAttackIsFinished(defencePokemon.isDead())
    }

    fun notifyAttackIsFinished(creatureHasDied: Boolean) {
        val hasNextCreature = !shouldSwitch()
        val winner = seeWhichTeamWon()
        EventHandlers.instance.notifyAttackIsFinished(creatureHasDied, hasNextCreature, winner)
    }

    fun seeWhichTeamWon(): Int {
        return if (defendingPlayer.team.isEmpty()) {
            0
        } else if (attackingPlayer.team.isEmpty()) {
            1
        } else {
            -1
        }
    }

    fun onCreatureHasDied() {
        killCurrentDefendingCreature()
        notifyAttackIsFinished(false)
    }

    fun killCurrentDefendingCreature() {
        println("${defendingPlayer.team[creatureIndex].creatureName} fainted!")
        AnimationCreator.deathAnimation().run()
        // Remove the defeated creature from the defending player's team
        defendingPlayer.removeCreature(creatureIndex)
    }

    protected fun applyStatusEffects(attacker: Creature) {
        if (attacker.effectStatuses.size != 0) {
            for (effect in attacker.effectStatuses) {
                effect.applicationOfEffectStatus(EffectTrigger.ONTURN)
            }
        }
    }


    private fun onFinish() {
        isFinished = true
        if (attackingPlayer.team.isEmpty()) {
            winner = defendingPlayer
        } else {
            winner = attackingPlayer
        }
    }

    companion object {
        lateinit var instance: Fight
    }
}