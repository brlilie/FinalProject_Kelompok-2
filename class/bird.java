package projectuap;

public class Bird extends GameObject {
    private static final double GRAVITY = 1.0;
    private static final double JUMP_FORCE = -8.0;
    private double velocityY;
    
    public Bird(double x, double y) {
        super(x, y, 34, 24, "/projectuap/flappybird.png");
        this.velocityY = 0;
    }
    
    public void jump() {
        velocityY = JUMP_FORCE;
    }
    
    @Override
    public void update() {
        velocityY += GRAVITY;
        y += velocityY;
    }
    
    public boolean isOutOfBounds(double screenHeight) {
        return y <= 0 || y >= screenHeight;
    }
}
