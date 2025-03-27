package ru.nsu.shabalina;

import java.util.ArrayDeque;
import java.util.Deque;

public class OrderPool {
    private final Deque<PizzaOrder> orders = new ArrayDeque<>();
    private boolean isClosed = false;

    public synchronized void addOrder(PizzaOrder order) {
        orders.add(order);
        notifyAll();
    }

    public synchronized PizzaOrder getOrder() throws InterruptedException {
        while (orders.isEmpty() && !isClosed) {
            wait();
        }
        return orders.isEmpty() ? null : orders.pollFirst();
    }

    public synchronized void closePool() {
        isClosed = true;
        notifyAll();
    }
}
