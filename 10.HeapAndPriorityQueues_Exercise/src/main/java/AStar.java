import java.util.*;

public class AStar {
    public static final String INVALID_MAP_FORMAT = "Map should not be null and should have at least 1 row and column";

    private char[][] map;

    public AStar(char[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            throw new IllegalArgumentException(INVALID_MAP_FORMAT);
        }

        this.map = map;
    }

    public static int getH(Node current, Node goal) { // shortest distance to goal without paying attention to obstacles
        return Math.abs(current.getRow() - goal.getRow()) + Math.abs(current.getCol() - goal.getCol());
    }

    public Iterable<Node> getPath(Node start, Node goal) { // can move only horizontally and vertically
        PriorityQueue<Node> lowestFPriorityQueue = new PriorityQueue<>();
        lowestFPriorityQueue.enqueue(start);

        Map<Node, Integer> cellNumberOfMoves = new HashMap<>(); // key -> cell, value -> number of moves made to get to it from start
        cellNumberOfMoves.put(start, 0);

        Map<Node, Node> childParent = new HashMap<>(); // key -> cell, value -> cell it came from
        childParent.put(start, null);

        while (lowestFPriorityQueue.size() != 0 && !lowestFPriorityQueue.peek().equals(goal)) {
            Node currentNode = lowestFPriorityQueue.dequeue();
            addAllDirections(lowestFPriorityQueue, currentNode, cellNumberOfMoves, childParent, goal);
        }

        if (!childParent.containsKey(goal)) { // goal is not reached
            List<Node> listWithNullValue = new ArrayList<>(1);
            listWithNullValue.add(null);
            return listWithNullValue;
        }

        ArrayDeque<Node> nodesFromStartToGoal = new ArrayDeque<>();

        Node current = goal;
        while (current != null) {
            nodesFromStartToGoal.addFirst(current);
            current = childParent.get(current); // current becomes its parent. Only start has null parent
        }

        return nodesFromStartToGoal;
    }

    private boolean validCoordinates(int row, int col) {
        return row >= 0 && row < map.length && col >= 0 && col < map[0].length && map[row][col] != 'W'; // 'W' is for "Wall". Can't go there
    }

    private void addAllDirections(PriorityQueue<Node> lowestFPriorityQueue, Node currentNode, Map<Node, Integer> cellNumberOfMoves, Map<Node, Node> childParent, Node goal) {
        if (validCoordinates(currentNode.getRow() - 1, currentNode.getCol())) { // up
            Node newNode = new Node(currentNode.getRow() - 1, currentNode.getCol());
            updateCell(lowestFPriorityQueue, newNode, currentNode, cellNumberOfMoves, childParent, goal);
        }

        if (validCoordinates(currentNode.getRow() + 1, currentNode.getCol())) { // down
            Node newNode = new Node(currentNode.getRow() + 1, currentNode.getCol());
            updateCell(lowestFPriorityQueue, newNode, currentNode, cellNumberOfMoves, childParent, goal);
        }

        if (validCoordinates(currentNode.getRow(), currentNode.getCol() - 1)) { // left
            Node newNode = new Node(currentNode.getRow(), currentNode.getCol() - 1);
            updateCell(lowestFPriorityQueue, newNode, currentNode, cellNumberOfMoves, childParent, goal);
        }

        if (validCoordinates(currentNode.getRow(), currentNode.getCol() + 1)) { // right
            Node newNode = new Node(currentNode.getRow(), currentNode.getCol() + 1);
            updateCell(lowestFPriorityQueue, newNode, currentNode, cellNumberOfMoves, childParent, goal);
        }
    }

    /**
     *  Checks if cell is visited. If not, just add it to structures and set its "f". <br/>
     *  If it has been visited, then compare how many moves were needed to get to it for this iteration and how many
     *  moves were needed for the previous time. If less moves were needed for this iteration then update the node "F",
     *  the number of moves needed to get to it "cellNumberOfMoves" and the parent, from which the node has been reached
     *  via "childParent".
     */
    private void updateCell(PriorityQueue<Node> lowestFPriorityQueue, Node newNode, Node currentNode, Map<Node, Integer> cellNumberOfMoves, Map<Node, Node> childParent, Node goal) {
        Integer numberOfMovesNeededToGetToNewCell = cellNumberOfMoves.get(currentNode) + 1; // sum number of moves needed to get to parent and 1 (they are neighbors)
        Integer numberOfMovesFromNewCellToGoal = getH(newNode, goal);

        if (!cellNumberOfMoves.containsKey(newNode)) { //first time visited cell with these coordinates
            cellNumberOfMoves.put(newNode, numberOfMovesNeededToGetToNewCell);
            childParent.put(newNode, currentNode); // newNode is child and parent node (the node from which the child is reached) is "currentNode"
            newNode.setF(numberOfMovesNeededToGetToNewCell + numberOfMovesFromNewCellToGoal); // number of moves needed to get to the cell + number of moves needed to get to goal
            lowestFPriorityQueue.enqueue(newNode);
        } else { // visited a second/third/... time
            if (numberOfMovesNeededToGetToNewCell < cellNumberOfMoves.get(newNode)) { // if we got to this cell in less moves, then that's a possible better solution and it NEEDS to be updated
                cellNumberOfMoves.put(newNode, numberOfMovesNeededToGetToNewCell);
                childParent.put(newNode, currentNode);
                newNode.setF(numberOfMovesNeededToGetToNewCell + numberOfMovesFromNewCellToGoal);
                lowestFPriorityQueue.decreaseKey(newNode); // update the priority of the node.
            } // do NOT update if we got to this cell in MORE moves than in a previous attempt
        }
    }
}
