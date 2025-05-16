package ru.nsu.shabalina;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, choose the mode of graph presentation:" +
                "\n" + "1 for Adjacency Matrix" +
                "\n" + "2 for Incidence Matrix" +
                "\n" + "3 for Adjacency List" +
                "\n");

        int mode = scanner.nextInt();
        System.out.println("Enter the number of vertices and edges, respectively: ");
        int countOfVertex = scanner.nextInt();
        int countOfEdges = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки

        System.out.print("Please, choose the input mode:" +
                "\n" + "1 for keyboard input" +
                "\n" + "2 for file input" +
                "\n");
        int inputMode = scanner.nextInt();
        scanner.nextLine(); // Считываем оставшийся символ новой строки

        Graph graph = null;

        switch (mode) {
            case 1:
                System.out.println("Adjacency Matrix mode selected");
                graph = new AdjacencyMatrix(countOfVertex, countOfEdges);
                break;
            case 2:
                System.out.println("Incidence Matrix mode selected");
                graph = new IncidenceMatrix(countOfVertex, countOfEdges);
                break;
            case 3:
                System.out.println("Adjacency List mode selected");
                graph = new AdjacencyList(countOfVertex);
                break;
            default:
                System.out.println("Invalid mode selected");
                scanner.close();
                return;
        }

        if (inputMode == 2) {
            System.out.println("Enter the file path: ");
            String filePath = scanner.nextLine();
            graph.readFromFile(filePath);
        } else {
            System.out.println("Enter the edges (fromVertex toVertex): ");
            for (int i = 0; i < countOfEdges; i++) {
                String input = scanner.nextLine();
                String[] numbers = input.split(" ");
                int fromVertex = Integer.parseInt(numbers[0]);
                int toVertex = Integer.parseInt(numbers[1]);
                graph.addEdge(fromVertex, toVertex);
            }
        }

        System.out.println("Neighbors of vertex 1: " + graph.getNeighbors(1));
        System.out.println("Cycle exists: " + graph.cycleExist());
        System.out.println("Topological sort: " + graph.topologicalSort());

        scanner.close();
    }
}

