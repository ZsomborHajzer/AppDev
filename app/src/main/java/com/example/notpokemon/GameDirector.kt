package com.example.notpokemon

import android.os.Handler
import kotlin.random.Random

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {

    private val character = Character(gameBoardFragment.getStartSquare())

    override fun run(){
        runAroundDemo(character);
    }

    private fun runAroundDemo(character: Character){
        while(true){
            val number = DiceRoller.rollD6()
            character.moveThisManySpaces(number)
            sleep(2000);
        }
    }
}