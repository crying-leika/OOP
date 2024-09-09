package test.java.ru.nsu.shabalina;

import main.java.ru.nsu.shabalina.Heapsort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeapsortTest {
    @Test
    public void testRandom(){
        int[] sut = {1, 3, 2, 2};
        int[] sortedSut = {1, 2, 2, 3};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void testAreEqual(){
        int[] sut = {1, 1, 1, 1, 1};
        int[] sortedSut = {1, 1, 1, 1, 1};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void testIsOne(){
        int[] sut = {-2};
        int[] sortedSut = {-2};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void testIsEmpty(){
        int[] sut = {};
        int[] sortedSut = {};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void testIsNull(){
        int[] sut = null;
        int[] sortedSut = null;
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void testRandomWithNegatives(){
        int[] sut = {-3, 5, 0, 19, -9, 1};
        int[] sortedSut = {-9, -3, 0, 1, 5, 19};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void testIsSorted(){
        int[] sut = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] sortedSut = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }
}