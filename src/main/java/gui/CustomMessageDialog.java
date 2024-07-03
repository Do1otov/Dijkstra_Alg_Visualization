package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.KeyEvent;

public class CustomMessageDialog {
    public static void showMessageDialog(Component parent, String title, String message, Integer width, Integer height) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), title, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(GUISettings.APP_COLOR);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(GUISettings.APP_COLOR);
        messagePanel.add(new JLabel(message));
        dialog.add(messagePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(GUISettings.APP_COLOR);

        JButton okButton = createStyledButton("OK");
        okButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.getRootPane().setDefaultButton(okButton);
        dialog.getRootPane().registerKeyboardAction(e -> dialog.dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        dialog.getRootPane().registerKeyboardAction(e -> okButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

        dialog.setSize(width, height);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
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
