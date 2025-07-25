package santorini.view;

import javax.swing.*;
import java.awt.*;

public class SantoriniApp {

    private JFrame frame;
    private CardLayout layout;
    private JPanel container;

    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;
    private WinScreenPanel winScreen;

    public SantoriniApp() {
        frame = new JFrame("Santorini");
        frame.setSize(850, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        layout = new CardLayout();
        container = new JPanel(layout);

        mainMenuScreen = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
        winScreen = new WinScreenPanel(this);

        container.add(mainMenuScreen, "menu");
        container.add(gameScreen, "game");
        container.add(winScreen, "win");

        frame.setContentPane(container);
        frame.setVisible(true);

        showMenu();
    }

    public void showMenu() {
        layout.show(container, "menu");
    }

    public void showGame() {
        gameScreen.startGame(); // resets or starts fresh
        layout.show(container, "game");
    }

    public void showWin(String winnerName) {
        winScreen.setWinner(winnerName);
        layout.show(container, "win");
        winScreen.revalidate();
        winScreen.repaint();
    }
}