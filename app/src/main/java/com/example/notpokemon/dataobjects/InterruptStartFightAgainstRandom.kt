package com.example.notpokemon.dataobjects

data class InterruptStartFightAgainstRandom(
    val playerID: String,
    val creatureTemplateId: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val event:String = "interruptStartFightAgainstRandom"
)