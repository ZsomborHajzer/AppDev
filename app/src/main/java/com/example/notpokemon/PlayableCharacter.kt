package com.example.notpokemon

import com.example.notpokemon.Board.Elements.SteppableTile
import com.example.notpokemon.dataobjects.Player
import com.example.notpokemon.views.BoardView


class PlayableCharacter(startingSquare: SteppableTile, name: String, var icon:Int = R.drawable.low_res_tanuki) : Fighter(name) {
    var currentSquare: SteppableTile;

    private var squareHistory = ArrayList<SteppableTile>();


    lateinit var id: String
    lateinit var role: String

    var stepsTaken = 0

    constructor(player:Player, startingSquare: SteppableTile) : this(startingSquare, player.username, player.imageResource) {
        this.id = player.id
        this.role = player.role
    }

    init {
        this.currentSquare = startingSquare;
        onMove(currentSquare)
    }

    fun moveThisManySpaces(totalSteps:Int): Boolean { //boolean = isInterrupted
        var waitTime = 500L;
        stepsTaken = 0

        BoardView.instance.updateStepsToGoText(totalSteps)
        while (stepsTaken < totalSteps){
            Thread.sleep(waitTime)
            var nextSquare = currentSquare.nextSquare
            if(onMove(nextSquare)){
                return true
            }
            stepsTaken++;
            BoardView.instance.updateStepsToGoText(totalSteps-stepsTaken)
        }

        return currentSquare.onTileStay(this)
    }

    public fun onInteractSquare(){
        // for now won't be used. but always good to have extra event opportunities
    }

    public fun onMove(square: SteppableTile):Boolean{
        currentSquare.onTileExit(this)
        addToSquareHistory(currentSquare)
        currentSquare = square
        return currentSquare.onTileEntry(this)
    }

    fun addToSquareHistory(square: SteppableTile){
        this.squareHistory.add(square)
        if(squareHistory.size > maxSquareHistory){
            squareHistory.removeFirst();
        }
    }

    // for if we have any character interactions with movement in the future (like status effects)
    fun rollMovement(): Int{
        return DiceRoller.rollD6()
    }

    companion object{
        val maxSquareHistory = 10;
    }
}
