package ru.nsu.shabalina;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import ru.nsu.shabalina.model.Direction;
import ru.nsu.shabalina.model.GameField;
import ru.nsu.shabalina.model.GameState;
import ru.nsu.shabalina.view.GameView;

public class GameController {
    @FXML
    private Canvas gameCanvas;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label stateLabel;

    private GameField gameField;
    private GameView gameView;
    private AnimationTimer gameLoop;

    @FXML
    public void initialize() {
        // Инициализация игрового поля и представления
        gameField = new GameField(20);
        gameView = new GameView(gameCanvas, gameField);

        // Устанавливаем фокус на холст
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
        System.out.println("Canvas focus requested on initialize");

        // Настройка игрового цикла
        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long updateInterval = 200_000_000; // 200 мс (5 обновлений в секунду)

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= updateInterval) {
                    gameField.update();
                    gameView.render();
                    updateUI();
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();

        // Начальная отрисовка
        gameView.render();
        updateUI();

        // Дополнительная проверка фокуса при клике
        gameCanvas.setOnMouseClicked(event -> {
            gameCanvas.requestFocus();
            System.out.println("Canvas focus requested on click");
        });
    }

    private void updateUI() {
        scoreLabel.setText("Score: " + gameField.getScore());
        GameState state = gameField.getState();
        stateLabel.setText(state == GameState.RUNNING ? "" :
                state == GameState.PAUSED ? "Paused" :
                        "Game Over");
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        System.out.println("Key pressed: " + event.getCode() + ", Canvas focused: " + gameCanvas.isFocused());
        switch (event.getCode()) {
            case UP, W -> gameField.setDirection(Direction.UP);
            case DOWN, S -> gameField.setDirection(Direction.DOWN);
            case LEFT, A -> gameField.setDirection(Direction.LEFT);
            case RIGHT, D -> gameField.setDirection(Direction.RIGHT);
            case SPACE -> gameField.togglePause();
        }
        gameCanvas.requestFocus();
    }
}

