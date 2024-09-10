package main.java.ru.nsu.shabalina;

public class Heapsort {
    /**
     * Sort given int array and returns it.
     * @param data - unsorted array
     * @return sorted array
     */
    public static int[] heapsort(int[] data) {
        if (data == null){
            return null;
        }
        if (data.length == 0 || data.length == 1) {
            return data;
        }
        for (int i = data.length/ 2 - 1; i >= 0; i--) {
            heapify(data, i, data.length);
        }

        int cnt = 1;
        for (int i = data.length - 1; i >= 0; i--) {
            int swapPos = data.length - cnt;
            swap(data, 0, swapPos);
            heapify(data, 0, i);
            cnt++;
        }
        return data;
    }

    private static void heapify(int[] data, int root, int len) {
        int indexOfBiggest = root;
        int leftChild = root * 2 + 1;
        int rightChild = root * 2 + 2;
        if (leftChild < len && data[leftChild] > data[indexOfBiggest]) {
            indexOfBiggest = leftChild;
        }
        if (rightChild < len && data[rightChild] > data[indexOfBiggest]) {
            indexOfBiggest = rightChild;
        }
        if (indexOfBiggest != root) {
            swap(data, indexOfBiggest, root);
            heapify(data, indexOfBiggest, len);
        }
    }

    private static void swap(int[] data, int ind1, int ind2) {
        int temp = data[ind1];
        data[ind1] = data[ind2];
        data[ind2] = temp;
    }
}
