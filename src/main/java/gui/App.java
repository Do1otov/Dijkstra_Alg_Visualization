package gui;

import model.*;
import static gui.Settings.*;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private final GridBagConstraints gbc;

    private DirectedGraph graph;

    private final ControlPanelsManager controlPanelsManager;
    private final GraphFieldManager graphFieldManager;
    private final StepsFieldManager stepsFieldManager;
    private final AlgorithmManager algorithmManager;

    public App() {
        setTitle("Dijkstra's algorithm visualizer");
        setSize(1024, 768);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        ImageIcon icon = new ImageIcon(getClass().getResource("/app.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(APP_COLOR);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        UIManager.put("ToolTip.background", APP_COLOR);
        UIManager.put("ToolTip.foreground", TITLE_COLOR);
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(CONTROL_PANEL_COLOR));

        this.graph = new DirectedGraph();

        controlPanelsManager = new ControlPanelsManager(this);
        graphFieldManager = new GraphFieldManager(this);
        stepsFieldManager = new StepsFieldManager(this);
        algorithmManager = new AlgorithmManager(this);
    }

    public GridBagConstraints getGBC() {
        return gbc;
    }

    public void setGraph(DirectedGraph graph) {
        this.graph = graph;
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

    public StepsFieldManager getStepsFieldManager() {
        return stepsFieldManager;
    }

    public AlgorithmManager getAlgorithmManager() {
        return algorithmManager;
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
