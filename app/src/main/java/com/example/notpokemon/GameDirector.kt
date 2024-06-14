package com.example.notpokemon

import com.example.notpokemon.dataobjects.Player

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characters = ArrayList<PlayableCharacter>()

    init {
        instance = this
    }

    fun playTurn(character: PlayableCharacter){
        val number = DiceRoller.rollD6()
        character.moveThisManySpaces(number)
    }
    fun startTurn(playerId:String){

        if(!seeIfPlayerExists(playerId)){
            addCharacter(playerId)
        }

        playTurn(getCharacterFromId(playerId))

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
        throw IllegalArgumentException("character does not exist from ID")
    }

    companion object{
        lateinit var instance: GameDirector
        var players = ArrayList<Player>()
    }
}