package ru.nsu.shabalina.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import ru.nsu.shabalina.model.Coordinate;
import ru.nsu.shabalina.model.Food;
import ru.nsu.shabalina.model.GameField;
import ru.nsu.shabalina.model.Snake;

public class GameView {
    private final Canvas canvas;
    private final GameField gameField;
    private final double cellSize;
    private final int visibleRows; // Количество видимых строк

    public GameView(Canvas canvas, GameField gameField) {
        this.canvas = canvas;
        this.gameField = gameField;
        this.cellSize = canvas.getWidth() / gameField.getSize(); // 600 / 20 = 30 пикселей
        this.visibleRows = (int) (canvas.getHeight() / cellSize); // 360 / 30 = 12 строк
    }

    public void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Градиентный фон (светло-зеленый сверху, чуть темнее снизу)
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, canvas.getHeight(), false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.PALEGREEN),
                new Stop(1, Color.rgb(170, 220, 170))
        );
        gc.setFill(gradient);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Отрисовка клеточной сетки (голубоватые линии)
        gc.setStroke(Color.rgb(100, 150, 200, 0.5)); // Полупрозрачный голубой
        gc.setLineWidth(0.5);
        for (int i = 0; i <= gameField.getSize(); i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, canvas.getHeight());
        }
        for (int i = 0; i <= visibleRows; i++) { // Ограничиваем строки по высоте
            gc.strokeLine(0, i * cellSize, canvas.getWidth(), i * cellSize);
        }

        // Отрисовка змейки
        Snake snake = gameField.getSnake();
        boolean isHead = true;
        for (Coordinate segment : snake.getSegments()) {
            if (segment.getY() >= visibleRows) continue; // Пропускаем, если за пределами видимой области
            gc.setFill(isHead ? Color.rgb(0, 150, 0) : Color.rgb(50, 200, 50)); // Темно-зеленая голова, ярко-зеленое тело
            gc.setStroke(Color.rgb(0, 100, 0)); // Темная обводка
            gc.setLineWidth(1);
            gc.fillRect(segment.getX() * cellSize + 2, segment.getY() * cellSize + 2, cellSize - 4, cellSize - 4);
            gc.strokeRect(segment.getX() * cellSize + 2, segment.getY() * cellSize + 2, cellSize - 4, cellSize - 4);
            isHead = false;
        }

        // Отрисовка еды (круглое "яблоко" с бликом и усиленной анимацией)
        System.out.println("Foods to render: " + gameField.getFoods().size() + ", Positions: " +
                gameField.getFoods().stream().map(f -> f.getPosition().toString()).toList());
        for (Food food : gameField.getFoods()) {
            Coordinate pos = food.getPosition();
            if (pos.getY() >= visibleRows) continue; // Пропускаем, если за пределами видимой области
            double x = pos.getX() * cellSize + cellSize / 2;
            double y = pos.getY() * cellSize + cellSize / 2;
            double baseRadius = cellSize / 2 - 2;

            // Усиленная анимация пульсации, но с ограничением минимального размера
            double scale = 1.0 + 0.15 * Math.sin(System.currentTimeMillis() / 400.0);
            double radius = baseRadius * scale;
            // Ограничиваем минимальный радиус, чтобы еда была видна
            radius = Math.max(radius, baseRadius * 0.7); // Не меньше 70% от базового радиуса

            System.out.println("Drawing food at: (" + x + ", " + y + "), radius: " + radius +
                    ", Canvas bounds: (" + canvas.getWidth() + ", " + canvas.getHeight() + ")");

            // Основной круг (красное яблоко)
            gc.setFill(Color.rgb(200, 0, 0));
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            // Блик для объема
            gc.setFill(Color.rgb(255, 100, 100, 0.6));
            gc.setGlobalBlendMode(BlendMode.SRC_OVER);
            gc.fillOval(x - radius / 2, y - radius / 2, radius, radius);

            // Обводка
            gc.setStroke(Color.rgb(150, 0, 0));
            gc.setLineWidth(1.5);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }
}
