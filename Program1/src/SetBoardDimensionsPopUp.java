import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SetBoardDimensionsPopUp extends JFrame {

    // Create new JFrame for pop up settings menu
    JFrame setupMenu = new JFrame("Bomb Specifications");

    // Create panels for options
    JPanel difficulty, userInput, exit;

    // USer input to customize their game
    JTextField numRows, numColumns, numBombs;

    // Labels for user input
    JLabel rows, columns, bombs;

    // Buttons to quit user customization pop up
    JButton save, cancel, beginner, intermediate, expert;

    // Integers to store user input for number of rows, columns, and bombs
    int rowCount, columnCount, bombCount, gameDifficulty, finalSize, finalBombs;

    //Font for text
    private Font font = new Font("Arial", Font.PLAIN, 20);

    public SetBoardDimensionsPopUp() {

    }

    public SetBoardDimensionsPopUp(int r, int b) {

        // Initialize text boxes for user input
        numRows = new JTextField();
        numRows.setFont(font);
        numColumns = new JTextField();
        numColumns.setFont(font);
        numBombs = new JTextField();
        numBombs.setFont(font);

        // Initialize button to save user custom board or cancel their changes
        save = new JButton("Save");
        save.setFont(font);
        cancel = new JButton("Cancel");
        cancel.setFont(font);
        beginner = new JButton("Beginner");
        beginner.setFont(font);
        intermediate = new JButton("Intermediate");
        intermediate.setFont(font);
        expert = new JButton("Expert");
        expert.setFont(font);

        // Save the user specified game size and bomb number
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == save) {
                    rowCount = Integer.parseInt(numRows.getText());
                    columnCount = rowCount; // Set column and row count equal because board must be a square
                    bombCount = Integer.parseInt(numBombs.getText());
                    if (gameDifficulty != 1 && gameDifficulty != 2 && gameDifficulty != 3) {
                        gameDifficulty = 4;
                    }
                    if (gameDifficulty == 1) { // Beginner is a 4x4 board with 4 bombs
                        finalSize = 4;
                        finalBombs = 4;
                    }
                    if (gameDifficulty == 2) { // Intermediate is a 8x8 board with 15 bombs
                        finalSize = 8;
                        finalBombs = 15;
                    }
                    if (gameDifficulty == 3) { // Beginner is a 12x12 board with 40 bombs
                        finalSize = 12;
                        finalBombs = 40;
                    }
                    if (gameDifficulty == 4) { // User can customize board and number of bomb
                        finalSize = checkGridSize(rowCount);
                        finalBombs = checkNumBombs(bombCount, finalSize);
                    }
                    new Bombs(finalSize, finalBombs); // Create a new game based on specified grid size and number of bombs
                    setupMenu.dispose();
                    setupMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });

        // Do not save user changes
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cancel) {
                    new Bombs(r, b);
                    setupMenu.dispose();
                    setupMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
            }
        });

        beginner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numRows.setText("4");
                numColumns.setText("4");
                numBombs.setText("4");
                gameDifficulty = 1;
            }
        });

        intermediate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numRows.setText("8");
                numColumns.setText("8");
                numBombs.setText("15");
                gameDifficulty = 2;
            }
        });

        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numRows.setText("12");
                numColumns.setText("12");
                numBombs.setText("40");
                gameDifficulty = 3;
            }
        });

        rows = new JLabel("Rows: ");
        rows.setFont(font);
        columns = new JLabel("Columns: ");
        columns.setFont(font);
        bombs = new JLabel("Bombs: ");
        bombs.setFont(font);

        // Add everything to the JFrame
        difficulty = new JPanel();
        userInput = new JPanel();
        exit = new JPanel();
        difficulty.setLayout(new GridLayout(0, 3));
        difficulty.add(beginner);
        difficulty.add(intermediate);
        difficulty.add(expert);
        userInput.setLayout(new GridLayout(3, 2));
        userInput.add(rows);
        userInput.add(numRows);
        userInput.add(columns);
        userInput.add(numColumns);
        userInput.add(bombs);
        userInput.add(numBombs);
        exit.setLayout(new GridLayout(0, 2, 2, 10));
        exit.add(save);
        exit.add(cancel);
        setupMenu.setSize(new Dimension(500, 750));
        setupMenu.add(difficulty, BorderLayout.NORTH);
        setupMenu.add(userInput, BorderLayout.CENTER);
        setupMenu.add(exit, BorderLayout.SOUTH);
        setupMenu.pack();
        setupMenu.setVisible(true);
    }

    public int checkGridSize(int size) {
        if (size < 3) { // minimum grid size is a 3x3
            return 3;
        } else if (size > 12) {
            return 12; // maximum grid size is a 12x12
        } else {
            return size;
        }
    }

    public int checkNumBombs(int numBombs, int size) {
        if (numBombs < 2) { // minimum number of bombs is 2 bombs
            return 2;
        } else if (numBombs > (int) ((size * size) / 2)) { // maximum number of bombs is one half the total number of spaces
            return (int) ((size * size) / 2);
        } else {
            return numBombs;
        }
    }

    public int getRows() {
        return rowCount;
    }

    public int getColumns() {
        return columnCount;
    }

    public int getBombs() {
        return bombCount;
    }
}