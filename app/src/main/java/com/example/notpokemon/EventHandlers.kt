package com.example.notpokemon
import android.content.Intent
import com.example.notpokemon.dataobjects.EndGame
import com.example.notpokemon.dataobjects.EndTurn
import com.example.notpokemon.dataobjects.MoveAction
import com.example.notpokemon.dataobjects.StartGame
import com.google.gson.Gson


class EventHandlers(
    private val uiInitializer: UIInitializer,
    private val webSocketHandler: WebSocketHandler
) {

    fun setupEventHandlers() {
        uiInitializer.connectButton.setOnClickListener {
            val usernameText = uiInitializer.username.text.toString()
            if (usernameText.isBlank()) {
                uiInitializer.usernameLayout.error = "This field is required!"
            }

            if (usernameText.isNotBlank() && !webSocketHandler.isInitialized()) {
                webSocketHandler.startWebSocket(
                    //url = "ws://10.0.2.2:8081",
                    url = "ws:/16.16.126.99:8081",
                    onOpen = {
                        uiInitializer.receivedMessages.setText("Connected to the server")

                        val intent = Intent(uiInitializer.activity, LobbyHost::class.java)
                        uiInitializer.activity.startActivity(intent)

                             },
                    onClose = { reason -> uiInitializer.receivedMessages.append("\nConnection is closing: $reason") },
                    onError = { error -> uiInitializer.receivedMessages.setText("Error: $error") }
                )
            }

            if (webSocketHandler.isInitialized()) {
                webSocketHandler.sendMessage("{\"username\": \"$usernameText\"}")
            }
        }

        uiInitializer.sendButton.setOnClickListener {
            var messageText = uiInitializer.messageInput.text.toString()
            val gson = Gson()
            messageText = when (messageText) {
                "1" -> gson.toJson(EndGame(event = "endGame", timeStamp = System.currentTimeMillis()))
                "2" -> gson.toJson(EndTurn(event = "endTurn", timeStamp = System.currentTimeMillis()))
                "3" -> gson.toJson(MoveAction(event = "moveAction", fromPosition = "1", toPosition = "6", timeStamp = System.currentTimeMillis()))
                "4" -> gson.toJson(StartGame(event = "startGame", timeStamp = System.currentTimeMillis()))
                else -> messageText
            }
            webSocketHandler.sendMessage(messageText)
        }
    }
}
