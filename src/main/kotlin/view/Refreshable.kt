package view

import entity.Player

/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing
 * UI classes only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */
interface Refreshable {

    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshAfterStartGame() {}


    /**
     * perform refreshes that are necessary after player played card (i.e., knock, pass,...)
     *
     */
    fun refreshAfterPlayerAction() {}

    /**
     * perform refreshes that are necessary after the last round was played
     *
     * @param players
     */
    fun refreshAfterEndGame(players : MutableList<Player>){}

    /**
     * Refresh after current player's turn finished and next player was called
     *
     */
    fun refreshAfterNextPlayer(){}

    /**
     * Refresh after score of current player was updated
     *
     */
    fun refreshAfterUpdateScore(){}
}