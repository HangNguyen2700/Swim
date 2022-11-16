package view

import entity.Card
import entity.Player
import tools.aqua.bgw.core.BoardGameApplication

class SopraApplication : BoardGameApplication("SoPra Game") {

//   private val helloScene = HelloScene()
//    private val newGameScene = SwimNewGameScene()

    var player1 = Player("Bob", emptyList<Card>().toMutableList())
    var score1 = player1.setScore(20.0)
    var player2 = Player("Alice", emptyList<Card>().toMutableList())
    var score2 = player2.setScore(17.0)
    var player3 = Player("Ana", emptyList<Card>().toMutableList())
    var score3 = player3.setScore(3.5)
    var player4 = Player("Ana", emptyList<Card>().toMutableList())
    var score4 = player4.setScore(1.5)
    val players = mutableListOf<Player>(player1, player2, player3, player4)
    private val gameOverScene = SwimGameOverScene(players)


   init {
//        this.showGameScene(helloScene)
       this.showMenuScene(gameOverScene)
    }

}

