package exam_prep_1.p02_pit_fortress.main.models;


public class Player implements exam_prep_1.p02_pit_fortress.main.interfaces.IPlayer {

    private int radius;
    private String name;
    private int score;

    public Player(int radius, String name) {
        this.radius = radius;
        this.name = name;
        score = 0; // better readability
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int value) {
        this.score = value;
    }

    @Override
    public int compareTo(Player o) {
       return 0;
    }
}
