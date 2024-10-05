package task_1_1_2.src.main.java.ru.nsu.shabalina;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс, представляющий колоду карт для игры в Блэкджек.
 */
public class Deck {
    /**
     * Список карт в колоде.
     */
    public final List<Blackjack.Card> cards;

    /**
     * Генератор случайных чисел для перемешивания колоды.
     */
    private final Random rand;

    /**
     * Конструктор, инициализирующий колоду карт.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        this.rand = new Random();

        // Добавляем все карты в колоду
        for (Blackjack.Card.Suit suit : Blackjack.Card.Suit.values()) {
            for (Blackjack.Card.Rank rank : Blackjack.Card.Rank.values()) {
                cards.add(new Blackjack.Card(suit, rank));
            }
        }
    }

    /**
     * Метод для обмена карт в колоде.
     *
     * @param i индекс первой карты
     * @param j индекс второй карты
     * @param n размер колоды
     */
    public void swap(int i, int j, int n) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return;
        }

        Blackjack.Card tmp = this.cards.get(i);
        this.cards.set(i, this.cards.get(j));
        this.cards.set(j, tmp);
    }

    /**
     * Метод для перемешивания колоды.
     */
    public void shuffle() {
        for (int i = 0; i < 52; i++) {
            int index = rand.nextInt(cards.size() - i);
            swap(index, cards.size() - i - 1, cards.size());
        }
    }

    /**
     * Метод для извлечения верхней карты из колоды.
     *
     * @return верхняя карта из колоды
     * @throws IllegalStateException если колода пуста
     */
    public Blackjack.Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Колода пуста");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Метод для получения размера колоды.
     *
     * @return размер колоды
     */
    public int size() {
        return cards.size();
    }
}


