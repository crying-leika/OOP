package ru.nsu.shabalina;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Класс для тестирования колоды карт в игре Blackjack.
 */
public class DeckTest {

    /**
     * Тест для проверки корректности создания колоды.
     */
    @Test
    void deckTest() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            Blackjack.Card tmp = deck.cards.get(i);
            Blackjack.Card card = new Blackjack.Card(Blackjack.Card.Suit.values()[i / 13], Blackjack.Card.Rank.values()[i % 13]);
            assertEquals(card.suit, tmp.suit);
            assertEquals(card.rank, tmp.rank);
        }
    }

    /**
     * Тест для проверки метода swap.
     */
    @Test
    void swapTest() {
        for (int i = 0; i < 1000; i++) {
            Random rand = new Random();
            Deck deck = new Deck();

            int a = rand.nextInt(deck.size());
            int b = rand.nextInt(deck.size());

            Blackjack.Card fst = deck.cards.get(a);
            Blackjack.Card snd = deck.cards.get(b);
            deck.swap(a, b, deck.size());
            Blackjack.Card sndNew = deck.cards.get(b);
            assertEquals(fst.suit, sndNew.suit);
            assertEquals(fst.rank, sndNew.rank);

            Blackjack.Card fstNew = deck.cards.get(a);
            assertEquals(snd.suit, fstNew.suit);
            assertEquals(snd.rank, fstNew.rank);
        }
    }

    /**
     * Проверяет, что в колоде нет повторяющихся карт.
     *
     * @param cards список карт для проверки
     */
    private static void notRepeatInDeck(List<Blackjack.Card> cards) {
        for (Blackjack.Card card : cards) {
            long cnt = cards.stream().filter(card::equals).count();
            assertEquals(1, cnt);
        }
    }

    /**
     * Тест для проверки метода shuffle.
     */
    @Test
    void shuffleTest() {
        Deck d = new Deck();
        int size = d.size();
        assertEquals(size, 52);
        notRepeatInDeck(d.cards);

        for (int i = 0; i < 1000; i++) {
            d.shuffle();
            size = d.size();
            assertEquals(size, 52);
            notRepeatInDeck(d.cards);
        }
    }
}


