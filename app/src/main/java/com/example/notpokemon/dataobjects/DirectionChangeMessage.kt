package com.example.notpokemon.dataobjects

data class DirectionChangeMessage(val event: String, val id: String, val steps: Int, val directionIndex: Int)
