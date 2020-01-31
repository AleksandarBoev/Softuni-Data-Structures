package exam_prep_1.p02_pit_fortress.main.interfaces;


import exam_prep_1.p02_pit_fortress.main.models.Minion;

public interface IMinion extends Comparable<Minion> {

    int getId();

    int getX();

    int getHealth();
}
