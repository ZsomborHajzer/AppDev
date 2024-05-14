package com.example.notpokemon.Views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // example redirect ;)
        val intent = Intent(this@MainActivity, BoardView::class.java)
        startActivity(intent);
    }
}