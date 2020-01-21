package exercise.quad_tree;

import java.util.ArrayList;
import java.util.List;

public class QuadTree<T extends Boundable> {

    public static final int DEFAULT_MAX_DEPTH = 5;

    private int maxDepth;

    private Node<T> root;

    private Rectangle bounds;

    private int count;

    public QuadTree(int width, int height) {
        this(width, height, DEFAULT_MAX_DEPTH);
    }

    public QuadTree(int width, int height, int maxDepth) {
        this.root = new Node<T>(0, 0, width, height);
        this.bounds = this.root.getBounds();
        this.maxDepth = maxDepth;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    private void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getCount() {
        return this.count;
    }

    private void setCount(int count) {
        this.count = count;
    }

    public boolean insert(T item) {
        //Initially there are bounds for the quad tree in which it operates. Trying to insert outside those bounds
        //ain't gonna happen
        if (!item.getBounds().isInside(bounds)) {
            return false;
        }

        int depth = 1;
        Node<T> currentNode = root;
        while (currentNode.getChildren() != null) {
            int quadrant = getQuadrant(currentNode, item.getBounds());
            if (quadrant == -1) {
                break;
            }

            currentNode = currentNode.getChildren()[quadrant];
            depth++;
        }

        currentNode.getItems().add(item);
        split(currentNode, depth);
        count++;

        return true;
    }

    private void split(Node<T> currentNode, int nodeDepth) {
        if (!currentNode.shouldSplit() || nodeDepth > DEFAULT_MAX_DEPTH) {
            return;
        }

        int leftWidth = currentNode.getBounds().getWidth() / 2;
        int rightWidth = currentNode.getBounds().getWidth() - leftWidth; // current node width can be an odd number
        int topHeight = currentNode.getBounds().getHeight() / 2;
        int bottomHeight = currentNode.getBounds().getHeight() - topHeight;

        currentNode.setChildren(new Node[Node.MAX_ITEM_COUNT]);

        //this node will represent the top right quadrant
        currentNode.setChild(0, new Node<>(currentNode.getBounds().getMidX(), currentNode.getBounds().getY1(), rightWidth, topHeight));
        //top left
        currentNode.setChild(1, new Node<>(currentNode.getBounds().getX1(), currentNode.getBounds().getY1(), leftWidth, topHeight));
        //bottom left
        currentNode.setChild(2, new Node<>(currentNode.getBounds().getX1(), currentNode.getBounds().getMidY(), leftWidth, bottomHeight));
        //bottom right
        currentNode.setChild(3, new Node<>(currentNode.getBounds().getMidX(), currentNode.getBounds().getMidY(), rightWidth, bottomHeight));

        for (int i = 0; i < currentNode.getItems().size(); i++) {
            T item = currentNode.getItems().get(i);
            int quadrant = getQuadrant(currentNode, item.getBounds());
            if (quadrant != -1) {
                currentNode.getItems().remove(item);
                currentNode.getChildren()[quadrant].getItems().add(item);
                i--;
            }
        }

        for (Node child : currentNode.getChildren()) {
            split(currentNode, nodeDepth + 1);
        }
    }

    private int getQuadrant(Node<T> currentNode, Rectangle itemToInsertBounds) {
        if (currentNode == null) {
            return -1;
        }

        // if the item's to insert smaller Y coordinate (closer to beginning of Y coordinates) is bigger (lower in
        // coordinate system) than the smaller Y coordinate of the currentNode rectangle, a.k.a. is inside of Y1 boundary
        // && the bigger Y coordinate (further from beginning of Y coordinates) of the item to insert is smaller
        // (closer to beginning of Y coordinates) than the middle of the quadrant
        // ==> then it is in its upper half, a.k.a. in the upper quadrant of the current node.
        boolean inUpperQuadrant = currentNode.getBounds().getY1() <= itemToInsertBounds.getY1()
                && currentNode.getBounds().getMidY() >= itemToInsertBounds.getY2();

        boolean inLowerQuadrant = currentNode.getBounds().getY2() >= itemToInsertBounds.getY2()
                && currentNode.getBounds().getMidY() <= itemToInsertBounds.getY2();

        boolean inLeftQuadrant = currentNode.getBounds().getX1() <= itemToInsertBounds.getX1()
                && currentNode.getBounds().getMidX() >= itemToInsertBounds.getX2();

        boolean inRightQuadrant = currentNode.getBounds().getX2() >= itemToInsertBounds.getX2()
                && currentNode.getBounds().getMidX() <= itemToInsertBounds.getX1();

        // quadrants are numbered as so: top left = 0, top right = 1, bottom left = 2, bottom right = 3
        if (inUpperQuadrant) {
            if (inRightQuadrant) {
                return 0;
            } else if (inLeftQuadrant) {
                return 1;
            }
        } else if (inLowerQuadrant) {
            if (inLeftQuadrant) {
                return 2;
            } else if (inRightQuadrant) {
                return 3;
            }
        }

        return -1; // not inside the current node rectangle at all, let alone in a quadrant
    }

    public List<T> report(Rectangle bounds) {
        List<T> collisionCandidates = new ArrayList<>();

        getCollisionCandidates(root, bounds, collisionCandidates);

        return collisionCandidates;
    }

    private void getCollisionCandidates(Node node, Rectangle bounds, List<T> collisionCandidates) {
        int quadrant = getQuadrant(node, bounds);

        if (quadrant == -1) {
            getSubTreeContents(node, bounds, collisionCandidates);
        } else {
            if (node.getChildren() != null) {
                getCollisionCandidates(node.getChildren()[quadrant], bounds, collisionCandidates);
            }

            collisionCandidates.addAll(node.getItems());
        }
    }

    private void getSubTreeContents(Node node, Rectangle bounds, List<T> collisionCandidates) {
        if (node.getChildren() != null) {
            for (Node child : node.getChildren()) {
                if (child.getBounds().intersects(bounds)) {
                    getSubTreeContents(child, bounds, collisionCandidates);
                }
            }
        }

        collisionCandidates.addAll(node.getItems());
    }
}
