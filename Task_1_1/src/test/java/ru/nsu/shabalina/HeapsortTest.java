package test.java.ru.nsu.shabalina;

import main.java.ru.nsu.shabalina.Heapsort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HeapsortTest {
    @Test
    public void test1(){
        int[] sut = {1, 3, 2, 2};
        int[] sortedSut = {1, 2, 2, 3};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void test2(){
        int[] sut = {1, 1, 1, 1, 1};
        int[] sortedSut = {1, 1, 1, 1, 1};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void test3(){
        int[] sut = {-2};
        int[] sortedSut = {-2};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void test4(){
        int[] sut = {};
        int[] sortedSut = {};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void test5(){
        int[] sut = null;
        int[] sortedSut = null;
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void test6(){
        int[] sut = {-3, 5, 0, 19, -9, 1};
        int[] sortedSut = {-9, -3, 0, 1, 5, 19};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }

    @Test
    public void test7(){
        int[] sut = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] sortedSut = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Assertions.assertArrayEquals(sortedSut, Heapsort.heapsort(sut));
    }
}