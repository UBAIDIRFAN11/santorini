package santorini.view;

import santorini.model.Game;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private Game game;

    public GameScreen(SantoriniApp app) {
        this.setLayout(new BorderLayout());
        game = new Game(app, this);
    }

    public void startGame() {
        game.startGame();
        this.revalidate();
        this.repaint();
    }
}