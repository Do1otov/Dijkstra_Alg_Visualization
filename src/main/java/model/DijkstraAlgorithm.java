package model;

import java.util.*;

public class DijkstraAlgorithm {
    private final DirectedGraph graph;
    private final Map<Vertex, Integer> distances;
    private final Map<Vertex, Vertex> previousVertices;
    private final Set<Vertex> visited;
    private final PriorityQueue<Vertex> queue;
    private final List<String> steps;

    public DijkstraAlgorithm(DirectedGraph graph) {
        this.graph = graph;
        this.distances = new HashMap<>();
        this.previousVertices = new HashMap<>();
        this.visited = new HashSet<>();
        this.steps = new ArrayList<>();
        this.queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
    }

    public Integer getDistanceTo(Vertex end) {
        return distances.get(end);
    }

    public List<String> getPathTo(Vertex end) {
        List<String> path = new ArrayList<>();
        for (Vertex at = end; at != null; at = previousVertices.get(at)) {
            path.add(at.getId().toString());
        }
        Collections.reverse(path);
        return path;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void process(Vertex start) {
        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex curr = queue.poll();
            if (visited.contains(curr)) {
                continue;
            }
            visited.add(curr);
            steps.add("Processing vertex " + curr.getLabel());

            for (Edge edge : graph.getEdgesFrom(curr)) {
                Vertex neighbor = edge.getToV();
                if (!visited.contains(neighbor)) {
                    steps.add("Checking edge (" + curr.getLabel() + " -> " + neighbor.getLabel() + ")");
                    int newDist = distances.get(curr) + edge.getWeight();
                    if (newDist < distances.get(neighbor)) {
                        steps.add("Relaxing edge (" + curr.getLabel() + " -> " + neighbor.getLabel() + ") and updating distance to " + newDist);
                        distances.put(neighbor, newDist);
                        previousVertices.put(neighbor, curr);
                        queue.add(neighbor);
                    }
                }
            }
        }
    }
}
