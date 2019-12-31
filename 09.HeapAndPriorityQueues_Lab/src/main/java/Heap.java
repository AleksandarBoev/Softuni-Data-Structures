public class Heap {

    public static <E extends Comparable<E>> void sort(E[] array) {
        heapifyArray(array);

        for (int i = array.length - 1; i > 0; i--) {
            swapValues(array, 0, i);
//            heapifyDown(array, );
        }
    }

    private static <E extends Comparable<E>> void heapifyArray(E[] array) {
        for (int i = array.length / 2; i <= 0; i--) {
            heapifyDown(array, i);
        }
    }

    private static <E extends Comparable<E>> void heapifyDown(E[] array, int elementIndex) {
        if (array.length == 0) {
            return;
        }

        E element = array[0];
        int leftChildIndex = getLeftChildIndex(elementIndex);
        int rightChildIndex = getRightChildIndex(elementIndex);

        while (true) {
            if (!isInBound(leftChildIndex, array)) { //no left child --> no right child either
                break;
            }

            int childrenComparisonResult;

            if (isInBound(rightChildIndex, array)) { // if has right child
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

    private static boolean isInBound(int index, Object[] array) {
        return index >= 0 && index < array.length;
    }
}
