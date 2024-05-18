package com.example.notpokemon

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {

    private val character = PlayableCharacter(gameBoardFragment.getStartSquare(), "sally")
    init {
        character.addCreature(BattleManager.generateCreature())
    }

    override fun run(){
        runAroundDemo(character);
    }

    private fun runAroundDemo(playableCharacter: PlayableCharacter){
        while(true){
            val number = DiceRoller.rollD6()
            playableCharacter.moveThisManySpaces(number)
            sleep(2000);
        }
    }
}