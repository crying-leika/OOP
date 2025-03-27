package ru.nsu.shabalina;

import java.util.ArrayList;
import java.util.List;

public class PizzaShop {
    private final OrderPool orderPool;
    private final Storage storage;
    private final List<Thread> makerThreads = new ArrayList<>();
    private final List<Thread> workerThreads = new ArrayList<>();
    private final OrderCreator orderCreator;
    private final long operatingTime;

    public PizzaShop(PizzaShopConfig config) {
        this.orderPool = new OrderPool();
        this.storage = new Storage(config.storageCapacity);
        this.orderCreator = new OrderCreator(orderPool, config.orderInterval);
        this.operatingTime = config.operatingTimeSeconds;

        for (PizzaShopConfig.Maker m : config.makers) {
            PizzaMaker maker = new PizzaMaker(m.name, m.bakingTime, orderPool, storage);
            Thread t = new Thread(maker, "Maker-" + m.name);
            makerThreads.add(t);
        }

        for (PizzaShopConfig.Worker w : config.workers) {
            DeliveryWorker worker = new DeliveryWorker(w.name, w.loadCapacity, storage);
            Thread t = new Thread(worker, "Worker-" + w.name);
            workerThreads.add(t);
        }
    }

    public void openShop() {
        for (Thread t : makerThreads) {
            t.start();
        }
        for (Thread t : workerThreads) {
            t.start();
        }
        Thread creatorThread = new Thread(orderCreator, "OrderCreator");
        creatorThread.start();

        try {
            Thread.sleep(operatingTime * 1000);
            closeShop();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void closeShop() {
        orderCreator.haltCreation();
        orderPool.closePool();
        storage.closeStorage();

        for (Thread t : makerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (Thread t : workerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("PizzaShop closed.");
    }
}

