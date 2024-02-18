package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class VideoPlayer extends Application {
    Player player;
    FileChooser fileChooser;

    public void start(final Stage primaryStage) {
        // Setting up the menu
        MenuItem open = new MenuItem("Open");
        Menu file = new Menu("File");
        MenuBar menu = new MenuBar();
        file.getItems().add(open);
        menu.getMenus().add(file);

        // Adding functionality to switch to different videos
        fileChooser = new FileChooser();
        open.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                player.player.pause();
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    try {
                        player = new Player(file.toURI().toURL().toExternalForm());
                        player.setPreserveRatio(true); // сохранение пропорций видео
                        player.prefHeightProperty().bind(primaryStage.heightProperty()); // привязка высоты видео к высоте окна
                        player.prefWidthProperty().bind(primaryStage.widthProperty()); // привязка ширины видео к ширине окна
                        primaryStage.setTitle(file.getName());
                        primaryStage.show();
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        // Слушатель изменения размера окна
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            player.setFitWidth(newVal.doubleValue());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            player.setFitHeight(newVal.doubleValue());
        });

        // Creating initial player
        File file1 = new File("C:/programmer/1.mp4");
        try {
            player = new Player(file1.toURI().toURL().toExternalForm());
            player.setPreserveRatio(true); // сохранение пропорций видео
            player.prefHeightProperty().bind(primaryStage.heightProperty()); // привязка высоты видео к высоте окна
            player.prefWidthProperty().bind(primaryStage.widthProperty()); // привязка ширины видео к ширине окна
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        // Using BorderPane to center and scale the player
        BorderPane root = new BorderPane();
        root.setTop(menu);
        root.setCenter(player);

        // Setting up the scene and stage
        Scene scene = new Scene(root, 720, 535, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Video Player");
        primaryStage.show();
    }

    // Main function to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}

