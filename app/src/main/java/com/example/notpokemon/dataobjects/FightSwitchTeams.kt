package com.example.notpokemon.dataobjects

data class FightSwitchTeams(
    val event:String = "fightSwitchedTeams",
    val timestamp: Long = System.currentTimeMillis()
)