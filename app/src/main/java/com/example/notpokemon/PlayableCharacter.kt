package com.example.notpokemon

import com.example.notpokemon.Board.Elements.SteppableTile
import com.example.notpokemon.dataobjects.Player


class PlayableCharacter(startingSquare: SteppableTile, name: String) : Fighter(name) {
    var currentSquare: SteppableTile;

    private var squareHistory = ArrayList<SteppableTile>();
    val icon = R.drawable.low_res_tanuki


    lateinit var id: String
    lateinit var role: String

    constructor(player:Player, startingSquare: SteppableTile) : this(startingSquare, player.username) {
        this.id = player.id
        this.role = player.role
    }
    init {
        this.currentSquare = startingSquare;
        onMove(currentSquare)
    }

    fun moveThisManySpaces(number:Int){
        if (number < 1){
            throw IllegalArgumentException("function \"moveThisManySpaces\" from" + this.javaClass + "may not be less than 0")
        }
        var waitTime = 500L;
        var i = 1
        while (i <= number){
            var nextSquare = currentSquare.nextSquare
            onMove(nextSquare)
            i++;
            Thread.sleep(waitTime)
        }
        currentSquare.onTileStay(this)
    }

    public fun onInteractSquare(){
        // for now won't be used. but always good to have extra event opportunities
    }

    public fun onMove(square: SteppableTile){
        currentSquare.onTileExit(this)
        addToSquareHistory(currentSquare)
        currentSquare = square
        currentSquare.onTileEntry(this)
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
