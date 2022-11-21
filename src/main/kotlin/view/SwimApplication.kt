package view

import entity.Player
import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

class SwimApplication : BoardGameApplication("Swim"), Refreshable{

    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    private val gameScene = SwimGameScene(rootService)

    private val newGameScene = SwimNewGameScene().apply {
        startButton.onMouseClicked = {
            rootService.currentGame = null

            var playerNames : MutableList<String> = mutableListOf()
            playerNames.add(p1Input.text)
            playerNames.add(p2Input.text)
            playerNames.add(p3Input.text)
            playerNames.add(p4Input.text)
            playerNames.removeIf { playerName -> playerName.isBlank() }
            rootService.gameService.startGame(playerNames)

            checkNotNull(rootService.currentGame)
        }

        quitButton.onMouseClicked = {
            exit()
        }
    }

    init{
        rootService.addRefreshables(
            this,
            newGameScene,
            gameScene,
        )
        this.showGameScene(gameScene)
        this.showMenuScene(newGameScene)
    }

    override fun refreshAfterStartGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterEndGame(players: MutableList<Player>) {
        val gameOverScene = SwimGameOverScene(players)

        gameOverScene.apply {
            gameScene.resetGameScene()

            restartButton.onMouseClicked = {
                rootService.currentGame = null
                var playerNames : MutableList<String> = mutableListOf()
                players.forEachIndexed{index, _  -> playerNames.add(players[index].getName())}
                rootService.gameService.startGame(playerNames)

                checkNotNull(rootService.currentGame)
            }
            newGameButton.onMouseClicked = {
                this@SwimApplication.showMenuScene(newGameScene)
            }

            this@SwimApplication.showMenuScene(gameOverScene)
        }
    }

}