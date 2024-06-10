package com.example.notpokemon

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.example.notpokemon.animations.AnimationCreator
import com.example.notpokemon.animations.SwitchTeamAnimation

class Fight (val fighter1:Fighter, val fighter2: Fighter) : Fragment(R.layout.fight_layout){

    var attackingPlayer = fighter1
    var defendingPlayer = fighter2
    var attackerIndex = 0
    var defenderIndex = 0
    var isFinished = false
    private var viewCreated = false
    private var startHasBeenCalled = false
    lateinit var winner: Fighter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationCreator(this)

        AnimationCreator.switchTeamAnimation(attackingPlayer, defendingPlayer).run()

        viewCreated = true
        confirmStart()
    }

    // abstraction
    fun startFight(){
        startHasBeenCalled = true
        confirmStart()
    }

    // confirms that both the view has been created, and start has been called
    private fun confirmStart(){
        if(viewCreated && startHasBeenCalled){
            executeFight()
        }
    }

    // actual function
    private fun executeFight(){
        val fightSequence = FightSequence(this)
        Thread(fightSequence).start()
    }
}