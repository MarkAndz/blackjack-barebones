package participants;

import cards.Card;
import cards.Deck;
import game.Constants;
import java.util.ArrayList;
import java.util.List;

public abstract class Participant {
    protected List<Card> hand;

    public Participant() {
        this.hand = new ArrayList<>();
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public int calculateScore() {
        int score = 0;
        int aceCount = 0;

        for (Card card : hand) {
            score += card.getValue();
            if (card.getRank().equals("A")) {
                aceCount++;
            }
        }
        while (score > Constants.BLACKJACK_LIMIT && aceCount > 0)
        {
            score -= (Constants.ACE_HIGH_VALUE - Constants.ACE_LOW_VALUE);
            aceCount--;
        }

        return score;
    }

    public boolean isLost() {
        return calculateScore() > Constants.BLACKJACK_LIMIT;
    }
    public String getHandSummary() {
        return getHand().toString() + " (Score: " + calculateScore() + ")";
    }


    public String getHandString() {
        return this.getClass().getSimpleName() + " cards: " + hand + "\nScore: " + calculateScore();
    }
    public List<Card> getHand() {
        return hand;
    }

    public abstract void playTurn(Deck deck);
}
