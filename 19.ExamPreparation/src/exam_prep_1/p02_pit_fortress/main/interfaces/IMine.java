package exam_prep_1.p02_pit_fortress.main.interfaces;

import exam_prep_1.p02_pit_fortress.main.models.Mine;
import exam_prep_1.p02_pit_fortress.main.models.Player;

public interface IMine extends Comparable<Mine> {

    int getId();

    int getDelay();

    int getDamage();

    int getX();

    Player getPlayer();
}
