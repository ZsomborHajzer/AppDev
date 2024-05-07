package com.example.notpokemon

import android.os.Handler
import kotlin.random.Random

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {

    private val character = Character(gameBoardFragment.getSquare(0))

    override fun run(){
        runAroundDemo(character);
    }

    private fun runAroundDemo(character: Character){
        while(true){
            println("1")
            val number = DiceRoller.rollD6()
            moveThisManySpaces(character, number)
            sleep(2000);
        }
    }

    fun moveThisManySpaces(character: Character, number:Int){
        if (number < 1){
            throw IllegalArgumentException("function \"moveThisManySpaces\" from" + this.javaClass + "may not be less than 0")
        }

        var localCurrentSquare = character.currentSquare
        var waitTime = 500L;
        var i = 1
        while (i <= number){
            println("2")
            localCurrentSquare = localCurrentSquare.getNextSquares()[0] // TODO:: Implement multiple routes
            character.onMoveSquare(localCurrentSquare)
            i++;
            sleep(500)
        }
    }



}