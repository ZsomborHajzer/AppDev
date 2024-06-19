package com.example.notpokemon
import EventHandlers
import com.example.notpokemon.views.BoardView

//Simulate how the battle logic would go, kinda
open class BattleManager {
    //Setup
    public var fighter1 = Fighter("Sillie")
    public var fighter2 = Fighter("Sally")
    open protected val xpPerBattle = 5

    lateinit var originalFirstTeam: ArrayList<Creature>
    lateinit var originalSecondTeam: ArrayList<Creature>

    fun initializeFight(){
        val fightFragment = Fight(fighter1, fighter2)
        originalFirstTeam = fighter1.team.clone() as ArrayList<Creature>
        originalSecondTeam = fighter2.team.clone() as ArrayList<Creature>
        BoardView.instance.initializeFight(fightFragment)
    }

    open fun endFight(winner:Fighter){
        winner.addXP(xpPerBattle)

        //revert the teams back to how they were before the fight to continue the board game
        fighter1.team = originalFirstTeam
        fighter2.team = originalSecondTeam

        // heal creatures
        fighter1.healCreaturesFully()
        fighter2.healCreaturesFully()

        BoardView.instance.removeFight(Fight.instance)
        EventHandlers.instance.sendEndTurnMessage()
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
            val types = arrayOf("Haunted", "Space", "Aquatic", "Sugar") // TODO:: create enums
            val attackNames = arrayOf("Bite", "Throw", "Roll", "Whip", "Cry")

            val name = "Creature${(1..1000).random()}"
            val type = types.random()
            val attack = BiteAttack()
            attack.name = attackNames.random()
            val creature = ButterPig(attack)
            creature.creatureName = name
            creature.creatureType = type
            return creature
        }

        fun generateCreatureFromTemplate(creatureTemplateId:Int): Creature{
            when(creatureTemplateId){
                0 -> {
                    return ButterPig(BiteAttack())
                }
                1 -> {
                    return CandyLandBoss(BiteAttack())
                }
            }
            throw IllegalArgumentException("$creatureTemplateId template creature does not exist")
        }
    }
}