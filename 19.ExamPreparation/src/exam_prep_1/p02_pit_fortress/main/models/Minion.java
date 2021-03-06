package exam_prep_1.p02_pit_fortress.main.models;


import exam_prep_1.p02_pit_fortress.main.interfaces.IMinion;

public class Minion implements IMinion {
    private static final int DEFAULT_MINION_HEALTH = 100;

    private int id;
    private int x;
    private int health;

    public Minion(int id, int x) {
        this.id = id;
        this.x = x;
        health = DEFAULT_MINION_HEALTH;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int value) {
        this.health = value;
    }

    @Override
    public int compareTo(Minion o) {
        return 0;
    }
}
