package com.example.notpokemon

import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity


class Character {
    public lateinit var currentSquare: GameBoardSquareFragment;
    public var squareHistory = ArrayList<GameBoardSquareFragment>();
    private val maxSquareHistory = 10;
    public val characterIcon = R.drawable.low_res_tanuki

    constructor(square: GameBoardSquareFragment){
        this.currentSquare = square;
        indicateStandingOnSquare(currentSquare)
    }

    fun moveThisManySpaces(number:Int){
        if (number < 1){
            throw IllegalArgumentException("function \"moveThisManySpaces\" from" + this.javaClass + "may not be less than 0")
        }
        var localCurrentSquare = currentSquare
        var waitTime = 500L;
        var i = 1
        while (i <= number){
            localCurrentSquare = localCurrentSquare.getNextSquare() // TODO:: Implement multiple routes
            onMoveSquare(localCurrentSquare)
            i++;
            Thread.sleep(waitTime)
        }
    }

    public fun onInteractSquare(){
        // for now won't be used. but always good to have extra event opportunities
    }

    public fun onMoveSquare(square:GameBoardSquareFragment){
        currentSquare.clearSquareOverlay()
        addToSquareHistory(currentSquare)
        currentSquare = square
        indicateStandingOnSquare(currentSquare)
    }
    fun indicateStandingOnSquare(square:GameBoardSquareFragment){
        square.setOverlayFromResource(characterIcon)
    }

    fun addToSquareHistory(square: GameBoardSquareFragment){
        this.squareHistory.add(square)
        if(squareHistory.size > maxSquareHistory){
            squareHistory.removeFirst();
        }
    }
}