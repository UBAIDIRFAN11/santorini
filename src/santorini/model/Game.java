package santorini.model;

import santorini.controller.*;
import santorini.model.gods.GodCardFactory;
import santorini.model.meters.*;
import santorini.model.loseconditions.LoseCondition;
import santorini.model.loseconditions.NatureMeterDepletedLoseCondition;
import santorini.model.loseconditions.StuckLoseCondition;
import santorini.model.timing.*;
import santorini.view.BoardUI;
import santorini.view.NatureMeterLabel;
import santorini.view.SantoriniApp;
import santorini.view.TimerLabel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game {

    private final SantoriniApp app;
    private final JPanel container;

    private WorkerPlacementStrategy placementStrategy = new RandomPlacementStrategy();
    private static final int DEFAULT_ROWS = 5;
    private static final int DEFAULT_COLS = 5;
    private BoardUI ui;
    private List<Player> players = new ArrayList<>();
    private Board board = new Board(DEFAULT_ROWS, DEFAULT_COLS);

    ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("workers/worker_girl_orange1_32x32.png"));

    public Game(SantoriniApp app, JPanel container) {
        this.app = app;
        this.container = container;
    }

    public void startGame() {

        players.clear();
        container.removeAll();
        container.setLayout(new BorderLayout());


        List<GameTimer> timers = List.of(
                new DefaultPlayerTimer(5), // each player starts with 5 mins on the clock
                new DefaultPlayerTimer(5)
        );

        // === Initialize Players with meter/s ===
        List<String> playerNames = List.of("Player 1", "Player 2");
        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i), i);
            MeterStrategy natureMeter = new NatureMeter(0, 100);
            player.setMeter(MeterType.NATURE, natureMeter);
            players.add(player);
        }

        // === Assign God Cards ===
        GodCardPool pool = new GodCardPool(GodCardFactory.getDefaultGodCards());
        GodCardAssigner assigner = new GodCardAssigner(pool);
        assigner.assignTo(players);

        // === Create Turn Label ===
        JLabel turnLabel = new JLabel("Turn: " + players.get(0).getName(), JLabel.CENTER);
        turnLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        turnLabel.setOpaque(true);
        turnLabel.setBackground(new Color(39,39,39));
        turnLabel.setForeground(new Color(216,178,82));

        try {
            Font pixelFont = Font.createFont(Font.TRUETYPE_FONT,
                            getClass().getClassLoader().getResourceAsStream("fonts/gomarice_tall_block.ttf"))
                    .deriveFont(Font.PLAIN, 22f);
            turnLabel.setFont(pixelFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            turnLabel.setFont(new Font("Monospaced", Font.BOLD, 22));
        }

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(39,39,39));
        TimerLabel timerLabel1 = new TimerLabel(timers.get(0), players.get(0).getName());
        TimerLabel timerLabel2 = new TimerLabel(timers.get(1), players.get(1).getName());
        topBar.add(timerLabel1, BorderLayout.WEST);
        topBar.add(turnLabel, BorderLayout.CENTER);
        topBar.add(timerLabel2, BorderLayout.EAST);
        container.add(topBar, BorderLayout.NORTH);

        // === Initialize Board and Place Workers ===
        board.initialize();
        placementStrategy.placeWorkers(board, players);

        // === Randomly Assign Worker Colors ===
        List<String> colors = new ArrayList<>(List.of("blue", "red"));
        Collections.shuffle(colors);
        players.get(0).setWorkerColor(colors.get(0));
        players.get(1).setWorkerColor(colors.get(1));

        // === Initialize Board UI ===
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(850, 500));
        layeredPane.setLayout(null);

        layeredPane.setPreferredSize(new Dimension(850, 500));

        ui = new BoardUI(board, layeredPane);
        ui.setBounds(0, 0, 850, 541);
        ui.setGodCardInfo(players.get(0), players.get(1));
        ui.setTimers(players, timers);
        ui.setEcoMeters(players);

        new TimeOutHandler(players, timers, app);

        // === Layout Center Panel ===
        layeredPane.add(ui, JLayeredPane.DEFAULT_LAYER);
        container.add(layeredPane, BorderLayout.CENTER);

        // === Finalize Frame ===
        container.revalidate();
        container.repaint();

        Map<Player, NatureMeterLabel> ecoLabels = ui.getEcoLabels();

        BuildObserver meterObserver = new MeterManager<>(MeterType.NATURE);
        BuildObserver ecometerUIObserver = new NatureMeterUIObserver(ecoLabels);
        List<BuildObserver> buildObservers = List.of(meterObserver, ecometerUIObserver);


        //lose conditions
        List<LoseCondition> loseConditions = List.of(
                new StuckLoseCondition(),
                new NatureMeterDepletedLoseCondition()
        );

        // === Start Turn/timer Manager ===
        TimerManager timerManager = new GameTimerManager(timers);
        TurnManager gm = new TurnManager(players, board, ui, timerManager, buildObservers, loseConditions, app);
        gm.setTurnLabel(turnLabel);
        gm.startTurn();
    }
}