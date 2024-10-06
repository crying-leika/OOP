package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования игрока в игре Blackjack.
 */
public class GamerTest {

    private Gamer gamer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    /**
     * Настройка перед каждым тестом.
     */
    @BeforeEach
    public void setUp() {
        gamer = new Gamer();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Тест для проверки добавления карты.
     */
    @Test
    public void testAddCard() {
        Blackjack.Card card = new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace);
        gamer.addCard(card);
        assertEquals(11, gamer.getScore());
    }

    /**
     * Тест для проверки подсчета очков.
     */
    @Test
    public void testGetScore() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        assertEquals(21, gamer.getScore());
    }

    /**
     * Тест для проверки подсчета очков с тузами.
     */
    @Test
    public void testGetScoreWithAces() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Diamonds, Blackjack.Card.Rank.Nine));
        assertEquals(21, gamer.getScore());
    }

    /**
     * Тест для проверки очистки руки.
     */
    @Test
    public void testClearHand() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.clearHand();
        assertEquals(0, gamer.getScore());
    }

    /**
     * Тест для проверки вывода руки.
     */
    @Test
    public void testPrintHand() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        gamer.printHand(false, true);
        String output = outContent.toString();
        assertTrue(output.contains("Ваши карты: [Туз Пики, Король Червы] => 21"));
    }

    /**
     * Тест для проверки вывода закрытой руки.
     */
    @Test
    public void testPrintHandClosed() {
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Spades, Blackjack.Card.Rank.Ace));
        gamer.addCard(new Blackjack.Card(Blackjack.Card.Suit.Hearts, Blackjack.Card.Rank.King));
        gamer.printHand(true, false);
        String output = outContent.toString();
        assertTrue(output.contains("Карты дилера: [Туз Пики, <закрытая карта>]"));
    }

    /**
     * Тест для проверки вывода пустой руки.
     */
    @Test
    public void testPrintHandEmpty() {
        gamer.printHand(false, true);
        String output = outContent.toString();
        assertTrue(output.contains("Нет карт на руках"));
    }

    /**
     * Очистка после каждого теста.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}





