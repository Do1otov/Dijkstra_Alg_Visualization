package test.java;

import model.Edge;
import model.UndirectedGraph;
import model.Vertex;
import org.junit.Before;
import org.junit.Test;
import java.awt.*;
import java.util.List;

import static org.junit.Assert.*;

public class UndirectedGraphTest {
    private UndirectedGraph graph;

    @Before
    public void setUp() {
        graph = new UndirectedGraph();
    }

    @Test
    public void testAddVertex() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        graph.addVertex(v1);

        assertEquals(1, graph.getVertices().size());
        assertTrue(graph.getVertices().contains(v1));
    }

    @Test
    public void testAddDuplicateVertex() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("1", 0, 0, Color.RED);
        graph.addVertex(v1);
        graph.addVertex(v2);

        assertEquals(2, graph.getVertices().size());
    }

    @Test
    public void testRemoveVertex() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        graph.addVertex(v1);
        graph.removeVertex(v1);

        assertEquals(0, graph.getVertices().size());
        assertFalse(graph.getVertices().contains(v1));
    }

    @Test
    public void testGetEdgesFrom() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.addEdge(edge);

        List<Edge> edgesFromV1 = graph.getEdgesFrom(v1);
        List<Edge> edgesFromV2 = graph.getEdgesFrom(v2);

        assertEquals(1, edgesFromV1.size());
        assertEquals(1, edgesFromV2.size());
        assertTrue(edgesFromV1.contains(edge));
    }

    @Test
    public void testGetEdgesBetween() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.addEdge(edge);

        List<Edge> edges = graph.getEdgesFrom(v1);

        assertEquals(1, edges.size());
        assertTrue(edges.contains(edge));
    }

    @Test
    public void testAddEdgeTwice() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.addEdge(edge);
        graph.addEdge(edge);

        assertEquals(1, graph.getEdgesFrom(v1).size()); // should not allow duplicate edges
        assertEquals(1, graph.getEdgesFrom(v2).size());
    }

    @Test
    public void testRemoveNonexistentEdge() {
        Vertex v1 = new Vertex("1", 0, 0, Color.RED);
        Vertex v2 = new Vertex("2", 0, 0, Color.GREEN);
        graph.addVertex(v1);
        graph.addVertex(v2);

        Edge edge = new Edge(v1, v2, Color.BLACK);
        graph.removeEdge(edge); // removing an edge that doesn't exist

        assertEquals(0, graph.getEdgesFrom(v1).size());
        assertEquals(0, graph.getEdgesFrom(v2).size());
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
