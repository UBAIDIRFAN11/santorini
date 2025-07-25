package santorini.view;

import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JPanel {

    public MainMenuScreen(SantoriniApp app) {
        setLayout(new BorderLayout());

        JPanel bg = new JPanel() {
            private Image background = new ImageIcon(getClass().getResource("/screens/santorini_title_screen.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bg.setLayout(new BoxLayout(bg, BoxLayout.Y_AXIS));

        JButton play = new JButton("Play");
        JButton quit = new JButton("Quit");

        DefaultButton.styleButton(play, DefaultButton.Style.MAIN_MENU);
        DefaultButton.styleButton(quit, DefaultButton.Style.MAIN_MENU);


        play.addActionListener(e -> app.showGame());
        quit.addActionListener(e -> System.exit(0));

        // Horizontal button row
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(play);
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(quit);
        buttonPanel.add(Box.createHorizontalGlue());

        // Vertical spacing wrapper
        JPanel verticalContainer = new JPanel();
        verticalContainer.setOpaque(false);
        verticalContainer.setLayout(new BoxLayout(verticalContainer, BoxLayout.Y_AXIS));
        verticalContainer.add(Box.createVerticalGlue());                // Push to bottom
        verticalContainer.add(buttonPanel);
        verticalContainer.add(Box.createRigidArea(new Dimension(0, 20))); // Space from bottom

        bg.add(verticalContainer); // add everything to background label
        add(bg, BorderLayout.CENTER);
    }
}