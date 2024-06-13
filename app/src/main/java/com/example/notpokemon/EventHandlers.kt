import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notpokemon.LobbyHostActivity
import com.example.notpokemon.UIInitializer
import com.example.notpokemon.WebSocketHandler
import com.example.notpokemon.dataobjects.EndGame
import com.example.notpokemon.dataobjects.EndTurn
import com.example.notpokemon.dataobjects.MoveAction
import com.example.notpokemon.dataobjects.Player
import com.example.notpokemon.dataobjects.StartGame
import com.google.gson.Gson
import com.google.gson.JsonObject

class EventHandlers(
    private val uiInitializer: UIInitializer,
    private val webSocketHandler: WebSocketHandler
) {

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
                "1" -> gson.toJson(EndGame(event = "endGame", timeStamp = System.currentTimeMillis()))
                "2" -> gson.toJson(EndTurn(event = "endTurn", timeStamp = System.currentTimeMillis()))
                "3" -> gson.toJson(MoveAction(event = "moveAction", fromPosition = "1", toPosition = "6", timeStamp = System.currentTimeMillis()))
                "4" -> gson.toJson(StartGame(event = "startGame", timeStamp = System.currentTimeMillis()))
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
                val players = gson.fromJson(jsonObject.get("players").asJsonArray, Array<Player>::class.java).toList()
                 LobbyHostActivity.players = ArrayList(players)
            }
            "connect" -> {
                val player = gson.fromJson(message, Player::class.java)
                sendPlayerBroadcast("NEW_PLAYER", player)
                getPlayersArray().add(player)
                tryStartLobbyHostActivity()
            }
        }
    }

    private fun getPlayersArray():ArrayList<Player>{
        return LobbyHostActivity.players
    }
    private fun tryStartLobbyHostActivity(){
        if(!LobbyHostActivity.hasInstance()) {
            val intent = Intent(uiInitializer.activity, LobbyHostActivity::class.java)
            uiInitializer.activity.startActivity(intent)
        }
    }

    private fun sendPlayerBroadcast(action: String, player: Player) {
        val intent = Intent(action)
        intent.putExtra("data", player)
        LocalBroadcastManager.getInstance(uiInitializer.activity).sendBroadcast(intent)
    }
}
