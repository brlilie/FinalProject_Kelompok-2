package projectuap;

import javafx.scene.image.Image;

public abstract class GameObject {
    protected double x, y, width, height;
    protected Image sprite;
    
    public GameObject(double x, double y, double width, double height, String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        loadSprite(imagePath);
    }
    
    private void loadSprite(String imagePath) {
        try {
            this.sprite = new Image(getClass().getResource(imagePath).toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading sprite: " + e.getMessage());
        }
    }
    
    public abstract void update();
    
    public boolean collidesWith(GameObject other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }
    
    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public Image getSprite() { return sprite; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}