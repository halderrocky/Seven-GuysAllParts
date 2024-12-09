package edu.sdccd.cisc190.altscenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FiveDodge {
    private Scene scene;
    private int conviction; // Variable to track the conviction stat
    private int madness; // Variable to track the madness stat
    private final Text gameStatus;
    private final Text statsText;
    private final String saveFileName = "game_data.txt"; // File name for saving and loading game data

    public FiveDodge(Stage primaryStage) {
        // Initial game status text
        gameStatus = new Text("You successfully dodged the attack and you closed the door.\n" +
                "However, Mika the Monkey is smart, and she decides to turn the power back off.\n" +
                "“I should’ve used the water when necessary…”, but at least she’s locked inside.\n" +
                "“I don’t think the office is a good idea, the better idea is to get the hell out of here.”\n");
        gameStatus.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Text for displaying stats (conviction and madness)
        statsText = new Text("Conviction: " + conviction + " | Madness: " + madness);
        statsText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Button actions
        Button oneButton = new Button("Attack with a water bottle (50% remaining)");
        oneButton.setStyle("-fx-font-size: 14px;");
        oneButton.setOnAction(e -> {
            attackWithWaterBottle(); // Call the attack method
            updateStats(); // Update the stats display after the attack
        });

        Button twoButton = new Button("Dodge attack");
        twoButton.setStyle("-fx-font-size: 14px;");
        twoButton.setOnAction(e -> {
            dodgeAttack(); // Call the dodge method
            updateStats(); // Update the stats display after dodging
        });

        Button saveButton = new Button("Save Game Data");
        saveButton.setStyle("-fx-font-size: 14px;");
        saveButton.setOnAction(e -> saveGameData()); // Call save method

        Button loadButton = new Button("Load Game Data");
        loadButton.setStyle("-fx-font-size: 14px;");
        loadButton.setOnAction(e -> loadGameData()); // Call load method

        // Create the BorderPane layout
        BorderPane layout = new BorderPane();

        // Add game status text to the top
        layout.setTop(gameStatus);

        // Add stats text below the game status text
        layout.setBottom(statsText);

        // Create a VBox to arrange buttons vertically
        VBox buttonBox = new VBox(10); // 10px spacing between buttons
        buttonBox.getChildren().addAll(oneButton, twoButton, saveButton, loadButton);

        // Set the VBox containing buttons to the center of the BorderPane
        layout.setCenter(buttonBox);

        // Scene creation with appropriate size
        scene = new Scene(layout, 400, 400);
    }

    // Method to handle attacking with a water bottle
    public void attackWithWaterBottle() {
        if (conviction > 3) {
            gameStatus.setText("You attack successfully and deal damage!");
            conviction += 1; // Increase conviction for a successful attack //TODO fix this method so the conviction actually increases by one
        } else {
            gameStatus.setText("Your attack lacks power; you fail to hit.");
            madness += 1; // Increase madness due to failure //TODO make it do the expected behavior in the test which is decrement
        }
    }

    /**
     * Handles the player's attempt to dodge an attack.
     */
    public void dodgeAttack() {
        if (madness < 5) {
            gameStatus.setText("You dodge the attack gracefully!");
            conviction -= 1; // Decrease conviction for dodging
        } else {
            gameStatus.setText("You are too overwhelmed to dodge effectively!");
            madness += 1; // Increase madness due to failure
        }
    }

    /**
     * Saves the player's current stats to a file.
     */
    public void saveGameData() {
        try (FileWriter writer = new FileWriter(saveFileName)) {
            writer.write("Conviction:" + conviction + "\n");
            writer.write("Madness:" + madness + "\n");
            gameStatus.setText("Game data saved successfully.");
        } catch (IOException e) {
            gameStatus.setText("Error saving game data.");
        }
    }

    /**
     * Loads the player's stats from a file.
     */
    public void loadGameData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFileName))) {
            conviction = Integer.parseInt(reader.readLine().split(":")[1].trim());
            madness = Integer.parseInt(reader.readLine().split(":")[1].trim());
            updateStats(); // Update the UI with loaded stats
            gameStatus.setText("Game data loaded successfully.");
        } catch (IOException e) {
            gameStatus.setText("No saved game data found, starting fresh.");
        } catch (NumberFormatException e) {
            gameStatus.setText("Error loading game data.");
        }
    }

    /**
     * Sets the current madness value.
     *
     * @param i the new madness value to set
     */
    public void setMadness(int i) {
        this.madness = i; // Set the madness to the provided value
    }

    // Method to update the stats text
    private void updateStats() {
        statsText.setText("Conviction: " + conviction + " | Madness: " + madness);
    }

    // Getter for the scene
    public Scene getScene() {
        return scene;
    }

    public int getConviction() {
        return conviction;
    }

    public int getMadness() {
        return madness;
    }
}
