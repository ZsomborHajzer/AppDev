package com.example.notpokemon
import com.example.notpokemon.views.BoardView
import kotlin.random.Random

//Simulate how the battle logic would go, kinda
class BattleManager : Runnable {
    //Setup
    private val team1 = ArrayList<Creature>()
    private val team2 = ArrayList<Creature>()
    public var fighter1 = Fighter("Sillie")
    public var fighter2 = Fighter("Sally")
    private val xpPerBattle = 500

    override fun run() {
        performRound(fighter1, fighter2)
    }

    fun playWithRandomCreatures() {
        //Create teams for both players...this is for simulation purposes only
        for(i in 1..4){
            val newCreature = generateCreature()
            fighter1.addCreature(newCreature)
        }
        for(i in 1..4){
            val newCreature = generateCreature()
            fighter2.addCreature(newCreature)
        }

        performRound(fighter1, fighter2)
    }

    // Battle logic for one round
    fun performRound(fighter1: Fighter, fighter2: Fighter) {
        var diceWinner = false

        while(diceWinner == false){
            //Roll die at the beginning of each round
            val player1Roll = rollDice(6)
            val player2Roll = rollDice(6)

            if (player1Roll > player2Roll) {
                println("${fighter1.getName()} goes first!")
                fight(fighter1, fighter2)
                diceWinner = true
            } else if (player2Roll > player1Roll) {
                println("${fighter2.getName()} goes first!")
                fight(fighter2, fighter1)
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
    fun fight(firstAttackingFighter: Fighter, firstDefendingFighter: Fighter) {
        var attackingPlayer = firstAttackingFighter
        var defendingPlayer = firstDefendingFighter
        val originalWinningTeam = attackingPlayer.team.clone() as ArrayList<Creature>
        val originalLosingTeam = defendingPlayer.team.clone() as ArrayList<Creature>

        val fight = Fight(attackingPlayer, defendingPlayer)

        initializeFight(fight)
        fight.startFight()
        val winner = awaitUntilFightIsFinished(fight)

        winner.addXP(xpPerBattle)

        //revert the teams back to how they were before the fight to continue the board game
        firstAttackingFighter.team = originalWinningTeam
        firstDefendingFighter.team = originalLosingTeam

        // heal creatures
        firstAttackingFighter.healCreaturesFully()
        firstDefendingFighter.healCreaturesFully()

        BoardView.instance.removeFight(fight)
    }

    private fun initializeFight(fightFragment: Fight){
        BoardView.instance.initializeFight(fightFragment)
    }

    /**
     * NEVER CALL THIS FROM UI THREAD
     * make sure you actually start the fight before calling this.
     * returns winning player
     **/
    public fun awaitUntilFightIsFinished(fight: Fight): Fighter{
        while (fight.isFinished == false){
            Thread.sleep(500)
        }
        return fight.winner
    }
    companion object{
        fun generateCreature(): Creature {
            val types = arrayOf("Fire", "Water", "Earth", "Air") // TODO:: create enums
            val hp = arrayOf(100, 150, 200, 500)
            val attackNames = arrayOf("Bite", "Throw", "Roll", "Whip", "Cry")

            val name = "Creature${(1..1000).random()}"
            val type = types.random()
            val health = hp.random()
            val attackName = attackNames.random()
            return Creature(name, type, health, attackName)
        }
    }
}

fun main() {
    val game = BattleManager()
    game.playWithRandomCreatures()
}