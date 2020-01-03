public class Heap {
    public static <E extends Comparable<E>> void sort(E[] array) { //sorting in ascending (small --> big)
        heapifyArray(array);
        sortArray(array);
    }

    private static <E extends Comparable<E>> void heapifyArray(E[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            heapifyDown(array, i, array.length);
        }
    }

    /**
     *
     * The sorting works like this:
     * <ul>
     * <li>
     *     Swap first with last element. Now the last element in the provided array is the biggest.
     *     But the heap has lost its properties (first element is not biggest).
     * </li>
     * <li>
     *     Heapify down is applied on the new first element to return max heap properties, but it is
     *     heapified down to a smaller array length so that the recently moved element (former biggest) is not touched.
     *     The first iteration of heapify down swaps the element with the bigger child, which now is the biggest element from index 0
     *     to (index - 1) of recently swapped element. Next iterations place the element on its correct position in a max heap.
     * </li>
     * <li>
     *     Repeat
     * </li>
     * </ul>
     */
    private static <E extends Comparable<E>> void sortArray(E[] array) {
        int counter = 0;

        for (int i = array.length - 1; i > 0; i--) {
            swapValues(array, 0, i);
            heapifyDown(array, 0, i);
        }
    }

    private static <E extends Comparable<E>> void heapifyDown(E[] array, int elementIndex, int arrayLength) {
        if (array.length == 0) {
            return;
        }

        E element = array[elementIndex];
        int leftChildIndex = getLeftChildIndex(elementIndex);
        int rightChildIndex = getRightChildIndex(elementIndex);

        while (true) {
            if (!isInBound(leftChildIndex, arrayLength)) { //no left child --> no right child either
                break;
            }

            int childrenComparisonResult;

            if (isInBound(rightChildIndex, arrayLength)) { // if has right child (for sure has left child also)
                // if bigger than both children, then no need to move it further down
                if (element.compareTo(array[leftChildIndex]) > 0 && element.compareTo(array[rightChildIndex]) > 0) {
                    break;
                }

                childrenComparisonResult = array[leftChildIndex].compareTo(array[rightChildIndex]);
            } else { // no right child
                if (element.compareTo(array[leftChildIndex]) > 0) {
                    break;
                }

                childrenComparisonResult = 1;
            }

            // goal is to swap with the bigger child to preserve data structure idea
            if (childrenComparisonResult > 0) { // if left child is bigger
                swapValues(array, elementIndex, leftChildIndex);
                elementIndex = leftChildIndex;
            } else { // if right child is bigger
                swapValues(array, elementIndex, rightChildIndex);
                elementIndex = rightChildIndex;
            }

            leftChildIndex = getLeftChildIndex(elementIndex);
            rightChildIndex = getRightChildIndex(elementIndex);
        }
    }

    private static void swapValues(Object[] array, int index1, int index2) {
        Object temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static int getLeftChildIndex(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    private static int getRightChildIndex(int parentIndex) {
        return getLeftChildIndex(parentIndex) + 1;
    }

    private static boolean isInBound(int index, int maxIndex) {
        return index >= 0 && index < maxIndex;
    }
}
