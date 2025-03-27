package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class StorageTest {

    @Test
    public void testStoreAndRetrievePizzas() throws InterruptedException {
        Storage storage = new Storage(2); // Склад на 2 пиццы
        PizzaOrder order1 = new PizzaOrder(1);
        PizzaOrder order2 = new PizzaOrder(2);

        storage.storePizza(order1); // Сохраняем первую пиццу
        storage.storePizza(order2); // Сохраняем вторую пиццу

        List<PizzaOrder> retrieved = storage.retrievePizzas(1); // Забираем 1 пиццу
        assertEquals(1, retrieved.size());
        assertEquals(order1, retrieved.get(0)); // Проверяем, что забрали первую пиццу

        retrieved = storage.retrievePizzas(2); // Забираем оставшиеся
        assertEquals(1, retrieved.size());
        assertEquals(order2, retrieved.get(0)); // Проверяем, что забрали вторую
    }

    @Test
    public void testRetrieveWhenEmpty() throws InterruptedException {
        Storage storage = new Storage(2);
        storage.closeStorage(); // Закрываем склад
        List<PizzaOrder> retrieved = storage.retrievePizzas(1);
        assertTrue(retrieved.isEmpty()); // Проверяем, что ничего не забрали
    }
}