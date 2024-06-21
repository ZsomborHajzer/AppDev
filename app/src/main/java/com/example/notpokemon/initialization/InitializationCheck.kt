package com.example.notpokemon.initialization

import com.example.notpokemon.websocketHandlers.EventHandlers
import com.example.notpokemon.views.fragments.Board.GameBoardFragment
import com.example.notpokemon.GameDirector

class InitializationCheck {
    companion object{
        lateinit var gameBoardFragment: GameBoardFragment;
        var max = 0;
        var current = 0;

        fun addToCheckNumber(){
            max += 1;
        }
        fun haveInitialized(){
            current += 1;
            if(current > max){
                throw IllegalStateException("InitializationCheck has been overloaded")
            }
            else if(current == max){
                execution()
            }
        }

        fun execution(){
            println("haveInitialized has been executed")
            GameDirector(gameBoardFragment);
            EventHandlers.instance.sendIsInitialized()
        }

        fun reset(){
            max = 0;
            current = 0;
        }
    }
}