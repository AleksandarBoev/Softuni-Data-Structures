package exercise.sweep_and_prune.io;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleReader implements Reader {
    private BufferedReader reader;

    public ConsoleReader(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }
}
