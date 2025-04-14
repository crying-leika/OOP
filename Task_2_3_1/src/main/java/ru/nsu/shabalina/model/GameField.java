package ru.nsu.shabalina.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameField {
    private final int size;
    private final Snake snake;
    private final List<Food> foods;
    private int score;
    private GameState state;
    private final Random random;
    private static final int VISIBLE_ROWS = 12; // Ограничиваем спавн по высоте (360 / 30 = 12)

    public GameField(int size) {
        this.size = size;
        this.snake = new Snake(size / 2, size / 2);
        this.foods = new ArrayList<>();
        this.score = 0;
        this.state = GameState.RUNNING;
        this.random = new Random();
        ensureFoodExists(); // Гарантируем наличие двух яблок при старте
        System.out.println("Initial food spawned. Foods count: " + foods.size());
    }

    public void update() {
        if (state != GameState.RUNNING) {
            return;
        }
        snake.move();
        Coordinate head = snake.getHeadPosition();
        // Проверка столкновения со стенами
        if (head.getX() < 0 || head.getX() >= size || head.getY() < 0 || head.getY() >= size) {
            state = GameState.GAME_OVER;
            System.out.println("Game Over: Hit wall");
            return;
        }
        // Проверка столкновения с собой
        if (snake.collidesWithItself()) {
            state = GameState.GAME_OVER;
            System.out.println("Game Over: Collided with self");
            return;
        }
        // Проверка поедания еды
        List<Food> foodsToRemove = new ArrayList<>();
        for (Food food : foods) {
            if (head.equals(food.getPosition())) {
                foodsToRemove.add(food);
                break; // Удаляем только одну еду за раз
            }
        }
        if (!foodsToRemove.isEmpty()) {
            for (Food food : foodsToRemove) {
                foods.remove(food);
                snake.grow();
                score += 10;
                System.out.println("Food eaten. Score: " + score);
            }
            spawnOneFood(); // Спавним ровно одно новое яблоко после поедания
        }
    }

    private void spawnOneFood() {
        List<Coordinate> freeCells = new ArrayList<>();
        List<Food> foodsSnapshot = new ArrayList<>(foods);
        Set<Coordinate> occupiedBySnake = new HashSet<>(snake.getSegments());
        Set<Coordinate> occupiedByFood = new HashSet<>();
        for (Food food : foodsSnapshot) {
            occupiedByFood.add(food.getPosition());
        }

        System.out.println("Occupied by snake: " + occupiedBySnake);
        System.out.println("Occupied by food: " + occupiedByFood);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < VISIBLE_ROWS; y++) { // Ограничиваем спавн по высоте
                Coordinate coord = new Coordinate(x, y);
                if (!occupiedBySnake.contains(coord) && !occupiedByFood.contains(coord)) {
                    freeCells.add(coord);
                }
            }
        }
        System.out.println("Free cells available: " + freeCells.size() +
                ", Snake segments: " + occupiedBySnake.size() +
                ", Food positions: " + occupiedByFood.size() +
                ", Total visible cells: " + (size * VISIBLE_ROWS));
        if (!freeCells.isEmpty()) {
            Coordinate foodPos = freeCells.get(random.nextInt(freeCells.size()));
            foods.add(new Food(foodPos));
            System.out.println("Spawned food at: " + foodPos + ". Foods count: " + foods.size());
        } else {
            System.err.println("No free cells to spawn food! Attempting forced spawn...");
            int x = random.nextInt(size);
            int y = random.nextInt(VISIBLE_ROWS); // Ограничиваем Y
            Coordinate forcedPos = new Coordinate(x, y);
            foods.add(new Food(forcedPos));
            System.err.println("Forced spawn at: " + forcedPos + ". Foods count: " + foods.size());
        }
    }

    private void ensureFoodExists() {
        // Спавним ровно два яблока
        while (foods.size() < 2) {
            spawnOneFood();
        }
    }

    public void setDirection(Direction dir) {
        snake.setDirection(dir);
    }

    public void togglePause() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSED;
            System.out.println("Game paused");
        } else if (state == GameState.PAUSED) {
            state = GameState.RUNNING;
            System.out.println("Game resumed");
        }
    }

    public int getSize() {
        return size;
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public int getScore() {
        return score;
    }

    public GameState getState() {
        return state;
    }
}
