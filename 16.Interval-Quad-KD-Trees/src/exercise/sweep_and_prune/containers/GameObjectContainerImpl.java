package exercise.sweep_and_prune.containers;

import exercise.sweep_and_prune.entities.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameObjectContainerImpl implements GameObjectContainer {
    private List<GameObject> gameObjects;
    private int tickCounter;

    public GameObjectContainerImpl() {
        gameObjects = new ArrayList<>();
    }

    @Override
    public void insert(GameObject gameObject) {
        insertSort(gameObject);
    }

    @Override
    public String move(String name, int newXCoordinate, int newYCoordinate) {
        GameObject gameObjectToMove = null;

        for (int i = 0; i < gameObjects.size(); i++) {
            if (name.equals(gameObjects.get(i).getName())) {
                gameObjectToMove = gameObjects.get(i);
                gameObjects.remove(i);
            }
        }

        if (gameObjectToMove == null) {
            return tick();
        }

        gameObjectToMove.move(newXCoordinate, newYCoordinate);
        insertSort(gameObjectToMove);

        return tick();
    }

    @Override
    public String tick() {
        tickCounter++;

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < gameObjects.size() - 1; i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                if (xCoordinatesIntersect(gameObjects.get(i), gameObjects.get(j))) {
                    if (gameObjects.get(i).intersects(gameObjects.get(j))) {
                        stringBuilder.append(String.format("(%d) %s collides with %s%n",
                                tickCounter,
                                gameObjects.get(i).getName(),
                                gameObjects.get(j).getName()));
                    }
                } else {
                    break; // if close to one another game objects by X do not collide, then further ones will also not collide for sure
                }
            }
        }

        return stringBuilder.toString().trim();
    }

    /*
        Idea of sort:
        Start comparing the element to be added with all list elements from last to first.
        Update the element index each time the element to insert is smaller than the current element.
        When the element to add is BIGGER than current element, break the loop. It means the element index has been found.
        If element index has not been changed from its initial value, then that means the element to add is bigger than
        the last element. And just needs to be added normally.
        Visualization of algorithm: https://upload.wikimedia.org/wikipedia/commons/0/0f/Insertion-sort-example-300px.gif
    */
    private void insertSort(GameObject element) {
        int elementIndex = -1;

        for (int i = gameObjects.size() - 1; i >= 0; i--) {
            if (element.compareTo(gameObjects.get(i)) <= 0) {
                elementIndex = i;
            } else {
                break;
            }
        }

        if (elementIndex == -1) {
            gameObjects.add(element);
        } else {
            gameObjects.add(elementIndex, element);
        }
    }

    private boolean xCoordinatesIntersect(GameObject gameObject1, GameObject gameObject2) {
        return gameObject1.getX1() < gameObject2.getX2()
                && gameObject1.getX2() > gameObject2.getX1();
    }
}
