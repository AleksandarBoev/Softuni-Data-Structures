package exercise.sweep_and_prune.entities;

public interface GameObject extends Comparable<GameObject> {
    String getName();

    int getX1();

    int getX2();

    int getY1();

    int getY2();

    void move(int newXCoordinate, int newYCoordinate);

    boolean intersects(GameObject other);
}
