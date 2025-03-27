package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderPoolTest {

    @Test
    public void testAddAndGetOrder() throws InterruptedException {
        OrderPool pool = new OrderPool();
        PizzaOrder order = new PizzaOrder(1);
        pool.addOrder(order);
        PizzaOrder retrieved = pool.getOrder();
        assertEquals(order, retrieved); // Проверяем, что заказ тот же
    }

    @Test
    public void testGetOrderWhenEmpty() throws InterruptedException {
        OrderPool pool = new OrderPool();
        pool.closePool(); // Закрываем очередь
        PizzaOrder retrieved = pool.getOrder();
        assertNull(retrieved); // Проверяем, что ничего не возвращается, если очередь пуста
    }
}

