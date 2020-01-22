package exercise.sweep_and_prune.containers;

import exercise.sweep_and_prune.entities.GameObject;

public interface GameObjectContainer<T extends GameObject, Comparable> {
    void insert(T gameObject);

    String move(String name, int newXCoordinate, int newYCoordinate);

    String tick();
}
