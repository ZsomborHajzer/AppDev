package com.example.notpokemon.websocketHandlers.dataobjects

data class FightSwitchTeams(
    val event: String = "fightSwitchedTeams",
    val timestamp: Long = System.currentTimeMillis()
)