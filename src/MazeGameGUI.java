import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MazeGameGUI extends JFrame {
    private GameController gameController;
    private MazePanel mazePanel;
    private JLabel timerLabel;
    private JLabel roundLabel;
    private Timer gameTimer;
    
    public MazeGameGUI() {
        gameController = new GameController();
        
        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        showMainMenu();
        
        setVisible(true);
    }
    
    private void showMainMenu() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.DARK_GRAY);
        
        JLabel titleLabel = new JLabel("MAZE GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.addActionListener(e -> startGame());
        
        JButton scoreboardButton = new JButton("Scoreboard");
        scoreboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreboardButton.setFont(new Font("Arial", Font.PLAIN, 24));
        scoreboardButton.addActionListener(e -> showScoreboard());
        
        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setFont(new Font("Arial", Font.PLAIN, 24));
        exitButton.addActionListener(e -> System.exit(0));
        
        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        menuPanel.add(startButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(scoreboardButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(exitButton);
        menuPanel.add(Box.createVerticalGlue());
        
        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void startGame() {
        String teamName = JOptionPane.showInputDialog(this, "Enter team name (max 20 characters):");
        
        if (teamName == null || teamName.trim().isEmpty()) {
            return;
        }
        
        if (teamName.length() > 20) {
            JOptionPane.showMessageDialog(this, "Team name must be 20 characters or less!");
            return;
        }
        
        // Check if team name already exists in database
        try {
            boolean exists = gameController.getScoreService().teamNameExists(teamName);
            if (exists) {
                JOptionPane.showMessageDialog(
                    this,
                    "Team name '" + teamName + "' already exists!\nPlease choose a different name.",
                    "Duplicate Team Name",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error checking team name: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        gameController.startGame(teamName);
        showGameScreen();
    }
    
    private void showGameScreen() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        // Top panel for info
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.setBackground(Color.LIGHT_GRAY);
        
        roundLabel = new JLabel("Round: " + getRoundName());
        roundLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel instructionsLabel = new JLabel("  |  P1: WASD (Blue)  |  P2: Arrows (Red)");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        infoPanel.add(roundLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        infoPanel.add(timerLabel);
        infoPanel.add(instructionsLabel);
        
        add(infoPanel, BorderLayout.NORTH);
        
        // Maze panel
        mazePanel = new MazePanel(gameController);
        add(mazePanel, BorderLayout.CENTER);
        
        // Add key listener with key press tracking to prevent multiple moves
        final boolean[] keyPressed = new boolean[1];
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!keyPressed[0]) {
                    keyPressed[0] = true;
                    handleKeyPress(e);
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed[0] = false;
            }
        });
        
        setFocusable(true);
        requestFocusInWindow();
        
        // Start game timer for UI updates
        gameTimer = new Timer(100, e -> updateGameState());
        gameTimer.start();
        
        revalidate();
        repaint();
    }
    
    private void handleKeyPress(KeyEvent e) {
        Direction direction = null;
        PlayerId playerId = null;
        
        switch (e.getKeyCode()) {
            // Player 1 (WASD)
            case KeyEvent.VK_W:
                direction = Direction.UP;
                playerId = PlayerId.PLAYER_1;
                break;
            case KeyEvent.VK_S:
                direction = Direction.DOWN;
                playerId = PlayerId.PLAYER_1;
                break;
            case KeyEvent.VK_A:
                direction = Direction.LEFT;
                playerId = PlayerId.PLAYER_1;
                break;
            case KeyEvent.VK_D:
                direction = Direction.RIGHT;
                playerId = PlayerId.PLAYER_1;
                break;
                
            // Player 2 (Arrows)
            case KeyEvent.VK_UP:
                direction = Direction.UP;
                playerId = PlayerId.PLAYER_2;
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.DOWN;
                playerId = PlayerId.PLAYER_2;
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.LEFT;
                playerId = PlayerId.PLAYER_2;
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.RIGHT;
                playerId = PlayerId.PLAYER_2;
                break;
        }
        
        if (direction != null && playerId != null) {
            gameController.onPlayerInput(playerId, direction);
            mazePanel.repaint();
        }
    }
    
    private void updateGameState() {
        // Update timer display
        long elapsed = gameController.getRoundManager().getTimer().getElapsedTime() / 1000;
        long timeLimit = gameController.getMaxTimeForDifficulty() / 1000;
        long remaining = timeLimit - elapsed;
        
        if (remaining < 0) remaining = 0;
        
        timerLabel.setText("Time: " + remaining + "s");
        
        // Check if time expired
        if (remaining <= 0 && !gameController.getRoundManager().isRoundComplete()) {
            gameTimer.stop();
            gameController.onTimerExpired();
        }
        
        GameState state = gameController.getGameState();
        
        if (state == GameState.ROUND_COMPLETE) {
            gameTimer.stop();
            showRoundComplete();
        } else if (state == GameState.GAME_OVER) {
            gameTimer.stop();
            
            if (gameController.getRoundManager().isRoundComplete() && 
                gameController.getCurrentDifficulty() == DifficultyLevel.HARD) {
                showYouWin();
            } else {
                showYouLose();
            }
        }
    }
    
    private void showRoundComplete() {
        // Get the difficulty that was JUST completed
        String completedRoundName = getRoundName();
        
        int result = JOptionPane.showConfirmDialog(
            this,
            "Round " + completedRoundName + " Complete!\nReady for next round?",
            "Round Complete",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (result == JOptionPane.OK_OPTION) {
            gameController.startNextRound();
            roundLabel.setText("Round: " + getRoundName());
            mazePanel.repaint();
            gameTimer.start();
        } else {
            showMainMenu();
        }
    }
    
    private void showYouWin() {
        long totalTime = gameController.calculateTotalTime() / 1000;
        
        JOptionPane.showMessageDialog(
            this,
            "Congratulations! You Win!\nTotal Time: " + totalTime + " seconds",
            "Victory!",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Save score (team name was already validated before game started)
        if (gameController.getTeam() != null) {
            gameController.saveTeamScore(gameController.getTeam().getTeamName());
        }
        
        // Show scoreboard BEFORE returning to main menu
        showScoreboard();
    }
    
    private void showYouLose() {
        JOptionPane.showMessageDialog(
            this,
            "Time's up! You Lose!",
            "Game Over",
            JOptionPane.ERROR_MESSAGE
        );
        
        showMainMenu();
    }
    
    private void showScoreboard() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setBackground(Color.DARK_GRAY);
        
        JLabel titleLabel = new JLabel("SCOREBOARD");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        scorePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        scorePanel.add(titleLabel);
        scorePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        java.util.List<ScoreRecord> scores = gameController.getTopScores();
        
        for (int i = 0; i < scores.size(); i++) {
            ScoreRecord record = scores.get(i);
            String text = String.format("%d. %s - %d seconds", 
                i + 1, record.getTeamName(), record.getTotalTime() / 1000);
            
            JLabel scoreLabel = new JLabel(text);
            scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            scorePanel.add(scoreLabel);
            scorePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        JButton backButton = new JButton("Back to Menu");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener(e -> showMainMenu());
        
        scorePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        scorePanel.add(backButton);
        
        add(scorePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private String getRoundName() {
        DifficultyLevel diff = gameController.getCurrentDifficulty();
        switch (diff) {
            case EASY: return "Easy";
            case MEDIUM: return "Medium";
            case HARD: return "Hard";
            default: return "Unknown";
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeGameGUI());
    }
}
