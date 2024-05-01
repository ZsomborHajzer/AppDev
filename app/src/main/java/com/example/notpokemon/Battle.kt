package com.example.notpokemon
import kotlin.random.Random

//Simulate how the battle logic would go, kinda
class Battle {
    //Setup
    val team1 = arrayOf<Creature>()
    val team2 = arrayOf<Creature>()
    val player1 = Player("Sillie", team1, 0)
    val player2 = Player("Sally", team2, 50)

    //Temporary function to create random creatures, can be changed or removed later
    fun generateCreature(): Creature {
        val types = arrayOf("Fire", "Water", "Earth", "Air")
        val hp = arrayOf(100, 150, 200, 500)
        val attackNames = arrayOf("Bite", "Throw", "Roll", "Whip", "Cry")

        val name = "Creature${(1..100).random()}"
        val type = types.random()
        val health = hp.random()
        val attackName = attackNames.random()
        return Creature(name, type, health, attackName)
    }

    //Roll die function
    fun rollDice(sides: Int): Int {
        return Random.nextInt(1, sides + 1)
    }

    // Battle logic for one round
    fun performRound(player1: Player, player2: Player) {
        var diceWinner = false

        while(diceWinner == false){
            //Roll die at the beginning of each round
            val player1Roll = rollDice(6)
            val player2Roll = rollDice(6)

            if (player1Roll > player2Roll) {
                println("${player1.getName()} goes first!")
                fight(player1, player2)
                diceWinner = true
            } else if (player2Roll > player1Roll) {
                println("${player2.getName()} goes first!")
                fight(player2, player1)
                diceWinner = true
            } else {
                println("It's a tie!")
            }
        }
    }

    // How attacking works
    fun fight(winningPlayer: Player, losingPlayer: Player) {
        val winningTeam = winningPlayer.getTeam()
        val losingTeam = losingPlayer.getTeam()
        val originalWinningTeam = winningTeam.clone()
        val originalLosingTeam = losingTeam.clone()

        var attackerIndex = 0
        var defenderIndex = 0
        var attackingTeam = winningTeam
        var defendingTeam = losingTeam

        //Two teams fighting
        while (winningTeam.isNotEmpty() && losingTeam.isNotEmpty()) { //Neither team has "lost" yet
            val attacker = attackingTeam[attackerIndex]
            val defender = defendingTeam[defenderIndex]

            println("${attacker.getName()} is fighting ${defender.getName()}!")
            attacker.attack(defender)

            // Check if the defender's creature is defeated
            if (defender.getHealthPoints() <= 0) {
                println("${defender.getName()} fainted!")
                // Remove the defeated creature from the defending player's team
                losingPlayer.removeCreature(defender)
            } else {
                // Increment defenderIndex only if the defender is not defeated
                defenderIndex++
            }

            // Switch teams if all creatures from the current attacking team have attacked
            if (attackerIndex == attackingTeam.lastIndex) {
                val temp = attackingTeam
                attackingTeam = defendingTeam
                defendingTeam = temp
                attackerIndex = 0
                defenderIndex = 0
                println("${losingPlayer.getName()} 's turn!")
            } else {
                attackerIndex++
            }

            println("Attacker team: ${attacker.getName()}, Defender team: ${defender.getName()}")
            println("Attacker index: $attackerIndex, Defender index: $defenderIndex") // Debugging print statement
        }

        if (winningTeam.isEmpty()) {
            println("${losingPlayer.getName()} wins!")
            val XPGain = losingPlayer.getXP() + 100 //Insert additional stats here?
            losingPlayer.setXP(XPGain)
        } else {
            println("${winningPlayer.getName()} wins!")
            val XPGain = winningPlayer.getXP() + 100 //Insert additional stats here?
            winningPlayer.setXP(XPGain)
        }

        //revert the teams back to how they were before the fight to continue the board game
        winningPlayer.setTeam(originalWinningTeam)
        losingPlayer.setTeam(originalLosingTeam)
    }

    //Battle logic, kinda
    fun play() {
        //Create teams for both players...this is for simulation purposes only
        for(i in 1..4){
            val newCreature = generateCreature()
            player1.addCreature(newCreature)
        }
        for(i in 1..4){
            val newCreature = generateCreature()
            player2.addCreature(newCreature)
        }

        //I decided to make a battle last 10 rounds for now, we can change this
        for (i in 1..10) {
            println("Round $i")
            performRound(player1, player2)
        }
    }
}

fun main() {
    val game = Battle()
    game.play()
}