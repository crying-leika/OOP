package ru.nsu.shabalina;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий игрока в игре Блэкджек.
 */
public class Gamer {
    private final List<Blackjack.Card> hand;

    public Gamer() {
        this.hand = new ArrayList<>();
    }

    /**
     * Добавляет карту в руку игрока.
     *
     * @param card карта для добавления
     */
    public void addCard(Blackjack.Card card) {
        hand.add(card);
    }

    /**
     * Печатает карты в руке игрока.
     *
     * @param close    флаг, указывающий, нужно ли скрывать одну из карт
     * @param isPlayer флаг, указывающий, является ли игрок игроком или дилером
     */
    public void printHand(boolean close, boolean isPlayer) {
        if (hand.isEmpty()) {
            System.out.println("Нет карт на руках");
        } else {
            if (isPlayer) {
                System.out.print("\t Ваши карты: ");
            } else {
                System.out.print("\t Карты дилера: ");
            }
            System.out.print("[");

            boolean over = false;
            int points = 0;
            for (Blackjack.Card card : hand) {
                points += card.getValue();
            }
            if (points > 21) {
                over = true;
            }

            if (!close) {
                for (int i = 0; i < hand.size(); i++) {
                    System.out.print(hand.get(i));
                    if (i < hand.size() - 1) {
                        System.out.print(", ");
                    }
                }
            } else if (!hand.isEmpty()) {
                System.out.print(hand.get(0) + ", <закрытая карта>");
            }

            System.out.print("]");

            if (!close) {
                System.out.println(" => " + getScore());
            } else {
                System.out.println();
            }
        }
    }

    /**
     * Возвращает количество очков в руке игрока.
     *
     * @return количество очков
     */
    public int getScore() {
        int score = 0;
        int aceCount = 0;

        for (Blackjack.Card card : hand) {
            score += card.getValue();
            if (card.getValue() == 11) {
                aceCount++;
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    /**
     * Очищает руку игрока.
     */
    public void clearHand() {
        hand.clear();
    }
}



