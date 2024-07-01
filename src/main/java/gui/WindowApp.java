package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class WindowApp extends JFrame {
    private final JPanel controlPanelLeft;
    private final JPanel controlPanelRight;
    private final JPanel graphArea;
    private final JTextArea stepsArea;

    private final static int BUTTON_ICON_SIZE = 30;
    private final static int[] CONTROL_PANELS_COLOR = {200, 200, 200};

    public WindowApp() {
        setTitle("Dijkstra's algorithm visualizer");
        // setIconImage(icon.getImage());
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        getContentPane().setBackground(new Color(241, 243, 249));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Left control panel
        controlPanelLeft = new RoundedPanel();
        controlPanelLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlPanelLeft.setBackground(new Color(CONTROL_PANELS_COLOR[0], CONTROL_PANELS_COLOR[1], CONTROL_PANELS_COLOR[2]));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(controlPanelLeft, gbc);

        JButton constructButton = createButton("/gui_icons/construct.png");
        JButton deleteButton = createButton("/gui_icons/delete.png");
        JButton deleteAllButton = createButton("/gui_icons/delete_all.png");
        JButton importButton = createButton("/gui_icons/import.png");
        JButton exportButton = createButton("/gui_icons/export.png");

        controlPanelLeft.add(constructButton);
        controlPanelLeft.add(deleteButton);
        controlPanelLeft.add(deleteAllButton);
        controlPanelLeft.add(importButton);
        controlPanelLeft.add(exportButton);

        // Right control panel
        controlPanelRight = new RoundedPanel();
        controlPanelRight.setLayout(new BorderLayout());
        controlPanelRight.setBackground(new Color(CONTROL_PANELS_COLOR[0], CONTROL_PANELS_COLOR[1], CONTROL_PANELS_COLOR[2]));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(controlPanelRight, gbc);

        JButton runButton = createButton("/gui_icons/run.png");
        JButton stepBackButton = createButton("/gui_icons/step_back.png");
        JButton stepForwardButton = createButton("/gui_icons/step_forward.png");

        RoundedPanel leftPanel = new RoundedPanel();
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(CONTROL_PANELS_COLOR[0], CONTROL_PANELS_COLOR[1], CONTROL_PANELS_COLOR[2]));
        leftPanel.add(runButton);

        RoundedPanel rightPanel = new RoundedPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(CONTROL_PANELS_COLOR[0], CONTROL_PANELS_COLOR[1], CONTROL_PANELS_COLOR[2]));
        rightPanel.add(stepBackButton);
        rightPanel.add(stepForwardButton);

        controlPanelRight.add(leftPanel, BorderLayout.WEST);
        controlPanelRight.add(rightPanel, BorderLayout.EAST);

        // Graph area
        graphArea = new RoundedPanel();
        graphArea.setBackground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(graphArea, gbc);

        // Steps area
        stepsArea = new JTextArea();
        stepsArea.setBackground(new Color(245, 245, 245));
        stepsArea.setEditable(false);

        JPanel stepsPanel = new RoundedPanel();
        stepsPanel.setLayout(new BorderLayout());
        stepsPanel.add(new JScrollPane(stepsArea), BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(stepsPanel, gbc);

    }

    private JButton createButton(String iconPath) {
        URL iconURL = getClass().getResource(iconPath);
        if (iconURL == null) {
            System.err.println("Could not find icon: " + iconPath);
            return new JButton();
        }

        ImageIcon icon = new ImageIcon(iconURL);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(BUTTON_ICON_SIZE, BUTTON_ICON_SIZE));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        return button;
    }

}
