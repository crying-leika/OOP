package ru.nsu.shabalina;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс для игры Blackjack.
 */
public class Blackjack {
    public Gamer player;
    public Gamer dealer;
    public Deck deck;
    public int playerCounter;
    public int dealerCounter;
    public int roundCounter;
    public Scanner scanner;

    /**
     * Конструктор для инициализации игры.
     */
    public Blackjack() {
        this.player = new Gamer();
        this.dealer = new Gamer();
        this.deck = new Deck();
        this.deck.shuffle();
        this.playerCounter = 0;
        this.dealerCounter = 0;
        this.roundCounter = 1;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Раздает карту игроку или дилеру.
     *
     * @param gamer игрок или дилер
     * @return разданная карта
     * @throws IllegalStateException если колода пуста
     */
    public Card dealCard(Gamer gamer) {
        if (deck.size() == 0) {
            throw new IllegalStateException("Колода пуста.");
        }
        Card card = deck.drawCard();
        gamer.addCard(card);
        return card;
    }

    /**
     * Безопасное считывание целого числа из ввода.
     *
     * @param text сообщение для повторного ввода
     * @return введенное целое число
     */
    public int myScanInt(String text) {
        int input;
        while (true) {
            try {
                input = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print(text);
                scanner.next();
            }
        }
        return input;
    }

    /**
     * Проведение одного раунда игры.
     *
     * @param test флаг для тестирования
     * @param simulateGame флаг для симуляции игры
     */
    public void round(boolean test, boolean simulateGame) {
        if (roundCounter <= 1) {
            System.out.println("Добро пожаловать в Блэкджек!");
        } else {
            System.out.println("\n");
        }
        System.out.println("Раунд " + roundCounter);

        if (!simulateGame) {
            player.clearHand();
            dealer.clearHand();

            if (deck.size() < 52 / 2) {
                deck = new Deck();
                deck.shuffle();
            }

            dealCard(player);
            dealCard(player);
            dealCard(dealer);
        }

        System.out.println("Дилер раздал карты: ");
        player.printHand(false, true);
        dealer.printHand(true, false);

        System.out.println("\nВаш ход:");
        System.out.println("----------");

        while (true) {
            if (player.getScore() >= 21) {
                break;
            }

            System.out.print("Введите '1', чтобы взять карту, '0', чтобы остановиться: ");
            int input;
            if (test) {
                input = 0;
            } else {
                input = myScanInt("Введите '1', чтобы взять карту, '0', чтобы остановиться: ");
            }

            if (input != 1) {
                break;
            } else {
                Card tmp = dealCard(player);
                System.out.print("Вы открыли карту: ");
                System.out.println(tmp);
            }

            player.printHand(false, true);
            dealer.printHand(true, false);
        }

        if (player.getScore() > 21) {
            dealerCounter++;
            System.out.print("Вы проиграли! ");
        } else if (player.getScore() == 21) {
            playerCounter++;
            System.out.print("Вы выиграли! ");
        } else if (player.getScore() < 21) {
            System.out.println("\nХод дилера:");
            System.out.println("----------");

            boolean first = true;
            while (first || dealer.getScore() < 17) {
                Card tmp = dealCard(dealer);

                if (first) {
                    System.out.print("Дилер открывает закрытую карту ");
                } else {
                    System.out.print("Дилер открывает карту: ");
                }

                System.out.println(tmp);

                player.printHand(false, true);
                dealer.printHand(false, false);

                first = false;
            }

            if (dealer.getScore() > 21 || (player.getScore() > dealer.getScore())) {
                playerCounter++;
                System.out.print("Вы выиграли! ");
            } else if (player.getScore() == dealer.getScore()) {
                System.out.print("Ничья! ");
            } else if (player.getScore() < dealer.getScore()) {
                dealerCounter++;
                System.out.print("Вы проиграли! ");
            }
        }

        System.out.println("Счет игрок/дилер: " + playerCounter + "/" + dealerCounter);
        roundCounter++;
    }

    /**
     * Основной игровой цикл.
     *
     * @param test флаг для тестирования
     * @param simulateGame флаг для симуляции игры
     */
    public void game(boolean test, boolean simulateGame) {
        while (true) {
            round(test, simulateGame);

            System.out.print("Введите '1', чтобы сыграть еще раз, '0', чтобы остановиться: ");
            int input;

            if (test) {
                input = 0;
            } else {
                input = myScanInt("Введите '1', чтобы сыграть еще раз, '0', чтобы остановиться: ");
            }

            if (input != 1) {
                break;
            }
        }
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        game.game(false, false);
    }

    /**
     * Класс для представления карты.
     */
    public static class Card {
        /**
         * Перечисление для рангов карт.
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
         * Перечисление для мастей карт.
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

        /**
         * Конструктор для создания карты.
         *
         * @param suit масть карты
         * @param rank ранг карты
         */
        public Card(Suit suit, Rank rank) {
            this.suit = suit;
            this.rank = rank;
        }

        /**
         * Возвращает значение карты.
         *
         * @return значение карты
         */
        public int getValue() {
            return rank.getValue();
        }

        /**
         * Возвращает строковое представление карты.
         *
         * @return строковое представление карты
         */
        @Override
        public String toString() {
            return rank.getRuName() + " " + suit.getRuName();
        }
    }
}


