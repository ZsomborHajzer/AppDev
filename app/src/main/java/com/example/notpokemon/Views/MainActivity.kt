package com.example.notpokemon.Views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.instance = this

        // example redirect ;)
        val intent = Intent(this@MainActivity, BoardView::class.java)
        startActivity(intent);
    }

    companion object{
        lateinit var instance: MainActivity
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
}