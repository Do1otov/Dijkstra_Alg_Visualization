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

    private boolean isRunDijkstra;

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

        this.isDirected = true;
        this.graphField = graphFieldManager.getGraphField();
        this.graph = new DirectedGraph();
        this.isRunDijkstra = false;
    }

    public void setGraphDirection(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public void setGraph(DirectedGraph graph) {
        this.graph = graph;
    }

    public GridBagConstraints getGBC() {
        return gbc;
    }

    public boolean graphIsDirected() {
        return isDirected;
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

    public ControlPanelsManager getControlPanelsManager() {
        return controlPanelsManager;
    }

    public GraphFieldManager getGraphFieldManager() {
        return graphFieldManager;
    }

    public void runDijkstra() {
        Vertex startVertex = graphFieldManager.getFirstVertex();
        if (startVertex == null) {
            CustomMessageDialog.showMessageDialog(this, "Error", "Start vertex is not selected.", 250, 100);
            return;
        }

        this.isRunDijkstra = true;
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.process(startVertex);
        displayDijkstraSteps(dijkstra);
        graphField.repaint();
    }

    public void resetRunDijkstra() {
        this.isRunDijkstra = false;
    }

    private void displayDijkstraSteps(DijkstraAlgorithm dijkstra) {
        JTextArea stepsField = controlPanelsManager.getStepsField();
        stepsField.setText("");
        int stepNumber = 1;
        for (String step : dijkstra.getSteps()) {
            stepsField.append("Step " + stepNumber++ + ": " + step + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
