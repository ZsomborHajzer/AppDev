package com.example.notpokemon

import EventHandlers
import com.example.notpokemon.dataobjects.Player

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characters = ArrayList<PlayableCharacter>()

    init {
        instance = this
    }

    fun startTurn(playerId:String){
        getStartTurnRunnable(playerId).start()
    }

    fun getStartTurnRunnable(playerId: String): Thread{
        return Thread {
            run {
                println("RAN RAN RAN")


                playTurn(getCharacterFromId(playerId))

                endTurn()
            }
        }
    }

    fun moveCharacterById(id: String, distance: Int){
        getCharacterMoveThread(id, distance).start()
    }

    fun getCharacterMoveThread(id:String, distance: Int):Thread{
        return Thread{
            run{
                if(!seeIfPlayerExists(id)){
                    addCharacter(id)
                }

                getCharacterFromId(id).moveThisManySpaces(distance)

                endTurn()
            }
        }
    }

    fun playTurn(character: PlayableCharacter){
        val number = DiceRoller.rollD6()
        character.moveThisManySpaces(number)
    }

    fun endTurn(){
        EventHandlers.instance.sendEndTurnMessage()
    }

    fun seeIfPlayerExists(playerId:String):Boolean{
        for (character in characters){
            if(character.id == playerId){
                return true
            }
        }
        return false
    }

    fun addCharacter(playerId: String): PlayableCharacter{
        val player = getPlayerById(playerId)
        val character = PlayableCharacter(player, gameBoardFragment.getStartSquare())
        character.addCreature(ButterPig(BiteAttack()))
        characters.add(character)
        return character
    }

    fun rollMovementDice(): Int{
        return getCharacterFromId(thisPlayerId).rollMovement()
    }

    fun getPlayerById(playerId: String): Player{
        for(player in players){
            if(player.id == playerId){
                return player
            }
        }
        throw IllegalArgumentException("player does not exist from id")
    }

    fun getCharacterFromId(id:String):PlayableCharacter{
        for (character in characters){
            if(character.id == id){
                return character
            }
        }
        return addCharacter(id)
    }

    companion object{
        lateinit var instance: GameDirector
        var players = ArrayList<Player>()
        lateinit var thisPlayerId: String
    }
}