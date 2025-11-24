package cards;

public class DeckFactory {
    public static Deck createDeck(DeckType type)
    {
        if (type == DeckType.DOUBLE)
        {
            return new DoubleDeck();
        }
        return new Deck();
    }
}