package com.example.notpokemon.views.activities
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        // Redirect to ConnectPageActivity
        val intent = Intent(this@MainActivity, ConnectPageActivity::class.java)
        startActivity(intent)
        finish()  // Call finish to close MainActivity after redirection
    }

    companion object {
        lateinit var instance: MainActivity
    }
}


//TODO Figure out how to implement flow between views:
//    private lateinit var uiInitializer: UIInitializer
//    private lateinit var webSocketHandler: WebSocketHandler
//    private lateinit var eventHandlers: com.example.notpokemon.websocketHandlers.EventHandlers
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
//        eventHandlers = com.example.notpokemon.websocketHandlers.EventHandlers(uiInitializer, webSocketHandler)
//        eventHandlers.setupEventHandlers()
//    }
//}