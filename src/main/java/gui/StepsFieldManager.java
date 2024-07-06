package gui;

import model.DijkstraState;
import static gui.Settings.*;

import javax.swing.*;
import java.awt.*;

public class StepsFieldManager {
    private final App app;
    private final JTextArea stepsField;

    public StepsFieldManager(App app) {
        this.app = app;

        stepsField = new JTextArea();
        stepsField.setBackground(STEPS_FIELD_COLOR);
        stepsField.setEditable(false);
        stepsField.setLineWrap(true);
        stepsField.setWrapStyleWord(true);

        stepsField.setFont(new Font("Arial", Font.PLAIN, STEPS_FIELD_FONT));

        JScrollPane scrollPane = new JScrollPane(stepsField);
        int width = 200, height = 500;
        scrollPane.setPreferredSize(new Dimension(width, height));
        scrollPane.setMinimumSize(new Dimension(width, height));
        scrollPane.setMaximumSize(new Dimension(width, height));

        JPanel stepsPanel = new JPanel();
        stepsPanel.setLayout(new BorderLayout());
        stepsPanel.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = app.getGBC();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        app.add(stepsPanel, gbc);
    }

    public void display() {
        clear();
        DijkstraState state = app.getAlgorithmManager().getState();
        for (String step : state.getSteps()) {
            stepsField.append(step);
        }
    }

    public void clear() {
        stepsField.setText("");
    }
}
