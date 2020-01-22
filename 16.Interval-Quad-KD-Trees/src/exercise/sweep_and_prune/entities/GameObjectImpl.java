package exercise.sweep_and_prune.entities;

public class GameObjectImpl implements GameObject {
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;

    private String name;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public GameObjectImpl(String name, int x1, int y1) {
        this(name, x1, x1 + DEFAULT_WIDTH, y1, y1 + DEFAULT_HEIGHT);
    }

    public GameObjectImpl(String name, int x1, int x2, int y1, int y2) {
        this.name = name;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public String getName() {
        return name;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    private void setX1(int x1) {
        this.x1 = x1;
        x2 = x1 + DEFAULT_WIDTH;
    }

    private void setY1(int y1) {
        this.y1 = y1;
        y2 = y1 + DEFAULT_HEIGHT;
    }

    public void move(int newXCoordinate, int newYCoordinate) {
        setX1(newXCoordinate);
        setY1(newYCoordinate);
    }

    @Override
    public boolean intersects(GameObject other) {
        return this.getX1() <= other.getX2() &&
                other.getX1() <= this.getX2() &&
                this.getY1() <= other.getY2() &&
                other.getY1() <= this.getY2();
    }

    @Override
    public int compareTo(GameObject gameObject) {
        return Integer.compare(x1, gameObject.getX1());
    }
}

