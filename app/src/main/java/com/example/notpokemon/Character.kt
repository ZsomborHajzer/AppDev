package com.example.notpokemon

import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import androidx.core.content.ContextCompat.startActivity


class Character {
    public lateinit var currentSquare: GameBoardSquareFragment;
    public var squareHistory = ArrayList<GameBoardSquareFragment>();
    private val maxSquareHistory = 10;
    public val characterIcon = R.drawable.board_square_occupied
    public val defaultIcon = R.drawable.board_square

    constructor(square: GameBoardSquareFragment){
        this.currentSquare = square;
        indicateStandingOnSquare(currentSquare)
    }

    public fun onInteractSquare(){
        // for now won't be used. but always good to have extra event opportunities
    }

    public fun onMoveSquare(square:GameBoardSquareFragment){
        addToSquareHistory(currentSquare)
        squareHistory.last().setImageFromResource(defaultIcon)
        currentSquare = square
        indicateStandingOnSquare(currentSquare)
    }

    fun indicateStandingOnSquare(square:GameBoardSquareFragment){
        square.setImageFromResource(characterIcon)
    }



    fun addToSquareHistory(square: GameBoardSquareFragment){
        this.squareHistory.add(square)
        if(squareHistory.size > maxSquareHistory){
            squareHistory.removeFirst();
        }
    }
}