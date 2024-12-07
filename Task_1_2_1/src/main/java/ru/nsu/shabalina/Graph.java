package ru.nsu.shabalina;

import java.util.List;

public interface Graph {
    void addVertex();

    void removeVertex(int vertex);

    void addEdge(int fromVertex, int toVertex);

    void removeEdge(int fromVertex, int toVertex);

    List<Integer> getNeighbors(int vertex);

    void readFromFile(String filePath);

    boolean cycleExist();

    List<Integer> topologicalSort();
}

