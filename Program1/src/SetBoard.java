import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ActionListener;

public class SetBoard {

    // Grid to hold game tiles
    private GameTiles tiles[][];

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    public SetBoard(int size, int bombs, ActionListener AL) {
        tiles = new GameTiles[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                String imgPath = "pic/clearedTile.jpg";
                ImageIcon img = new ImageIcon(loader.getResource(imgPath));

                tiles[i][j] = new GameTiles(img);
                tiles[i][j].setPreferredSize(new Dimension(50, 50));
                tiles[i][j].setID(9);
                tiles[i][j].setRevealed(false);
                tiles[i][j].hideFront();
                tiles[i][j].addActionListener(AL);
            }
        }
    }

    // creates a game board
    public void fillBoardView(JPanel view, int size, int bombs) {
        setMines(bombs);
        setNumberHints();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                view.add(tiles[i][j]);
                view.setSize(new DimensionUIResource(1000, 1000));
            }
        }
        view.repaint();
    }

    // resets board
    public void resetBoard() {
        //new SetBoard(size, bombs, AL);
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j].hideFront();
            }
        }
    }

    // Determines if the current neighbor tile is a bomb
    public int bombCount(int x, int y) {
        if (x < tiles.length && x >= 0 && y < tiles.length && y >= 0) {
            if (tiles[x][y].id() == 10) {
                return 1;
            }
        }
        return 0;
    }

    // Checks neighbors for bombs
    public int mineCount(int x, int y) {
        int numMines = 0;
        int x1 = x - 1;
        int x2 = x + 1;
        int y1 = y - 1;
        int y2 = y + 1;
        numMines += bombCount(x1, y1);
        numMines += bombCount(x1, y);
        numMines += bombCount(x1, y2);
        numMines += bombCount(x, y1);
        numMines += bombCount(x, y2);
        numMines += bombCount(x2, y1);
        numMines += bombCount(x2, y);
        numMines += bombCount(x2, y2);
        return numMines;
    }

    // Randomly set mines
    public void setMines(int bombs) {
        String imgPath = "pic/bomb.jpg";
        ImageIcon bombPic = new ImageIcon(loader.getResource(imgPath));
        int bombsOnBoard = 0;
        while (bombsOnBoard < bombs) {
            int randomX = (int) (Math.random() * (tiles.length - 1) + 1);
            int randomY = (int) (Math.random() * (tiles.length - 1) + 1);
            if (tiles[randomX][randomY].id() != 10) {
                tiles[randomX][randomY].setFrontImage(bombPic);
                tiles[randomX][randomY].setID(10);
                bombsOnBoard++;
            }
        }
    }

    // Checks neighbors for bombs/empty/spaces/numbers to clear empty areas
    public void clearEmptySpaces(int x, int y) {
        if (x >= 0 && x < tiles.length && y >= 0 && y < tiles.length) { // check for bounds
            if (tiles[x][y].id() == 9 && tiles[x][y].revealed() == false) {
                tiles[x][y].showFront();
                tiles[x][y].setRevealed(true);
                clearEmptySpaces(x + 1, y + 1);
                clearEmptySpaces(x + 1, y);
                clearEmptySpaces(x + 1, y - 1);
                clearEmptySpaces(x, y + 1);
                clearEmptySpaces(x, y - 1);
                clearEmptySpaces(x - 1, y + 1);
                clearEmptySpaces(x - 1, y);
                clearEmptySpaces(x - 1, y - 1);
            } else if(tiles[x][y].id() > 0 && tiles[x][y].id() < 9){
                tiles[x][y].showFront();
                tiles[x][y].setRevealed(true);
            } else{
                return;
            }
        } else {
            return;
        }
    }

    // Set number hints based on where mines are
    public void setNumberHints() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                int mines = mineCount(i, j);
                if (mines > 0 && tiles[i][j].id() != 10) { // If there's a bomb mark the neighbor as number hints
                    tiles[i][j].setID(mines);
                    String imgPath = "pic/" + mines + ".jpg";
                    ImageIcon numHint = new ImageIcon(loader.getResource(imgPath));
                    tiles[i][j].setFrontImage(numHint);
                }
            }
        }
    }

    // Clear the area of empty tiles
    public void clearEmptyTiles(GameTiles square) {
        int x = square.getXLocation();
        int y = square.getYLocation();
                clearEmptySpaces(x, y);
    }

    // Reveal board when you lose the game
    public void lostGame(int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j].showFront();
            }
        }
    }

    // Check to see if entire board is revealed and if so you win
    public int checkForWinner(int size, int bombs) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].revealed() == true) {
                    count++;
                    if (count == (size * size) - bombs) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

}
