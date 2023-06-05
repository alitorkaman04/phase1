import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
    private static int V;
    private static int[][] graph;

    public Graph(int V)
    {
        this.V = V;
        graph = new int[V][V];
    }

    public static void addEdge(int source, int destination, int weight) {
        graph[source][destination] = weight;
        graph[destination][source] = weight; //دو طرفه بودن گراف
    }

    public static int getEdgeWeight (int source, int destination) {
        return graph[source][destination] ;
    }

    public static List<Integer> aStar(int source, int destination) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] distance = new int[V];
        int[] parent = new int[V];
        boolean[] visited = new boolean[V];
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        distance[source] = 0;
        pq.offer(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentNode = current.id;

            if (currentNode == destination) {
                break;
            }

            if (visited[currentNode]) {
                continue;
            }

            visited[currentNode] = true;

            for (int neighbor = 0; neighbor < V; neighbor++) {
                if (graph[currentNode][neighbor] != 0) {
                    int newCost = distance[currentNode] + graph[currentNode][neighbor];
                    if (newCost < distance[neighbor]) {
                        distance[neighbor] = newCost;
                        parent[neighbor] = currentNode ;
                        int priority = newCost;
                        pq.offer(new Node(neighbor, priority));
                    }
                }
            }
        }

        return buildPath(parent, destination);
    }

    private static List<Integer> buildPath(int[] parent, int destination) {
        List<Integer> path = new ArrayList<>();
        int current = destination;
        while (current != -1) {
            path.add(0, current);
            current = parent[current];
        }
        return path;
    }

    static class Node implements Comparable<Node> {
        int id;
        int priority;

        public Node(int id, int priority) {
            this.id = id;
            this.priority = priority;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(priority, other.priority);
        }
    }


}
//
//public class Main {
//    public static void main(String[] args) throws FileNotFoundException {
//
//        String filePath = "graph.txt";
//
//        File file = new File(filePath);
//        Scanner raeder = new Scanner(file);
//        String[] firstline = raeder.nextLine().split(" ");
//        int node = Integer.parseInt(firstline[0]);
//        Graph graph = new Graph(node);
//        int mline = Integer.parseInt(firstline[1]);
//        for (int i = 0; i < mline; i++) {
//            String[] lines = raeder.nextLine().split(" ");
//            int source = Integer.parseInt(lines[0]);
//            int dest = Integer.parseInt(lines[1]);
//            int weight = Integer.parseInt(lines[2]);
//            graph.addEdge(source - 1, dest - 1, weight);
//        }
//
//
//        // run
//        int source = 3;
//        int destination = 100;
//
//        List<Integer> shortestPath = graph.aStar(source - 1, destination - 1);
//
//        int totalCost = 0 ;
//        for (int i = 0; i < shortestPath.size() - 1; i++) {
//            int a = shortestPath.get(i);
//            int b = shortestPath.get(i + 1);
//            totalCost += graph.getEdgeWeight(a,b);
//        }
//
//        System.out.println("Total cost: " + totalCost);
//
//
//
//        if (shortestPath.isEmpty()) {
//            System.out.println("No path found from source to destination.");
//        } else {
//            System.out.println("Shortest path from " + source + " to " + destination + ": " + shortestPath);
//        }
//
//    }
//}
