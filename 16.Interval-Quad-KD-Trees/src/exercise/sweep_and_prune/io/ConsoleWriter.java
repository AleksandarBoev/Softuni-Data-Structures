package exercise.sweep_and_prune.io;

public class ConsoleWriter implements Writer {
    @Override
    public void write(String string) {
        System.out.print(string);
    }

    @Override
    public void writeLine(String string) {
        System.out.println(string);
    }
}
