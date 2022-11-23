package view

import entity.Player
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

/**
 * [MenuScene] that is displayed when the game is finished. It shows the final result of the game
 * as well as the score. Also, there are two buttons: one for starting a new game and one for
 * restart the game with the same players.
 *
 * @constructor Create empty Swim game over scene
 */
class SwimGameOverScene(val players: MutableList<Player>) : MenuScene(), Refreshable {
    private val mainGrid: GridPane<GridPane<UIComponent>> = GridPane(950, 520, columns = 1, rows = 4)
    private val endGameGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val winGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val playersGrid = GridPane<UIComponent>(columns = 3, rows = 4)
    private val buttonsGrid = GridPane<UIComponent>(columns = 3, rows = 1)

    private val labelFont = Font(30)
    private val buttonTextFont = Font(30, color = Color.WHITE)

    private val headLineLabel = Label(
        text = "END GAME",
        height = 200,
        width = 1000,
        font = Font(100, color = Color.ORANGE, fontWeight = Font.FontWeight.BOLD),
    )

    private val winLabel = Label(
        text = "${players[0].getName()} win!",
        height = 35,
        width = 400,
        font = Font(50, color = Color.WHITE, fontWeight = Font.FontWeight.BOLD)
    )

    val restartButton: Button = Button(
        width = 300, height = 100,
        text = "Restart",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
    }

    val newGameButton: Button = Button(
        width = 300, height = 100,
        text = "New Game",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
    }

    init {
        endGameGrid[0, 0] = headLineLabel
        mainGrid[0, 0] = endGameGrid

        winGrid[0, 0] = winLabel
        winGrid.setRowHeight(0, 200)
        mainGrid[0, 1] = winGrid

        for (i in 0 until players.size) {
            val playerLabel = Label(
                text = "${players[i].getName()}:",
                font = labelFont
            )
            val scoreLabel = Label(
                text = players[i].getScore().toString(),
                font = labelFont
            )
            playersGrid[0, i] = playerLabel
            playersGrid[2, i] = scoreLabel
            playersGrid.setRowHeight(i, 50)
        }
        mainGrid[0, 2] = playersGrid

        buttonsGrid[0, 0] = restartButton
        buttonsGrid[2, 0] = newGameButton
        buttonsGrid.setColumnWidth(1, 100)
        buttonsGrid.setRowHeight(0, 270)
        mainGrid[0, 3] = buttonsGrid

        background = ColorVisual(224, 255, 255)
        addComponents(mainGrid)
    }
}