package gui;

import model.DijkstraAlgorithm;
import model.DirectedGraph;
import model.Vertex;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private final GridBagConstraints gbc;

    private boolean isDirected;
    private final JPanel graphField;
    private DirectedGraph graph;

    private boolean runDijkstra;

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

        isDirected = true;
        graphField = graphFieldManager.getGraphField();
        graph = new DirectedGraph();
    }

    public void runDijkstra() {
        Vertex startVertex = graphFieldManager.getFirstVertex();
        if (startVertex == null) {
            CustomMessageDialog.showMessageDialog(this, "Error", "Start vertex is not selected.");
            return;
        }

        this.runDijkstra = true;
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.process(startVertex);
        displayDijkstraSteps(dijkstra);
        graphField.repaint();
    }

    public boolean isRunDijkstra() {
        return runDijkstra;
    }

    public void resetRunDijkstra() {
        this.runDijkstra = false;
    }

    private void displayDijkstraSteps(DijkstraAlgorithm dijkstra) {
        JTextArea stepsField = controlPanelsManager.getStepsField();
        stepsField.setText("");
        int stepNumber = 1;
        for (String step : dijkstra.getSteps()) {
            stepsField.append("Step " + stepNumber++ + ": " + step + "\n");
        }
    }

    public void setGraphDirection(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public void setGraph(DirectedGraph graph) {
        this.graph = graph;
    }

    public boolean graphIsDirected() {
        return isDirected;
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

    public ControlPanelsManager getControlPanelsManager() {
        return controlPanelsManager;
    }

    public GraphFieldManager getGraphFieldManager() {
        return graphFieldManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
