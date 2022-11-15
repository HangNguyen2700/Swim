package view

import tools.aqua.bgw.core.BoardGameApplication

class SopraApplication : BoardGameApplication("SoPra Game") {

//   private val helloScene = HelloScene()
    private val newGameScene = SwimNewGameScene()

   init {
//        this.showGameScene(helloScene)
       this.showMenuScene(newGameScene)
    }

}

