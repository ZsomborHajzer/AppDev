package com.example.notpokemon.Views
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.notpokemon.ConnectPageActivity
import com.example.notpokemon.EventHandlers
import com.example.notpokemon.UIInitializer
import com.example.notpokemon.WebSocketHandler


class MainActivity : ComponentActivity() {

    private lateinit var uiInitializer: UIInitializer
    private lateinit var webSocketHandler: WebSocketHandler
    private lateinit var eventHandlers: EventHandlers
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.instance = this

        // Redirect to ConnectPageActivity
        val intent = Intent(this@MainActivity, ConnectPageActivity::class.java)
        startActivity(intent)
        uiInitializer = UIInitializer(this)
        uiInitializer.initialize()

        webSocketHandler = WebSocketHandler {
            uiInitializer.receivedMessages.append("\n$it")
        }

        eventHandlers = EventHandlers(uiInitializer, webSocketHandler)
        eventHandlers.setupEventHandlers()
    }

    companion object {
        lateinit var instance: MainActivity
    }
}


    //TODO Figure out how to implement flow between views:
//    private lateinit var uiInitializer: UIInitializer
//    private lateinit var webSocketHandler: WebSocketHandler
//    private lateinit var eventHandlers: EventHandlers
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        uiInitializer = UIInitializer(this)
//        uiInitializer.initialize()
//
//        webSocketHandler = WebSocketHandler {
//            uiInitializer.receivedMessages.append("\n$it")
//        }
//
//        eventHandlers = EventHandlers(uiInitializer, webSocketHandler)
//        eventHandlers.setupEventHandlers()
//    }
//}