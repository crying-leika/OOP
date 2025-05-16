package ru.nsu.shabalina;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Storage {
    private final int maxSize;
    private final Deque<PizzaOrder> pizzas = new ArrayDeque<>();
    private boolean isClosed = false;

    public Storage(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void storePizza(PizzaOrder order) throws InterruptedException {
        while (pizzas.size() >= maxSize && !isClosed) {
            wait();
        }
        if (!isClosed) {
            pizzas.add(order);
            notifyAll();
        }
    }

    public synchronized List<PizzaOrder> retrievePizzas(int count) throws InterruptedException {
        while (pizzas.isEmpty() && !isClosed) {
            wait();
        }
        int available = Math.min(count, pizzas.size());
        List<PizzaOrder> result = new LinkedList<>();
        for (int i = 0; i < available; i++) {
            result.add(pizzas.pollFirst());
        }
        notifyAll();
        return result;
    }

    public synchronized void closeStorage() {
        isClosed = true;
        notifyAll();
    }
}

