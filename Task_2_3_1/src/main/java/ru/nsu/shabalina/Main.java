package ru.nsu.shabalina;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);

        // Получаем контроллер и устанавливаем обработчик клавиш
        GameController controller = loader.getController();
        scene.setOnKeyPressed(controller::handleKeyPress);

        // Устанавливаем фокус на сцену
        scene.getRoot().requestFocus();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

