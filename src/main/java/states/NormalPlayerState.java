package states;

import cards.Card;
import cards.Deck;
import participants.Player;

public class NormalPlayerState implements PlayerState {
    private final Player player;

    public NormalPlayerState(Player player) {
        this.player = player;
    }

    @Override
    public void playTurn(Deck deck)
    {
    }

    @Override
    public void hit(Card card)
    {

        player.receiveCard(card);
    }

    @Override
    public boolean wantsToHit(String input)
    {

        return input.equalsIgnoreCase("h");
    }

    @Override
    public boolean canSplit() {
        return player.getHand().size() == 2 && player.getHand().get(0).getRank().equals(player.getHand().get(1).getRank());
    }

    @Override
    public void split(Deck deck) {
        if (canSplit()) {
            player.setState(new SplitPlayerState(player, deck));
        }
    }

    @Override
    public boolean canDoubleDown() {
        if(player.getHand().size() == 2){
            int total = player.calculateScore();
            if(total == 9 || total == 10 || total == 11){
                return true;
            }
        }
        return false;
    }

    @Override
    public void doubleDown(Deck deck) {
        if (canDoubleDown()) {
            player.receiveCard(deck.drawCard());
            player.setState(new states.DoubleDownPlayerState(player));
        }
    }
}