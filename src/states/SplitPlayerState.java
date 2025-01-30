package states;

import cards.Card;
import cards.Deck;
import game.Constants;
import participants.Player;
import states.PlayerState;
import states.NormalPlayerState;

import java.util.ArrayList;
import java.util.List;

public class SplitPlayerState implements PlayerState {
    private final Player player;
    private final List<Card> hand1;
    private final List<Card> hand2;
    private boolean playingFirstHand;

    public SplitPlayerState(Player player, Deck deck) {
        this.player = player;
        this.hand1 = new ArrayList<>();
        this.hand2 = new ArrayList<>();
        this.playingFirstHand = true;

        if (player.getHand().size() < 2) {
            throw new IllegalStateException("Player does not have enough cards to split.");
        }

        hand1.add(player.getHand().removeFirst());
        hand2.add(player.getHand().removeFirst());

        hand1.add(deck.drawCard());
        hand2.add(deck.drawCard());
    }

    @Override
    public void playTurn(Deck deck) {
        if (playingFirstHand) {
            if (isBusted(hand1)) {
                playingFirstHand = false;
            }
        } else {
            if (isBusted(hand2)) {
                player.setState(new NormalPlayerState(player));
            }
        }
    }

    @Override
    public void hit(Card card) {
        if (playingFirstHand) {
            hand1.add(card);
        } else {
            hand2.add(card);
        }
    }

    @Override
    public boolean wantsToHit(String input) {
        return input.equalsIgnoreCase("h");
    }

    @Override
    public boolean canSplit() {
        return false;
    }

    @Override
    public void split(Deck deck) {
        throw new IllegalStateException("Cannot split again after already splitting.");
    }

    @Override
    public boolean canDoubleDown() {
        return playingFirstHand ? hand1.size() == 2 : hand2.size() == 2;
    }

    @Override
    public void doubleDown(Deck deck) {
        if (canDoubleDown()) {
            hit(deck.drawCard());
            playingFirstHand = !playingFirstHand;
        }
    }

    private boolean isBusted(List<Card> hand) {
        int score = hand.stream().mapToInt(Card::getValue).sum();
        return score > Constants.BLACKJACK_LIMIT;
    }
}