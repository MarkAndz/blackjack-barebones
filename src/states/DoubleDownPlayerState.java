package states;

import cards.Card;
import cards.Deck;
import participants.Player;
import states.NormalPlayerState;
import states.PlayerState;

public class DoubleDownPlayerState implements PlayerState {
    private final Player player;

    public DoubleDownPlayerState(Player player) {
        this.player = player;
    }

    @Override
    public void playTurn(Deck deck) {
        player.receiveCard(deck.drawCard());
        player.setState(new NormalPlayerState(player));
    }

    @Override
    public void hit(Card card) {
        throw new IllegalStateException("Cannot hit after doubling down.");
    }

    @Override
    public boolean wantsToHit(String input) {
        return false;
    }

    @Override
    public boolean canSplit() {
        return false;
    }

    @Override
    public void split(Deck deck) {
        throw new IllegalStateException("Cannot split after doubling down.");
    }

    @Override
    public boolean canDoubleDown() {
        return false;
    }

    @Override
    public void doubleDown(Deck deck) {
        throw new IllegalStateException("Cannot double down again.");
    }
}
