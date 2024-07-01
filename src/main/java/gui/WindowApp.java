package main.java.gui;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class WindowApp extends JFrame {
    private final JPanel controlPanelLeft;
    private final JPanel controlPanelRight;
    private final JPanel graphArea;
    private final JTextArea stepsArea;

    private final static int BUTTON_ICON_SIZE = 30;
    private final static int[] CONTROL_PANELS_COLOR = {200, 200, 200};

    private JToggleButton editButton;
    private JToggleButton deleteButton;
    private ButtonGroup toggleGroup;

    public WindowApp() {
        setTitle("Dijkstra's algorithm visualizer");
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        getContentPane().setBackground(new Color(241, 243, 249));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        UIManager.put("ToolTip.background", new ColorUIResource(241, 243, 249));
        UIManager.put("ToolTip.foreground", new ColorUIResource(0, 0, 0));
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(200, 200, 200)));

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

        editButton = createToggleButton("/gui_icons/construct.png", "Edit Mode");
        deleteButton = createToggleButton("/gui_icons/delete.png", "Delete Mode");
        JButton deleteAllButton = createButton("/gui_icons/delete_all.png", "Clear the Field");
        JButton importButton = createButton("/gui_icons/import.png", "Import Graph");
        JButton exportButton = createButton("/gui_icons/export.png", "Export Graph");

        controlPanelLeft.add(editButton);
        controlPanelLeft.add(deleteButton);
        controlPanelLeft.add(deleteAllButton);
        controlPanelLeft.add(importButton);
        controlPanelLeft.add(exportButton);

        toggleGroup = new ButtonGroup();
        toggleGroup.add(editButton);
        toggleGroup.add(deleteButton);
        editButton.setSelected(true);

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

        JButton runButton = createButton("/gui_icons/run.png", "Run");
        JButton stepBackButton = createButton("/gui_icons/step_back.png", "Step Back");
        JButton stepForwardButton = createButton("/gui_icons/step_forward.png", "Step Forward");

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

    private JToggleButton createToggleButton(String iconPath, String toolTipText) {
        URL iconURL = getClass().getResource(iconPath);
        if (iconURL == null) {
            System.err.println("Could not find icon: " + iconPath);
            return new JToggleButton();
        }

        ImageIcon icon = new ImageIcon(iconURL);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(BUTTON_ICON_SIZE, BUTTON_ICON_SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
        JToggleButton button = new JToggleButton(icon);
        button.setPreferredSize(new Dimension(BUTTON_ICON_SIZE, BUTTON_ICON_SIZE));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setToolTipText(toolTipText);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setContentAreaFilled(true);
                    button.setOpaque(true);
                    button.setBackground(new Color(180, 180, 180));
                    button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setContentAreaFilled(false);
                    button.setOpaque(false);
                    button.setBackground(UIManager.getColor("control"));
                    button.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button.isSelected()) {
                    button.setContentAreaFilled(true);
                    button.setOpaque(true);
                    button.setBackground(new Color(200, 200, 200));
                    button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
                } else {
                    button.setContentAreaFilled(false);
                    button.setOpaque(false);
                    button.setBackground(UIManager.getColor("control"));
                    button.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        });

        if (button.isSelected()) {
            button.setContentAreaFilled(true);
            button.setOpaque(true);
            button.setBackground(new Color(200, 200, 200));
            button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        }

        return button;
    }

    private JButton createButton(String iconPath, String toolTipText) {
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
        button.setToolTipText(toolTipText);

        button.setModel(new DefaultButtonModel() {
            @Override
            public boolean isPressed() {
                return false;
            }

            @Override
            public boolean isArmed() {
                return false;
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(true);
                button.setOpaque(true);
                button.setBackground(new Color(180, 180, 180));
                button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setBackground(UIManager.getColor("control"));
                button.setBorder(BorderFactory.createEmptyBorder());
            }
        });
        return button;
    }
}
