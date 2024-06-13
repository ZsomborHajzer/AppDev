package com.example.notpokemon

import com.example.notpokemon.dataobjects.Player

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characters = ArrayList<PlayableCharacter>()
    private var playerTurn = 0

    fun addCharacter(): PlayableCharacter{
        val character = PlayableCharacter(players[playerTurn], gameBoardFragment.getStartSquare())
        character.addCreature(ButterPig(BiteAttack()))
        characters.add(character)
        return character
    }

    fun playTurn(character: PlayableCharacter){
        val number = DiceRoller.rollD6()
        character.moveThisManySpaces(number)
        playerTurn++
    }
    override fun run(){
        while(true){
            if(playerTurn < characters.size){
                playTurn(characters[playerTurn])
            }
            else if(characters.size < players.size){
                val character = addCharacter()
                playTurn(character)
            }
            else{
                playerTurn = 0
                playTurn(characters[playerTurn])
            }
        }
    }

    companion object{
        var players = ArrayList<Player>()
    }
}