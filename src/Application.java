import javax.swing.*;
import santorini.view.SantoriniApp;

/**
 * The main entry point for launching the Santorini game application.
 * <p>
 * This class initializes the main menu using the Swing event dispatch thread.
 *
 * Author: FIT3077 Team Santorinians (005) (Sprint 2 implementation)
 */
public class Application {

    /**
     * Launches the Santorini game
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SantoriniApp::new);
    }
}