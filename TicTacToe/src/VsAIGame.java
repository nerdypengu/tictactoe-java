import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VsAIGame extends JPanel {
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the drawing graphics

    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80); // Red #EF6950
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Define game objects
    private Board board; // the game board
    private State currentState; // the current state of the game
    private Seed currentPlayer; // the current player
    private JLabel statusBar; // for displaying status message
    private int playerNo = 1;

    /** Constructor to setup the UI and game components */
    public VsAIGame() {

        // This JPanel fires MouseEvent
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED && playerNo == 1) {
                        // Update cells[][] and return the new game state after the move
                        currentState = board.stepGame(currentPlayer, row, col);
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        playerNo++;

                    } else if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED && playerNo == 2) {
                        AIMove();
                    }
                }
                // Refresh the drawing canvas
                repaint(); // Callback paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        // Create a JButton
        JButton newGameButton = new JButton("New Game");
        newGameButton.setPreferredSize(new Dimension(100, 30));
        newGameButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerNo = 1;
                newGame(); // restart the game
                repaint(); // Callback paintComponent().
            }
        });

        // Create a JPanel to hold the status bar and the button
        JPanel statusBarPanel = new JPanel();
        statusBarPanel.setLayout(new BorderLayout());

        // Create a JPanel for the status bar with FlowLayout to align components to the
        // left
        JPanel statusBarContentPanel = new JPanel();
        statusBarContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add the status bar to the content panel
        statusBarContentPanel.add(statusBar);

        // Add the status bar content panel to the main panel at the WEST (left)
        // position
        statusBarPanel.add(statusBarContentPanel, BorderLayout.WEST);

        // Add the button panel to the main panel at the EAST (right) position
        statusBarPanel.add(newGameButton, BorderLayout.EAST);

        // Add the panel to the JFrame
        super.setLayout(new BorderLayout());
        super.add(statusBarPanel, BorderLayout.PAGE_END); // same as SOUTH
        super.setPreferredSize(new Dimension(450, 480));
        // account for statusBar in height
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Set up Game
        initGame();
        newGame();
    }

    // AI Movement
    public void AIMove() {
        double minValue = 0; // replace with your desired minimum value
        double maxValue = 3; // replace with your desired maximum value
        int p2Col = (int) minValue + (int) (Math.random() * (maxValue - minValue));
        int p2Row = (int) minValue + (int) (Math.random() * (maxValue - minValue));
        if (board.cells[p2Row][p2Col].content != Seed.NO_SEED) {
            AIMove();
        } else {
            currentState = board.stepGame(Seed.NOUGHT, p2Row, p2Col);
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            playerNo--;
        }
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board(); // allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS; // cross plays first
        currentState = State.PLAYING; // ready to play
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) { // Callback via repaint()
        super.paintComponent(g);
        setBackground(COLOR_BG); // set its background color

        board.paint(g); // ask the game board to paint itself

        // Print status-bar message
        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw!");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won!");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won!");
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tic Tac Toe");
        // Set the content-pane of the JFrame to an instance of main JPanel
        frame.setContentPane(new VsAIGame());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // center the application window
        frame.setVisible(true); // show itz

    }
}
