package exercise.sweep_and_prune;

import exercise.sweep_and_prune.containers.GameObjectContainer;
import exercise.sweep_and_prune.containers.GameObjectContainerImpl;
import exercise.sweep_and_prune.engine.SweepAndPrune;
import exercise.sweep_and_prune.entities.GameObject;
import exercise.sweep_and_prune.factory.Factory;
import exercise.sweep_and_prune.factory.GameObjectsFactory;
import exercise.sweep_and_prune.io.ConsoleReader;
import exercise.sweep_and_prune.io.ConsoleWriter;
import exercise.sweep_and_prune.io.Reader;
import exercise.sweep_and_prune.io.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
Problem description:

Store all objects in a list and sort them by their X1 bound (see image). This way every next object has a greater or equal X1.
- For each object in the list, perform collision detection with all next objects whose X1 is less or equal to the current object's X2.
- Each game frame sort the list again and recheck for collisions in the same way. Use a sorting algorithm which performs fast on nearly sorted collections, such as insertion sort (best case O(n) complexity, worst case - O(n2)).
- Here we assume objects will not move too much between game frames, that's why our list will remain nearly sorted at almost all times.

 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Reader consoleReader = new ConsoleReader(bufferedReader);
        Writer consoleWriter = new ConsoleWriter();
        Factory<GameObject> gameObjectsFactory = new GameObjectsFactory();
        GameObjectContainer gameObjectContainer = new GameObjectContainerImpl();

        SweepAndPrune sweepAndPrune = new SweepAndPrune(consoleReader, consoleWriter, gameObjectsFactory, gameObjectContainer);

        sweepAndPrune.run();
    }
}
