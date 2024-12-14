package ru.nsu.shabalina;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomHashMap<K, V> implements Iterable<KeyValuePair<K, V>> {
    private static final double LOAD_FACTOR = 0.75;
    private KeyValuePair<K, V>[] buckets;
    private int elementCount = 0;
    private int bucketCount = 16;
    private int modificationCount = 0;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        buckets = (KeyValuePair<K, V>[]) new KeyValuePair[bucketCount];
    }

    private int computeBucketIndex(K key) {
        return Math.abs(key.hashCode()) % bucketCount;
    }

    public void insert(K key, V val) {
        if (elementCount >= bucketCount * LOAD_FACTOR) {
            expand();
        }

        int index = computeBucketIndex(key);

        if (buckets[index] == null) {
            buckets[index] = new KeyValuePair<>(key, val);
            elementCount++;
            modificationCount++;
            return;
        }

        if (buckets[index].getKey().equals(key)) {
            buckets[index].setValue(val);
            modificationCount++;
            return;
        }

        // Handle collision with linear probing
        int originalIndex = index;
        index = (index + 1) % bucketCount;

        while (index != originalIndex) {
            if (buckets[index] == null) {
                buckets[index] = new KeyValuePair<>(key, val);
                elementCount++;
                modificationCount++;
                return;
            }

            if (buckets[index].getKey().equals(key)) {
                buckets[index].setValue(val);
                modificationCount++;
                return;
            }

            index = (index + 1) % bucketCount;
        }
    }

    public void remove(K key) {
        int index = computeBucketIndex(key);
        int originalIndex = index;

        do {
            if (buckets[index] != null && buckets[index].getKey().equals(key)) {
                buckets[index] = null;
                elementCount--;
                modificationCount++;
                rehashAfterRemoval(index);
                return;
            }
            index = (index + 1) % bucketCount;
        } while (index != originalIndex);
    }

    private void rehashAfterRemoval(int removedIndex) {
        int current = (removedIndex + 1) % bucketCount;
        while (buckets[current] != null) {
            KeyValuePair<K, V> pair = buckets[current];
            int properIndex = computeBucketIndex(pair.getKey());

            if (properIndex != current) {
                buckets[current] = null;
                insert(pair.getKey(), pair.getValue());
                elementCount--; // insert() increments count, so we decrement here
            }

            current = (current + 1) % bucketCount;
        }
    }

    public V fetch(K key) {
        int index = computeBucketIndex(key);
        int originalIndex = index;

        do {
            if (buckets[index] != null && buckets[index].getKey().equals(key)) {
                return buckets[index].getValue();
            }
            index = (index + 1) % bucketCount;
        } while (index != originalIndex);

        return null;
    }

    public boolean hasKey(K key) {
        return fetch(key) != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CustomHashMap<?, ?> other = (CustomHashMap<?, ?>) obj;
        if (elementCount != other.elementCount) return false;

        // Проверяем все элементы текущей таблицы
        for (KeyValuePair<K, V> pair : buckets) {
            if (pair != null) {
                boolean found = false;
                // Проверяем наличие такого же элемента в другой таблице
                for (KeyValuePair<?, ?> otherPair : other.buckets) {
                    if (otherPair != null &&
                            pair.getKey().equals(otherPair.getKey()) &&
                            pair.getValue().equals(otherPair.getValue())) {
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (KeyValuePair<K, V> pair : buckets) {
            if (pair != null) {
                result = 31 * result + pair.getKey().hashCode();
                result = 31 * result + (pair.getValue() == null ? 0 : pair.getValue().hashCode());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (KeyValuePair<K, V> pair : buckets) {
            if (pair != null) {
                sb.append("Key: ").append(pair.getKey())
                        .append(", Value: ").append(pair.getValue())
                        .append("\n");
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        bucketCount *= 2;
        KeyValuePair<K, V>[] oldBuckets = buckets;
        buckets = (KeyValuePair<K, V>[]) new KeyValuePair[bucketCount];
        elementCount = 0;

        for (KeyValuePair<K, V> pair : oldBuckets) {
            if (pair != null) {
                insert(pair.getKey(), pair.getValue());
            }
        }
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator() {
        return new MapIterator();
    }

    private class MapIterator implements Iterator<KeyValuePair<K, V>> {
        private int currentIndex = 0;
        private int expectedModCount = modificationCount;
        private int returnedCount = 0;

        @Override
        public boolean hasNext() {
            return returnedCount < elementCount;
        }

        @Override
        public KeyValuePair<K, V> next() {
            if (expectedModCount != modificationCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            while (currentIndex < bucketCount && buckets[currentIndex] == null) {
                currentIndex++;
            }

            if (currentIndex >= bucketCount) {
                throw new NoSuchElementException();
            }

            returnedCount++;
            return buckets[currentIndex++];
        }
    }
}


