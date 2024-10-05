package task_1_1_2.src.main.java.ru.nsu.shabalina;

/**
 * Класс, представляющий карту для игры в Блэкджек.
 */
public class Card {
    /**
     * Перечисление, представляющее достоинство карты.
     */
    public enum Rank {
        Ace("Туз", 11),
        Two("Двойка", 2),
        Three("Тройка", 3),
        Four("Четвёрка", 4),
        Five("Пятёрка", 5),
        Six("Шестёрка", 6),
        Seven("Семёрка", 7),
        Eight("Восьмёрка", 8),
        Nine("Девятка", 9),
        Ten("Десятка", 10),
        Jack("Валет", 10),
        Queen("Дама", 10),
        King("Король", 10);

        private final String ruName;
        private final int value;

        Rank(String ruName, int value) {
            this.ruName = ruName;
            this.value = value;
        }

        public String getRuName() {
            return ruName;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Перечисление, представляющее масть карты.
     */
    public enum Suit {
        Spades("Пики"),
        Clubs("Трефы"),
        Diamonds("Бубны"),
        Hearts("Червы");

        private final String ruName;

        Suit(String ruName) {
            this.ruName = ruName;
        }

        public String getRuName() {
            return ruName;
        }
    }

    public final Suit suit;
    public final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank.getRuName() + " " + suit.getRuName();
    }
}


