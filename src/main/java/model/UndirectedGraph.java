package model;

import java.util.List;

public class UndirectedGraph extends DirectedGraph {
    @Override
    public void setEdgeWeight(Edge edge, Integer weight) {
        super.setEdgeWeight(edge, weight);
        List<Edge> edgesFrom = super.getEdgesFrom(edge.getToV());
        for (Edge e : edgesFrom) {
            if (e.getToV().equals(edge.getFromV())) {
                super.setEdgeWeight(e, weight);
                break;
            }
        }
    }

    @Override
    public void addEdge(Edge edge) {
        super.addEdge(edge);
        Edge reversed = new Edge(edge.getToV(), edge.getFromV(), edge.getColor());
        reversed.setWeight(edge.getWeight());
        super.addEdge(reversed);
    }

    @Override
    public void removeEdge(Edge edge) {
        super.removeEdge(edge);
        List<Edge> edgesFrom = super.getEdgesFrom(edge.getToV());
        Edge reversed = null;
        for (Edge e : edgesFrom) {
            if (e.getToV().equals(edge.getFromV())) {
                reversed = e;
                break;
            }
        }
        if (reversed != null) {
            super.removeEdge(reversed);
        }
    }
}
