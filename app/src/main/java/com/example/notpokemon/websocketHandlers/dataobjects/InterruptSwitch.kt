package com.example.notpokemon.websocketHandlers.dataobjects

data class InterruptSwitch(
    val playerID: String,
    val steps: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val event: String = "interruptSwitch"
)