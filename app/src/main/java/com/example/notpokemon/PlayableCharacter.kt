package com.example.notpokemon

import com.example.notpokemon.Board.Elements.SteppableTile


class PlayableCharacter(startingSquare: SteppableTile, name: String) : Fighter(name) {
    var currentSquare: SteppableTile;

    private var squareHistory = ArrayList<SteppableTile>();
    var icon = R.drawable.low_res_tanuki

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

    companion object{
        val maxSquareHistory = 10;
    }
}
