package com.example.notpokemon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notpokemon.dataobjects.Player

class LobbyHostActivity : AppCompatActivity() {

    private lateinit var player1Card: CardView
    private lateinit var player2Card: CardView
    private lateinit var player3Card: CardView
    private lateinit var player4Card: CardView

    private val playerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    "UPDATE_PLAYERS" -> {
                        val players = it.getParcelableArrayListExtra<Player>("data")
                        players?.let { playerList ->
                            updatePlayerCards(playerList)
                        }
                    }
                    "NEW_PLAYER" -> {
                        val player = it.getParcelableExtra<Player>("data")
                        player?.let { playerData ->
                            updatePlayerCard(playerData)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby_host)

        player1Card = findViewById(R.id.Player1CardView)
        player2Card = findViewById(R.id.Player2CardView)
        player3Card = findViewById(R.id.Player3CardView)
        player4Card = findViewById(R.id.Player4CardView)

        // Initially hide all player cards
        hideAllPlayerCards()

        // Register the broadcast receiver
        val filter = IntentFilter().apply {
            addAction("UPDATE_PLAYERS")
            addAction("NEW_PLAYER")
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(playerReceiver, filter)

        // Get the user data passed from ConnectPageActivity
        intent.getParcelableArrayListExtra<Player>("players")?.let { players ->
            updatePlayerCards(players)
        } ?: run {
            intent.getParcelableExtra<Player>("player")?.let { player ->
                updatePlayerCard(player)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerReceiver)
    }

    private fun hideAllPlayerCards() {
        player1Card.visibility = View.GONE
        player2Card.visibility = View.GONE
        player3Card.visibility = View.GONE
        player4Card.visibility = View.GONE
    }

    private fun updatePlayerCards(players: List<Player>) {
        hideAllPlayerCards()
        players.forEach { player ->
            updatePlayerCard(player)
        }
    }

    private fun updatePlayerCard(player: Player) {
        Log.d("LobbyHostActivity", "Updating card for ${player.username}")
        when (player.role) {
            "player1" -> {
                player1Card.visibility = View.VISIBLE
                findViewById<TextView>(R.id.textViewUsernamePlayer1).text = player.username
                findViewById<TextView>(R.id.textViewPlayer1Status).text = "Connected"
            }
            "player2" -> {
                player2Card.visibility = View.VISIBLE
                findViewById<TextView>(R.id.textViewUsernamePlayer2).text = player.username
                findViewById<TextView>(R.id.textViewPlayer2Status).text = "Connected"
            }
            "player3" -> {
                player3Card.visibility = View.VISIBLE
                findViewById<TextView>(R.id.textViewUsernamePlayer3).text = player.username
                findViewById<TextView>(R.id.textViewPlayer3Status).text = "Connected"
            }
            "player4" -> {
                player4Card.visibility = View.VISIBLE
                findViewById<TextView>(R.id.textViewUsernamePlayer4).text = player.username
                findViewById<TextView>(R.id.textViewPlayer4Status).text = "Connected"
            }
        }
    }
}

