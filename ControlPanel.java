package org.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class ControlPanel extends HBox {
    private final MediaPlayer mediaPlayer;

    // introducing Sliders
    Slider time = new Slider(); // Slider for time
    Slider vol = new Slider(); // Slider for volume
    Button playButton = new Button("||"); // For pausing the player
    Label volumeLabel = new Label("Volume: ");

    public ControlPanel(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;

        setAlignment(Pos.CENTER); // setting the HBox to center
        setPadding(new Insets(5, 10, 5, 10));

        // Settih the preference for volume bar
        vol.setPrefWidth(70);
        vol.setMinWidth(30);
        vol.setValue(100);
        HBox.setHgrow(time, Priority.ALWAYS);
        playButton.setPrefWidth(30);

        // Adding the components to the ControlPanel
        getChildren().addAll(playButton, time, volumeLabel, vol);

        // Adding Functionality
        playButton.setOnAction(e -> {
            Status status = mediaPlayer.getStatus(); // To get the status of Player
            if (status == Status.PLAYING) {
                if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    mediaPlayer.play();
                } else {
                    mediaPlayer.pause();
                    playButton.setText(">");
                }
            } else if (status == Status.HALTED || status == Status.STOPPED || status == Status.PAUSED) {
                mediaPlayer.play();
                playButton.setText("||");
            }
        });

        // Providing functionality to time slider
        mediaPlayer.currentTimeProperty().addListener(ov -> updatesValues());

        time.valueProperty().addListener(ov -> {
            if (time.isPressed()) {
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(time.getValue() / 100));
            }
        });

        // providing functionality to volume slider
        vol.valueProperty().addListener(ov -> {
            if (vol.isPressed()) {
                mediaPlayer.setVolume(vol.getValue() / 100);
            }
        });
    }

    protected void updatesValues() {
        double currentTime = mediaPlayer.getCurrentTime().toMillis();
        double totalDuration = mediaPlayer.getTotalDuration().toMillis();
        double percentage = currentTime / totalDuration * 100.0;
        time.setValue(percentage);
    }
}
