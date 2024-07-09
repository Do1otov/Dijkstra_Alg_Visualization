package model;

import java.util.*;

public abstract class DijkstraAlgorithm {
    protected final DirectedGraph graph;

    public DijkstraAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }

    public abstract void process(Vertex start, Vertex end);

    protected abstract void saveState(Vertex vertex, Edge edge, Vertex neighbor, String inequality);

    public abstract DijkstraState getState(Integer index);

    public abstract Integer getNumberStates();
}
