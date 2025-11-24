import cards.Card;
import participants.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerAceTest {
    @Test
    public void testAceHandling() {
        Player player = new Player();

        player.receiveCard(new Card("A", "Spades"));
        player.receiveCard(new Card("10", "Hearts"));
        assertEquals(21, player.calculateScore());

        player = new Player();
        player.receiveCard(new Card("A", "Spades"));
        player.receiveCard(new Card("10", "Hearts"));
        player.receiveCard(new Card("2", "Clubs")); // Would be 23 if Ace = 11
        assertEquals(13, player.calculateScore());
    }
}
