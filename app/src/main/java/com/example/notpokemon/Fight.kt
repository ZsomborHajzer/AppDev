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

class Fight (val fighter1:Fighter, val fighter2: Fighter) : Fragment(R.layout.fight_layout){

    var attackingPlayer = fighter1
    var defendingPlayer = fighter2
    var attackerIndex = 0
    var defenderIndex = 0
    var isFinished = false
    private var viewCreated = false
    private var startHasBeenCalled = false
    lateinit var winner: Fighter

    lateinit var playerName1TV: TextView
    lateinit var playerName2TV: TextView
    lateinit var player1CreaturesView: TableLayout
    lateinit var player2CreaturesView: TableLayout
    lateinit var battleMapView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationCreator(this)

        // initializing the view components
        playerName1TV = requireView().findViewById(R.id.firstFighterTitle)
        playerName2TV = requireView().findViewById(R.id.secondFighterTitle)
        player1CreaturesView = requireView().findViewById(R.id.firstFighterCreatures)
        player2CreaturesView = requireView().findViewById(R.id.secondFighterCreatures)
        battleMapView = requireView().findViewById(R.id.battlemapImage)

        // setting the inner variables of the text components
        playerName1TV.text = attackingPlayer.getName()
        playerName2TV.text = defendingPlayer.getName()

        assignCreaturesToUI()

        viewCreated = true
        confirmStart()
    }

    fun assignCreaturesToUI(){
        if(attackingPlayer.team.size > player1CreaturesView.size || defendingPlayer.team.size > player2CreaturesView.size){
            throw IllegalStateException("attacking player cannot have more creatures than there are creature slots in fight_layout. ${this.javaClass}")
        }

        var playerNumber = 1
        while (playerNumber <= 2){

            var creatureIndex = 0
            while (creatureIndex < attackingPlayer.team.size){

                getCreatureNameTextView(playerNumber, creatureIndex).text = getPlayerByNumber(playerNumber).team[creatureIndex].creatureName
                getCreatureImageView(playerNumber, creatureIndex).setImageResource(getPlayerByNumber(playerNumber).team[creatureIndex].imageResource)

                creatureIndex++
            }

            playerNumber++
        }
    }

    fun getCreatureImageView(playerNumber: Int, creatureIndex: Int): ImageView{
        return getCreatureContainer(playerNumber, creatureIndex)[0] as ImageView
    }
    fun getCreatureNameTextView(playerNumber: Int, creatureIndex: Int): TextView{
        return getCreatureContainer(playerNumber, creatureIndex)[1] as TextView
    }
    fun getCreatureContainer(playerNumber: Int, creatureIndex: Int): ViewGroup{
        val containerParent = getCreatureLayoutByPlayerNumber(playerNumber)[creatureIndex] as ViewGroup
        return containerParent[0] as ViewGroup
    }

    fun getCreatureLayoutByPlayerNumber(playerNumber: Int): TableLayout{
        if(playerNumber == 1){
            return player1CreaturesView
        }
        else if(playerNumber == 2){
            return player2CreaturesView
        }
        else{
            throw IllegalArgumentException("there cannot be more than 2 players per battle as of now. ${this.javaClass}")
        }
    }

    fun getPlayerByNumber(playerNumber: Int): Fighter{
        if(playerNumber == 1){
            return fighter1
        }
        else if(playerNumber == 2){
            return fighter2
        }

        else{
            throw IllegalArgumentException("there cannot be more than 2 players per battle as of now. ${this.javaClass}")
        }
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