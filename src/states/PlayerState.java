package states;

import cards.Card;
import cards.Deck;
import participants.Player;

public interface PlayerState
{
    void playTurn(Deck deck);
    void hit(Card card);
    boolean wantsToHit(String input);
    boolean canSplit();
    void split(Deck deck);
    boolean canDoubleDown();
    void doubleDown(Deck deck);
}
