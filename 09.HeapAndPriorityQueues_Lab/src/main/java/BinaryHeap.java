import java.util.ArrayList;
import java.util.List;

public class BinaryHeap<T extends Comparable<T>> { // Max heap implementation (parent element is bigger and all children are smaller)
    public static final String EMPTY_HEAP_EXCEPTION = "No elements in heap!";

    private List<T> heap;
    private int size; //kind of pointless, but this is how I got the project skeleton

    public BinaryHeap() {
        heap = new ArrayList<>();
    }

    public int size() {
        return heap.size();
    }

    public void insert(T element) {
        heap.add(element); // added to last index of array and from there it will climb as much as needed via hepifyUp

        heapifyUp(element, heap.size() - 1);
    }

    public T peek() {
        checkIfHeapIsEmpty();

        return heap.get(0);
    }

    public T pull() {
        checkIfHeapIsEmpty();

        T result = heap.get(0);
        swapValues(0, heap.size() - 1);
        heap.remove(heap.size() - 1);

        heapifyDown(0);

        return result;
    }

    //TODO simplify
    private void heapifyDown(int elementIndex) {
        if (heap.isEmpty()) {
            return;
        }

        T element = heap.get(0);
        int leftChildIndex = getLeftChildIndex(elementIndex);
        int rightChildIndex = getRightChildIndex(elementIndex);

        while (true) {
            if (!isInBound(leftChildIndex)) { //no left child --> no right child either
                break;
            }

            int childrenComparisonResult;

            if (isInBound(rightChildIndex)) { // if has right child
                // if bigger than both children, then no need to move it further down
                if (element.compareTo(heap.get(leftChildIndex)) > 0 && element.compareTo(heap.get(rightChildIndex)) > 0) {
                    break;
                }

                childrenComparisonResult = heap.get(leftChildIndex).compareTo(heap.get(rightChildIndex));
            } else { // no right child
                if (element.compareTo(heap.get(leftChildIndex)) > 0) {
                    break;
                }

                childrenComparisonResult = 1;
            }

            // goal is to swap with the bigger child to preserve data structure idea
            if (childrenComparisonResult > 0) { // if left child is bigger
                swapValues(elementIndex, leftChildIndex);
                elementIndex = leftChildIndex;
            } else { // if right child is bigger
                swapValues(elementIndex, rightChildIndex);
                elementIndex = rightChildIndex;
            }

            leftChildIndex = getLeftChildIndex(elementIndex);
            rightChildIndex = getRightChildIndex(elementIndex);
        }
    }

    private void heapifyUp(T element, int elementIndex) {
        int parentIndex = getParentIndex(elementIndex);

        while (element.compareTo(heap.get(parentIndex)) > 0 && elementIndex >= 0) {
            swapValues(elementIndex, parentIndex);
            elementIndex = parentIndex;
            parentIndex = getParentIndex(elementIndex);
        }
    }

    private void swapValues(int index1, int index2) {
        T temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / 2;
    }

    private int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private int getRightChildIndex(int parentIndex) {
        return getLeftChildIndex(parentIndex) + 1;
    }

    private void checkIfHeapIsEmpty() {
        if (heap.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_HEAP_EXCEPTION);
        }
    }

    private boolean isInBound(int index) {
        return index >= 0 && index < heap.size();
    }
}
