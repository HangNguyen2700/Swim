package view

import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.components.uicomponents.UIComponent
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import java.awt.Color

class SwimNewGameScene : MenuScene(), Refreshable {
    private val mainGrid: GridPane<GridPane<UIComponent>> = GridPane( 950, 520, columns = 1, rows = 3)
    private val newGameGrid = GridPane<UIComponent>(columns = 1, rows = 1)
    private val playersGrid = GridPane<UIComponent>(columns = 1, rows = 11)
    private val buttonsGrid = GridPane<UIComponent>(columns = 3, rows = 1)

    private val labelFont = Font(30)
    private val buttonTextFont = Font(30, color = Color.WHITE)

    private val headLineLabel = Label(
        text = "NEW GAME",
        height = 200,
        width = 1000,
        font = Font(100, color = Color.ORANGE, fontWeight = Font.FontWeight.BOLD),
    )

    private val p1Label = Label(
        width = 400, height = 35,
        text = "Player 1",
        font = labelFont
    )

    private val p2Label = Label(
        width = 400, height = 35,
        text = "Player 2",
        font = labelFont
    )

    private val p3Label = Label(
        width = 400, height = 35,
        text = "Player 3",
        font = labelFont
    )

    private val p4Label = Label(
        width = 400, height = 35,
        text = "Player 4",
        font = labelFont
    )

    private val playerLabels = listOf<Label>(p1Label, p2Label, p3Label, p4Label)

    private val p1Input: TextField = TextField(
        width = 400, height = 50,
        text = ""
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = this.text.isBlank() || p2Input.text.isBlank()
        }
    }

    private val p2Input: TextField = TextField(
        width = 400, height = 50,
        text = ""
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || this.text.isBlank()
        }
    }

    private val p3Input: TextField = TextField(
        width = 400, height = 50,
        text = ""
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || p2Input.text.isBlank()
        }
    }

    private val p4Input: TextField = TextField(
        width = 400, height = 50,
        text = ""
    ).apply {
        onKeyTyped = {
            startButton.isDisabled = p1Input.text.isBlank() || p2Input.text.isBlank()
        }
    }

    private val playerInputs = listOf<TextField>(p1Input, p2Input, p3Input, p4Input)

    val startButton: Button = Button(
        width = 300, height = 100,
        text = "Start Game",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
    }

    val quitButton: Button = Button(
        width = 300, height = 100,
        text = "Quit Game",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
    }

    init {
        newGameGrid[0,0] = headLineLabel
        mainGrid[0,0] = newGameGrid

        for(i in 0..10 step 3){
            playersGrid[0,i] = playerLabels[i/3]
            playersGrid[0,i+1] = playerInputs[i/3]
        }
        for(i in 0..10){
            playersGrid.setRowHeight(i,50)
        }
        for(i in 2..10 step 3){
            playersGrid.setRowHeight(i,20)
        }
        mainGrid[0,1] = playersGrid

        buttonsGrid[0,0] = quitButton
        buttonsGrid[2,0] = startButton
        buttonsGrid.setColumnWidth(1,100)
        buttonsGrid.setRowHeight(0,270)
        mainGrid[0,2] = buttonsGrid

        background = ColorVisual(224, 255, 255)
        addComponents(mainGrid)
    }

}