package test.java;

import model.DijkstraState;
import model.Edge;
import model.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

public class DijkstraStateTest {

    private Vertex v1, v2;
    private Edge e1;
    private Map<Vertex, Integer> distances;
    private Set<Vertex> visited;
    private List<String> steps;

    @Before
    public void setUp() {
        v1 = new Vertex("1", 0, 0, Color.RED);
        v2 = new Vertex("2", 0, 0, Color.GREEN);
        e1 = new Edge(v1, v2, Color.BLACK);
        distances = new HashMap<>();
        visited = new HashSet<>();
        steps = new ArrayList<>();
    }

    @Test
    public void testDijkstraStateInitialization() {
        String inequality = "Dijkstra's inequality";
        DijkstraState state = new DijkstraState(distances, visited, (ArrayList<String>) steps, v1, e1, v2, inequality);

        assertEquals(distances, state.getDistances());
        assertEquals(visited, state.getVisited());
        assertEquals(steps, state.getSteps());
        assertEquals(v1, state.getCurrentVertex());
        assertEquals(e1, state.getCurrentEdge());
        assertEquals(v2, state.getNeighborVertex());
        assertEquals(inequality, state.getInequality());
    }


    @Test
    public void testDijkstraStateCopy() {
        String inequality = "Dijkstra's inequality";
        DijkstraState state1 = new DijkstraState(distances, visited, (ArrayList<String>) steps, v1, e1, v2, inequality);

        Map<Vertex, Integer> newDistances = new HashMap<>(distances);
        Set<Vertex> newVisited = new HashSet<>(visited);
        List<String> newSteps = new ArrayList<>(steps);
        DijkstraState state2 = new DijkstraState(newDistances, newVisited, (ArrayList<String>) newSteps, v1, e1, v2, inequality);

        state2.getDistances().put(v1, 1);
        state2.getVisited().add(v1);
        state2.getSteps().add("Step 1");

        assertNotEquals(state1.getDistances(), state2.getDistances());
        assertNotEquals(state1.getVisited(), state2.getVisited());
        assertNotEquals(state1.getSteps(), state2.getSteps());
    }

    @Test
    public void testDijkstraStateWithEmptyParameters() {
        DijkstraState state = new DijkstraState(new HashMap<>(), new HashSet<>(), new ArrayList<>(), null, null, null, "");

        assertNotNull(state.getDistances());
        assertNotNull(state.getVisited());
        assertNotNull(state.getSteps());
        assertNull(state.getCurrentVertex());
        assertNull(state.getCurrentEdge());
        assertNull(state.getNeighborVertex());
        assertEquals("", state.getInequality());
    }
}
