package view

import entity.Card
import service.CardImageLoader
import service.RootService
import tools.aqua.bgw.components.ComponentView
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.layoutviews.GridPane
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.ImageVisual
import java.awt.Color

class SwimGameScene(private val rootService: RootService) : BoardGameScene(), Refreshable {
    private val mainGrid = GridPane<GridPane<ComponentView>>(950, 520, columns = 3, rows = 3)
    private val unusedCardsGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val openCardsGrid = GridPane<ComponentView>(columns = 3, rows = 2)
    private val playerCardsGrid = GridPane<ComponentView>(columns = 3, rows = 2)
    private val swimGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val leftButtonsGrid = GridPane<ComponentView>(columns = 1, rows = 2)
    private val rightButtonsGrid = GridPane<ComponentView>(columns = 1, rows = 4)

    private val labelFont = Font(30)
    private val buttonTextFont = Font(17, color = Color.WHITE)

    private var selectedAction = false
    private var knocked = false
    private var openCardIndex = 4
    private var playerCardIndex = 4

    private val swimLabel = Label(
        text = "SWIM",
        height = 100,
        width = 1000,
        font = Font(100, color = Color.ORANGE, fontWeight = Font.FontWeight.BOLD),
    )
    private val knockedLabel = Label(
        text = "No one has knocked",
        height = 100,
        width = 1000,
        font = labelFont,
    )

    private val unusedCards = LabeledStackView(label = "unused cards")

    private val openCardsLabel = Label(
        text = "open cards",
        font = labelFont
    )
    private val currentPlayerLabel = Label(
//        text = rootService.currentGame!!.getCurrentPlayer().getName(),
        text = "Current: ...",
        font = labelFont
    )

    private val openCard1 = LabeledStackView(label = "open card 1").apply {
        onMouseClicked = {
            openCardIndex = 0
            onSelectedCards(0, 1, "open")
        }
    }
    private val openCard2 = LabeledStackView(label = "open card 2").apply {
        onMouseClicked = {
            openCardIndex = 1
            onSelectedCards(1, 1, "open")
        }
    }
    private val openCard3 = LabeledStackView(label = "open card 3").apply {
        onMouseClicked = {
            openCardIndex = 2
            onSelectedCards(2, 1, "open")
        }
    }
    private val openCardsLabeledStackView = listOf(openCard1, openCard2, openCard3)

    private val playerCard1 = LabeledStackView(label = "player's card 1").apply {
        onMouseClicked = {
            playerCardIndex = 0
            onSelectedCards(0, 0, "player")
        }
    }
    private val playerCard2 = LabeledStackView(label = "player's card 2").apply {
        onMouseClicked = {
            playerCardIndex = 1
            onSelectedCards(1, 0, "player")
        }
    }
    private val playerCard3 = LabeledStackView(label = "player's card 3").apply {
        onMouseClicked = {
            playerCardIndex = 2
            onSelectedCards(2, 0, "player")
        }
    }
    private val playerCardsLabeledStackView = listOf(playerCard1, playerCard2, playerCard3)


    private fun onSelectedCards(column: Int, row: Int, cardType: String) {
        if (cardType == "Open Card") {
            openCardsGrid.setRowCenterMode(1, Alignment.CENTER)
            openCardsGrid.setCellCenterMode(column, row, Alignment.TOP_CENTER)
        } else {
            playerCardsGrid.setRowCenterMode(0, Alignment.CENTER)
            playerCardsGrid.setCellCenterMode(column, row, Alignment.TOP_CENTER)
        }
    }


    val flipCardButton: Button = Button(
        width = 200, height = 70,
        text = "Flip Card",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
        onMouseClicked = {
            playerCardsLabeledStackView.forEach { cardView ->
                when (cardView.peek().currentSide) {
                    CardView.CardSide.BACK -> cardView.peek().showFront()
                    CardView.CardSide.FRONT -> cardView.peek().showBack()
                }
            }
        }
    }

    val nextPlayerButton: Button = Button(
        width = 200, height = 70,
        text = "Next Player",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
        this.isDisabled = !selectedAction
        onMouseClicked = {
            rootService.gameService.nextPlayer()
        }
    }

    val exchangeOneCardButton: Button = Button(
        width = 200, height = 70,
        text = "Exchange One Card",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
        onMouseClicked = {
//            var playerCards = rootService.currentGame!!.getCurrentPlayer().getPlayerCards()
//            var openCards = rootService.currentGame!!.getOpenCards()
//            checkNotNull(playerCards){"game was not initialized"}
//            checkNotNull(openCards){"game was not initialized"}

            rootService.playerService.exchangeOneCard(playerCardIndex, openCardIndex)

            this.isDisabled = true
            exchangeAllCardsButton.isDisabled = true
            passButton.isDisabled = true
            knockButton.isDisabled = true
            nextPlayerButton.isDisabled = false
        }
    }

    val exchangeAllCardsButton: Button = Button(
        width = 200, height = 70,
        text = "Exchange All Cards",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
        onMouseClicked = {
            rootService.playerService.exchangeAllCards()

            exchangeOneCardButton.isDisabled = true
            this.isDisabled = true
            passButton.isDisabled = true
            knockButton.isDisabled = true
            nextPlayerButton.isDisabled = false
        }
    }

    val passButton: Button = Button(
        width = 200, height = 70,
        text = "Pass",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
        onMouseClicked = {
            rootService.playerService.pass()

            exchangeOneCardButton.isDisabled = true
            exchangeAllCardsButton.isDisabled = true
            this.isDisabled = true
            knockButton.isDisabled = true
            nextPlayerButton.isDisabled = false
        }
    }

