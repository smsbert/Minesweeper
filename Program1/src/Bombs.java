import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// All files started from and based on the template provided: memory-game-template
public class Bombs extends JFrame implements ActionListener {

    // Game board
    private SetBoard gameBoard;
    private JPanel boardView;

    // Number of rows, columns, and bombs for the game
    int rows, columns, bombs;

    // MenuBar Items
    private JMenuBar menuOptions = new JMenuBar();
    private JMenu game;
    private JMenuItem newGame, gameSetup, exitGame, help;

    // Timer for the game
    private JTextField time, remainingBombs;

    // Timer for the game
    Timer timer;

    // Panel for the timer and number of remaining bombs counter
    private JPanel timePanel = new JPanel();

    // Smiley face when you win frowny face when you lose
    private JButton face;

    // Game timer: will be configured to trigger an event when user interacts with game board
    private Timer gameEvent;

    //Font for text
    private Font font = new Font("Arial", Font.PLAIN, 20);

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();


    // Initial game or not
    int seconds = 0, minutes = 0;

    Bombs() {
        new Bombs(4, 4); // Initialize first game to easy 4x4 with 4 bombs
    }


    Bombs(int size, int numBombs) {

        super("Minesweeper");

        // Get Dimensions
        rows = size;
        columns = size;
        bombs = numBombs;

        // Initialize menu bar options
        game = new JMenu("Game");
        game.setFont(font);
        newGame = new JMenuItem("New");
        newGame.setFont(font);
        gameSetup = new JMenuItem("Setup");
        gameSetup.setFont(font);
        exitGame = new JMenuItem("Exit");
        exitGame.setFont(font);
        help = new JMenuItem("Help");
        help.setFont(font);
        game.add(newGame);
        game.add(gameSetup);
        game.addSeparator();
        game.add(exitGame);

        // Creates a new game of the same size as it previously was
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Bombs(rows, bombs);
            }
        });

        // Shows pop up menu to set the game board size as beginner, intermediate, expert, or a custom game
        gameSetup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetBoardDimensionsPopUp s = new SetBoardDimensionsPopUp(rows, bombs);
                dispose();
            }
        });

        // Exits the game
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exitGame) {
                    System.exit(0);
                }
            }
        });

        // Menu option adds instructions on how to play the game
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame helpPopup = new JFrame();
                JOptionPane instructions = new JOptionPane();
                instructions.setMessage("How to Play: \n* Each tile contains an empty space, a number hint, or a bomb. \n" +
                        "* If you click on a bomb, the game is over and you lose. \n" +
                        "* If you click on number tile, the number will be revealed. The number indicates the amount of bombs " +
                        "\n  in the number tiles neighboring squares.\n" +
                        "* If you click an empty tile all empty tiles in the are will be cleared and all neighboring number hints will be revealed. \n" +
                        "* A tile can have 8, 5, or 3 neighboring squares depending on its location. \n" +
                        "* The neighboring squares are the squares North, South, East, West, NorthEast, SouthEast, SouthWest, and Northwest " +
                        "\n  of the original square clicked on.");
                helpPopup.setSize(new Dimension(1000, 750));
                helpPopup.setVisible(true);
                helpPopup.setFont(new Font("Arial", Font.PLAIN, 40));
                instructions.setFont(new Font("Arial", Font.PLAIN, 40));
                instructions.setVisible(true);
                helpPopup.add(instructions);
                helpPopup.setVisible(true);
            }
        });

        // Initialize timer, smiley face icon to indicate game win or loss and remaining bomb count
        /*remainingBombs = new JTextField(bombs);
        remainingBombs.setFont(new Font("DigtalFont.TTF", Font.BOLD, 75));
        remainingBombs.setBackground(Color.BLACK);
        remainingBombs.setForeground(Color.RED);
        remainingBombs.setBorder(BorderFactory.createLoweredBevelBorder());
        */
        String imgPath = "pic/smiley.jpg";
        ImageIcon smile = new ImageIcon(loader.getResource(imgPath));
        face = new JButton(smile);
        face.setPreferredSize(new Dimension(40, 40));

        boardView = new JPanel(); // used to hold game board

        // Initialize game play
        gameEvent = new Timer(2000, this);

        // Add menu bar and game to the frame
        menuOptions.add(game);
        menuOptions.add(help);
        setJMenuBar(menuOptions);

        time = new JTextField(minutes + ":" + seconds);
        time.setColumns(4);
        time.setFont(new Font("DigtalFont.TTF", Font.BOLD, 40));
        time.setBackground(Color.BLACK);
        time.setForeground(Color.RED);
        time.setBorder(BorderFactory.createLoweredBevelBorder());

        // Add timer, remaining bombs count, and smiley face to the panel
        //timePanel.add(remainingBombs, BorderLayout.WEST);
        timePanel.add(face, BorderLayout.WEST);
        timePanel.add(time, BorderLayout.EAST);

        add(timePanel, BorderLayout.NORTH);
        boardView.setLayout(new GridLayout(rows, columns, 2, 0));
        gameBoard = new SetBoard(rows, bombs, this);
        gameBoard.fillBoardView(boardView, rows, bombs);
        add(boardView, BorderLayout.SOUTH);
        setSize(new Dimension(500, 750));
        face.setIcon(smile);
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent pause) {
                if (seconds == 60) {
                    minutes += 01;
                    seconds = 00;
                } else {
                    seconds++;
                }
                if (minutes < 10 && seconds < 10) {
                    time.setText("0" + minutes + ":0" + seconds);
                } else if (minutes < 10) {
                    time.setText("0" + minutes + ":" + seconds);
                } else if (seconds < 10) {
                    time.setText(minutes + ":0" + seconds);
                }
            }
        });
        pack();
        setVisible(true);
        timer.start();
        gameEvent.start();
    }

    public void actionPerformed(ActionEvent e) {
        GameTiles currentTile = (GameTiles) e.getSource();
        if (currentTile.id() == 10) {
            String imgPath = "pic/incorrectBomb.jpg";
            ImageIcon badBomb = new ImageIcon(loader.getResource(imgPath));
            currentTile.setFrontImage(badBomb);
            loser();
        } else if (currentTile.id() == 1 || currentTile.id() == 2 || currentTile.id() == 3 || currentTile.id() == 4 ||
                currentTile.id() == 5 || currentTile.id() == 6 || currentTile.id() == 7 || currentTile.id() == 8) {
            currentTile.showFront();
            currentTile.setRevealed(true);
            winner();
        } else if (currentTile.id() == 9) {
            gameBoard.clearEmptyTiles(currentTile);
            winner();
        }
    }

    public void loser() {
        timer.stop();
        String imgPath = "pic/frown.jpg";
        ImageIcon frown = new ImageIcon(loader.getResource(imgPath));
        face.setIcon(frown);
        gameBoard.lostGame(rows);
        JFrame loserFrame = new JFrame();
        JLabel loserNote = new JLabel("You lost, better luck next time.");
        loserNote.setFont(font);
        loserFrame.add(loserNote);
        loserFrame.setPreferredSize(new Dimension(350, 250));
        loserFrame.setVisible(true);
        loserFrame.pack();
    }

    public void winner() {
        int win = gameBoard.checkForWinner(rows, bombs);
        if (win == 1) {
            timer.stop();
            JFrame winnerFrame = new JFrame();
            JLabel winnerNote = new JLabel("Congrats you won!");
            winnerNote.setFont(font);
            winnerFrame.add(winnerNote);
            winnerFrame.setPreferredSize(new Dimension(350, 250));
            winnerFrame.setVisible(true);
            winnerFrame.pack();
        }
    }

    // Main program executes the Minesweeper game
    public static void main(String args[]) {
        Bombs M = new Bombs();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
