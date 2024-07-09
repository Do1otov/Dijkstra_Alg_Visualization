package model;

import java.util.*;
public class DijkstraAlgorithm2 extends DijkstraAlgorithm{
    private final Map<Vertex, Integer> distances;
    private final Set<Vertex> visited;
    private final PriorityQueue<Vertex> queue;

    private final List<String> steps;
    private final List<DijkstraState> states;

    public DijkstraAlgorithm2(DirectedGraph graph) {
        super(graph);
        this.distances = new HashMap<>();
        this.visited = new HashSet<>();
        this.queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        this.steps = new ArrayList<>();
        this.states = new ArrayList<>();

        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
    }

    public void process(Vertex start, Vertex end) {
        steps.add("Step 0:\n  The start of Dijkstra`s algorithm.\n");
        saveState(null, null, null, null);

        distances.put(start, 0);
        queue.add(start);
        int count = 1;

        while (!queue.isEmpty()) {
            Vertex curr = queue.poll();
            if (visited.contains(curr)) {
                continue;
            }
            visited.add(curr);

            steps.add(String.format("\nStep %d:\n  Processing vertex (%s).\n", count, curr.getLabel()));
            saveState(curr, null, null, null);

            for (Edge edge : graph.getEdgesFrom(curr)) {
                Vertex neighbor = edge.getToV();
                if (!visited.contains(neighbor)) {
                    steps.add(String.format("  Processing edge (%s -> %s).\n", curr.getLabel(), neighbor.getLabel()));
                    saveState(curr, edge, neighbor, null);

                    int newDist = distances.get(curr) + edge.getWeight();
                    String stringSign = newDist < distances.get(neighbor) ? " < " : " > ";
                    String stringDist = distances.get(neighbor) < Integer.MAX_VALUE ? distances.get(neighbor).toString() : "∞";
                    String inequality = distances.get(curr) + " + " + edge.getWeight() + stringSign + stringDist;

                    steps.add(String.format("  Distance inequality: %s.\n", inequality));
                    saveState(curr, edge, neighbor, inequality);

                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        queue.add(neighbor);

                        steps.add(String.format("  Relaxation of edge (%s -> %s) and updating the distance to vertex (%s): New distance = %d.\n", curr.getLabel(), neighbor.getLabel(), neighbor.getLabel(), newDist));
                        saveState(curr, edge, neighbor, null);
                    }
                }
            }
            count++;
        }
        steps.add(String.format("\nStep %d: The end of Dijkstra's algorithm.\n", count));
        saveState(null, null, null, null);
    }

    protected void saveState(Vertex vertex, Edge edge, Vertex neighbor, String inequality) {
        states.add(new DijkstraState(new HashMap<>(distances), new HashSet<>(visited), new ArrayList<>(steps), vertex, edge, neighbor, inequality));
    }

    public DijkstraState getState(Integer index) {
        return states.get(index);
    }

    public Integer getNumberStates() {
        return states.size();
    }
}
