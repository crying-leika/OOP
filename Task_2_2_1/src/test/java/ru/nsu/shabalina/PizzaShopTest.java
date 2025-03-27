package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PizzaShopTest {

    @Test
    public void testPizzaShopWorkflow() throws Exception {
        // Перенаправляем вывод в строку для проверки
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Загружаем конфигурацию из JSON
        PizzaShopConfig config = PizzaShopConfig.loadConfig("pizzeria-config.json");
        PizzaShop shop = new PizzaShop(config);

        // Запускаем пиццерию
        shop.openShop();

        // Ждем, пока симуляция завершится
        Thread.sleep(config.operatingTimeSeconds * 1000L + 5000);

        // Восстанавливаем стандартный вывод
        System.setOut(originalOut);

        // Проверяем, что в логе есть ключевые этапы обработки заказа
        String output = outContent.toString();
        assertTrue(output.contains("[Order 1] [NEW]")); // Новый заказ
        assertTrue(output.contains("[Order 1] [COOKING]")); // Готовится
        assertTrue(output.contains("[Order 1] [STORED]")); // На складе
        assertTrue(output.contains("[Order 1] [ON_DELIVERY]")); // На доставке
        assertTrue(output.contains("[Order 1] [COMPLETED]")); // Завершен
    }
}

