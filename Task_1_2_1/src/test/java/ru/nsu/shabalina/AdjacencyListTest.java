package ru.nsu.shabalina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {

    private AdjacencyList graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyList(4);
    }

    @Test
    void testAddVertex() {
        graph.addVertex();
        assertEquals(0, graph.getNeighbors(5).size());
    }

    @Test
    void testRemoveVertex() {
        graph.addEdge(1, 2);
        graph.removeVertex(1);
        assertFalse(graph.getNeighbors(2).contains(1));
    }

    @Test
    void testAddEdge() {
        graph.addEdge(1, 2);
        assertTrue(graph.getNeighbors(1).contains(2));
        assertTrue(graph.getNeighbors(2).contains(1));
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);
        assertFalse(graph.getNeighbors(1).contains(2));
        assertFalse(graph.getNeighbors(2).contains(1));
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        List<Integer> neighbors = graph.getNeighbors(1);
        assertTrue(neighbors.contains(2));
        assertTrue(neighbors.contains(3));
    }

    @Test
    void testCycleExist() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        assertTrue(graph.cycleExist());
    }

    @Test
    void testNoCycleExist() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        assertFalse(graph.cycleExist());
    }

    @Test
    void testTopologicalSort() {
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        List<Integer> sortedGraph = graph.topologicalSort();
        assertEquals(List.of(1, 2, 3, 4), sortedGraph);
    }

}

