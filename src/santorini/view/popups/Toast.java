package santorini.view.popups;

import javax.swing.*;
import java.awt.*;

// === Toast Message ===
public class Toast extends JPanel {
    public Toast(String message) {
        setOpaque(false);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>");
        label.setForeground(Color.ORANGE);
        label.setBackground(new Color(39,39,39));
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(loadPixelFont());

        add(label, BorderLayout.CENTER);

        Timer timer = new Timer(2500, e -> {
            Container parent = getParent();
            if (parent != null) {
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private Font loadPixelFont() {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                            ClassLoader.getSystemResourceAsStream("fonts/edundot.ttf"))
                    .deriveFont(Font.PLAIN, 12f);
        } catch (Exception e) {
            e.printStackTrace(); // So you can see if it fails again
            return new Font("Monospaced", Font.PLAIN, 12); // fallback
        }
    }
}