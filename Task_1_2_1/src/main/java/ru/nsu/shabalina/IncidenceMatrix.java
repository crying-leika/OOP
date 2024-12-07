package ru.nsu.shabalina;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IncidenceMatrix implements Graph {
    private int[][] incidenceMatrix;
    private int countOfVertex;
    private int countOfEdges;

    public IncidenceMatrix(int vertices, int edges) {
        this.countOfVertex = vertices;
        this.countOfEdges = edges;
        this.incidenceMatrix = new int[vertices][edges];
    }

    @Override
    public void addVertex() {
        int newSize = countOfVertex + 1;
        int[][] newMatrix = new int[newSize][countOfEdges];

        for (int i = 0; i < countOfVertex; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, countOfEdges);
        }

        incidenceMatrix = newMatrix;
        countOfVertex = newSize;
    }


    @Override
    public void removeVertex(int vertex) {
        if (vertex >= countOfVertex) {
            return;
        }

        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix[vertex][i] != 0) {
                for (int j = 0; j < countOfVertex; j++) {
                    incidenceMatrix[j][i] = 0;
                }
            }
        }

        int[][] newMatrix = new int[countOfVertex - 1][countOfEdges];
        for (int i = 0, newRow = 0; i < countOfVertex; i++) {
            if (i != vertex) {
                newMatrix[newRow++] = incidenceMatrix[i];
            }
        }
        incidenceMatrix = newMatrix;
        countOfVertex--;
    }

    @Override
    public void addEdge(int vertex1, int vertex2) {
        if (countOfEdges == 0) {
            return;
        }

        int edge = 0;
        for (; edge < countOfEdges; edge++) {
            if (incidenceMatrix[vertex1 - 1][edge] == 0 && incidenceMatrix[vertex2 - 1][edge] == 0) {
                incidenceMatrix[vertex1 - 1][edge] = 1;
                if (vertex1 == vertex2) {
                    incidenceMatrix[vertex2 - 1][edge] = 1;
                } else {
                    incidenceMatrix[vertex2 - 1][edge] = -1;
                }
                break;
            }
        }
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) {
        for (int i = 0; i < countOfEdges; i++) {
            if ((incidenceMatrix[vertex1 - 1][i] == 1 && incidenceMatrix[vertex2 - 1][i] == -1)
                    || (incidenceMatrix[vertex1 - 1][i] == -1 && incidenceMatrix[vertex2 - 1][i] == 1)) {
                incidenceMatrix[vertex1 - 1][i] = 0;
                incidenceMatrix[vertex2 - 1][i] = 0;
                break;
            }
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        Set<Integer> neighborsSet = new HashSet<>();
        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix[vertex - 1][i] != 0) {
                for (int j = 0; j < countOfVertex; j++) {
                    if (j != vertex - 1 && incidenceMatrix[j][i] != 0 &&
                            incidenceMatrix[j][i] != incidenceMatrix[vertex - 1][i]) {
                        neighborsSet.add(j + 1);
                    }
                }
            }
        }
        return new ArrayList<>(neighborsSet);
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] vertices = line.split(" ");
                int vertex1 = Integer.parseInt(vertices[0]);
                int vertex2 = Integer.parseInt(vertices[1]);
                addEdge(vertex1, vertex2);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }

    @Override
    public boolean cycleExist() {
        List<Integer> topologicalOrder = topologicalSort();
        return topologicalOrder.size() != countOfVertex;
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[countOfVertex];

        for (int i = 0; i < countOfVertex; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, result);
            }
        }

        if (!result.isEmpty()) {
            int firstElement = result.remove(0);
            result.add(firstElement);
        }

        return result;
    }

    private void topologicalSortUtil(int vertex, boolean[] visited, List<Integer> result) {
        visited[vertex] = true;

        for (int i = 0; i < countOfEdges; i++) {
            if (incidenceMatrix[vertex][i] == 1) {
                for (int j = 0; j < countOfVertex; j++) {
                    if (incidenceMatrix[j][i] == -1 && !visited[j]) {
                        topologicalSortUtil(j, visited, result);
                    }
                }
            }
        }

        result.add(0, vertex + 1);
    }
}

