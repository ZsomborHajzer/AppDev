package com.example.notpokemon

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characterAmount = 2
    public var characters = ArrayList<PlayableCharacter>()
    private var playerTurn = 0

    fun addCharacter(): PlayableCharacter{
        val character = PlayableCharacter(gameBoardFragment.getStartSquare(), "sally")
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
            else if(characters.size < characterAmount){
                val character = addCharacter()
                playTurn(character)
            }
            else{
                playerTurn = 0
                playTurn(characters[playerTurn])
            }
        }
    }

}