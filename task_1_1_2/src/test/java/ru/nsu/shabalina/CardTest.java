package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;
import ru.nsu.shabalina.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

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

    @Test
    void pointsTest() {
        Card card;

        // For each suit
        for (int i = 0; i < 4; i++) {
            Card.Suit suit = Card.Suit.values()[i];

            // Ace
            card = new Card(suit, Card.Rank.Ace);
            assertEquals(11, card.getValue());

            // 2..10
            for (int j = 1; j < 10; j++) {
                card = new Card(suit, Card.Rank.values()[j]);
                assertEquals(j + 1, card.getValue());
            }

            // Jack
            card = new Card(suit, Card.Rank.Jack);
            assertEquals(10, card.getValue());
            // Queen
            card = new Card(suit, Card.Rank.Queen);
            assertEquals(10, card.getValue());
            // King
            card = new Card(suit, Card.Rank.King);
            assertEquals(10, card.getValue());
        }
    }
}


