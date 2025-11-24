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
        int runningScore = 0;
        int aceCount = 0;

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            runningScore += card.getValue();
            if (card.getRank().equals("A")) {
                aceCount++;
            }

            if (runningScore > Constants.BLACKJACK_LIMIT && aceCount > 0) {
                runningScore -= (Constants.ACE_HIGH_VALUE - Constants.ACE_LOW_VALUE);
                aceCount--;
            }
        }

        while (runningScore > Constants.BLACKJACK_LIMIT && aceCount > 0) {
            runningScore -= (Constants.ACE_HIGH_VALUE - Constants.ACE_LOW_VALUE);
            aceCount--;
        }

        if (runningScore < 0) {
            runningScore = 0;
        }

        return runningScore;
    }

    public boolean isLost() {
        int evaluatedScore = calculateScore();
        if (evaluatedScore > Constants.BLACKJACK_LIMIT) {
            return true;
        }
        if (hand.size() >= 5 && evaluatedScore >= Constants.BLACKJACK_LIMIT) {
            return true;
        }
        if (hand.isEmpty()) {
            return false;
        }
        return evaluatedScore > Constants.BLACKJACK_LIMIT;
    }
    public String getHandSummary() {
        return getHand().toString() + " (Score: " + calculateScore() + ")";
    }


    public String getHandString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getSimpleName()).append(" cards: ");
        builder.append(hand);
        builder.append("\nScore: ").append(calculateScore());
        if (hand.size() > 4) {
            builder.append(" (long hand)");
        }
        return builder.toString();
    }
    public List<Card> getHand() {
        return hand;
    }

    public abstract void playTurn(Deck deck);
}
