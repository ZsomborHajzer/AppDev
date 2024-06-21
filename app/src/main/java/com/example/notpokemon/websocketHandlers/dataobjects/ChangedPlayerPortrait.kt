package com.example.notpokemon.websocketHandlers.dataobjects

data class ChangedPlayerPortrait(
    val player: String,
    val imageResource: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val event: String = "changedPlayerPortrait"
)