package model;

import java.util.*;

public class DirectedGraph {
    private final Map<UUID, Vertex> vertices;
    private final Map<Vertex, List<Edge>> adjacencyList;
    protected boolean isDirected;
    private Integer nextLabel;

    public DirectedGraph() {
        this.vertices = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.isDirected = true;
        this.nextLabel = 1;
    }

    public void setEdgeWeight(Edge edge, Integer weight) {
        edge.setWeight(weight);
    }

    public Vertex getVertexById(UUID id) {
        return vertices.get(id);
    }

    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    public List<Edge> getEdgesFrom(Vertex vertex) {
        return new ArrayList<>(adjacencyList.get(vertex));
    }

    public boolean isDirected() {
        return isDirected;
    }

    public void addVertex(Vertex vertex) {
        if (vertex.getLabel() == null || vertex.getLabel().isEmpty()) {
            vertex.setLabel(String.valueOf(nextLabel++));
        } else {
            int vertexLabel = Integer.parseInt(vertex.getLabel());
            if (vertexLabel >= nextLabel) {
                nextLabel = vertexLabel + 1;
            }
        }
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

    public void removeVertex(Vertex vertex) {
        Vertex removed = vertices.remove(vertex.getId());
        if (removed != null) {
            adjacencyList.remove(removed);
            for (List<Edge> edges : adjacencyList.values()) {
                edges.removeIf(e -> e.getToV().equals(removed));
            }
            reassignLabels();
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

    public String toJSON() {
        StringBuilder verticesJSON = new StringBuilder();
        StringBuilder edgesJSON = new StringBuilder();

        for (Vertex vertex : vertices.values()) {
            verticesJSON.append(vertex.toJSON()).append(",");
        }

        for (List<Edge> edges : adjacencyList.values()) {
            for (Edge edge : edges) {
                edgesJSON.append(edge.toJSON()).append(",");
            }
        }

        if (!verticesJSON.isEmpty()) verticesJSON.setLength(verticesJSON.length() - 1);
        if (!edgesJSON.isEmpty()) edgesJSON.setLength(edgesJSON.length() - 1);

        return String.format("{\"isDirected\":%b,\"nextLabel\":%d,\"vertices\":[%s],\"edges\":[%s]}",
                isDirected, nextLabel, verticesJSON, edgesJSON);
    }

    public static DirectedGraph fromJSON(String json) {
        DirectedGraph graph = new DirectedGraph();
        json = json.trim();

        String isDirectedStr = json.substring(json.indexOf("\"isDirected\":") + 13, json.indexOf(",", json.indexOf("\"isDirected\":")));
        graph.isDirected = Boolean.parseBoolean(isDirectedStr.trim());

        String nextLabelStr = json.substring(json.indexOf("\"nextLabel\":") + 12, json.indexOf(",", json.indexOf("\"nextLabel\":")));
        graph.nextLabel = Integer.parseInt(nextLabelStr.trim());

        int verticesStart = json.indexOf("\"vertices\":[") + 12;
        int verticesEnd = json.indexOf("],", verticesStart);
        String verticesData = json.substring(verticesStart, verticesEnd);

        if (!verticesData.isEmpty()) {
            String[] vertexArray = verticesData.split("\\},");
            for (String vertexData : vertexArray) {
                vertexData = vertexData.endsWith("}") ? vertexData : vertexData + "}";
                Vertex vertex = Vertex.fromJSON(vertexData);
                graph.addVertex(vertex);
            }
        }

        int edgesStart = json.indexOf("\"edges\":[") + 9;
        int edgesEnd = json.lastIndexOf("]");
        String edgesData = json.substring(edgesStart, edgesEnd);

        if (!edgesData.isEmpty()) {
            String[] edgeArray = edgesData.split("\\},");
            for (String edgeData : edgeArray) {
                edgeData = edgeData.endsWith("}") ? edgeData : edgeData + "}";
                Edge edge = Edge.fromJSON(edgeData, graph);
                graph.addEdge(edge);
            }
        }
        return graph;
    }
    
    private void reassignLabels() {
        List<Vertex> vertexList = new ArrayList<>(vertices.values());
        vertexList.sort(Comparator.comparing(Vertex::getLabel));
        int label = 1;
        for (Vertex vertex : vertexList) {
            vertex.setLabel(String.valueOf(label++));
        }
        nextLabel = label;
    }
}
