package ru.nsu.shabalina;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PizzaShopConfig {
    public long orderInterval;
    public int storageCapacity;
    public int operatingTimeSeconds;
    public List<Maker> makers;
    public List<Worker> workers;

    public static PizzaShopConfig loadConfig(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), PizzaShopConfig.class);
    }

    public static class Maker {
        public String name;
        public double bakingTime;
    }

    public static class Worker {
        public String name;
        public int loadCapacity;
    }
}

