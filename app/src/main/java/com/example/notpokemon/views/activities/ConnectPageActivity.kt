package com.example.notpokemon.views.activities
import com.example.notpokemon.websocketHandlers.EventHandlers
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notpokemon.R
import com.example.notpokemon.initialization.UIInitializer
import com.example.notpokemon.websocketHandlers.WebSocketHandler


class ConnectPageActivity : AppCompatActivity() {

    private lateinit var uiInitializer: UIInitializer
    private lateinit var webSocketHandler: WebSocketHandler
    private lateinit var eventHandlers: EventHandlers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_page)

        uiInitializer = UIInitializer(this)
        uiInitializer.initialize()

        webSocketHandler = WebSocketHandler {
            runOnUiThread {
                uiInitializer.receivedMessages.append("\n$it")
            }
        }

        eventHandlers = EventHandlers(uiInitializer, webSocketHandler)
        eventHandlers.setupEventHandlers()
    }
}

//
//class ConnectPageActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_connnect_page)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.connectPage)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}