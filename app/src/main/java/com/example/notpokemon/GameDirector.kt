package com.example.notpokemon

import EventHandlers
import com.example.notpokemon.dataobjects.Player

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characters = ArrayList<PlayableCharacter>()

    init {
        instance = this
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

                val isInterrupted = getCharacterFromId(id).moveThisManySpaces(distance)

                if(!isInterrupted){
                    EventHandlers.instance.sendEndTurnMessage()
                }
            }
        }
    }

    fun playTurn(character: PlayableCharacter){
        val number = DiceRoller.rollD6()
        character.moveThisManySpaces(number)
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

    fun onStartPVPFight(fighter1Id: String, fighter2Id: String) {
        battleManager = BattleManagerPVP()
        battleManager.fighter1 = getCharacterFromId(fighter1Id)
        battleManager.fighter2 = getCharacterFromId(fighter2Id)
        battleManager.initializeFight()
    }

    fun onStartPVEFight(fighter1Id: String, creatureTemplate:Int){
        battleManager = BattleManager()
        battleManager.fighter1 = getCharacterFromId(fighter1Id)
        val randomEncounterFighter = Fighter("the whispers in the woods")
        randomEncounterFighter.addCreature(BattleManager.generateCreatureFromTemplate(creatureTemplate))
        battleManager.fighter2 = randomEncounterFighter
        battleManager.initializeFight()
    }

    fun onEndFight(winnerIndex:Int){
        var winner:Fighter
        if(winnerIndex == 0){
            winner = battleManager.fighter1
        }
        else{
            winner = battleManager.fighter2
        }
        battleManager.endFight(winner)
    }

    companion object{
        lateinit var instance: GameDirector
        var players = ArrayList<Player>()
        lateinit var thisPlayerId: String
        lateinit var battleManager: BattleManager
    }
}