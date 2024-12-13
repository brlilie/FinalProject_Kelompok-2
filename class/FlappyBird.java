package projectuap;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.List;

public class FlappyBird {
    private static final int SCREEN_WIDTH = 360;
    private static final int SCREEN_HEIGHT = 640;
    private static final int PIPE_SPACING = 120;
    
    private StackPane root;
    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private Image backgroundImg;
    
    private Bird bird;
    private List<Pipe> pipes;
    private boolean gameOver;
    private double score;
    
    public void initGame() {
        setupGameComponents();
        initializeGameState();
        setupInputHandling();
        startGameLoop();
    }
    
    private void setupGameComponents() {
        root = new StackPane();
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        backgroundImg = new Image(getClass().getResource("/projectuap/GameBg.png").toExternalForm());
    }
    
    private void initializeGameState() {
        bird = new Bird(SCREEN_WIDTH / 8, SCREEN_HEIGHT / 2);
        pipes = new ArrayList<>();
        score = 0;
        gameOver = false;
    }
    
    private void setupInputHandling() {
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                handleSpacePress();
            }
        });
        root.setFocusTraversable(true);
    }
    
    private void handleSpacePress() {
        if (!gameOver) {
            bird.jump();
        } else {
            restartGame();
        }
    }
    
    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) {
                    updateGame();
                }
                renderGame();
            }
        };
        gameLoop.start();
    }
    
    private void updateGame() {
        bird.update();
        
        if (bird.isOutOfBounds(SCREEN_HEIGHT)) {
            gameOver = true;
            return;
        }
        
        updatePipes();
        checkCollisions();
        updateScore();
    }
    
    private void updatePipes() {
        pipes.forEach(Pipe::update);
        pipes.removeIf(pipe -> pipe.getX() + pipe.getWidth() < 0);
        
        if (pipes.isEmpty() || pipes.get(pipes.size() - 1).getX() < SCREEN_WIDTH / 2) {
            addNewPipes();
        }
    }
    
    private void addNewPipes() {
        double pipeX = SCREEN_WIDTH;
        double topPipeY = -128 - (Math.random() * 256);
        
        pipes.add(new Pipe(pipeX, topPipeY, true));
        pipes.add(new Pipe(pipeX, topPipeY + 512 + PIPE_SPACING, false));
    }
    
    private void checkCollisions() {
        for (Pipe pipe : pipes) {
            if (bird.collidesWith(pipe)) {
                gameOver = true;
                return;
            }
        }
    }
    
    private void updateScore() {
        for (Pipe pipe : pipes) {
            if (!pipe.isPassed() && bird.getX() > pipe.getX() + pipe.getWidth()) {
                score += 0.5;
                pipe.setPassed(true);
            }
        }
    }
    
    private void renderGame() {
        gc.drawImage(backgroundImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        for (Pipe pipe : pipes) {
            gc.drawImage(pipe.getSprite(), pipe.getX(), pipe.getY(), 
                        pipe.getWidth(), pipe.getHeight());
        }
        
        gc.drawImage(bird.getSprite(), bird.getX(), bird.getY(), 
                    bird.getWidth(), bird.getHeight());
        
        renderUI();
    }
    
    private void renderUI() {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Montserrat", 24));
        gc.fillText("Score: " + (int) score, 10, 20);
        
        if (gameOver) {
            gc.fillText("Game Over", 120, 300);
            gc.fillText("Press SPACE to restart", 70, 330);
        }
    }
        
    public void restartGame() {
        initializeGameState();
        if (gameLoop != null) {
            gameLoop.stop();
        }
        gameLoop.start();
    }
    
    public StackPane getGameRoot() {
        return root;
    }
}