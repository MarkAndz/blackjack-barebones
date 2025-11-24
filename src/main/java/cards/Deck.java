package cards;

import game.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    private void initializeDeck() {
        for (String suit : Constants.SUITS) {
            for (String rank : Constants.RANKS) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffleDeck()
    {
        Collections.shuffle(cards);
    }


    protected List<Card> getCards() {
        return cards;
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty.");
        }
        return cards.remove(0);
    }

    public int getRemainingCards() {
        return cards.size();
    }
}