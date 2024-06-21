package com.example.notpokemon.websocketHandlers.dataobjects

data class EndGame(val event: String, val timeStamp: Long = System.currentTimeMillis())