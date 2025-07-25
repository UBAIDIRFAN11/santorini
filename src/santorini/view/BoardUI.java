package santorini.view;

import santorini.model.*;
import santorini.controller.TurnManager;
import santorini.model.Player;
import santorini.model.timing.GameTimer;
import santorini.view.popups.Toast;

import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Map;

public class BoardUI extends JPanel {

    private final ViewCell[][] view;
    private TurnManager tm;
    private GodCardBackgroundLabel player1Panel;
    private GodCardBackgroundLabel player2Panel;
    private TimerLabel timerLabel1;
    private TimerLabel timerLabel2;
    private NatureMeterLabel natureMeterLabel1;
    private NatureMeterLabel natureMeterLabel2;
    private Map<Player, NatureMeterLabel> ecoLabels = new java.util.HashMap<>();
    private JPanel centerPanel;
    private JLayeredPane layeredPane;
    private JPanel interactionBlocker;

    public BoardUI(Board board, JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
        interactionBlocker = new JPanel();
        interactionBlocker.setBackground(new Color(0, 0, 0, 0)); // Fully transparent
        interactionBlocker.setOpaque(false);
        interactionBlocker.setVisible(true);

        interactionBlocker.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                e.consume(); // actively block propagation
            }
        });
        interactionBlocker.addMouseMotionListener(new MouseMotionAdapter() {
            @Override public void mouseDragged(MouseEvent e) {
                e.consume();
            }
        });

        interactionBlocker.addMouseMotionListener(new MouseMotionAdapter() {});
        interactionBlocker.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());



        int r = board.getRows(), c = board.getCols();
        view = new ViewCell[r][c];

        setLayout(new BorderLayout());
        setBackground(new Color(39, 39, 39));

        JPanel gridPanel = new JPanel(new GridLayout(r, c));
        gridPanel.setBackground(new Color(39, 39, 39));
        gridPanel.setBorder(BorderFactory.createLineBorder(new Color(39, 39, 39), 4));

        int flowerRow = (int) (Math.random() * r);
        int flowerCol = (int) (Math.random() * c);

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                BufferedImage tile = (i == flowerRow && j == flowerCol) ? ViewCell.grassFlower : ViewCell.ground;
                ViewCell vc = new ViewCell(board.getCell(i, j), tile);
                view[i][j] = vc;
                final int rr = i, cc = j;
                vc.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (tm != null) tm.handleClick(rr, cc);
                    }
                });
                gridPanel.add(vc);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        player1Panel = new GodCardBackgroundLabel();
        player1Panel.setBackgroundImage("screens/default_bg.jpg");
        styleGodCardPanel(player1Panel);

        player2Panel = new GodCardBackgroundLabel();
        player2Panel.setBackgroundImage("screens/default_bg.jpg");
        styleGodCardPanel(player2Panel);

        add(player1Panel, BorderLayout.WEST);
        add(player2Panel, BorderLayout.EAST);
    }

    private void styleGodCardPanel(JLabel panel) {
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 350));
        panel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setVerticalAlignment(SwingConstants.BOTTOM);
        panel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.setBorder(BorderFactory.createLineBorder(new Color(39, 39, 39), 6));
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return "";
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public void setGodCardInfo(Player p1, Player p2) {
        String god1 = p1.getGodCard().getName();
        player1Panel.setText("<html><div style='text-align:center; color: #000000;'>" +
                "P1: " + god1 + "</div></html>");
        player1Panel.setToolTipText(p1.getGodCard().getDescription());
        player1Panel.setBackgroundImage("gods/" + god1.toLowerCase() + ".jpg");

        String god2 = p2.getGodCard().getName();
        player2Panel.setText("<html><div style='text-align:center; color: #000000;'>" +
                "P2: " + god2 + "</div></html>");
        player2Panel.setToolTipText(p2.getGodCard().getDescription());
        player2Panel.setBackgroundImage("gods/" + god2.toLowerCase() + ".jpg");
    }

    public void attachTurnManager(TurnManager t) {
        tm = t;
    }

    public void highlight(int r, int c, boolean f) {
        view[r][c].setHighlighted(f);
    }

    public void refresh() {
        for (var row : view)
            for (var vc : row)
                vc.repaint();
    }

    public void setTimers(List<Player> players, List<GameTimer> timers) {
        timerLabel1 = new TimerLabel(timers.get(0), players.get(0).getName());
        timerLabel2 = new TimerLabel(timers.get(1), players.get(1).getName());
    }

    public void setEcoMeters(List<Player> players) {
        centerPanel = new JPanel();
        centerPanel.setOpaque(false);

        natureMeterLabel1 = new NatureMeterLabel(players.get(0).getName());
        natureMeterLabel2 = new NatureMeterLabel(players.get(1).getName());

        Dimension meterSize = new Dimension(190, 10);
        natureMeterLabel1.setPreferredSize(meterSize);
        natureMeterLabel2.setPreferredSize(meterSize);

        ecoLabels.put(players.get(0), natureMeterLabel1);
        ecoLabels.put(players.get(1), natureMeterLabel2);

        JPanel ecoPanel = new JPanel(new GridLayout(1, 1));
        ecoPanel.setBackground(new Color(39, 39, 39));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(natureMeterLabel1);

        centerPanel = new JPanel();
        centerPanel.setOpaque(false);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(natureMeterLabel2);

        ecoPanel.add(leftPanel);
        ecoPanel.add(centerPanel);
        ecoPanel.add(rightPanel);

        this.add(ecoPanel, BorderLayout.SOUTH);
    }

    public Map<Player, NatureMeterLabel> getEcoLabels() {
        return ecoLabels;
    }

    public void updateWorkerAnimationState(Player currentPlayer, Worker selectedWorker) {
        for (ViewCell[] row : view) {
            for (ViewCell vc : row) {
                Worker w = vc.getModel().getWorker();
                if (w != null) {
                    boolean shouldAnimate =
                            currentPlayer != null &&
                                    w.getOwner().equals(currentPlayer) &&
                                    (selectedWorker == null || w == selectedWorker);
                    vc.setAnimationActive(shouldAnimate);
                } else {
                    vc.setAnimationActive(false);
                }
            }
        }
    }

    public void showToast(String message) {
        Toast toast = new Toast(message);
        centerPanel.removeAll();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.add(toast);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private Font loadCustomFont(float size) {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/gomarice_tall_block.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.BOLD, (int) size);
        }
    }

    public void showGodPowerPrompt(String message, Runnable onYes, Runnable onNo) {
        JPanel modal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(39, 39, 39, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
            }
        };
        modal.setLayout(null);
        modal.setOpaque(false);
        modal.setBorder(BorderFactory.createLineBorder(new Color(216,178,82), 2));
        modal.setSize(300, 180);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(loadCustomFont(18f));
        label.setForeground(new Color(216,178,82));
        label.setBounds(25, 15, 250, 30);
        modal.add(label);

        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        DefaultButton.styleButton(yes, DefaultButton.Style.MODAL_PROMPT);
        DefaultButton.styleButton(no, DefaultButton.Style.MODAL_PROMPT);

        yes.setBounds(100, 60, 100, 30);
        no.setBounds(100, 100, 100, 30);
        modal.add(yes);
        modal.add(no);

        JPanel timerBar = new JPanel();
        timerBar.setBackground(new Color(216,178,82));
        int barHeight = 8;
        int fullWidth = 300;
        timerBar.setBounds(0, 172, fullWidth, barHeight);
        modal.add(timerBar);

        yes.addActionListener(e -> {
            layeredPane.remove(modal);
            layeredPane.remove(interactionBlocker);
            layeredPane.repaint();
            onYes.run();
        });
        no.addActionListener(e -> {
            layeredPane.remove(modal);
            layeredPane.remove(interactionBlocker);
            layeredPane.repaint();
            onNo.run();
        });

        SwingUtilities.invokeLater(() -> {
            // Blocker should be below the modal
            interactionBlocker.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
            layeredPane.add(interactionBlocker, JLayeredPane.PALETTE_LAYER);

            // Center modal after layout is complete
            int x = (layeredPane.getWidth() - modal.getWidth()) / 2;
            int y = (layeredPane.getHeight() - modal.getHeight()) / 2;
            modal.setBounds(x, y, 300, 180);

            layeredPane.add(modal, JLayeredPane.MODAL_LAYER);
            layeredPane.revalidate();
            layeredPane.repaint();
        });

        final int playerTimeLimit = 10000;
        final int animationDuration = 9200;
        final int interval = 40;
        final int steps = animationDuration / interval;
        final int[] currentStep = {0};

        Timer shrinkTimer = new Timer(interval, null);
        shrinkTimer.addActionListener(e -> {
            currentStep[0]++;
            int newWidth = fullWidth - (fullWidth * currentStep[0] / steps);
            timerBar.setBounds(0, 172, Math.max(0, newWidth), barHeight);
            modal.repaint();
        });
        shrinkTimer.setRepeats(true);
        shrinkTimer.start();

        Timer dismissTimer = new Timer(playerTimeLimit, e -> {
            shrinkTimer.stop();
            timerBar.setBounds(0, 172, 0, barHeight);
            modal.repaint();
            if (modal.getParent() != null) {
                layeredPane.remove(modal);
                layeredPane.remove(interactionBlocker);
                layeredPane.repaint();
                onNo.run();
            }
        });
        dismissTimer.setRepeats(false);
        dismissTimer.start();
    }
}