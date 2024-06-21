package com.example.notpokemon.websocketHandlers.dataobjects

data class DirectionChangeMessage(
    val event: String,
    val id: String,
    val steps: Int,
    val directionIndex: Int
)
