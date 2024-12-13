package projectuap;

public class Pipe extends GameObject {
    private static final double SPEED = 2.0;
    private boolean passed;

    public Pipe(double x, double y, boolean isTop) {
        super(x, y, 64, 512, isTop ? "/projectuap/TrunkUp.png" : "/projectuap/TrunkDown.png");
        this.passed = false;
    }

    @Override
    public void update() {
        x -= SPEED;
    }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}
