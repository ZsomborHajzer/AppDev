package com.example.notpokemon.websocketHandlers.dataobjects

data class MoveAction(
    val event: String,
    val fromPosition: String,
    val toPosition: String,
    val timeStamp: Long
)