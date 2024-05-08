package com.example.notpokemon

class Fight (var attackingPlayer:Player, var defendingPlayer: Player){
    var attackerIndex = 0
    var defenderIndex = 0

    fun start(): Player{
    //Two teams fighting
        while (attackingPlayer.getTeam().isNotEmpty() && defendingPlayer.getTeam().isNotEmpty()) { //Neither team has "lost" yet
            var attackerSize = attackingPlayer.getTeam().size
            var defenderSize = defendingPlayer.getTeam().size
            val attacker = attackingPlayer.getTeam()[attackerIndex]
            val defender = defendingPlayer.getTeam()[defenderIndex]

            println("${attacker.getName()} is fighting ${defender.getName()}!")
            attacker.attack(defender)

            // Check if the defender's creature is defeated
            if (defender.healthPoints <= 0) {
                println("${defender.getName()} fainted!")
                // Remove the defeated creature from the defending player's team
                defendingPlayer.removeCreature(defenderIndex)
            }

            // Switch teams if all creatures from the current attacking team have attacked
            if (attackerIndex >= attackingPlayer.getTeam().lastIndex || defenderIndex >= defendingPlayer.getTeam().lastIndex) {
                val temp = attackingPlayer
                attackingPlayer = defendingPlayer
                defendingPlayer = temp
                attackerIndex = 0
                defenderIndex = 0
                println("${attackingPlayer.getName()} 's turn!")
            } else {
                attackerIndex++
                defenderIndex++
            }

            println("Attacker team: ${attacker.getName()}, Defender team: ${defender.getName()}")
            println("Attacker index: $attackerIndex, Defender index: $defenderIndex") // Debugging print statement
        }
        if (attackingPlayer.getTeam().isEmpty()) {
            return defendingPlayer
        } else {
            return attackingPlayer
        }
    }
}