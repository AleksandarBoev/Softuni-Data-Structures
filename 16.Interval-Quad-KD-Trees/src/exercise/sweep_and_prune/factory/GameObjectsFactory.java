package exercise.sweep_and_prune.factory;

import exercise.sweep_and_prune.entities.GameObject;
import exercise.sweep_and_prune.entities.GameObjectImpl;

public class GameObjectsFactory implements Factory<GameObject> {
    @Override
    public GameObject create(String... data) {
        return new GameObjectImpl(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
    }
}
