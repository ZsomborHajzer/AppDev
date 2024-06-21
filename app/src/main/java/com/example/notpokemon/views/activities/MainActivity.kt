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
