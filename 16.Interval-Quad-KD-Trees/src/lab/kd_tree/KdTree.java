package lab.kd_tree;

import java.util.function.Consumer;

public class KdTree {

    public class Node {

        private Point2D point2D;
        private Node left;
        private Node right;

        public Node(Point2D point) {
            this.setPoint2D(point);
        }

        public Point2D getPoint2D() {
            return this.point2D;
        }

        public void setPoint2D(Point2D point2D) {
            this.point2D = point2D;
        }

        public Node getLeft() {
            return this.left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return point2D.toString();
        }
    }

    private Node root;

    public Node getRoot() {
        return this.root;
    }

    public boolean contains(Point2D point) {
        Node currentNode = root;
        int depth = 0;

        while (currentNode != null && !currentNode.point2D.equals(point)) {
            int pointComparisonResult = comparePoints(currentNode.point2D, point, depth);

            if (pointComparisonResult > 0) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }

            depth++;
        }

        return currentNode != null;
    }

    public void insert(Point2D point) {
        root = insert(root, point, 0);
    }

    private Node insert(Node currentNode, Point2D point2D, int depth) {
        if (currentNode == null) {
            return new Node(point2D);
        }

        int comparisonResult = comparePoints(currentNode.point2D, point2D, depth);

        if (comparisonResult > 0) {
            currentNode.left = insert(currentNode.left, point2D, depth + 1);
        } else if (comparisonResult < 0) {
            currentNode.right = insert(currentNode.right, point2D, depth + 1);
        }

        return currentNode;
    }

    public void eachInOrder(Consumer<Point2D> consumer) {
       this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node node, Consumer<Point2D> consumer) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.getLeft(), consumer);
        consumer.accept(node.getPoint2D());
        this.eachInOrder(node.getRight(), consumer);
    }

    private int compareByX(Point2D point1, Point2D point2) {
        return Double.compare(point1.getX(), point2.getX());
    }

    private int compareByY(Point2D point1, Point2D point2) {
        return Double.compare(point1.getY(), point2.getY());
    }

    private int comparePoints(Point2D point1, Point2D point2, int depth) {
        int comparisonResult;
        if (depth % 2 == 0) {
            comparisonResult = compareByX(point1, point2);

            if (comparisonResult == 0) {
                comparisonResult = compareByY(point1, point2);
            }
        } else {
            comparisonResult = compareByY(point1, point2);

            if (comparisonResult == 0) {
                comparisonResult = compareByX(point1, point2);
            }
        }

        return comparisonResult;
    }
}
