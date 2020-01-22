package exercise.sweep_and_prune.factory;

public interface Factory<T> {
    T create(String... data);
}
