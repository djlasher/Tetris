/**
 * TCSS 305 - Winter 2016
 * Assignment 6 - Tetris
 */
package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** 
 * This class creates a panel of information, such as keybindings. 
 * @author Clay Blair
 *  @version 4 March 2016
 */
public class WordPanel extends JPanel {
    /** Sets the serial number. **/
    private static final long serialVersionUID = 1L;
    /** Reports a key for down. **/
    private JLabel myDown;
    /** Reports a key for counter clockwise. **/
    private JLabel myCCW;
    /** Reports a key for clockwise. **/
    private JLabel myCW;
    /** Reports a key for left. **/
    private JLabel myLeft;
    /** Reports a key for right. **/
    private JLabel myRight;
    /** Posts a header for the panel. **/
    private JLabel myBindings;
    /** Reports a key to drop the piece. **/
    private JLabel myDrop;
    
    /** 
     * Constructor, assigns values to fields and sets up the panel.
     */
    public WordPanel() {
        myBindings = new JLabel("Current Keybindings:");
        myDown = new JLabel("Down arrow: move piece down");
        myLeft = new JLabel("Left arrow: move piece left");
        myRight = new JLabel("Right arrow: move piece right");
        myCCW = new JLabel("Page Down: rotate piece counterclockwise");
        myCW = new JLabel("Page Up: rotate piece clockwise");
        myDrop = new JLabel("Space bar: drop piece to the bottom");
        add(myBindings);
        add(myDown);
        add(myLeft);
        add(myRight);
        add(myCCW);
        add(myCW);
        add(myDrop);
        setVisible(true);
        revalidate();
    }
}
