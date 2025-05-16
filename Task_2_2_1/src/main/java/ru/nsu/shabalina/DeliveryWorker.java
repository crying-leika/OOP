package ru.nsu.shabalina;

import java.util.List;

public class DeliveryWorker implements Runnable {
    private final String workerName;
    private final int maxLoad;
    private final Storage storage;

    public DeliveryWorker(String workerName, int maxLoad, Storage storage) {
        this.workerName = workerName;
        this.maxLoad = maxLoad;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<PizzaOrder> orders = storage.retrievePizzas(maxLoad);
                if (orders.isEmpty()) {
                    break;
                }
                for (PizzaOrder order : orders) {
                    order.setStatus(Status.ON_DELIVERY);
                    System.out.println(order);
                }
                Thread.sleep(2000); // Delivery time
                for (PizzaOrder order : orders) {
                    order.setStatus(Status.COMPLETED);
                    System.out.println(order);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("DeliveryWorker " + workerName + " finished work.");
    }
}

