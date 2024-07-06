package model;

import gui.*;

import javax.swing.*;

import static gui.Settings.*;

public class AlgorithmManager {
    private final App app;

    private DijkstraAlgorithm dijkstra;
    private boolean isRun;
    private Integer stateIndex;

    public AlgorithmManager(App app) {
        this.app = app;
        this.isRun = false;
        this.stateIndex = 0;
    }

    public DijkstraState getState() {
        return dijkstra.getState(stateIndex);
    }

    public boolean isRun() {
        return isRun;
    }

    public void reset() {
        dijkstra = null;
        isRun = false;
        stateIndex = 0;

        for (Vertex vertex : app.getGraph().getVertices()) {
            vertex.setColor(VERTEX_COLOR);
            for (Edge edge : app.getGraph().getEdgesFrom(vertex)) {
                edge.setColor(EDGE_COLOR);
            }
        }
        app.getStepsFieldManager().clear();
    }

    public void stepBack() {
        if (isRun && stateIndex > 0) {
            stateIndex--;
            update();
        }
    }

    public void stepForward() {
        if (stateIndex.equals(0)) {
            run();
            if (isRun) {
                stateIndex++;
                update();
            }
        } else if (isRun && stateIndex < dijkstra.getNumberStates() - 1) {
            stateIndex++;
            update();
        }
    }

    public void runFull() {
        run();
        if (isRun) {
            stateIndex = dijkstra.getNumberStates() - 1;
            update();
        }
    }

    private void run() {
        Vertex startVertex = app.getGraphFieldManager().getFirstVertex();
        if (startVertex == null) {
            CustomMessageDialog.showMessageDialog(app.getGraphFieldManager().getGraphField(), "Error", "Start vertex is not selected.", 250, 100);
            return;
        }

        reset();
        isRun = true;
        dijkstra = new DijkstraAlgorithm(app.getGraph());
        dijkstra.process(startVertex);
    }

    private void update() {
        app.getStepsFieldManager().display();
        app.getGraphFieldManager().getGraphField().repaint();
    }
}
