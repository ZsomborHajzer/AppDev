package com.example.notpokemon.animations

import android.view.View
import com.example.notpokemon.creatures.Creature
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.R
import pl.droidsonroids.gif.GifImageView

class ExecuteAttackAnimation(fight: Fight, val attackingCreature: Creature, val defendingCreature: Creature, val damage:Double) : Animation(fight) {
    val attackAnimationView = fight.requireView().findViewById<GifImageView>(R.id.attackView)
    override fun execute() {
        runOnUiThread(generateStartRunnable())
        Thread.sleep(1500)
        runOnUiThread(startAttackRunnable())
        Thread.sleep(3000)
        runOnUiThread(generateEndRunnable())
        Thread.sleep(2000)
    }

    fun generateStartRunnable(): Runnable{
        return Runnable(){
            run{
                setToFront()
            }
        }
    }

    fun startAttackRunnable(): Runnable{
        return Runnable(){
            run{
                startAttackAnimation()
                attackDamageText.visibility = View.VISIBLE
                attackDamageText.text = damage.toString()
            }
        }
    }

    fun generateEndRunnable(): Runnable{
        return Runnable(){
            run{
                stopAttackAnimation()
                resetAnimation()
                attackDamageText.visibility = View.INVISIBLE
                attackDamageText.text = ""
            }
        }
    }

    fun setToFront(){
        getActiveCreatureViewByPlayerNumber(1).setImageResource(attackingCreature.imageResource)
        getActiveCreatureViewByPlayerNumber(2).setImageResource(defendingCreature.imageResource)
        activeAttackerHealth.visibility = View.VISIBLE
        activeDefenderHealth.visibility = View.VISIBLE
        activeAttackerHealth.text = attackingCreature.healthPoints.toString()
        activeDefenderHealth.text = defendingCreature.healthPoints.toString()
    }

    fun startAttackAnimation(){
        attackAnimationView.setImageResource(attackingCreature.attack.imageResource)
    }

    fun stopAttackAnimation(){
        attackAnimationView.setImageDrawable(null)
    }

    fun resetAnimation(){
        getActiveCreatureViewByPlayerNumber(1).setImageDrawable(null)
        getActiveCreatureViewByPlayerNumber(2).setImageDrawable(null)
        activeAttackerHealth.visibility = View.INVISIBLE
        activeDefenderHealth.visibility = View.INVISIBLE

    }

}