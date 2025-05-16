package ru.nsu.shabalina;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

public class AdjacencyList implements Graph {
    private int countOfVertex;
    private int countOfEdges;
    private Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyList(int countOfVertex) {
        this.countOfVertex = countOfVertex;
        this.countOfEdges = 0;
        this.adjacencyList = new HashMap<>();
        for (int i = 1; i <= countOfVertex; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    @Override
    public void addVertex() {
        countOfVertex++;
        adjacencyList.put(countOfVertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < 1 || vertex > countOfVertex) {
            throw new IllegalArgumentException("Vertex out of bounds");
        }

        adjacencyList.remove(vertex);

        for (List<Integer> edges : adjacencyList.values()) {
            edges.remove(Integer.valueOf(vertex));
        }

        Map<Integer, List<Integer>> newAdjacencyList = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            int newKey = entry.getKey() > vertex ? entry.getKey() - 1 : entry.getKey();
            List<Integer> newEdges = new ArrayList<>();
            for (int neighbor : entry.getValue()) {
                newEdges.add(neighbor > vertex ? neighbor - 1 : neighbor);
            }
            newAdjacencyList.put(newKey, newEdges);
        }
        adjacencyList = newAdjacencyList;

        countOfVertex--;
    }


    @Override
    public void addEdge(int fromVertex, int toVertex) {
        if (fromVertex < 1 || fromVertex > countOfVertex || toVertex < 1 || toVertex > countOfVertex) {
            throw new IllegalArgumentException("Vertex out of bounds");
        }
        adjacencyList.get(fromVertex).add(toVertex);
        countOfEdges++;
    }

    @Override
    public void removeEdge(int fromVertex, int toVertex) {
        if (fromVertex < 1 || fromVertex > countOfVertex || toVertex < 1 || toVertex > countOfVertex) {
            throw new IllegalArgumentException("Vertex out of bounds");
        }
        List<Integer> edges = adjacencyList.get(fromVertex);
        if (edges.remove(Integer.valueOf(toVertex))) {
            countOfEdges--;
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        if (vertex < 1 || vertex > countOfVertex) {
            throw new IllegalArgumentException("Vertex out of bounds");
        }
        List<Integer> neighbors = new ArrayList<>(adjacencyList.get(vertex));
        for (int i = 1; i <= countOfVertex; i++) {
            if (i != vertex && adjacencyList.get(i).contains(vertex)) {
                neighbors.add(i);
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
        boolean[] visited = new boolean[countOfVertex + 1];
        boolean[] recStack = new boolean[countOfVertex + 1];

        for (int i = 1; i <= countOfVertex; i++) {
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

        for (int neighbor : adjacencyList.get(v)) {
            if (dfs(neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack[v] = false;
        return false;
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sortedGraph = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] inDegree = new int[countOfVertex + 1];

        if (cycleExist()) {
            System.out.println("Topological sort is not possible because the graph has a cycle");
            return sortedGraph;
        }

        for (int i = 1; i <= countOfVertex; i++) {
            for (int neighbor : adjacencyList.get(i)) {
                inDegree[neighbor]++;
            }
        }

        for (int i = 1; i <= countOfVertex; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            sortedGraph.add(u);

            for (int neighbor : adjacencyList.get(u)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedGraph;
    }
}

