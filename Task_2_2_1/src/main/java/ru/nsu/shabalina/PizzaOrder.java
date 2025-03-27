package ru.nsu.shabalina;

public class PizzaOrder {
    private final int id;
    private Status status;

    public PizzaOrder(int id) {
        this.id = id;
        this.status = Status.NEW;
    }

    public int getId() {
        return id;
    }

    public synchronized Status getStatus() {
        return status;
    }

    public synchronized void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "[Order " + id + "] [" + status + "]";
    }
}

