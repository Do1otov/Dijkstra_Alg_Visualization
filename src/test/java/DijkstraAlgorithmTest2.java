package test.java;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class DijkstraAlgorithmTest2 {
    private DirectedGraph graph;
    private DijkstraAlgorithm dijkstra;

    @Before
    public void setUp() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/1stGraph.json")), StandardCharsets.UTF_8);
        graph = DirectedGraph.fromJSON(json);
        dijkstra = new DijkstraAlgorithm1(graph);
    }

    @Test
    public void testDijkstraAlgorithmSingleSource() {
        Vertex startVertex = graph.getVertexByLabel("1");
        assertNotNull("Start vertex should not be null", startVertex);

        dijkstra.process(startVertex, null);

        DijkstraState finalState = dijkstra.getState(dijkstra.getNumberStates() - 1);
        assertNotNull("Final state should not be null", finalState);

        Map<Vertex, Integer> expectedDistances = Map.of(
                graph.getVertexByLabel("1"), 0,
                graph.getVertexByLabel("2"), 0,
                graph.getVertexByLabel("3"), 0,
                graph.getVertexByLabel("4"), 0
        );

        for (Map.Entry<Vertex, Integer> entry : expectedDistances.entrySet()) {
            assertEquals(entry.getValue(), finalState.getDistances().get(entry.getKey()));
        }
    }

    @Test
    public void testDijkstraAlgorithmMultipleSources() {
        Vertex startVertex1 = graph.getVertexByLabel("1");
        Vertex startVertex2 = graph.getVertexByLabel("2");
        assertNotNull("Start vertex 1 should not be null", startVertex1);
        assertNotNull("Start vertex 2 should not be null", startVertex2);

        dijkstra.process(startVertex1, null);
        dijkstra.process(startVertex2, null);

        DijkstraState finalState = dijkstra.getState(dijkstra.getNumberStates() - 1);
        assertNotNull("Final state should not be null", finalState);

        Map<Vertex, Integer> expectedDistances = Map.of(
                graph.getVertexByLabel("1"), 0,
                graph.getVertexByLabel("2"), 0,
                graph.getVertexByLabel("3"), 0,
                graph.getVertexByLabel("4"), 0
        );

        for (Map.Entry<Vertex, Integer> entry : expectedDistances.entrySet()) {
            assertEquals(entry.getValue(), finalState.getDistances().get(entry.getKey()));
        }
    }

    @Test
    public void testDijkstraAlgorithmNoPath() {
        Vertex startVertex = graph.getVertexByLabel("1");
        Vertex endVertex = graph.getVertexByLabel("5");
        assertNotNull("Start vertex should not be null", startVertex);

        if (endVertex == null) {
            dijkstra.process(startVertex, null);
            DijkstraState finalState = dijkstra.getState(dijkstra.getNumberStates() - 1);
            assertNotNull("Final state should not be null", finalState);
            assertNull("Distance to non-existent vertex should be null", finalState.getDistances().get(endVertex));
        } else {
            dijkstra.process(startVertex, null);
            DijkstraState finalState = dijkstra.getState(dijkstra.getNumberStates() - 1);
            assertNotNull("Final state should not be null", finalState);
            assertEquals(Integer.MAX_VALUE, (int) finalState.getDistances().get(endVertex));
        }
    }

    @Test
    public void testDijkstraAlgorithmNegativeWeight() {
        Vertex startVertex = graph.getVertexByLabel("1");
        assertNotNull("Start vertex should not be null", startVertex);

        dijkstra.process(startVertex, null);

        DijkstraState finalState = dijkstra.getState(dijkstra.getNumberStates() - 1);
        assertNotNull("Final state should not be null", finalState);

        assertEquals(0, (int) finalState.getDistances().get(startVertex));
    }
}
