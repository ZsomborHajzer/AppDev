package com.example.notpokemon.dataobjects

data class InterruptStartFightAgainstPlayer(val event:String = "interruptStartFightAgainstPlayer", val player1ID: String, val player2ID: String, val timestamp: Long)