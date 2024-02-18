package org.example;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane {
    Media media;
    MediaPlayer player;
    MediaView view;
    Pane mpane;
    ControlPanel controlPanel;

    public Player(String file) {
        media = new Media(file);
        player = new MediaPlayer(media);
        view = new MediaView(player);
        mpane = new Pane();
        mpane.getChildren().add(view);

        // Centering the pane containing the MediaView
        setCenter(mpane);

        // Creating and adding ControlPanel at the bottom
        controlPanel = new ControlPanel(player);
        setBottom(controlPanel);

        setStyle("-fx-background-color:#bfc2c7");
        player.play();

        // Listen for changes in the size of the BorderPane
        widthProperty().addListener((observable, oldValue, newValue) -> resizeMediaPane());
        heightProperty().addListener((observable, oldValue, newValue) -> resizeMediaPane());

        // Set alignment of mpane to center
        BorderPane.setAlignment(mpane, Pos.CENTER);

        // Add error listener to the MediaPlayer
        player.setOnError(() -> {
            System.out.println("Media error occurred: " + player.getError());
        });
    }

    // Method to resize the media pane to match the BorderPane size
    private void resizeMediaPane() {
        mpane.setPrefSize(getWidth(), getHeight());
    }

    public void setPreserveRatio(boolean value) {
        view.setPreserveRatio(value);
    }

    public void setFitWidth(double width) {
        view.setFitWidth(width);
    }

    public void setFitHeight(double height) {
        view.setFitHeight(height);
    }
}

