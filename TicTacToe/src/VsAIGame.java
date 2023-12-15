/* KELOMPOK 10
 * Eugenia Indrawan - 5026221020
 * Ashila Mahdiyyah - 5026221148
 * Razi Alvaro Arman - 5026221168
 * 
*/

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

    public VsAIGame() {

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED && playerNo == 1) {

                        currentState = board.stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        playerNo++;
                        if (currentState == State.PLAYING) {
                            AIMove();
                        }

                    }

                }
                repaint();
            }
        });

        // Setup the status bar
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        JButton newGameButton = new JButton("New Game");
        newGameButton.setPreferredSize(new Dimension(100, 30));
        newGameButton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerNo = 1;
                newGame();
                repaint();
            }
        });

        JPanel statusBarPanel = new JPanel();
        statusBarPanel.setLayout(new BorderLayout());

        JPanel statusBarContentPanel = new JPanel();
        statusBarContentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        statusBarContentPanel.add(statusBar);

        statusBarPanel.add(statusBarContentPanel, BorderLayout.WEST);

        statusBarPanel.add(newGameButton, BorderLayout.EAST);

        super.setLayout(new BorderLayout());
        super.add(statusBarPanel, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(450, 480));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        initGame();
        newGame();
    }

    // AI Movement
    public void AIMove() {
        double minValue = 0;
        double maxValue = 3;
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

    public void initGame() {
        board = new Board();
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);

        board.paint(g);

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
        frame.setContentPane(new VsAIGame());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
