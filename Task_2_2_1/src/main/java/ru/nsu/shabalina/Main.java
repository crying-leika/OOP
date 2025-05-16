package ru.nsu.shabalina;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PizzaShopConfig config = PizzaShopConfig.loadConfig("pizzeria-config.json");
        PizzaShop shop = new PizzaShop(config);
        shop.openShop();
    }
}

