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
        initialDeal();
        GameUI.displayDealerHand(dealer, true);

        handleSplitting();
        handleDoublingDown();

        playerTurn();

        dealerTurn();
        determineWinner();
    }

    private void initialDeal() {
        dealCardToPlayer();
        dealCardToDealer();
        dealCardToPlayer();
        dealCardToDealer();
    }

    private void dealCardToPlayer() {
        player.hit(deck.drawCard());
    }

    private void dealCardToDealer() {
        dealer.receiveCard(deck.drawCard());
    }

    //Split logic
    private void handleSplitting() {
        if (player.canSplit()) {
            GameUI.displayPlayerHand(player);
            GameUI.displayGameResult("You have the option to split. Do you want to?(Y/N)");
            String input = getPlayerChoice();
            if (input.equals("y")) {
                player.split(deck);
                GameUI.displayPlayerHand(player);
            }
        }
    }

    //Double Down
    private void handleDoublingDown() {
        if (player.canDoubleDown()) {
            GameUI.displayPlayerHand(player);
            GameUI.displayGameResult("You have the option to double down. Do you want to?(Y/N)");
            String input = getPlayerChoice();
            if (input.equals("y")) {
                player.doubleDown(deck);
                if (player.getState() instanceof DoubleDownPlayerState) {
                    return;
                }
            }
        }
    }

    private void playerTurn() {
        while (!(player.getState() instanceof DoubleDownPlayerState)) {
            GameUI.displayPlayerHand(player);
            GameUI.promptPlayerAction();
            String input = getPlayerChoice();

            if (input.equalsIgnoreCase("H")) {  //Hit
                dealCardToPlayer();
                if (player.isLost()) {
                    GameUI.displayGameResult("You lost. Dealer wins.");
                    return;
                }
            } else if (input.equalsIgnoreCase("S")) {  //Stand
                return;
            } else {
                GameUI.displayGameResult("Invalid input. Try again.");
            }
        }
    }

    private void dealerTurn() {
        if(player.isLost())
            return;
        while (dealer.shouldHit()) {
            dealCardToDealer();
        }
    }

    private void determineWinner() {
        GameUI.displayFinalHands(player, dealer);

        if (player.isLost()) {
            GameUI.displayGameResult("Dealer wins.");
        } else if (dealer.isLost() || player.calculateScore() > dealer.calculateScore()) {
            GameUI.displayGameResult("Player wins.");
        } else if (player.calculateScore() < dealer.calculateScore()) {
            GameUI.displayGameResult("Dealer wins.");
        } else {
            GameUI.displayGameResult("Tie.");
        }
    }

    private String getPlayerChoice()
    {
        return scanner.nextLine().trim().toLowerCase();
    }
}