import cards.Card;
import participants.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerDoubleDownTest {
    @Test
    public void testPlayerDoubleDown() {
        Player player = new Player();

        player.receiveCard(new Card("5", "Hearts"));
        player.receiveCard(new Card("4", "Spades"));
        assertTrue(player.canDoubleDown());

        player = new Player();
        player.receiveCard(new Card("7", "Hearts"));
        player.receiveCard(new Card("6", "Spades"));
        assertFalse(player.canDoubleDown());

        player = new Player();
        player.receiveCard(new Card("5", "Hearts"));
        player.receiveCard(new Card("4", "Spades"));
        player.receiveCard(new Card("2", "Diamonds"));
        assertFalse(player.canDoubleDown());
    }
}
