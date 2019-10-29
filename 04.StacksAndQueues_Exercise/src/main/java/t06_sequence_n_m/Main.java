package t06_sequence_n_m;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Integer[] inputNumbers = Arrays.stream(consoleReader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .toArray(n -> new Integer[n]);
        consoleReader.close();

        Integer startingNumber = inputNumbers[0];
        Integer endNumber = inputNumbers[1];

        Node firstNode = new Node(startingNumber, null);
        ArrayDeque<Node> nodesQueue = new ArrayDeque<>();

        nodesQueue.addLast(firstNode);

        Node shortestDistanceNode = null;
        while (!nodesQueue.isEmpty()) {
            Node currentNode = nodesQueue.poll();

            if (currentNode.value < endNumber) {
                Node currentPlusOne = new Node(currentNode.value + 1, currentNode);
                Node currentPlusTwo = new Node(currentNode.value + 2, currentNode);
                Node currentTimesTwo = new Node(currentNode.value * 2, currentNode);

                nodesQueue.addLast(currentPlusOne);
                nodesQueue.addLast(currentPlusTwo);
                nodesQueue.addLast(currentTimesTwo);
            } else if (currentNode.value == endNumber) {
                System.out.println(getOutput(currentNode));
                return;
            }
        }
    }

    private static String getOutput(Node shortestDistanceNode) {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> numbersStack = new ArrayDeque<>(); //to have the numbers in correct order
        while (shortestDistanceNode != null) {
            numbersStack.addFirst(shortestDistanceNode.value);
            shortestDistanceNode = shortestDistanceNode.prev;
        }

        while (!numbersStack.isEmpty()) {
            sb.append(numbersStack.pop()).append(" -> ");
        }

        return sb.substring(0, sb.lastIndexOf(" -> "));
    }

    private static class Node {
        private Integer value;
        private Node prev;

        public Node(Integer value, Node prev) {
            this.value = value;
            this.prev = prev;
        }
    }
}