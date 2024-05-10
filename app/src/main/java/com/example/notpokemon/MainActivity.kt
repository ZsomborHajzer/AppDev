package com.example.notpokemon

import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit


class MainActivity : AppCompatActivity(R.layout.base_screen) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        MainActivity.instance = this

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            val battleManager = BattleManager()
            Thread(battleManager).start()
        }
    }


    companion object{
        lateinit var instance: MainActivity
    }
}

