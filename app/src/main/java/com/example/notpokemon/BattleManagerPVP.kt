package com.example.notpokemon

class BattleManagerPVP : BattleManager() {
    override val xpPerBattle: Int
        get() = 10

    override fun endFight(winner:Fighter){
        super.endFight(winner)
        val character = winner as PlayableCharacter
        character.onMove(character.currentSquare.nextSquare)
    }
}