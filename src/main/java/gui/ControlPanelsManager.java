package gui;

import model.DirectedGraph;
import model.UndirectedGraph;
import model.Vertex;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ControlPanelsManager {
    private final App app;
    private final GridBagConstraints gbc;
    private JTextArea stepsField;

    private final ButtonsManager buttonsManager;

    private JToggleButton editButton;
    private JToggleButton deleteButton;

    public ControlPanelsManager(App app) {
        this.app = app;
        this.gbc = app.getGBC();
        initStepsField();
        buttonsManager = new ButtonsManager();

        initControlPanelLeft();
        initControlPanelRight();
    }

    public JTextArea getStepsField() {
        return stepsField;
    }

    public JToggleButton getEditButton() {
        return editButton;
    }

    public JToggleButton getDeleteButton() {
        return deleteButton;
    }

    private void initStepsField() {
        stepsField = new JTextArea();
        stepsField.setBackground(GUISettings.STEPS_AREA_COLOR);
        stepsField.setEditable(false);
        stepsField.setLineWrap(true);
        stepsField.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(stepsField);
        int width = 200, height = 500;
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setMinimumSize(new Dimension(width, height));
        scrollPane.setMaximumSize(new Dimension(width, height));

        JPanel stepsPanel = new RoundedPanel();
        stepsPanel.setLayout(new BorderLayout());
        stepsPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        app.add(stepsPanel, gbc);
    }

    private void initControlPanelLeft() {
        JPanel controlPanelLeft = new RoundedPanel();
        controlPanelLeft.setLayout(new BorderLayout());
        controlPanelLeft.setBackground(GUISettings.CONTROL_PANEL_COLOR);

        RoundedPanel leftPanel = new RoundedPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(GUISettings.CONTROL_PANEL_COLOR);

        RoundedPanel rightPanel = new RoundedPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(GUISettings.CONTROL_PANEL_COLOR);

        controlPanelLeft.add(leftPanel, BorderLayout.WEST);
        controlPanelLeft.add(rightPanel, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ButtonGroup toggleGroup = new ButtonGroup();

        editButton = buttonsManager.createToggleButton("/gui_icons/construct.png", "Edit Mode", toggleGroup);
        deleteButton = buttonsManager.createToggleButton("/gui_icons/delete.png", "Delete Mode", toggleGroup);

        JButton deleteAllButton = buttonsManager.createButton("/gui_icons/delete_all.png", "Clear the Field");
        JButton switchGraphTypeButton = buttonsManager.createButton("/gui_icons/switch_directed.png", "Switch Graph Type (Current: Directed)");
        JButton saveButton = buttonsManager.createButton("/gui_icons/save.png", "Save the Graph");
        JButton loadButton = buttonsManager.createButton("/gui_icons/load.png", "Load the Graph");

        leftPanel.add(editButton);
        leftPanel.add(deleteButton);
        leftPanel.add(deleteAllButton);
        leftPanel.add(Box.createHorizontalStrut(GUISettings.BUTTON_SIZE));
        leftPanel.add(switchGraphTypeButton);
        rightPanel.add(saveButton);
        rightPanel.add(loadButton);

        deleteAllButton.addActionListener(e -> {
            Vertex firstVertex = app.getGraphFieldManager().getFirstVertex();
            firstVertex = null;
            app.getGraph().clear();
            app.resetRunDijkstra();
            app.getGraphField().repaint();
        });

        switchGraphTypeButton.addActionListener(e -> {
            app.getGraph().clear();
            app.resetRunDijkstra();
            app.setGraph(app.getGraph().isDirected() ? new UndirectedGraph() : new DirectedGraph());
            app.getGraphField().repaint();
            changeSwitchGraphTypeButtonIcon(switchGraphTypeButton, app.getGraph().isDirected());
        });

        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
                }

                @Override
                public String getDescription() {
                    return "JSON Files (*.json)";
                }
            });

            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".json")) {
                    selectedFile = new File(path + ".json");
                }
                try (FileWriter file = new FileWriter(selectedFile)) {
                    file.write(app.getGraph().toJSON());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
                }

                @Override
                public String getDescription() {
                    return "JSON Files (*.json)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try (FileReader reader = new FileReader(selectedFile)) {
                    StringBuilder jsonBuilder = new StringBuilder();
                    int c;
                    while ((c = reader.read()) != -1) {
                        jsonBuilder.append((char) c);
                    }
                    String json = jsonBuilder.toString();
                    DirectedGraph loadedGraph = DirectedGraph.fromJSON(json);
                    if (loadedGraph.isDirected()) {
                        app.setGraph(loadedGraph);
                    } else {
                        UndirectedGraph undirectedGraph = UndirectedGraph.fromJSON(json);
                        app.setGraph(undirectedGraph);
                    }
                    changeSwitchGraphTypeButtonIcon(switchGraphTypeButton, loadedGraph.isDirected());
                    app.getGraphField().repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        app.add(controlPanelLeft, gbc);
    }

    private void initControlPanelRight() {
        JPanel controlPanelRight = new RoundedPanel();
        controlPanelRight.setLayout(new BorderLayout());
        controlPanelRight.setBackground(GUISettings.CONTROL_PANEL_COLOR);

        RoundedPanel leftPanel = new RoundedPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(GUISettings.CONTROL_PANEL_COLOR);

        RoundedPanel rightPanel = new RoundedPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(GUISettings.CONTROL_PANEL_COLOR);

        controlPanelRight.add(leftPanel, BorderLayout.WEST);
        controlPanelRight.add(rightPanel, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton runButton = buttonsManager.createButton("/gui_icons/run.png", "Run Completely");
        JButton stepBackButton = buttonsManager.createButton("/gui_icons/step_back.png", "Step Back");
        JButton stepForwardButton = buttonsManager.createButton("/gui_icons/step_forward.png", "Step Forward / Run by Steps");

        leftPanel.add(runButton);
        rightPanel.add(stepBackButton);
        rightPanel.add(stepForwardButton);

        runButton.addActionListener(e -> app.fullRunDijkstra());
        stepBackButton.addActionListener(e -> app.stepBack());
        stepForwardButton.addActionListener(e -> app.stepForward());

        app.add(controlPanelRight, gbc);
    }

    private void changeSwitchGraphTypeButtonIcon(JButton button, boolean isDirected) {
        String iconPath = isDirected ? "/gui_icons/switch_directed.png" : "/gui_icons/switch_undirected.png";
        String tooltipText = isDirected ? "Switch Graph Type (Current: Directed)" : "Switch Graph Type (Current: Undirected)";
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(GUISettings.BUTTON_SIZE, GUISettings.BUTTON_SIZE, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(resizedImg));
        button.setToolTipText(tooltipText);
    }

    private static class RoundedPanel extends JPanel {
        public RoundedPanel() {
            super();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
        }
    }
}
