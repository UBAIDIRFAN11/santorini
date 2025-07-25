package santorini.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

public class DefaultButton {

    public enum Style {
        MAIN_MENU,
        MODAL_PROMPT,
        WIN_SCREEN
    }

    public static void styleButton(JButton button, Style style) {
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setFocusable(false);

        switch (style) {
            case MAIN_MENU:
                applyMainMenuStyle(button);
                break;
            case MODAL_PROMPT:
                applyModalPromptStyle(button);
                break;
            case WIN_SCREEN:
                applyWinScreenStyle(button);
                break;
        }
    }

    private static void applyMainMenuStyle(JButton button) {
        button.setFont(loadCustomFont(28f));
        Color base = new Color(216,178,82);
        Color border = new Color(28, 28, 28, 168);
        Color hover = new Color(255, 186, 43, 134);

        button.setBackground(base);
        button.setForeground(new Color(0, 0, 0, 205));
        button.setBorder(BorderFactory.createLineBorder(border, 4));
        button.setPreferredSize(new Dimension(300, 80));
        button.setMaximumSize(new Dimension(300, 80));

        addHover(button, base, hover);
    }

    private static void applyModalPromptStyle(JButton button) {
        button.setFont(loadCustomFont(15f));
        Color base = new Color(216, 178, 82);
        Color hover = new Color(255, 194, 133);

        button.setBackground(base);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        addHover(button, base, hover);
    }

    private static void addHover(JButton button, Color normal, Color hover) {
        button.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }
            @Override public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }


    private static void applyWinScreenStyle(JButton button) {
        button.setFont(loadCustomFont(20f));
        Color base = new Color(216,178,82);
        Color hover = new Color(255, 194, 133);

        button.setBackground(base);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createLineBorder(new Color(28, 28, 28, 168), 3));
        button.setPreferredSize(new Dimension(180, 50));

        addHover(button, base, hover);
    }


    private static Font loadCustomFont(float size) {
        try {
            InputStream is = DefaultButton.class.getResourceAsStream("/fonts/gomarice_tall_block.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size);
        }
    }
}
