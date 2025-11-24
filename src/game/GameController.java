package game;

import cards.Deck;
import cards.DeckFactory;
import cards.DeckType;
import participants.Player;
import participants.Dealer;
import states.DoubleDownPlayerState;
import ui.GameUI;
import java.util.Scanner;

public class GameController {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final Scanner scanner;

    public GameController()
    {
        this.deck = DeckFactory.createDeck(DeckType.STANDARD);
        this.player = new Player();
        this.dealer = new Dealer();
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        GameUI.displayWelcomeMessage();
        boolean askedAboutSplit = false;
        boolean askedAboutDouble = false;
        boolean playerResolvedTurn = false;
        boolean playerAlreadyBust = false;

        int dealsMade = 0;
        while (dealsMade < 4) {
            if (dealsMade % 2 == 0) {
                player.hit(deck.drawCard());
            } else {
                dealer.receiveCard(deck.drawCard());
            }
            dealsMade++;
            if (dealsMade == 2) {
                GameUI.displayDealerHand(dealer, true);
            }
        }
        GameUI.displayPlayerHand(player);

        while (!playerResolvedTurn) {
            if (!askedAboutSplit && player.canSplit()) {
                GameUI.displayGameResult("You have the option to split. Do you want to?(Y/N)");
                String splitChoice = scanner.nextLine().trim().toLowerCase();
                if (splitChoice.equals("y") || splitChoice.equals("yes")) {
                    player.split(deck);
                    askedAboutSplit = true;
                    GameUI.displayPlayerHand(player);
                    continue;
                } else if (splitChoice.equals("n") || splitChoice.equals("no")) {
                    askedAboutSplit = true;
                } else {
                    GameUI.displayGameResult("I did not understand that answer, so I will ask you again later.");
                    continue;
                }
            }

            if (!askedAboutDouble && player.canDoubleDown()) {
                GameUI.displayPlayerHand(player);
                GameUI.displayGameResult("You have the option to double down. Do you want to?(Y/N)");
                String doubleChoice = scanner.nextLine().trim().toLowerCase();
                if (doubleChoice.equals("y") || doubleChoice.equals("yes")) {
                    player.doubleDown(deck);
                    askedAboutDouble = true;
                    if (player.getState() instanceof DoubleDownPlayerState) {
                        playerResolvedTurn = true;
                        continue;
                    }
                } else if (doubleChoice.equals("n") || doubleChoice.equals("no")) {
                    askedAboutDouble = true;
                } else {
                    GameUI.displayGameResult("That did not look like a yes/no answer, so we will revisit it.");
                    continue;
                }
            }

            if (player.getState() instanceof DoubleDownPlayerState) {
                playerResolvedTurn = true;
                continue;
            }

            GameUI.promptPlayerAction();
            String actionInput = scanner.nextLine().trim();
            if (actionInput.isEmpty()) {
                GameUI.displayGameResult("Please enter an action so we can continue.");
                continue;
            }

            char actionCode = Character.toLowerCase(actionInput.charAt(0));
            if (actionCode == 'h') {
                player.hit(deck.drawCard());
                GameUI.displayPlayerHand(player);
                if (player.isLost()) {
                    GameUI.displayGameResult("You lost. Dealer wins.");
                    playerAlreadyBust = true;
                    playerResolvedTurn = true;
                }
            } else if (actionCode == 's') {
                playerResolvedTurn = true;
            } else if (actionCode == 'd') {
                if (player.canDoubleDown()) {
                    player.doubleDown(deck);
                    if (!(player.getState() instanceof DoubleDownPlayerState)) {
                        GameUI.displayGameResult("Double down executed, but you still get to choose further actions.");
                        askedAboutDouble = true;
                    } else {
                        playerResolvedTurn = true;
                    }
                } else {
                    GameUI.displayGameResult("Cannot double down right now. Try another action.");
                }
            } else {
                GameUI.displayGameResult("Invalid input. Try again.");
            }
        }

        GameUI.displayDealerHand(dealer, false);

        boolean dealerFinished = playerAlreadyBust;
        while (!dealerFinished) {
            boolean dealerWantsToHit = dealer.shouldHit();
            if (dealerWantsToHit) {
                dealer.receiveCard(deck.drawCard());
            }
            if (!dealerWantsToHit || dealer.isLost()) {
                dealerFinished = true;
            }
        }

        GameUI.displayFinalHands(player, dealer);
        int playerScore = player.calculateScore();
        int dealerScore = dealer.calculateScore();
        if (playerAlreadyBust) {
            GameUI.displayGameResult("Dealer wins.");
        } else if (dealer.isLost()) {
            GameUI.displayGameResult("Player wins.");
        } else if (playerScore > dealerScore) {
            GameUI.displayGameResult("Player wins.");
        } else if (playerScore < dealerScore) {
            GameUI.displayGameResult("Dealer wins.");
        } else {
            GameUI.displayGameResult("Tie.");
        }
    }
}