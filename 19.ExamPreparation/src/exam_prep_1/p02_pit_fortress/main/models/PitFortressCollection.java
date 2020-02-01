package exam_prep_1.p02_pit_fortress.main.models;

import exam_prep_1.p02_pit_fortress.main.interfaces.IMine;
import exam_prep_1.p02_pit_fortress.main.interfaces.IMinion;
import exam_prep_1.p02_pit_fortress.main.interfaces.IPitFortress;

import java.util.*;

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
    private static final String NOT_ENOUGH_PLAYERS_MESSAGE = "Not enough players!";
    private static final String NO_SUCH_PLAYER_MESSAGE = "Player not found!";
    private static final int MINIMUM_DELAY_ALLOWED = 1;
    private static final int MAXIMUM_DELAY_ALLOWED = 10000;
    private static final String INVALID_MINE_DELAY_MESSAGE =
            "Mine delay should be between "
                    + MINIMUM_DELAY_ALLOWED
                    + " and "
                    + MAXIMUM_DELAY_ALLOWED
                    + " inclusive.";
    private static final String INVALID_MINE_COORDINATE_MESSAGE =
            "Mine coordinate should be between "
            + X_COORDINATE_LOWER_BOUND
            + " and "
            + X_COORDINATE_UPPER_BOUND
            + " inclusive";

    private HashMap<String, Player> namePlayer;
    private TreeMap<Integer, TreeMap<Integer, Minion>> xCoordinateToIdMinion;
    private NavigableMap<Integer, NavigableMap<String, Player>> scoreToNamePlayer;
    private TreeMap<Integer, TreeMap<Integer, Mine>> delayToIdMine;

    private int mineIdCounter;
    private int minionIdCounter;

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

        Minion minion = new Minion(++minionIdCounter, xCoordinate);

        if (!xCoordinateToIdMinion.containsKey(xCoordinate)) {
            xCoordinateToIdMinion.put(xCoordinate, new TreeMap<>());
        }

        xCoordinateToIdMinion.get(xCoordinate).put(minion.getId(), minion);
    }

    /*
    •	SetMine(playerName, xCoordinate, delay, damage) – The given player shoots a mine
    at position X-coordinate. After {delay} turns, all minions in the range
    [X – explosion radius, X + explosion radius] receive {damage} damage.
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
        if (player == null) {
            throw new IllegalArgumentException(NO_SUCH_PLAYER_MESSAGE);
        }

        if (xCoordinate < X_COORDINATE_LOWER_BOUND || xCoordinate > X_COORDINATE_UPPER_BOUND) {
            throw new IllegalArgumentException(INVALID_MINE_COORDINATE_MESSAGE);
        }

        if (delay < MINIMUM_DELAY_ALLOWED || delay > MAXIMUM_DELAY_ALLOWED) {
            throw new IllegalArgumentException(INVALID_MINE_DELAY_MESSAGE);
        }

        Mine mine = new Mine(++mineIdCounter, delay, xCoordinate, player, damage);
        if (!delayToIdMine.containsKey(delay)) {
            delayToIdMine.put(delay, new TreeMap<>());
        }

        delayToIdMine.get(delay).put(mine.getId(), mine);
    }

    @Override
    public Iterable<Minion> reportMinions() {
        ArrayList<Minion> result = new ArrayList<>();

        for (TreeMap<Integer, Minion> idMinion : xCoordinateToIdMinion.values()) {
            result.addAll(idMinion.values());
        }

        return result;
    }

    @Override
    public Iterable<Player> top3PlayersByScore() {
        ArrayList<Player> top3Players = new ArrayList<>(3);

        int counter = 0;
        for (NavigableMap<String, Player> namePlayer : scoreToNamePlayer.descendingMap().values()) {
            for (Player currentPlayer : namePlayer.descendingMap().values()) {
                top3Players.add(currentPlayer);
                if (++counter == 3) {
                    break;
                }
            }

            if (counter == 3) {
                break;
            }
        }

        if (counter < 3) {
            throw new IllegalArgumentException(NOT_ENOUGH_PLAYERS_MESSAGE);
        }

        return top3Players;
    }

    @Override
    public Iterable<Player> min3PlayersByScore() {
        ArrayList<Player> min3Players = new ArrayList<>(3);

        int counter = 0;
        for (NavigableMap<String, Player> namePlayer : scoreToNamePlayer.values()) {
            for (Player currentPlayer : namePlayer.values()) {
                min3Players.add(currentPlayer);
                if (++counter == 3) {
                    break;
                }
            }

            if (counter == 3) {
                break;
            }
        }

        if (counter < 3) {
            throw new IllegalArgumentException(NOT_ENOUGH_PLAYERS_MESSAGE);
        }

        return min3Players;
    }

    @Override
    public Iterable<Mine> getMines() {
        ArrayList<Mine> result = new ArrayList<>();

        for (TreeMap<Integer, Mine> idMine : delayToIdMine.values()) {
            result.addAll(idMine.values());
        }

        return result;
    }

    /*
    - First: the delays on mines are updated and so is the data structure which uses the delays as keys.
    - Second: all mines with updated delay, which equals 0 are saved into a new variable. They are also
    removed from the data structure, which uses delays as keys, because they will explode and will not be used after.
    - Third: for each exploding mine, the effect of area is calculated and the minions affected are saved in a
    collection. Then each of those minions takes damage from the mine. If it dies, the mines owner (player)
    gets a point (and the player data structure is updated) and the minion is taken out of the data structure so
    that it does not give other players points.
     */
    @Override
    public void playTurn() {
        TreeMap<Integer, TreeMap<Integer, Mine>> delayToIdMineUpdated = new TreeMap<>();

        for (Map.Entry<Integer, TreeMap<Integer, Mine>> delayToIdMineMapEntry : delayToIdMine.entrySet()) {
            Integer newDelay = delayToIdMineMapEntry.getKey() - 1;
            delayToIdMineUpdated.put(newDelay, delayToIdMineMapEntry.getValue());

            for (Mine currentMine : delayToIdMineMapEntry.getValue().values()) {
                currentMine.setDelay(newDelay);
            }
        }

        delayToIdMine = delayToIdMineUpdated;
        TreeMap<Integer, Mine> idMineExploding = delayToIdMine.remove(0);

        if (idMineExploding == null) {
            return;
        }

        for (Mine currentMine : idMineExploding.values()) { // iterating mines with delay 0, a.k.a. exploding mines
            int[] mineEffectArea = getMineEffectArea(currentMine);

            SortedMap<Integer, TreeMap<Integer, Minion>> affectedMinionsCoordinateToIdMinion =
                    xCoordinateToIdMinion.subMap(mineEffectArea[0], mineEffectArea[1] + 1);

            HashMap<Integer, HashSet<Integer>> minionsToDeleteCoordinateToIds = new HashMap<>();

            // iterating affected minions by currently exploding mine
            for (Map.Entry<Integer, TreeMap<Integer, Minion>> coordinateToIdMinion : affectedMinionsCoordinateToIdMinion.entrySet()) {
                for (Minion currentMinion : coordinateToIdMinion.getValue().values()) {
                    currentMinion.setHealth(currentMinion.getHealth() - currentMine.getDamage()); // gets hit from explosion

                    if (currentMinion.getHealth() <= 0) { // dies
                        Player mineOwner = currentMine.getPlayer();

                        int oldScore = mineOwner.getScore();
                        int newScore = oldScore + 1;
                        mineOwner.setScore(newScore);

                        scoreToNamePlayer.get(oldScore).remove(mineOwner.getName());
                        if (scoreToNamePlayer.get(oldScore).isEmpty()) {
                            scoreToNamePlayer.remove(oldScore);
                        }

                        if (!scoreToNamePlayer.containsKey(newScore)) {
                            scoreToNamePlayer.put(newScore, new TreeMap<>());
                        }

                        scoreToNamePlayer.get(newScore).put(mineOwner.getName(), mineOwner);

                        if (!minionsToDeleteCoordinateToIds.containsKey(currentMinion.getX())) {
                            minionsToDeleteCoordinateToIds.put(currentMinion.getX(), new HashSet<>());
                        }

                        minionsToDeleteCoordinateToIds.get(currentMinion.getX()).add(currentMinion.getId());
                    }
                }
            }

            for (Map.Entry<Integer, HashSet<Integer>> integerHashSetEntry : minionsToDeleteCoordinateToIds.entrySet()) {
                xCoordinateToIdMinion.get(integerHashSetEntry.getKey()).keySet().removeAll(integerHashSetEntry.getValue());

                if (xCoordinateToIdMinion.get(integerHashSetEntry.getKey()).isEmpty()) {
                    xCoordinateToIdMinion.remove(integerHashSetEntry.getKey());
                }
            }
        }
    }

    private int getCount(Map<Object, Map<Object, Object>> mapWithInnerMap) {
        int counter = 0;

        for (Map value : mapWithInnerMap.values()) {
            counter += value.size();
        }

        return counter;
    }

    /**
     *
     * @param mine
     * @return int[] -> first element is left bound, second element is right bound
     */
    private int[] getMineEffectArea(Mine mine) {
        int x1 = mine.getX() - mine.getPlayer().getRadius();
        int x2 = mine.getX() + mine.getPlayer().getRadius();

        return new int[] {x1, x2};
    }
}
