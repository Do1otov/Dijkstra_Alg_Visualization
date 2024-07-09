package test.java;

import model.DirectedGraph;
import model.Edge;
import model.Vertex;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GraphConservationTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setup() throws IOException {
        folder.create();
    }

    @After
    public void tearDown() {
        folder.delete();
    }

    @Test
    public void testSaveGraphToJson() throws IOException {
        DirectedGraph graph = new DirectedGraph();
        Vertex v1 = new Vertex("1", 1, 2, Color.black);
        Vertex v2 = new Vertex("2", 2, 3, Color.yellow);
        Edge e = new Edge(v1, v2, Color.RED);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(e);

        File file = folder.newFile("graph.json");

        FileWriter writer = new FileWriter(file);
        writer.write(graph.toJSON());
        writer.close();

        String json = Files.readString(Paths.get(file.getAbsolutePath()));
        assertEquals(graph.toJSON(), json);
    }

    @Test
    public void testSaveGraphToJsonAbsolutePath() throws IOException {
        DirectedGraph graph = new DirectedGraph();
        Vertex v1 = new Vertex("1", 1, 2, Color.black);
        Vertex v2 = new Vertex("2", 2, 3, Color.yellow);
        Edge e = new Edge(v1, v2, Color.black);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(e);

        File file = new File("src/test/resources/testGraph.json");

        FileWriter writer = new FileWriter(file);
        writer.write(graph.toJSON());
        writer.close();

        String json = Files.readString(Paths.get(file.getAbsolutePath()));
        assertEquals(graph.toJSON(), json);
    }

    @Test(expected = IOException.class)
    public void testSaveGraphToJsonIOException() throws IOException {
        DirectedGraph graph = new DirectedGraph();
        Vertex v1 = new Vertex("1", 1, 2, Color.black);
        Vertex v2 = new Vertex("2", 2, 3, Color.yellow);
        Edge e = new Edge(v1, v2, Color.black);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(e);

        File file = new File("/invalid/path/to/graph.json");

        FileWriter writer = new FileWriter(file);
        writer.write(graph.toJSON());
        writer.close();
    }

    @Test
    public void testClearGraph() {
        DirectedGraph graph = new DirectedGraph();
        Vertex v1 = new Vertex("1", 1, 2, Color.black);
        Vertex v2 = new Vertex("2", 2, 3, Color.yellow);
        Edge e = new Edge(v1, v2, Color.RED);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addEdge(e);

        graph.clear();

        assertEquals(0, graph.getVertices().size());
    }
}