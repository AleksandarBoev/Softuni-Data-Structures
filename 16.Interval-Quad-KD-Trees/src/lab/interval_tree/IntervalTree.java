package lab.interval_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IntervalTree {

    private class Node {

        private Interval interval;
        private double max;
        private Node right;
        private Node left;

        public Node(Interval interval) {
            this.interval = interval;
            this.max = interval.getHi();
        }

        @Override
        public String toString() {
            return interval.toString() + " | max = " + max;
        }
    }

    private Node root;

    public void insert(double lo, double hi) {
        this.root = this.insert(this.root, lo, hi);
    }

    public void eachInOrder(Consumer<Interval> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    public Interval searchAny(double lo, double hi) {
        Node currentNode = root;

        while (currentNode != null && !currentNode.interval.intersects(lo, hi)) {
            if (currentNode.left != null && currentNode.left.max > lo) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
        }

        return currentNode == null ? null : currentNode.interval;
    }

    public Iterable<Interval> searchAll(double lo, double hi) {
        List<Interval> intersectionIntervals = new ArrayList<>();

        searchAll(root, lo, hi, intersectionIntervals);

        return intersectionIntervals;
    }

    private void searchAll(Node currentNode, double lo, double hi, List<Interval> intersectionIntervals) {
        if (currentNode == null) {
            return;
        }

        if (currentNode.left != null && currentNode.left.max > lo) {
            searchAll(currentNode.left, lo, hi, intersectionIntervals);
        }

        if (currentNode.interval.intersects(lo, hi)) {
            intersectionIntervals.add(currentNode.interval);
        }

        if (currentNode.right != null) { // "&& currentNode.right.interval.getLo() < hi" does not catch the last test, which I added
            searchAll(currentNode.right, lo, hi, intersectionIntervals);
        }
    }

    private void eachInOrder(Node node, Consumer<Interval> consumer) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, consumer);
        consumer.accept(node.interval);
        this.eachInOrder(node.right, consumer);
    }

    private Node insert(Node node, double lo, double hi) {
        if (node == null) {
            return new Node(new Interval(lo, hi));
        }

        int cmp = Double.compare(lo, node.interval.getLo());
        if (cmp < 0) {
            node.left = insert(node.left, lo, hi);
            node.max = getMax(node, node.left);
        } else if (cmp > 0) {
            node.right = insert(node.right, lo, hi);
            node.max = getMax(node, node.right);
        }

        return node;
    }

    private double getMax(Node node1, Node node2) {
        if (node1 == null && node2 == null) {
            return 0.0d;
        } else if (node1 == null) {
            return node2.max;
        } else if (node2 == null) {
            return node1.max;
        } else {
            return Math.max(node1.max, node2.max);
        }
    }
}
