import javax.swing.*;

public class GameTiles extends JButton {

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    // Card front icon
    private Icon front;
    // Card back image
    private Icon back = new ImageIcon(loader.getResource("pic/unopenedTile.jpg"));

    // ID + Name
    private int id, x, y;
    private String customName;
    private boolean revealed;

    // Default constructor
    public GameTiles() {
        super();
    }

    // Constructor with card front initialization
    public GameTiles(ImageIcon frontImage) {
        super();
        front = frontImage;
        super.setIcon(front);
    }

    // Set the image used as the front of the card
    public void setFrontImage(ImageIcon frontImage) {
        front = frontImage;
    }

    /* Card flipping functions */
    // Shows the card front
    public void showFront() {
        setIcon(front);
        repaint();
    }

    // Shows the card back
    public void hideFront() {
        setIcon(back);
        repaint();
    }

    // Metadata: ID number
    public int id() {
        return id;
    }

    public void setID(int i) {
        id = i;
    }

    // Metadata: Custom name
    public String customName() {
        return customName;
    }

    public void setCustomName(String s) {
        customName = s;
    }

    public boolean revealed(){return revealed;}

    public void setRevealed(boolean b){ revealed = b;}

    public int getXLocation() { return x;}

    public void setXLocation(int newX) { x = newX;}

    public int getYLocation() { return y;}

    public void setYLocation(int newY) { y = newY;}

}
