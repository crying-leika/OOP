package ru.nsu.shabalina;

public class OrderCreator implements Runnable {
    private final OrderPool orderPool;
    private final long creationInterval;
    private boolean active = true;
    private int orderNumber = 0;

    public OrderCreator(OrderPool orderPool, long creationInterval) {
        this.orderPool = orderPool;
        this.creationInterval = creationInterval;
    }

    public void haltCreation() {
        active = false;
    }

    @Override
    public void run() {
        while (active) {
            try {
                PizzaOrder order = new PizzaOrder(++orderNumber);
                System.out.println(order);
                orderPool.addOrder(order);
                Thread.sleep(creationInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("OrderCreator stopped.");
    }
}

