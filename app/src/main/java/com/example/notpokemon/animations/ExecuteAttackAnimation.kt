package com.example.notpokemon.animations

import com.example.notpokemon.Creature
import com.example.notpokemon.Fight
import com.example.notpokemon.R
import pl.droidsonroids.gif.GifImageView

class ExecuteAttackAnimation(fight: Fight, val attackingCreature:Creature, val defendingCreature:Creature) : Animation(fight) {
    val attackAnimationView = fight.requireView().findViewById<GifImageView>(R.id.attackView)
    override fun execute() {
        Thread.sleep(1000)
        runOnUiThread(generateStartRunnable())
        Thread.sleep(4000)
        runOnUiThread(generateEndRunnable())
        Thread.sleep(2000)
    }

    fun generateStartRunnable(): Runnable{
        return Runnable(){
            run{
                setToFront()
                startAttackAnimation()
            }
        }
    }
    fun generateEndRunnable(): Runnable{
        return Runnable(){
            run{
                stopAttackAnimation()
                resetAnimation()
            }
        }
    }

    fun setToFront(){
        getActiveCreatureViewByPlayerNumber(1).setImageResource(attackingCreature.imageResource)
        getActiveCreatureViewByPlayerNumber(2).setImageResource(defendingCreature.imageResource)
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
    }

}