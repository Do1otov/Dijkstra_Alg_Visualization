package model;

import org.junit.Before;
import org.junit.Test;
import java.awt.*;

import static org.junit.Assert.*;

public class DirectedGraphTest {
    private DirectedGraph graph;

    @Before
    public void setUp() {
        graph = new DirectedGraph();
    }

    @Test
    public void testAddVertex() {
        Vertex vertex = new Vertex("1", 0, 0, Color.RED);
        graph.addVertex(vertex);
        assertEquals(1, graph.getVertices().size());
    }

    @Test
    public void testAddEdge() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.addEdge(edge);
        assertEquals(1, graph.getEdgesFrom(v1).size());
    }

    @Test
    public void testRemoveVertex() {
        Vertex vertex = new Vertex("1", 0, 0, Color.RED);
        graph.addVertex(vertex);
        graph.removeVertex(vertex);
        assertEquals(0, graph.getVertices().size());
        assertFalse(graph.getVertices().contains(vertex));
    }

    @Test
    public void testRemoveEdge() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.addEdge(edge);
        graph.removeEdge(edge);
        assertEquals(0, graph.getEdgesFrom(v1).size());
    }

    @Test
    public void testClearGraph() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);
        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.addEdge(edge);

        graph.clear();
        assertEquals(0, graph.getVertices().size());
    }
}
