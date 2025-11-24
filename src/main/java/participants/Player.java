package participants;

import cards.Card;
import cards.Deck;
import states.PlayerState;
import states.NormalPlayerState;
import java.util.List;

public class Player extends Participant {
    private PlayerState state;

    public Player() {
        super();
        this.state = new NormalPlayerState(this);
    }

    @Override
    public void playTurn(Deck deck) {
        state.playTurn(deck);
    }

    public void hit(Card card) {
        state.hit(card);
    }
    public boolean canSplit() {
        return state.canSplit();
    }

    public void split(Deck deck) {
        state.split(deck);
    }

    public boolean canDoubleDown() {
        return state.canDoubleDown();
    }

    public void doubleDown(Deck deck) {
        state.doubleDown(deck);
    }

    public void setState(PlayerState newState) {
        this.state = newState;
    }

    public List<Card> getHand() {
        return super.getHand();
    }
    public PlayerState getState() {
        return this.state;
    }

}
