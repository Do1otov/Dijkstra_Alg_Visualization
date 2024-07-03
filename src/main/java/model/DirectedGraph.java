package model;

import java.util.*;

public class DirectedGraph {
    private final Map<UUID, Vertex> vertices;
    private final Map<Vertex, List<Edge>> adjacencyList;
    private Integer nextLabel;

    public DirectedGraph() {
        this.vertices = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.nextLabel = 1;
    }

    public void addVertex(Vertex vertex) {
        vertex.setLabel(String.valueOf(nextLabel++));
        vertices.put(vertex.getId(), vertex);
        adjacencyList.put(vertex, new ArrayList<>());
    }

    public void addEdge(Edge edge) {
        List<Edge> edgesFrom = adjacencyList.get(edge.getFromV());
        for (Edge e : edgesFrom) {
            if (e.getToV().equals(edge.getToV())) {
                return;
            }
        }
        edgesFrom.add(edge);
    }

    public void setEdgeWeight(Edge edge, Integer weight) {
        edge.setWeight(weight);
    }

    public void removeVertex(Vertex vertex) {
        Vertex removed = vertices.remove(vertex.getId());
        if (removed != null) {
            adjacencyList.remove(removed);
            for (List<Edge> edges : adjacencyList.values()) {
                edges.removeIf(e -> e.getToV().equals(removed));
            }
            renumberLabels();
        }
    }

    public void removeEdge(Edge edge) {
        List<Edge> edgesFrom = adjacencyList.get(edge.getFromV());
        if (edgesFrom != null) {
            edgesFrom.remove(edge);
        }
    }

    public void clear() {
        vertices.clear();
        adjacencyList.clear();
        nextLabel = 1;
    }

    public Vertex getVertex(UUID id) {
        return vertices.get(id);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    public List<Edge> getEdgesFrom(Vertex vertex) {
        return new ArrayList<>(adjacencyList.get(vertex));
    }



    private void renumberLabels() {
        List<Vertex> vertexList = new ArrayList<>(vertices.values());
        vertexList.sort(Comparator.comparing(Vertex::getLabel));
        int label = 1;
        for (Vertex vertex : vertexList) {
            vertex.setLabel(String.valueOf(label++));
        }
        nextLabel = label;
    }
}
