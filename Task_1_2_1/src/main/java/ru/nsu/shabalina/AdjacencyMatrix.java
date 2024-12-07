package ru.nsu.shabalina;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyMatrix implements Graph {
    private int size;
    private int[][] adjacencyMatrix;

    public AdjacencyMatrix(int countOfVertex, int countOfEdges) {
        this.size = countOfVertex;
        this.adjacencyMatrix = new int[countOfVertex][countOfVertex];
    }

    @Override
    public void addVertex() {
        int newSize = size + 1;
        int[][] newMatrix = new int[newSize][newSize];

        for (int i = 0; i < size; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, size);
        }

        adjacencyMatrix = newMatrix;
        size = newSize;
    }


    @Override
    public void removeVertex(int vertex) {
        for (int i = 0; i < size; i++) {
            adjacencyMatrix[i][vertex - 1] = 0;
            adjacencyMatrix[vertex - 1][i] = 0;
        }
    }

    @Override
    public void addEdge(int fromVertex, int toVertex) {
        adjacencyMatrix[fromVertex - 1][toVertex - 1] = 1;
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        adjacencyMatrix[fromVertex - 1][toVertex - 1] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (adjacencyMatrix[vertex - 1][i] == 1 || adjacencyMatrix[i][vertex - 1] == 1) {
                neighbors.add(i + 1);
            }
        }
        return neighbors;
    }


    @Override
    public void readFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                int fromVertex = Integer.parseInt(numbers[0]);
                int toVertex = Integer.parseInt(numbers[1]);
                addEdge(fromVertex, toVertex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cycleExist() {
        boolean[] visited = new boolean[size];
        boolean[] recStack = new boolean[size];

        for (int i = 0; i < size; i++) {
            if (dfs(i, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfs(int v, boolean[] visited, boolean[] recStack) {
        if (recStack[v]) {
            return true;
        }

        if (visited[v]) {
            return false;
        }

        visited[v] = true;
        recStack[v] = true;

        for (int i = 0; i < size; i++) {
            if (adjacencyMatrix[v][i] == 1) {
                if (dfs(i, visited, recStack)) {
                    return true;
                }
            }
        }

        recStack[v] = false;
        return false;
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sortedGraph = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] inDegree = new int[size];

        if (cycleExist()) {
            System.out.println("Topological sort is not possible because the graph has a cycle");
            return sortedGraph;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix[j][i] == 1) {
                    inDegree[i]++;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            sortedGraph.add(u + 1);

            for (int v = 0; v < size; v++) {
                if (adjacencyMatrix[u][v] == 1) {
                    inDegree[v]--;
                    if (inDegree[v] == 0) {
                        queue.add(v);
                    }
                }
            }
        }

        return sortedGraph;
    }
}

