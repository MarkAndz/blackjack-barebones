package cards;

import game.Constants;

public class Card
{
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit; 
    }
    //
    public int getValue() {
        if (rank.equals("A"))
            return Constants.ACE_HIGH_VALUE;
        if (rank.equals("K") || rank.equals("Q") || rank.equals("J"))
            return Constants.FACE_CARD_VALUE;

        return Integer.parseInt(rank);
    }
    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
