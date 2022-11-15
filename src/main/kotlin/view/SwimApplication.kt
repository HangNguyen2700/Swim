package view

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

class SwimApplication : BoardGameApplication("Swim"), Refreshable{

    // Central service from which all others are created/accessed
    // also holds the currently active game
    private val rootService = RootService()

    private val gameScene = SwimNewGameScene()

//    init {
//
//        // all scenes and the application itself need too
//        // react to changes done in the service layer
//        rootService.addRefreshables(
//            this,
//            gameScene,
//            gameFinishedMenuScene,
//            newGameMenuScene
//        )
//
//        // This is just done so that the blurred background when showing
//        // the new game menu has content and looks nicer
//        rootService.gameService.startGame("Bob", "Alice")
//
//        this.showGameScene(gameScene)
//        this.showMenuScene(newGameMenuScene, 0)
//
//    }
}