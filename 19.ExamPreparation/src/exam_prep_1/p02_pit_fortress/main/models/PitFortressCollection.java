package exam_prep_1.p02_pit_fortress.main.models;

import exam_prep_1.p02_pit_fortress.main.interfaces.IMine;
import exam_prep_1.p02_pit_fortress.main.interfaces.IMinion;
import exam_prep_1.p02_pit_fortress.main.interfaces.IPitFortress;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class PitFortressCollection implements IPitFortress {
    private static final String DUPLICATE_PLAYER_NAME_MESSAGE = "A player with this name already exists!";
    private static final String NEGATIVE_MINE_RADIUS_MESSAGE = "Mine radius can't be a negative number!";
    private static final int X_COORDINATE_LOWER_BOUND = 0;
    private static final int X_COORDINATE_UPPER_BOUND = 1000000;
    private static final String INVALID_MINION_COORDINATE_MESSAGE =
            "Minion coordinate should be between "
                    + X_COORDINATE_LOWER_BOUND
                    + " and "
                    + X_COORDINATE_UPPER_BOUND;

    HashMap<String, Player> namePlayer;
    TreeMap<Integer, TreeMap<Integer, IMinion>> xCoordinateToIdMinion;
    NavigableMap<Integer, NavigableMap<String, Player>> scoreToNamePlayer;
    TreeMap<Integer, TreeMap<Integer, IMine>> delayToIdMine;

    public PitFortressCollection() {
        namePlayer = new HashMap<>();
        xCoordinateToIdMinion = new TreeMap<>();
        scoreToNamePlayer = new TreeMap<>();
        scoreToNamePlayer.put(0, new TreeMap<>()); // new players start with score 0, so this will definitely happen
        delayToIdMine = new TreeMap<>();
    }

    @Override
    public int getPlayerCount() {
        int counter = 0;

        for (Map value : scoreToNamePlayer.values()) {
            counter += value.size();
        }

        return counter;
    }

    @Override
    public int getMinionCount() {
        int counter = 0;

        for (Map value : xCoordinateToIdMinion.values()) {
            counter += value.size();
        }

        return counter;
    }

    @Override
    public int getMineCount() {
        int counter = 0;

        for (Map value : delayToIdMine.values()) {
            counter += value.size();
        }

        return counter;
    }

    @Override
    public void addPlayer(String name, int mineRadius) {
        if (namePlayer.containsKey(name)) {
            throw new IllegalArgumentException(DUPLICATE_PLAYER_NAME_MESSAGE);
        }

        if (mineRadius < 0) {
            throw new IllegalArgumentException(NEGATIVE_MINE_RADIUS_MESSAGE);
        }

        Player newPlayer = new Player(mineRadius, name); // an actual mine is created only when a player shoots it

        namePlayer.put(name, newPlayer);
        scoreToNamePlayer.get(0).put(name, newPlayer);
    }

    @Override
    public void addMinion(int xCoordinate) {
        if (xCoordinate < X_COORDINATE_LOWER_BOUND || xCoordinate > X_COORDINATE_UPPER_BOUND) {
            throw new IllegalArgumentException(INVALID_MINION_COORDINATE_MESSAGE);
        }

        IMinion minion = new Minion(xCoordinate);

        if (!xCoordinateToIdMinion.containsKey(xCoordinate)) {
            xCoordinateToIdMinion.put(xCoordinate, new TreeMap<>());
        }

        xCoordinateToIdMinion.get(xCoordinate).put(minion.getId(), minion);
    }

    /*
    •	SetMine(playerName, xCoordinate, delay, damage) – The given player shoots a mine
    at position X-coordinate. After {delay} turns, all minions in the range
    [X – explosion radius, X + explosion radius] receive {damage} damage.
o	If a minion falls at 0 or less health as a result to the damage, it is removed from the game.
The player that shot the mine receives +1 score.
o	If a player with the given name does not exist, throws an Argument Exception.
o	The xCoordinate must always be an integer between [0…1 000 000]. In case of an invalid coordinate
 throws an Argument Exception.
o	The delay must be an integer between [1…10 000]. In case of an invalid delay throws an Argument Exception.
o	The damage must always be an integer between [0…100]. In case of an invalid damage, throws an Argument Exception.
o	The mine Ids are unique - they start from 1 and grow with 1 for every new mine
(ex. first mine will have Id = 1, second - Id = 2 and so on).

     */
    @Override
    public void setMine(String playerName, int xCoordinate, int delay, int damage) {
        Player player = namePlayer.get(playerName);
        IMine mine = new Mine(delay, xCoordinate, player, damage);
        if (!delayToIdMine.containsKey(delay)) {
            delayToIdMine.put(delay, new TreeMap<>());
        }

        delayToIdMine.get(delay).put(mine.getId(), mine);
    }

    @Override
    public Iterable<Minion> reportMinions() {
        return null;
    }

    @Override
    public Iterable<Player> top3PlayersByScore() {
        return null;
    }

    @Override
    public Iterable<Player> min3PlayersByScore() {
        return null;
    }

    @Override
    public Iterable<Mine> getMines() {
        return null;
    }

    @Override
    public void playTurn() {

    }

    private int getCount(Map<Object, Map<Object, Object>> mapWithInnerMap) {
        int counter = 0;

        for (Map value : mapWithInnerMap.values()) {
            counter += value.size();
        }

        return counter;
    }
}
