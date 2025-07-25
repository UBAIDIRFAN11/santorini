package santorini.view;

import javax.swing.*;
import java.awt.*;

public class WinScreenPanel extends JPanel {

    private JLabel label;

    private final Image backgroundImage =
            new ImageIcon(getClass().getClassLoader().getResource("screens/game_over.png")).getImage();

    public WinScreenPanel(SantoriniApp app) {
        setLayout(new BorderLayout());

        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 32));
        label.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton restart = new JButton("Restart");
        JButton exit = new JButton("Exit");

        restart.addActionListener(e -> app.showGame()); // restart game
        exit.addActionListener(e -> System.exit(0));

        DefaultButton.styleButton(restart, DefaultButton.Style.WIN_SCREEN);
        DefaultButton.styleButton(exit, DefaultButton.Style.WIN_SCREEN);

        buttonPanel.add(restart);
        buttonPanel.add(exit);

        add(label, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setWinner(String name) {
        label.setText(name + " wins!");
    }
}