    val knockButton: Button = Button(
        width = 200, height = 70,
        text = "Knock",
        font = buttonTextFont
    ).apply {
        visual = ColorVisual(70, 130, 180)
        onMouseClicked = {
            knocked = true
            knockedLabel.text = "${rootService.currentGame!!.getCurrentPlayer().getName()} has knocked"

            rootService.playerService.knock()

            exchangeOneCardButton.isDisabled = true
            exchangeAllCardsButton.isDisabled = true
            passButton.isDisabled = true
            this.isDisabled = true
            nextPlayerButton.isDisabled = false
        }
    }


    private fun showCardBack(
        cardStack: MutableList<Card>,
        cardImageLoader: CardImageLoader,
        stackView: List<LabeledStackView>
    ) {
        for (i in 0..2) {
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(
                    cardImageLoader.frontImageFor(
                        cardStack[i].suit,
                        cardStack[i].value
                    )
                ),
                back = ImageVisual(cardImageLoader.backImage)
            )
            cardView.showBack()
            stackView[i].clear()
            stackView[i].add(cardView)
        }
    }

    private fun showCardFront(
        cardStack: MutableList<Card>,
        cardImageLoader: CardImageLoader,
        stackView: List<LabeledStackView>
    ) {
        for (i in 0..2) {
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(
                    cardImageLoader.frontImageFor(
                        cardStack[i].suit,
                        cardStack[i].value
                    )
                ),
                back = ImageVisual(cardImageLoader.backImage)
            )
            cardView.showFront()
            stackView[i].clear()
            stackView[i].add(cardView)
        }
    }


    override fun refreshAfterStartGame() {
        val game = rootService.currentGame
        val openCards = game!!.getOpenCards()
        val playerCards = game.getCurrentPlayer().getPlayerCards()

        val cardImageLoader = CardImageLoader()

        showCardBack(playerCards, cardImageLoader, playerCardsLabeledStackView)
        showCardFront(openCards, cardImageLoader, openCardsLabeledStackView)

        currentPlayerLabel.text = game.getCurrentPlayer().getName()
        //nextPlayerName.text = game.players[1].name

        nextPlayerButton.isDisabled = true
    }

    override fun refreshAfterPlayerAction() {
        val game = rootService.currentGame
        val openCards = game!!.getOpenCards()
        val playerCards = game.getCurrentPlayer().getPlayerCards()

        val cardImageLoader = CardImageLoader()

        showCardFront(playerCards,cardImageLoader,playerCardsLabeledStackView)
        showCardFront(openCards,cardImageLoader,openCardsLabeledStackView)

        knockButton.isDisabled = !knocked
    }

    override fun refreshAfterNextPlayer() {
        val game = rootService.currentGame
        val openCards = game!!.getOpenCards()
        val playerCards = game.getCurrentPlayer().getPlayerCards()

        val cardImageLoader = CardImageLoader()

        showCardBack(playerCards,cardImageLoader,playerCardsLabeledStackView)

        currentPlayerLabel.text = rootService.currentGame!!.getCurrentPlayer().getName()

        openCardsGrid.setRowCenterMode(1,Alignment.CENTER)
        playerCardsGrid.setRowCenterMode(0,Alignment.CENTER)

        passButton.isDisabled = false
        knockButton.isDisabled = !knocked
        exchangeOneCardButton.isDisabled = false
        exchangeAllCardsButton.isDisabled = false
        nextPlayerButton.isDisabled = true
    }

    /**
     * TODO reset knockedPlayer and knockButton to original setup
     *
     */
    fun resetGameScene() {
        knocked = false
        knockedLabel.text = "No one has knocked"

        knockButton.isDisabled = false
    }

    init {
        swimGrid[0, 0] = swimLabel
        swimGrid[0, 1] = knockedLabel
        mainGrid[1, 1] = swimGrid

        unusedCardsGrid[0, 0] = unusedCards
        mainGrid[0, 0] = unusedCardsGrid

        for (i in 0..2) {
            openCardsGrid[i, 1] = openCardsLabeledStackView[i]
            playerCardsGrid[i, 0] = playerCardsLabeledStackView[i]
            openCardsGrid.setColumnWidth(i, 200)
            playerCardsGrid.setColumnWidth(i, 200)
        }
        openCardsGrid.setRowHeight(1, 300)
        playerCardsGrid.setRowHeight(0, 300)

        openCardsGrid[1, 0] = openCardsLabel
        playerCardsGrid[1, 1] = currentPlayerLabel
        mainGrid[1, 0] = openCardsGrid
        mainGrid[1, 2] = playerCardsGrid

        leftButtonsGrid[0, 0] = flipCardButton
        leftButtonsGrid[0, 1] = nextPlayerButton
        leftButtonsGrid.setRowHeight(1, 100)
        mainGrid[0, 2] = leftButtonsGrid

        rightButtonsGrid[0,0] = exchangeOneCardButton
        rightButtonsGrid[0,1] = exchangeAllCardsButton
        rightButtonsGrid[0,2] = passButton
        rightButtonsGrid[0,3] = knockButton
        for(i in 0..3){
            rightButtonsGrid.setRowHeight(i, 80)
        }
        mainGrid[2,2] = rightButtonsGrid

        background = ColorVisual(224, 255, 255)
        addComponents(mainGrid)
    }
}