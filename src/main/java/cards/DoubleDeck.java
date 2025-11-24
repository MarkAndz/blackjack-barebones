package cards;

import java.util.Collections;

public class DoubleDeck extends Deck {
    public DoubleDeck()
    {
        super();
        getCards().addAll(getCards());
        Collections.shuffle(getCards());
    }
}
