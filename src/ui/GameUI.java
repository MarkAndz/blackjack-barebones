package ui;

import participants.Player;
import participants.Dealer;

public class GameUI {

    public static void displayWelcomeMessage() {
        System.out.println("Welcome to Blackjack!");
    }

    public static void displayPlayerHand(Player player) {
        System.out.println("Your hand: " + player.getHandSummary());
    }

    public static void displayDealerHand(Dealer dealer, boolean hideFirstCard) {
        if (hideFirstCard) {
            if (dealer.getHand().size() > 1) {
                System.out.println("Dealer's hand: [Hidden], " + dealer.getHand().get(1));
            } else {
                System.out.println("Dealer's hand: [Hidden]");
            }
        } else {
            System.out.println("Dealer's hand: " + dealer.getHandSummary());
        }
    }


    public static void displayFinalHands(Player player, Dealer dealer) {
        System.out.println("Final Hands:");
        System.out.println("Player: " + player.getHandSummary());
        System.out.println("Dealer: " + dealer.getHandSummary());
    }

    public static void displayGameResult(String result) {
        System.out.println(result);
    }

    public static void promptPlayerAction() {
        System.out.print("Hit (H), Stand (S)? ");
    }
}
