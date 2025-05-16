package ru.nsu.shabalina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomHashMapTest {
    private CustomHashMap<String, Integer> map;

    @BeforeEach
    public void init() {
        map = new CustomHashMap<>();
    }

    @Test
    public void testInsertAndFetch() {
        map.insert("apple", 5);
        map.insert("banana", 7);
        assertEquals(5, map.fetch("apple"));
        assertEquals(7, map.fetch("banana"));
    }

    @Test
    public void testUpdateValue() {
        map.insert("apple", 5);
        map.insert("apple", 8);
        assertEquals(8, map.fetch("apple"));
    }

    @Test
    public void testRemoveElement() {
        map.insert("apple", 5);
        map.remove("apple");
        assertNull(map.fetch("apple"));
    }

    @Test
    public void testKeyExistence() {
        map.insert("apple", 5);
        assertTrue(map.hasKey("apple"));
        assertFalse(map.hasKey("banana"));
    }

    @Test
    public void testMapEquality() {
        CustomHashMap<String, Integer> map1 = new CustomHashMap<>();
        map1.insert("a", 1);

        CustomHashMap<String, Integer> map2 = new CustomHashMap<>();
        map2.insert("a", 1);

        assertEquals(map1, map2);
    }

    @Test
    public void testMapInequality() {
        CustomHashMap<String, Integer> map1 = new CustomHashMap<>();
        map1.insert("a", 1);

        CustomHashMap<String, Integer> map2 = new CustomHashMap<>();
        map2.insert("a", 2);

        assertNotEquals(map1, map2);
    }

    @Test
    public void testStringRepresentation() {
        map.insert("a", 1);
        map.insert("b", 2);
        String result = map.toString();
        assertTrue(result.contains("Key: a, Value: 1") && result.contains("Key: b, Value: 2"));
    }

    @Test
    public void testLargeMap() {
        for (int i = 0; i < 1000; i++) {
            map.insert("key" + i, i);
        }

        for (int i = 0; i < 1000; i++) {
            assertEquals(i, map.fetch("key" + i));
        }
    }

    @Test
    public void testRemoveNonexistentKey() {
        map.remove("nonexistent");
        assertNull(map.fetch("nonexistent"));
    }

    @Test
    public void testIteratorConcurrentModification() {
        map.insert("a", 1);
        Iterator<KeyValuePair<String, Integer>> it = map.iterator();
        map.insert("b", 2);
        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    public void testEmptyIterator() {
        Iterator<KeyValuePair<String, Integer>> it = map.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    public void testMapExpansion() {
        for (int i = 0; i < 20; i++) {
            map.insert("key" + i, i);
        }
        assertTrue(map.toString().contains("Key: key0, Value: 0"));
        assertTrue(map.toString().contains("Key: key19, Value: 19"));
    }

    @Test
    public void testCollisionHandling() {
        // Force collision by using keys with same hash
        map.insert("Aa", 1);
        map.insert("BB", 2);
        assertEquals(1, map.fetch("Aa"));
        assertEquals(2, map.fetch("BB"));
    }
}
