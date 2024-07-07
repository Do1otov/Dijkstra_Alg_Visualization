package model;

import org.junit.Test;
import java.awt.Color;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EdgeTest {

    @Test
    public void testEdgeInitialization() {
        Vertex from = new Vertex("1", 0, 0, Color.RED);
        Vertex to = new Vertex("2", 0, 0, Color.GREEN);
        Edge edge = new Edge(from, to, Color.BLACK);

        assertEquals(from, edge.getFromV());
        assertEquals(to, edge.getToV());
        assertEquals(0, edge.getWeight().intValue());
        assertEquals(Color.BLACK, edge.getColor());
    }

    @Test
    public void testEdgeSetters() {
        Vertex from = new Vertex("1", 0, 0, Color.RED);
        Vertex to = new Vertex("2", 0, 0, Color.GREEN);
        Edge edge = new Edge(from, to, Color.BLACK);

        edge.setWeight(5);
        edge.setColor(Color.BLUE);

        assertEquals(5, edge.getWeight().intValue());
        assertEquals(Color.BLUE, edge.getColor());
    }

    @Test
    public void testNotEqualsMethod() {
        Vertex from1 = new Vertex("1", 0, 0, Color.RED);
        Vertex to1 = new Vertex("2", 0, 0, Color.GREEN);
        Edge edge1 = new Edge(from1, to1, Color.BLACK);

        Vertex from2 = new Vertex("1", 0, 0, Color.RED);
        Vertex to2 = new Vertex("3", 0, 0, Color.GREEN); // Different 'to' vertex
        Edge edge2 = new Edge(from2, to2, Color.BLACK);

        assertNotEquals(edge1, edge2);
    }
}
