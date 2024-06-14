package com.example.notpokemon

import EventHandlers
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notpokemon.dataobjects.Player
import com.example.notpokemon.views.BoardView

class LobbyHostActivity : AppCompatActivity() {

    private lateinit var player1Card: CardView
    private lateinit var player2Card: CardView
    private lateinit var player3Card: CardView
    private lateinit var player4Card: CardView

    private val playerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                when (it.action) {
                    "NEW_PLAYER" -> {
                        println("RAN NEW PLAYER")
                        val player = it.getParcelableExtra<Player>("data")
                        player?.let { playerData ->
                            players.add(player)
                            updatePlayerCard()
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
        Companion.instance = this

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
        updatePlayerCard()

        addStartButtonListener()
    }

    fun addStartButtonListener(){
        findViewById<Button>(R.id.button).setOnClickListener {
            EventHandlers.instance.sendStartGameMessage()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // Unregister the broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playerReceiver)
    }

    private fun hideAllPlayerCards() {
        player1Card.visibility = View.INVISIBLE
        player2Card.visibility = View.INVISIBLE
        player3Card.visibility = View.INVISIBLE
        player4Card.visibility = View.INVISIBLE
    }

    private fun updatePlayerCards(players: List<Player>) {
        hideAllPlayerCards()
        players.forEach { player ->
            updatePlayerCard()
        }
    }

    fun updatePlayerCard() {
        players.forEach { player ->
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
    companion object{
        lateinit var instance: LobbyHostActivity
        var players = ArrayList<Player>()
        fun hasInstance():Boolean{
            return ::instance.isInitialized
        }
    }
}

