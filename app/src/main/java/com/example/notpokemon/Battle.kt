package com.example.notpokemon
import kotlin.random.Random

//Simulate how the battle logic would go, kinda
class Battle {
    //Setup
    val team1 = ArrayList<Creature>()
    val team2 = ArrayList<Creature>()
    val player1 = Player("Sillie", team1, 0)
    val player2 = Player("Sally", team2, 50)
    val xpPerBattle = 500

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

        performRound(player1, player2)

    }

    //Temporary function to create random creatures, can be changed or removed later
    fun generateCreature(): Creature {
        val types = arrayOf("Fire", "Water", "Earth", "Air")
        val hp = arrayOf(100, 150, 200, 500)
        val attackNames = arrayOf("Bite", "Throw", "Roll", "Whip", "Cry")

        val name = "Creature${(1..1000).random()}"
        val type = types.random()
        val health = hp.random()
        val attackName = attackNames.random()
        return Creature(name, type, health, attackName)
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

    //Roll die function
    fun rollDice(sides: Int): Int {
        return Random.nextInt(1, sides + 1)
    }

    // How attacking works
    fun fight(firstAttackingPlayer: Player, firstDefendingPlayer: Player) {
        var attackingPlayer = firstAttackingPlayer
        var defendingPlayer = firstDefendingPlayer
        val originalWinningTeam = attackingPlayer.getTeam().clone() as ArrayList<Creature>
        val originalLosingTeam = defendingPlayer.getTeam().clone() as ArrayList<Creature>

        val winner = Fight(attackingPlayer, defendingPlayer).start()
        winner.addXP(xpPerBattle)

        //revert the teams back to how they were before the fight to continue the board game
        firstAttackingPlayer.setTeam(originalWinningTeam)
        firstDefendingPlayer.setTeam(originalLosingTeam)

        // heal creatures
        firstAttackingPlayer.healCreaturesFully()
        firstDefendingPlayer.healCreaturesFully()
    }
}

fun main() {
    val game = Battle()
    game.play()
}