package exercise.sweep_and_prune.engine;

import exercise.sweep_and_prune.entities.GameObject;
import exercise.sweep_and_prune.containers.GameObjectContainer;
import exercise.sweep_and_prune.factory.Factory;
import exercise.sweep_and_prune.io.Reader;
import exercise.sweep_and_prune.io.Writer;

import java.io.IOException;

public class SweepAndPrune implements Engine {
    private Reader reader;
    private Writer writer;
    private Factory<GameObject> gameObjectFactory;
    private GameObjectContainer gameObjectContainer;

    public SweepAndPrune(Reader reader,
                         Writer writer,
                         Factory<GameObject> gameObjectFactory,
                         GameObjectContainer gameObjectContainer) {
        this.reader = reader;
        this.writer = writer;
        this.gameObjectFactory = gameObjectFactory;
        this.gameObjectContainer = gameObjectContainer;
    }

    @Override
    public void run() throws IOException {
        String input;
        while (!(input = reader.readLine()).equals("end")) {
            String[] inputTokens = input.split(" ");

            switch (inputTokens[0]) {
                case "add":
                    GameObject newGameObject = gameObjectFactory.create(inputTokens[1], inputTokens[2], inputTokens[3]);
                    gameObjectContainer.insert(newGameObject);
                    break;

                case "tick":
                    String result = gameObjectContainer.tick();
                    if ("".equals(result)) {
                        break;
                    }
                    writer.writeLine(result);
                    break;

                case "move":
                    result = gameObjectContainer.move(inputTokens[1], Integer.parseInt(inputTokens[2]), Integer.parseInt(inputTokens[3]));
                    writer.writeLine(result);
                    break;
            }
        }
    }
}
