package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования игры Blackjack.
 */
public class BlackjackTest {

    /**
     * Проверяет, что в колоде нет повторяющихся карт.
     *
     * @param cards список карт для проверки
     */
    private static void notRepeatInDeck(List<Blackjack.Card> cards) {
        for (Blackjack.Card card : cards) {
            int cnt = 0;
            for (Blackjack.Card otherCard : cards) {
                if (card.equals(otherCard)) {
                    cnt++;
                }
            }
            assertEquals(1, cnt);
        }
    }

    /**
     * Тест для проверки инициализации игры.
     */
    @Test
    void gameClassTest() {
        Blackjack game = new Blackjack();

        assertNotNull(game.player);
        assertNotNull(game.dealer);

        assertNotNull(game.deck);
        notRepeatInDeck(game.deck.cards);

        assertEquals(0, game.playerCounter);
        assertEquals(0, game.dealerCounter);

        assertEquals(1, game.roundCounter);
    }

    /**
     * Тест для проверки раздачи карт.
     */
    @Test
    void dealCardTest() {
        Gamer player = new Gamer();
        Blackjack game = new Blackjack();

        Blackjack.Card tmp = game.dealCard(player);
        assertNotNull(tmp);
        assertFalse(game.deck.cards.contains(tmp));

        Blackjack.Card tmp2 = game.dealCard(player);
        assertNotNull(tmp2);
        assertFalse(game.deck.cards.contains(tmp2));
        assertNotEquals(tmp, tmp2);

        while (game.deck.size() > 0) {
            game.dealCard(player);
        }

        assertThrows(IllegalStateException.class, () -> game.dealCard(player));
    }

    /**
     * Тест для проверки метода myScanInt.
     */
    @Test
    void myScanIntTest() {
        String simulatedInput = "sdhglsd\nOkay...\n123\n15\naboba\nhaha7\nda\n-5\n";
        InputStream originalIn = System.in;

        int input = 0;
        Blackjack game = new Blackjack();

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(in);

            Scanner scanner = new Scanner(System.in);
            game.scanner = scanner;

            input = game.myScanInt("снова: ");
            assertEquals(123, input);
            input = game.myScanInt("снова: ");
            assertEquals(15, input);
            input = game.myScanInt("снова: ");
            assertEquals(-5, input);
        } finally {
            System.setIn(originalIn);
        }
    }

    /**
     * Тест для проверки раунда игры.
     */
    @Test
    void roundTest() {
        Blackjack game = new Blackjack();
        game.round(true, false);
    }

    /**
     * Тест для проверки всей игры.
     */
    @Test
    void gameTest() {
        Blackjack game = new Blackjack();
        game.game(true, false);
    }

    /**
     * Тест для проверки победы игрока с 21 очком.
     */
    @Test
    void playerWinsWith21PointsTest() {
        Blackjack g = new Blackjack();
        g.player.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        g.player.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        g.dealer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Clubs, Blackjack.Card.Rank.Five));

        assertEquals(21, g.player.getScore());

        g.round(true, true);
        assertEquals(1, g.playerCounter);
        assertEquals(2, g.roundCounter);
    }

    /**
     * Тест для проверки ввода игрока.
     */
    @Test
    void playerInputTest2() {
        String simulatedInput = "0\n1\n0\n0\n";
        InputStream originalIn = System.in;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(in);
            Blackjack g = new Blackjack();

            g.deck.cards.add(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
            g.deck.cards.add(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
            g.deck.cards.add(new Blackjack.Card(Blackjack.Card.Suit.Clubs, Blackjack.Card.Rank.Five));
            g.deck.cards.add(new Blackjack.Card(Blackjack.Card.Suit.Diamonds, Blackjack.Card.Rank.Ten));
            g.deck.cards.add(new Blackjack.Card(Blackjack.Card.Suit.Diamonds, Blackjack.Card.Rank.Jack));
            g.deck.cards.add(new Blackjack.Card(Blackjack.Card.Suit.Diamonds, Blackjack.Card.Rank.Queen));

            g.game(false, false);

            assertEquals(3, g.roundCounter);
            assertTrue(g.playerCounter + g.dealerCounter >= 2);
        } finally {
            System.setIn(originalIn);
        }
    }
}



