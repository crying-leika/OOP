package task_1_1_2.src.main.java.ru.nsu.shabalina;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    public final List<Blackjack.Card> cards;
    private final Random rand;

    public Deck() {
        this.cards = new ArrayList<>();
        this.rand = new Random();

        for (Blackjack.Card.Suit suit : Blackjack.Card.Suit.values()) {
            for (Blackjack.Card.Rank rank : Blackjack.Card.Rank.values()) {
                cards.add(new Blackjack.Card(suit, rank));
            }
        }
    }

    public void swap(int i, int j, int n) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return;
        }

        Blackjack.Card tmp = this.cards.get(i);
        this.cards.set(i, this.cards.get(j));
        this.cards.set(j, tmp);
    }

    public void shuffle() {
        for (int i = 0; i < 52; i++) {
            int index = rand.nextInt(cards.size() - i);
            swap(index, cards.size() - i - 1, cards.size());
        }
    }

    public Blackjack.Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Колода пуста");
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}

