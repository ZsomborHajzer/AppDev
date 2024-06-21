package com.example.notpokemon.websocketHandlers

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notpokemon.battle.Fight
import com.example.notpokemon.GameDirector
import com.example.notpokemon.views.activities.LobbyHostActivity
import com.example.notpokemon.playerObjects.PlayableCharacter
import com.example.notpokemon.R
import com.example.notpokemon.initialization.UIInitializer
import com.example.notpokemon.playerObjects.Player
import com.example.notpokemon.views.activities.BoardView
import com.example.notpokemon.websocketHandlers.dataobjects.StartGame
import com.google.gson.Gson
import com.google.gson.JsonObject

class EventHandlers(
    private val uiInitializer: UIInitializer,
    private val webSocketHandler: WebSocketHandler
) {

    init {
        instance = this
    }

    fun setupEventHandlers() {
        uiInitializer.connectButton.setOnClickListener {
            val usernameText = uiInitializer.username.text.toString()
            if (usernameText.isBlank()) {
                uiInitializer.usernameLayout.error = "This field is required!"
                return@setOnClickListener
            }

            if (usernameText.isNotBlank() && !webSocketHandler.isInitialized()) {
                webSocketHandler.startWebSocket(
                    url = "ws://16.16.126.99:8081",
                    onOpen = {
                        uiInitializer.receivedMessages.setText("Connected to the server")
                        Log.d("WebSocket", "Connected to the server")
                    },
                    onClose = { reason -> uiInitializer.receivedMessages.append("\nConnection is closing: $reason") },
                    onError = { error -> uiInitializer.receivedMessages.setText("Error: $error") }
                )
            }

            if (webSocketHandler.isInitialized()) {
                webSocketHandler.sendMessage("{\"username\": \"$usernameText\"}")
            }
        }

        webSocketHandler.setOnMessageReceivedListener { message ->
            handleWebSocketMessage(message)
        }

        uiInitializer.sendButton.setOnClickListener {
            var messageText = uiInitializer.messageInput.text.toString()
            val gson = Gson()
            messageText = when (messageText) {
                "1" -> gson.toJson(
                    com.example.notpokemon.websocketHandlers.dataobjects.EndGame(
                        event = "endGame",
                        timeStamp = System.currentTimeMillis()
                    )
                )

                "2" -> gson.toJson(
                    com.example.notpokemon.websocketHandlers.dataobjects.EndTurn(
                        event = "endTurn",
                        timeStamp = System.currentTimeMillis()
                    )
                )

                "3" -> gson.toJson(
                    com.example.notpokemon.websocketHandlers.dataobjects.MoveAction(
                        event = "moveAction",
                        fromPosition = "1",
                        toPosition = "6",
                        timeStamp = System.currentTimeMillis()
                    )
                )

                "4" -> gson.toJson(
                    StartGame(
                        event = "startGame",
                        timeStamp = System.currentTimeMillis()
                    )
                )

                else -> messageText
            }
            webSocketHandler.sendMessage(messageText)
        }
    }

    private fun handleWebSocketMessage(message: String) {
        val gson = Gson()
        val jsonObject = gson.fromJson(message, JsonObject::class.java)
        val event = jsonObject.get("event").asString

        Log.d("WebSocket", "Received message: $message")

        when (event) {
            "currentPlayers" -> {
                println("CURRENT PLAYER EVENTHANDLER")
                val players =
                    gson.fromJson(jsonObject.get("players").asJsonArray, Array<Player>::class.java)
                        .toList()
                LobbyHostActivity.players = ArrayList(players)
            }

            "connect" -> {
                val player = gson.fromJson(message, Player::class.java)
                if (player.imageResource == 0) {
                    player.imageResource = R.drawable.low_res_tanuki
                }
                sendPlayerBroadcast("NEW_PLAYER", player)
                println("ADDED PLAYER")
                if (!hasConnectedToLobby) {
                    LobbyHostActivity.players.add(player)
                    thisDevicePlayerId = player.id
                    hasConnectedToLobby = true
                }
                tryStartLobbyHostActivity()
            }

            "startGame" -> {
                print("I got the startgame!")
                val jsonPlayers = jsonObject.get("clients").asJsonArray
                val playerObjects = ArrayList<Player>()
                for (player in jsonPlayers) {
                    println(player)
                    playerObjects.add(Player.fromJsonObject(player.asJsonObject))
                }
                receiveStartGame(playerObjects)
            }

            "rollMovementDice" -> {
                onRollMovementDice()
            }

            "moveCharacter" -> {
                val id = jsonObject.get("id").asString
                val distance = jsonObject.get("distance").asString
                GameDirector.instance.moveCharacterById(id, distance.toInt())
            }

            "startFightBetweenPlayers" -> {
                val fighter1Id = jsonObject.get("fighter1").asString
                val fighter2Id = jsonObject.get("fighter2").asString
                GameDirector.instance.onStartPVPFight(fighter1Id, fighter2Id)
            }

            "startPVEFight" -> {
                val fighter1Id = jsonObject.get("fighter").asString
                val creatureTemplateId = jsonObject.get("creatureTemplate").asInt
                Thread() {
                    run {
                        GameDirector.instance.onStartPVEFight(fighter1Id, creatureTemplateId)
                    }
                }.start()

            }

            "creatureAttackRequest" -> {
                val creatureIndex = jsonObject.get("creature").asInt
                Fight.instance.onRequestAttackMove(creatureIndex)
            }

            "creatureAttacks" -> {
                val attackingCreatureIndex = jsonObject.get("attackingCreatureIndex").asInt
                val defendingCreatureIndex = jsonObject.get("defendingCreatureIndex").asInt
                val attackMoveIndex = jsonObject.get("attackMoveIndex").asInt
                val chanceModifier = jsonObject.get("chanceModifier").asInt
                Thread() {
                    run {
                        Fight.instance.onAttack(
                            attackingCreatureIndex,
                            defendingCreatureIndex,
                            attackMoveIndex,
                            chanceModifier
                        )
                    }
                }.start()
            }

            "switchTeams" -> {
                Thread() {
                    run {
                        Fight.instance.switchSides()
                    }
                }.start()
            }

            "creatureDied" -> {
                Thread() {
                    run {
                        Fight.instance.onCreatureHasDied()
                    }
                }.start()
            }

            "endBattle" -> {
                val winningCharacterIndex = jsonObject.get("winningCharacterIndex").asInt
                Thread() {
                    run {
                        GameDirector.instance.onEndFight(winningCharacterIndex)
                    }
                }.start()
            }

            "requestDirection" -> {
                val playerId = jsonObject.get("id").asString
                val steps = jsonObject.get("steps").asInt
                GameDirector.instance.onDirectionRequest(steps)
            }

            "changeDirectionMove" -> {
                val playerId = jsonObject.get("id").asString
                val steps = jsonObject.get("steps").asInt
                val directionIndex = jsonObject.get("directionIndex").asInt
                GameDirector.instance.onChangeDirectionMove(playerId, steps, directionIndex)
            }

            "changedPlayerPortrait" -> {
                val playerId = jsonObject.get("player").asString
                val imageResource = jsonObject.get("imageResource").asInt
                LobbyHostActivity.getPlayerById(playerId).imageResource = imageResource
                LobbyHostActivity.instance.updatePlayerCards()
            }
        }
    }

    private fun receiveStartGame(players: ArrayList<Player>) {
        GameDirector.players = players
        val intent = Intent(LobbyHostActivity.instance, BoardView::class.java)
        LobbyHostActivity.instance.startActivity(intent);
    }

    public fun sendStartGameMessage() {
        val gson = Gson()
        val startGameMessage =
            gson.toJson(StartGame(event = "startGame", timeStamp = System.currentTimeMillis()))
        webSocketHandler.sendMessage(startGameMessage)
    }

    public fun sendEndTurnMessage() {
        val startGameMessage =
            Gson().toJson(StartGame(event = "endTurn", timeStamp = System.currentTimeMillis()))
        webSocketHandler.sendMessage(startGameMessage)
    }

    private fun tryStartLobbyHostActivity() {
        if (!LobbyHostActivity.hasInstance()) {
            val intent = Intent(uiInitializer.activity, LobbyHostActivity::class.java)
            uiInitializer.activity.startActivity(intent)
        }
    }

    fun sendIsInitialized() {
        println("initialization message has been sent")
        GameDirector.thisPlayerId = thisDevicePlayerId
        val gson = Gson()
        val inInitializeMessage = gson.toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.GameInitialized(
                playerID = thisDevicePlayerId
            )
        )
        webSocketHandler.sendMessage(inInitializeMessage)
    }

    private fun sendPlayerBroadcast(action: String, player: Player) {
        val intent = Intent(action)
        intent.putExtra("data", player)
        LocalBroadcastManager.getInstance(uiInitializer.activity).sendBroadcast(intent)
    }

    private fun onRollMovementDice() {
        GameDirector.instance.onRequestMovementDice()
    }

    fun sendMovementRollResult(roll: Int) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.MovementRollResult(
                event = "movementRollResult",
                roll,
                timeStamp = System.currentTimeMillis()
            )
        )
        webSocketHandler.sendMessage(message)
    }

    public fun sendInterruptFightAgainstPlayer(
        player1: PlayableCharacter,
        player2: PlayableCharacter
    ) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.InterruptStartFightAgainstPlayer(
                player1.id,
                player2.id
            )
        )
        webSocketHandler.sendMessage(message)
    }

    public fun sendInterruptFightAgainstAI(player: PlayableCharacter, creatureTemplateId: Int) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.InterruptStartFightAgainstRandom(
                player.id,
                creatureTemplateId
            )
        )
        webSocketHandler.sendMessage(message)
    }

    public fun sendReadyToFight() {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.BattleReadyToFight()
        )
        webSocketHandler.sendMessage(message)
    }

    public fun sendCreatureAttacks(
        attackingCreatureIndex: Int,
        defendingCreatureIndex: Int,
        attackMoveIndex: Int,
        chanceModifier: Int
    ) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.FightCreatureAttack(
                attackingCreatureIndex,
                defendingCreatureIndex,
                attackMoveIndex,
                chanceModifier
            )
        )
        webSocketHandler.sendMessage(message)
    }

    fun notifyAttackIsFinished(creatureDied: Boolean, hasNextCreature: Boolean, teamWonIndex: Int) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.BattleFinishedAttack(
                creatureDied,
                hasNextCreature,
                teamWonIndex
            )
        )
        webSocketHandler.sendMessage(message)
    }

    fun sendInterruptDirectionChange(playerId: String, steps: Int) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.InterruptSwitch(
                playerId,
                steps
            )
        )
        webSocketHandler.sendMessage(message)
    }

    fun sendChangeDirectionMove(id: String, steps: Int, directionIndex: Int) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.DirectionChangeMessage(
                "changeDirectionMove",
                id,
                steps,
                directionIndex
            )
        )
        webSocketHandler.sendMessage((message))
    }

    public fun notifyTeamsHaveBeenSwitched() {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.FightSwitchTeams()
        )
        webSocketHandler.sendMessage(message)
    }

    public fun sendPlayerPortraitChangedMessage(playerId: String, imageResource: Int) {
        val message = Gson().toJson(
            com.example.notpokemon.websocketHandlers.dataobjects.ChangedPlayerPortrait(
                playerId,
                imageResource
            )
        )
        webSocketHandler.sendMessage(message)
    }

    companion object {
        lateinit var instance: EventHandlers
        lateinit var thisDevicePlayerId: String
        var hasConnectedToLobby = false
    }
}
