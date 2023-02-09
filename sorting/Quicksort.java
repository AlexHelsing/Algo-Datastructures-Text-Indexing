package sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Quicksort<E> extends SortingAlgorithm<E, Comparator<? super E>> {
    QuicksortPivotSelector selector;
    InsertionSort<E> insertionSort;

    public Quicksort(Comparator<? super E> comparator, QuicksortPivotSelector selector) {
        super(comparator);
        this.selector = selector;
        insertionSort = new InsertionSort<>(comparator);
    }

    // Notes for the below methods:
    // * Use the class attribute `comparator` to perform comparisons.
    // * You can use `swap(list, i, j)` to swap indices i and j.
    // * If the list range is given by `from` and `to`, the last element is `list.get(to - 1)`.

    // Sort the given range of the list in-place.
    public void sort(List<E> list, int from, int to) {
        // Base case
        int size = to - from;
        if (size == 0)
            return;

        int pivotIndex = partition(list, from, to);
        sort(list, from, pivotIndex);
        sort(list, pivotIndex + 1, to);
    }

    // Partition the given range of a list.
    // Return the final position of the pivot.
    private int partition(List<E> list, int from, int to) {
        // The index of the element that should be used as the pivot.
        int pivotIndex = selector.pivotIndex(list, from, to, comparator);
        E pivot = list.get(pivotIndex);

        // Hint: You can use Util.swap(list, i, j) to swap indices i and j.
        swap(list, pivotIndex, to - 1);

        int i = from;
        int j = to - 2;
        while (i <= j) {
            while (comparator.compare(list.get(i), pivot) < 0) {
                i++;
            }
            while (comparator.compare(pivot, list.get(j)) < 0) {
                j--;
            }
            if (i <= j) {
                swap(list, i, j);
                i++;
                j--;
            }
        }

        // Swap pivot to its final position
        swap(list, i, to - 1);

        return i;
    }

    // Run your own tests here!
    public static void main(String[] args) {
        Quicksort<String> algorithm = new Quicksort<>(Comparator.naturalOrder(), QuicksortPivotSelector.FIRST);

        List<String> list = Arrays.asList("turtle", "cat", "zebra", "swan", "dog");
        algorithm.sort(list);
        System.out.println(list);

        list = Arrays.asList("turtle", "cat", "zebra", "swan", "dog", "horse", "fly", "elephant", "duck", "monkey");
        algorithm.sort(list);
        System.out.println(list);
    }
}