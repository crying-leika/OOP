package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования карт в игре Blackjack.
 */
public class CardTest {

    /**
     * Тест для проверки создания карт с различными мастями и рангами.
     */
    @Test
    void cardTest() {
        Card.Suit suit;
        Card.Rank rank;

        Card card;

        for (int i = 0; i < 52; i++) {
            suit = Card.Suit.values()[i / 13];
            rank = Card.Rank.values()[i % 13];

            card = new Card(suit, rank);

            assertEquals(suit, card.suit);
            assertEquals(rank, card.rank);
        }
    }

    /**
     * Тест для проверки значений очков карт.
     */
    @Test
    void pointsTest() {
        Card card;

        // Для каждой масти
        for (int i = 0; i < 4; i++) {
            Card.Suit suit = Card.Suit.values()[i];

            // Туз
            card = new Card(suit, Card.Rank.Ace);
            assertEquals(11, card.getValue());

            // 2..10
            for (int j = 1; j < 10; j++) {
                card = new Card(suit, Card.Rank.values()[j]);
                assertEquals(j + 1, card.getValue());
            }

            // Валет
            card = new Card(suit, Card.Rank.Jack);
            assertEquals(10, card.getValue());
            // Дама
            card = new Card(suit, Card.Rank.Queen);
            assertEquals(10, card.getValue());
            // Король
            card = new Card(suit, Card.Rank.King);
            assertEquals(10, card.getValue());
        }
    }
}



