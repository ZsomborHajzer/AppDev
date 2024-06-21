package com.example.notpokemon.animations

import android.widget.TextView
import androidx.core.view.size
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.playerObjects.Fighter
import com.example.notpokemon.R

class SwitchTeamAnimation(fight: Fight, val futureAttacker: Fighter, val futureDefender: Fighter) : Animation(fight) {

    val playerName1TV = fight.requireView().findViewById<TextView>(R.id.firstFighterTitle)
    val playerName2TV = fight.requireView().findViewById<TextView>(R.id.secondFighterTitle)

    override fun execute() {
        runOnUiThread(createRunnable())
        Thread.sleep(1000)
    }

    fun createRunnable(): Runnable{
        return Runnable {
            run {
                setNames()
                assignCreaturesToUI()
            }
        }
    }

    fun setNames(){
        // setting the inner variables of the text components
        playerName1TV.text = futureAttacker.name
        playerName2TV.text = futureDefender.name
    }

    fun assignCreaturesToUI(){
        if(futureAttacker.team.size > player1CreaturesView.size || futureDefender.team.size > player2CreaturesView.size){
            throw IllegalStateException("attacking player cannot have more creatures than there are creature slots in fight_layout. ${this.javaClass}")
        }

        var playerNumber = 1
        while (playerNumber <= 2){

            var creatureIndex = 0
            while (creatureIndex < getPlayerByNumber(playerNumber).team.size){

                getCreatureNameTextView(playerNumber, creatureIndex).text = getPlayerByNumber(playerNumber).team[creatureIndex].creatureName
                getCreatureImageView(playerNumber, creatureIndex).setImageResource(getPlayerByNumber(playerNumber).team[creatureIndex].imageResource)

                creatureIndex++
            }

            playerNumber++
        }
    }

    fun getPlayerByNumber(playerNumber: Int): Fighter {
        if(playerNumber == 1){
            return futureAttacker
        }
        else if(playerNumber == 2){
            return futureDefender
        }

        else{
            throw IllegalArgumentException("there cannot be more than 2 players per battle as of now. ${this.javaClass}")
        }
    }


}