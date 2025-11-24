package participants;

import cards.Deck;
import game.Constants;

public class Dealer extends Participant {
    public Dealer() {
        super();
    }

    @Override
    public void playTurn(Deck deck) {
        while (shouldHit()) {
            receiveCard(deck.drawCard());
        }
    }

    public boolean shouldHit() {
        return calculateScore() < Constants.DEALER_HIT_THRESHOLD;
    }

    public boolean showingAce()
    {
        return !hand.isEmpty() && hand.get(0).getRank().equals("A");
    }

    public String getHandSummary(boolean hideFirstCard) {
        if (hideFirstCard)
        {
            return "Dealer's hand: [Hidden], " + hand.subList(1, hand.size());
        }
        return getHandString();
    }
}
