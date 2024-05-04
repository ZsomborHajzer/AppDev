package com.example.notpokemon

import android.os.Handler
import kotlin.random.Random

class GameDirector(val gameBoardFragment: GameBoardFragment) {

    private val character = Character(gameBoardFragment.getSquare(0))

    fun run(){
        val square = gameBoardFragment.getSquare(0)

        runAroundDemo(character);
    }

    private fun runAroundDemo(character: Character){
        val handler = Handler()
        val runner: Runnable = object : Runnable {
            override fun run() {
                var number = Random.nextInt(6)+1
                character.moveThisManySpaces(number)
                handler.postDelayed(this, 2000 + number*500L) // TODO:: create await method
            }
        }
        handler.postDelayed(runner, 2000)
    }

}