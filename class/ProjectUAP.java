package projectuap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectUAP extends Application {
    private Stage primaryStage;
    private Scene menuScene, gameScene;
    private FlappyBird flappyBird;

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Starting application...");
            this.primaryStage = primaryStage;

            // Buat instance FlappyBird
            flappyBird = new FlappyBird();
            flappyBird.initGame();
            gameScene = new Scene(flappyBird.getGameRoot(), 360, 640);

            // Buat menu scene
            menuScene = createMenuScene();

            // Tampilkan menu pertama kali
            primaryStage.setTitle("Flappy Bird");
            primaryStage.setScene(menuScene);
            primaryStage.show();
            System.out.println("Application started successfully.");
        } catch (Exception e) {
            System.err.println("Error in start method: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Scene createMenuScene() {
        try {
            System.out.println("Creating menu scene...");
            // Load Background
            Image menuBackground = new Image(getClass().getResource("/projectuap/MenuBg.png").toExternalForm());
            ImageView menuBackgroundView = new ImageView(menuBackground);
            menuBackgroundView.setFitWidth(360);
            menuBackgroundView.setFitHeight(640);

            // Buttons
            Button playButton = createImageButton("/projectuap/ButtonStart.png");
            Button exitButton = createImageButton("/projectuap/ButtonExit.png");

            // Button Actions
            playButton.setOnAction(e -> {
                System.out.println("Play button clicked.");
                flappyBird.restartGame();
                primaryStage.setScene(gameScene);
                flappyBird.getGameRoot().requestFocus(); // Request focus for key events
            });
            exitButton.setOnAction(e -> {
                System.out.println("Exit button clicked.");
                System.exit(0);
            });

            // Layout
            VBox buttons = new VBox(20, playButton, exitButton);
            buttons.setStyle("-fx-alignment: center;");

            StackPane root = new StackPane(menuBackgroundView, buttons);
            System.out.println("Menu scene created successfully.");
            return new Scene(root, 360, 640);
        } catch (Exception e) {
            System.err.println("Error creating menu scene: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Button createImageButton(String imagePath) {
        try {
            Image buttonImage = new Image(getClass().getResource(imagePath).toExternalForm());
            ImageView buttonImageView = new ImageView(buttonImage);
            Button button = new Button("", buttonImageView);
            button.setStyle("-fx-background-color: transparent;");
            return button;
        } catch (Exception e) {
            System.err.println("Error creating image button: " + e.getMessage());
            e.printStackTrace();
            return new Button("Error");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
