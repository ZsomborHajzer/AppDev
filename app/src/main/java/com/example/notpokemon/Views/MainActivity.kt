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
}