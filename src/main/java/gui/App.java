package gui;

import model.*;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private final GridBagConstraints gbc;

    private final JPanel graphField;
    private DirectedGraph graph;

    private boolean isRunDijkstra;
    private DijkstraAlgorithm dijkstra;
    private Integer stateIndex;

    private final ControlPanelsManager controlPanelsManager;
    private final GraphFieldManager graphFieldManager;

    public App() {
        setTitle("Dijkstra's algorithm visualizer");
        setSize(1024, 768);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        ImageIcon icon = new ImageIcon(getClass().getResource("/app.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(GUISettings.APP_COLOR);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        UIManager.put("ToolTip.background", GUISettings.APP_COLOR);
        UIManager.put("ToolTip.foreground", GUISettings.TITLE_COLOR);
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(GUISettings.CONTROL_PANEL_COLOR));

        controlPanelsManager = new ControlPanelsManager(this);
        graphFieldManager = new GraphFieldManager(this);

        this.graphField = graphFieldManager.getGraphField();
        this.graph = new DirectedGraph();
        this.isRunDijkstra = false;
        stateIndex = 0;
    }

    public void setGraph(DirectedGraph graph) {
        this.graph = graph;
    }

    public GridBagConstraints getGBC() {
        return gbc;
    }

    public JPanel getGraphField() {
        return graphField;
    }

    public DirectedGraph getGraph() {
        return graph;
    }

    public boolean isRunDijkstra() {
        return isRunDijkstra;
    }

    public DijkstraAlgorithm getDijkstra() {
        return dijkstra;
    }

    public Integer getStateIndex() {
        return stateIndex;
    }

    public ControlPanelsManager getControlPanelsManager() {
        return controlPanelsManager;
    }

    public GraphFieldManager getGraphFieldManager() {
        return graphFieldManager;
    }

    public void runDijkstra() {
        Vertex startVertex = graphFieldManager.getFirstVertex();
        if (startVertex == null) {
            CustomMessageDialog.showMessageDialog(graphField, "Error", "Start vertex is not selected.", 250, 100);
            return;
        }

        resetRunDijkstra();
        this.isRunDijkstra = true;
        dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.process(startVertex);
    }

    public void resetRunDijkstra() {
        this.isRunDijkstra = false;
        this.stateIndex = 0;
        for (Vertex vertex : graph.getVertices()) {
            vertex.setColor(GUISettings.VERTEX_COLOR);

            for (Edge edge : graph.getEdgesFrom(vertex)) {
                edge.setColor(GUISettings.EDGE_COLOR);
            }
        }
        controlPanelsManager.getStepsField().setText("");
    }

    public void fullRunDijkstra() {
        runDijkstra();
        if (isRunDijkstra) {
            stateIndex = dijkstra.getNumberStates() - 1;
            displaySteps();
            graphField.repaint();
        }
    }

    public void stepBack() {
        if (isRunDijkstra && stateIndex > 0) {
            stateIndex--;
            displaySteps();
            graphField.repaint();
        }
    }

    public void stepForward() {
        if (stateIndex.equals(0)) {
            runDijkstra();
            if (isRunDijkstra) {
                stateIndex++;
                displaySteps();
                graphField.repaint();
            }
        } else if (isRunDijkstra && stateIndex < dijkstra.getNumberStates() - 1) {
            stateIndex++;
            displaySteps();
            graphField.repaint();
        }
    }

    private void displaySteps() {
        JTextArea stepsField = controlPanelsManager.getStepsField();
        stepsField.setText("");

        DijkstraState state = dijkstra.getState(stateIndex);
        for (String log : state.getLogs()) {
            stepsField.append(log);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
