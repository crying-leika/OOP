package ru.nsu.shabalina;

public class PizzaMaker implements Runnable {
    private final String makerName;
    private final double bakingTime;
    private final OrderPool orderPool;
    private final Storage storage;

    public PizzaMaker(String makerName, double bakingTime, OrderPool orderPool, Storage storage) {
        this.makerName = makerName;
        this.bakingTime = bakingTime;
        this.orderPool = orderPool;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                PizzaOrder order = orderPool.getOrder();
                if (order == null) {
                    break;
                }
                order.setStatus(Status.COOKING);
                System.out.println(order);
                Thread.sleep((long) (bakingTime * 1000));
                order.setStatus(Status.STORED);
                System.out.println(order);
                storage.storePizza(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("PizzaMaker " + makerName + " finished work.");
    }
}

