package exam_prep_1.p02_pit_fortress.main.interfaces;

import exam_prep_1.p02_pit_fortress.main.models.Player;

public interface IPlayer extends Comparable<Player> {

    String getName();

    int getRadius();

    int getScore();
}
