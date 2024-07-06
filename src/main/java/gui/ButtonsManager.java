package gui;

import static gui.Settings.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

public class ButtonsManager {
    public JButton createButton(String iconPath, String toolTipText) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setToolTipText(toolTipText);

        button.setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                g.setColor(PRESSED_BUTTON_COLOR);
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(true);
                button.setOpaque(true);
                button.setBackground(MOUSE_ENTERED_BACKGROUND_COLOR);
                button.setBorder(BorderFactory.createLineBorder(MOUSE_ENTERED_BORDER_COLOR));
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

    public JToggleButton createToggleButton(String iconPath, String toolTipText, ButtonGroup toggleGroup) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);
        JToggleButton button = new JToggleButton(icon);
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setToolTipText(toolTipText);
        toggleGroup.add(button);

        button.setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                if (b.isContentAreaFilled()) {
                    g.setColor(PRESSED_BUTTON_COLOR);
                    g.fillRect(0, 0, b.getWidth(), b.getHeight());
                }
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!button.isSelected()) {
                    button.setContentAreaFilled(true);
                    button.setOpaque(true);
                    button.setBackground(MOUSE_ENTERED_BACKGROUND_COLOR);
                    button.setBorder(BorderFactory.createLineBorder(MOUSE_ENTERED_BORDER_COLOR));
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

        button.addActionListener(e -> {
            if (button.isSelected()) {
                button.setContentAreaFilled(true);
                button.setOpaque(true);
                button.setBackground(PRESSED_BUTTON_COLOR);
                button.setBorder(BorderFactory.createLineBorder(MOUSE_ENTERED_BORDER_COLOR));
            } else {
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setBackground(UIManager.getColor("control"));
                button.setBorder(BorderFactory.createEmptyBorder());
            }

            for (AbstractButton btn : Collections.list(toggleGroup.getElements())) {
                if (btn != button) {
                    btn.setContentAreaFilled(false);
                    btn.setOpaque(false);
                    btn.setBackground(UIManager.getColor("control"));
                    btn.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        });
        return button;
    }
}
