package com.example.notpokemon.Views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.example.notpokemon.GameBoardFragment
import com.example.notpokemon.GameDirector
import com.example.notpokemon.R

class BoardView : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_board)
    }

}