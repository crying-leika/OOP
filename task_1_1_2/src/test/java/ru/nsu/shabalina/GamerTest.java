package ru.nsu.shabalina;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamerTest {

    private Gamer gamer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        gamer = new Gamer();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddCard() {
        Blackjack.Card card = new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace);
        gamer.addCard(card);
        assertEquals(11, gamer.getScore());
    }

    @Test
    public void testGetScore() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        assertEquals(21, gamer.getScore());
    }

    @Test
    public void testGetScoreWithAces() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Diamonds, Blackjack.Card.Rank.Nine));
        assertEquals(21, gamer.getScore());
    }

    @Test
    public void testClearHand() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.clearHand();
        assertEquals(0, gamer.getScore());
    }

    @Test
    public void testPrintHand() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        gamer.printHand(false, true);
        String output = outContent.toString();
        assertTrue(output.contains("Ваши карты: [Туз Пики, Король Червы] => 21"));
    }

    @Test
    public void testPrintHandClosed() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        gamer.printHand(true, false);
        String output = outContent.toString();
        assertTrue(output.contains("Карты дилера: [Туз Пики, <закрытая карта>]"));
    }

    @Test
    public void testPrintHandEmpty() {
        gamer.printHand(false, true);
        String output = outContent.toString();
        assertTrue(output.contains("Нет карт на руках"));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}


