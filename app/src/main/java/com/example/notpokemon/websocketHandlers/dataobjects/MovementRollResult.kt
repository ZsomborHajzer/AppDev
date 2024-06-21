package com.example.notpokemon.websocketHandlers.dataobjects

data class MovementRollResult(
    val event: String = "movementRollResult",
    val result: Int,
    val timeStamp: Long
)