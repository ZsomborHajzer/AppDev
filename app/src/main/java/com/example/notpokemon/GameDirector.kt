package com.example.notpokemon

import com.example.notpokemon.websocketHandlers.EventHandlers
import com.example.notpokemon.views.fragments.Board.Elements.Tiles.SwitchTile
import com.example.notpokemon.battle.BattleManager
import com.example.notpokemon.battle.BattleManagerPVP
import com.example.notpokemon.creatures.ButterPig
import com.example.notpokemon.creatures.attacks.Bite
import com.example.notpokemon.playerObjects.Fighter
import com.example.notpokemon.playerObjects.PlayableCharacter
import com.example.notpokemon.playerObjects.Player
import com.example.notpokemon.views.activities.BoardView
import com.example.notpokemon.views.fragments.Board.GameBoardFragment

class GameDirector(val gameBoardFragment: GameBoardFragment) : Thread() {
    public var characters = ArrayList<PlayableCharacter>()

    init {
        instance = this
    }

    fun moveCharacterById(id: String, distance: Int) {
        getCharacterMoveThread(id, distance).start()
    }

    fun onChangeDirectionMove(playerId: String, steps: Int, directionIndex: Int) {
        val switchTile = getCharacterFromId(playerId).currentSquare as SwitchTile
        switchTile.switchNextTileByIndex(directionIndex)
        getCharacterMoveThread(playerId, steps).start()
    }

    fun getCharacterMoveThread(id: String, distance: Int): Thread {
        return Thread {
            run {
                if (!seeIfPlayerExists(id)) {
                    addCharacter(id)
                }

                val isInterrupted = getCharacterFromId(id).moveThisManySpaces(distance)

                if (!isInterrupted) {
                    EventHandlers.instance.sendEndTurnMessage()
                }
            }
        }
    }

    fun playTurn(character: PlayableCharacter) {
        val number = DiceRoller.rollD6()
        character.moveThisManySpaces(number)
    }

    fun seeIfPlayerExists(playerId: String): Boolean {
        for (character in characters) {
            if (character.id == playerId) {
                return true
            }
        }
        return false
    }

    fun addCharacter(playerId: String): PlayableCharacter {
        val player = getPlayerById(playerId)
        val character = PlayableCharacter(player, gameBoardFragment.getStartSquare())
        character.addCreature(ButterPig(Bite()))
        characters.add(character)
        return character
    }

    fun onRequestMovementDice() {
        BoardView.instance.showRollBanner()
    }

    fun onRolled() {
        val roll = rollMovementDice()
        EventHandlers.instance.sendMovementRollResult(roll)
    }


    fun rollMovementDice(): Int {
        return getCharacterFromId(thisPlayerId).rollMovement()
    }

    fun getPlayerById(playerId: String): Player {
        for (player in players) {
            if (player.id == playerId) {
                return player
            }
        }
        throw IllegalArgumentException("player does not exist from id")
    }

    fun getCharacterFromId(id: String): PlayableCharacter {
        for (character in characters) {
            if (character.id == id) {
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

    fun onStartPVEFight(fighter1Id: String, creatureTemplate: Int) {
        battleManager = BattleManager()
        battleManager.fighter1 = getCharacterFromId(fighter1Id)
        val randomEncounterFighter = Fighter("the whispers in the woods")
        randomEncounterFighter.addCreature(
            BattleManager.generateCreatureFromTemplate(
                creatureTemplate
            )
        )
        battleManager.fighter2 = randomEncounterFighter
        battleManager.initializeFight()
    }

    fun onEndFight(winnerIndex: Int) {
        var winner: Fighter
        if (winnerIndex == 0) {
            winner = battleManager.fighter1
        } else {
            winner = battleManager.fighter2
        }
        battleManager.endFight(winner)
    }

    fun onDirectionRequest(steps: Int) {
        val switchTile = getCharacterFromId(thisPlayerId).currentSquare as SwitchTile
        val squareList = switchTile.nextSquaresList
        val squareNames = ArrayList<String>()
        for (square in squareList) {
            squareNames.add(square.cardinalDirection)
        }
        val senderScript: (Int) -> Unit = { directionIndex ->
            EventHandlers.instance.sendChangeDirectionMove(thisPlayerId, steps, directionIndex)
        }

        BoardView.instance.spawnOptionsMenu(senderScript, squareNames)
    }


    companion object {
        lateinit var instance: GameDirector
        var players = ArrayList<Player>()
        lateinit var thisPlayerId: String
        lateinit var battleManager: BattleManager
    }
}