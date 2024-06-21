package com.example.notpokemon.views.activities

import com.example.notpokemon.websocketHandlers.EventHandlers
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.notpokemon.R
import com.example.notpokemon.playerObjects.Player

class LobbyHostActivity : AppCompatActivity() {

    private lateinit var player1Card: CardView
    private lateinit var player2Card: CardView
    private lateinit var player3Card: CardView
    private lateinit var player4Card: CardView

    private val playerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println(intent)
            intent?.let {
                when (it.action) {
                    "NEW_PLAYER" -> {
                        println("RAN NEW PLAYER")
                        val player = it.getParcelableExtra<Player>("data")
                        player?.let { playerData ->
                            players.add(player)
                            updatePlayerCards()
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
        instance = this

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
        updatePlayerCards()

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

    fun updatePlayerCards() {
        players.forEach { player ->
            Log.d("LobbyHostActivity", "Updating card for ${player.username}")
            if(player.imageResource == 0){
                player.imageResource = Player.imageResources[0]
            }
            when (player.role) {
                "player1" -> {
                    player1Card.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textViewUsernamePlayer1).text = player.username
                    findViewById<TextView>(R.id.textViewPlayer1Status).text = "Connected"
                    val image = findViewById<ImageView>(R.id.imageViewPlayer1)
                    image.setImageResource(player.imageResource)
                    if(player.id == EventHandlers.thisDevicePlayerId){ // if it's this client's instance
                        image.setOnClickListener(PortraitOnClickListener(image, player))
                    }
                }

                "player2" -> {
                    player2Card.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textViewUsernamePlayer2).text = player.username
                    findViewById<TextView>(R.id.textViewPlayer2Status).text = "Connected"
                    val image = findViewById<ImageView>(R.id.imageViewPlayer2)
                    image.setImageResource(player.imageResource)
                    if(player.id == EventHandlers.thisDevicePlayerId){ // if it's this client's instance
                        image.setOnClickListener(PortraitOnClickListener(image, player))
                    }
                }

                "player3" -> {
                    player3Card.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textViewUsernamePlayer3).text = player.username
                    findViewById<TextView>(R.id.textViewPlayer3Status).text = "Connected"
                    val image = findViewById<ImageView>(R.id.imageViewPlayer3)
                    image.setImageResource(player.imageResource)
                    if(player.id == EventHandlers.thisDevicePlayerId){ // if it's this client's instance
                        image.setOnClickListener(PortraitOnClickListener(image, player))
                    }
                }

                "player4" -> {
                    player4Card.visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textViewUsernamePlayer4).text = player.username
                    findViewById<TextView>(R.id.textViewPlayer4Status).text = "Connected"
                    val image = findViewById<ImageView>(R.id.imageViewPlayer4)
                    image.setImageResource(player.imageResource)
                    if(player.id == EventHandlers.thisDevicePlayerId){ // if it's this client's instance
                        image.setOnClickListener(PortraitOnClickListener(image, player))
                    }
                }
            }
        }
        val textString = "Lobby Size "+ players.size +"/4"
        findViewById<TextView>(R.id.lobbySizeText).text = textString
    }

    inner class PortraitOnClickListener(val view: ImageView, val player: Player): View.OnClickListener{
        override fun onClick(p0: View?) {
            player.cycleImage()
            Log.d("onclick portrait", "clicked")
            EventHandlers.instance.sendPlayerPortraitChangedMessage(player.id, player.imageResource)
        }

    }
    companion object{
        lateinit var instance: LobbyHostActivity
        var players = ArrayList<Player>()

        fun getPlayerById(id:String): Player {
            for (player in players){
                if(player.id == id){
                    return player
                }
            }
            throw IllegalStateException("player does not exist")
        }
        fun hasInstance():Boolean{
            return Companion::instance.isInitialized
        }
    }
}

