package model;

import java.util.*;

public class DijkstraState {
    private final Map<Vertex, Integer> distances;
    private final Set<Vertex> visited;
    private final List<String> steps;

    private final Vertex currentVertex;
    private final Edge currentEdge;
    private final Vertex neighborVertex;
    private final String inequality;

    public DijkstraState(Map<Vertex, Integer> distances, Set<Vertex> visited, ArrayList<String> steps, Vertex currentVertex, Edge currentEdge, Vertex neighborVertex, String inequality) {
        this.distances = distances;
        this.visited = visited;
        this.steps = steps;
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

    public List<String> getSteps() {
        return steps;
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
