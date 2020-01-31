package exam_prep_1.p02_pit_fortress.main.models;


import exam_prep_1.p02_pit_fortress.main.interfaces.IMine;

public class Mine implements IMine {
    private static int idCounter = 1;

    private int id;
    private int delay;
    private int x;
    private Player player;
    private int damage;

    public Mine(int delay, int x, Player player, int damage) {
        id = idCounter++;

        this.delay = delay;
        this.x = x;
        this.player = player;
        this.damage = damage;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    public void setDelay(int value) {
        this.delay = value;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public int compareTo(Mine o) {
        return 0;
    }
}
