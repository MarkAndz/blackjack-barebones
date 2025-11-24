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
    private boolean hand1Finished;
    private boolean hand2Finished;

    public SplitPlayerState(Player player, Deck deck) {
        this.player = player;
        this.hand1 = new ArrayList<>();
        this.hand2 = new ArrayList<>();
        this.playingFirstHand = true;
        this.hand1Finished = false;
        this.hand2Finished = false;

        if (player.getHand().size() < 2) {
            throw new IllegalStateException("Player does not have enough cards to split.");
        }

        List<Card> original = player.getHand();
        Card firstCard = null;
        Card secondCard = null;
        for (int i = 0; i < original.size(); i++) {
            Card current = original.get(i);
            if (i == 0) {
                firstCard = current;
            } else if (i == 1) {
                secondCard = current;
            }
        }

        if (firstCard != null) {
            hand1.add(firstCard);
        }
        if (secondCard != null) {
            hand2.add(secondCard);
        }

        original.clear();

        hand1.add(deck.drawCard());
        hand2.add(deck.drawCard());
        original.addAll(hand1);
        original.addAll(hand2);
    }

    @Override
    public void playTurn(Deck deck) {
        if (playingFirstHand) {
            int tempScore = 0;
            int aceCount = 0;
            for (Card card : hand1) {
                tempScore += card.getValue();
                if (card.getRank().equals("A")) {
                    aceCount++;
                }
            }
            while (tempScore > Constants.BLACKJACK_LIMIT && aceCount > 0) {
                tempScore -= (Constants.ACE_HIGH_VALUE - Constants.ACE_LOW_VALUE);
                aceCount--;
            }
            if (tempScore > Constants.BLACKJACK_LIMIT || hand1.size() >= 5) {
                playingFirstHand = false;
                hand1Finished = true;
            }
        } else {
            int tempScore = 0;
            int aceCount = 0;
            for (Card card : hand2) {
                tempScore += card.getValue();
                if (card.getRank().equals("A")) {
                    aceCount++;
                }
            }
            while (tempScore > Constants.BLACKJACK_LIMIT && aceCount > 0) {
                tempScore -= (Constants.ACE_HIGH_VALUE - Constants.ACE_LOW_VALUE);
                aceCount--;
            }
            if (tempScore > Constants.BLACKJACK_LIMIT || hand2.size() >= 5) {
                hand2Finished = true;
                player.setState(new NormalPlayerState(player));
                List<Card> original = player.getHand();
                original.clear();
                original.addAll(hand1);
                original.addAll(hand2);
            }
        }
    }

    @Override
    public void hit(Card card) {
        if (playingFirstHand) {
            hand1.add(card);
            List<Card> original = player.getHand();
            original.clear();
            original.addAll(hand1);
            original.addAll(hand2);
            if (hand1Finished) {
                playingFirstHand = false;
            }
        } else {
            hand2.add(card);
            List<Card> original = player.getHand();
            original.clear();
            original.addAll(hand1);
            original.addAll(hand2);
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
        if (playingFirstHand) {
            if (hand1.size() == 2) {
                return true;
            }
            return !hand1Finished && hand1.size() == 3 && hand1.get(0).getRank().equals(hand1.get(1).getRank());
        }
        if (!hand2Finished && hand2.size() == 2) {
            return true;
        }
        return false;
    }

    @Override
    public void doubleDown(Deck deck) {
        if (!canDoubleDown()) {
            return;
        }
        hit(deck.drawCard());
        if (playingFirstHand && hand1.size() > 2) {
            playingFirstHand = false;
            hand1Finished = true;
        } else if (!playingFirstHand) {
            hand2Finished = true;
            player.setState(new NormalPlayerState(player));
        } else {
            playingFirstHand = !playingFirstHand;
        }
    }
}