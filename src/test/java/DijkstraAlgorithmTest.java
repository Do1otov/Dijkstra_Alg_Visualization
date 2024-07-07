package com.example;

import model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.*;

public class DijkstraAlgorithmTest {
    private DirectedGraph graph;
    private DijkstraAlgorithm dijkstra;

    @Before
    public void setUp() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("src/test/resources/1stGraph.json")), StandardCharsets.UTF_8);
        graph = DirectedGraph.fromJSON(json);
        dijkstra = new DijkstraAlgorithm(graph);
    }

    @Test
    public void testDijkstraAlgorithmSingleSource() {
        Vertex startVertex = graph.getVertexByLabel("1");
        assertNotNull("Start vertex should not be null", startVertex);

        dijkstra.process(startVertex);

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
}
