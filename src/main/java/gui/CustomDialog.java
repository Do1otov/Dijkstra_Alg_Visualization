package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class CustomDialog {
    public static String showInputDialog(Component parent, String title, String message) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(GUISettings.APP_COLOR);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(GUISettings.APP_COLOR);
        messagePanel.add(new JLabel(message));
        dialog.add(messagePanel, BorderLayout.NORTH);

        JTextField textField = new JTextField(10);
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(GUISettings.APP_COLOR);
        inputPanel.add(textField);
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(GUISettings.APP_COLOR);

        JButton okButton = createStyledButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        JButton cancelButton = createStyledButton("Cancel");
        cancelButton.addActionListener(e -> {
            textField.setText(null);
            dialog.dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(250, 125);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return textField.getText();
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(80, 25));
        button.setBackground(GUISettings.CONTROL_PANEL_COLOR);
        button.setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                g.setColor(GUISettings.PRESSED_BUTTON_COLOR);
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createLineBorder(GUISettings.MOUSE_ENTERED_BORDER_COLOR));
                button.setBackground(GUISettings.MOUSE_ENTERED_BACKGROUND_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setBackground(GUISettings.CONTROL_PANEL_COLOR);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(GUISettings.PRESSED_BUTTON_COLOR);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(GUISettings.CONTROL_PANEL_COLOR);
            }
        });
        return button;
    }
}
