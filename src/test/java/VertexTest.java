package test.java;

import model.Vertex;
import org.junit.Test;
import java.awt.*;

import static org.junit.Assert.*;

public class VertexTest {

    @Test
    public void testVertex() {
        Vertex vertex = new Vertex("1", 10, 20, Color.RED);

        assertEquals("1", vertex.getLabel());
        assertEquals(10, vertex.getX().intValue());
        assertEquals(20, vertex.getY().intValue());
        assertEquals(Color.RED, vertex.getColor());

        vertex.setLabel("2");
        vertex.setX(30);
        vertex.setY(40);
        vertex.setColor(Color.GREEN);

        assertEquals("2", vertex.getLabel());
        assertEquals(30, vertex.getX().intValue());
        assertEquals(40, vertex.getY().intValue());
        assertEquals(Color.GREEN, vertex.getColor());
    }
}
