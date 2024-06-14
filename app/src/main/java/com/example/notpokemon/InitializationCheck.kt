package com.example.notpokemon

import EventHandlers

class InitializationCheck {
    companion object{
        lateinit var gameBoardFragment: GameBoardFragment;
        var max = 0;
        var current = 0;

        fun addToCheckNumber(){
            this.max += 1;
        }
        fun haveInitialized(){
            this.current += 1;
            if(this.current > max){
                throw IllegalStateException("InitializationCheck has been overloaded")
            }
            else if(this.current == max){
                execution()
            }
        }

        fun execution(){
            GameDirector(gameBoardFragment);
            EventHandlers.instance.sendIsInitialized()
            println("EXECUTTEEEDDD")
        }

        fun reset(){
            this.max = 0;
            this.current = 0;
        }
    }
}