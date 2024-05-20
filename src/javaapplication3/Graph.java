package javaapplication3;

import java.util.*;

class Graph {
    private final Map<Integer, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(int from, int to, int weight) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.get(from).add(new Edge(to, weight));
    }

    public List<Edge> getEdges(int node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    public Map<Integer, List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public int[][] getAdjacencyMatrix() {
        int maxNode = adjacencyList.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
        int[][] matrix = new int[maxNode][maxNode];

        for (int from : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(from)) {
                matrix[from - 1][edge.to - 1] = edge.weight;
            }
        }

        return matrix;
    }

    public String CaminoCorto(int start, int end) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        
        for (int node : adjacencyList.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int currentNode = current.to;
            int currentDist = current.weight;

            if (currentNode == end) {
                break;
            }

            for (Edge edge : getEdges(currentNode)) {
                int newDist = currentDist + edge.weight;
                if (newDist < distances.get(edge.to)) {
                    distances.put(edge.to, newDist);
                    previous.put(edge.to, currentNode);
                    pq.add(new Edge(edge.to, newDist));
                }
            }
        }

        LinkedList<Integer> path = new LinkedList<>();
        for (Integer at = end; at != null; at = previous.get(at)) {
            path.addFirst(at);
        }

        StringBuilder sb = new StringBuilder();
        int totalWeight = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            int weight = getEdges(from).stream().filter(e -> e.to == to).findFirst().get().weight;
            totalWeight += weight;
            sb.append("Salto del nodo ").append(from).append(" al nodo ").append(to).append(" peso ").append(weight).append(" total: ").append(totalWeight).append("\n");
        }
        return sb.toString();
    }

    public String CaminoLargo(int start, int end) {
        Set<Integer> visited = new HashSet<>();
        List<Integer> path = new ArrayList<>();
        List<Integer> longestPath = new ArrayList<>();
        int[] longestWeight = {0};
        findLongestPath(start, end, visited, path, longestPath, 0, longestWeight);

        StringBuilder sb = new StringBuilder();
        int totalWeight = 0;
        for (int i = 0; i < longestPath.size() - 1; i++) {
            int from = longestPath.get(i);
            int to = longestPath.get(i + 1);
            int weight = getEdges(from).stream().filter(e -> e.to == to).findFirst().get().weight;
            totalWeight += weight;
            sb.append("Salto del nodo ").append(from).append(" al nodo ").append(to).append(" peso ").append(weight).append(" total: ").append(totalWeight).append("\n");
        }
        return sb.toString();
    }

    private void findLongestPath(int current, int end, Set<Integer> visited, List<Integer> path, List<Integer> longestPath, int currentWeight, int[] longestWeight) {
        visited.add(current);
        path.add(current);

        if (current == end) {
            if (currentWeight > longestWeight[0]) {
                longestWeight[0] = currentWeight;
                longestPath.clear();
                longestPath.addAll(new ArrayList<>(path));
            }
        } else {
            for (Edge edge : getEdges(current)) {
                if (!visited.contains(edge.to)) {
                    findLongestPath(edge.to, end, visited, path, longestPath, currentWeight + edge.weight, longestWeight);
                }
            }
        }

        path.remove(path.size() - 1);
        visited.remove(current);
    }

    // Recorrido por Anchura (Breadth-First Search)
    public List<Integer> recorridoAnchura(int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (Edge edge : getEdges(node)) {
                if (!visited.contains(edge.to)) {
                    visited.add(edge.to);
                    queue.add(edge.to);
                }
            }
        }

        return result;
    }

    // Recorrido por Profundidad (Depth-First Search)
    public List<Integer> recorridoProfundidad(int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();

            if (!visited.contains(node)) {
                visited.add(node);
                result.add(node);

                for (Edge edge : getEdges(node)) {
                    if (!visited.contains(edge.to)) {
                        stack.push(edge.to);
                    }
                }
            }
        }

        return result;
    }
}
