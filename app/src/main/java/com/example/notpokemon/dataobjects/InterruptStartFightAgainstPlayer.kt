package com.example.notpokemon.dataobjects

data class InterruptStartFightAgainstPlayer(
    val player1ID: String,
    val player2ID: String,
    val timestamp: Long = System.currentTimeMillis(),
    val event:String = "interruptStartFightAgainstPlayer"
)