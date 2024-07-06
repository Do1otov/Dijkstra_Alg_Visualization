package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraState {
    private final Map<Vertex, Integer> distances;
    private final Set<Vertex> visited;
    private final List<String> logs;

    private final Vertex currentVertex;
    private final Edge currentEdge;
    private final Vertex neighborVertex;
    private final String inequality;

    public DijkstraState(Map<Vertex, Integer> distances, Set<Vertex> visited, ArrayList<String> logs, Vertex currentVertex, Edge currentEdge, Vertex neighborVertex, String inequality) {
        this.distances = distances;
        this.visited = visited;
        this.logs = logs;
        this.currentVertex = currentVertex;
        this.currentEdge = currentEdge;
        this.neighborVertex = neighborVertex;
        this.inequality = inequality;
    }

    public Map<Vertex, Integer> getDistances() {
        return distances;
    }

    public Set<Vertex> getVisited() {
        return visited;
    }

    public List<String> getLogs() {
        return logs;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public Edge getCurrentEdge() {
        return currentEdge;
    }

    public Vertex getNeighborVertex() {
        return neighborVertex;
    }

    public String getInequality() {
        return inequality;
    }
}
