/*
 * GamePanel.java
 * 
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;

/**
 * This class creates and handles the actions of the main tetris
 * game panel.
 * @author Clay Blair
 * @version 3 June 2016
 */
public class GamePanel extends JPanel implements Observer {

    /**
     * Generated serial number.
     */
    private static final long serialVersionUID = -4329581241840629446L;
    /** Tetris block size. **/
    private static final int BLOCK_SIZE = 35;
    /** Board width. **/
    private static final int DEFAULT_WIDTH = 10;
    /** Board height. **/
    private static final int DEFAULT_HEIGHT = 20;
    /** Starting timer delay. **/
    private static final int STARTING_TIMER = 1000;
    /** The offset of the y axis. **/
    private static final int Y_OFFSET = 5;
    /** The tetris board in use. **/
    private Board myBoard;
    /** Menu item for the end game command. **/
    private JMenuItem myMenuItem;
    /** Menu item for the new game command. **/
    private JMenuItem myNewMenuItem;
    /** Listener for key commands. **/
    private KeyBoardListener myKeyListener;
    /** Listener for pause command. **/
    private PauseListener myPauser;
    /** String for tetris board being passed in. **/
    private String myBoardGraphic;
    /** Timer for piece movement. **/
    private Timer myTimer;
    
    /**
     * Constructor, set up panel and initializes fields.
     * @param theBoard is the tetris board in use. 
     * @param theMenuItem is the end game menu item.
     * @param theOther is the new game menu item.
     */
    public GamePanel(final Board theBoard, final JMenuItem theMenuItem, 
                     final JMenuItem theOther) {
        myMenuItem = theMenuItem;
        myNewMenuItem = theOther;
        myBoard = theBoard;
        setup();
        myKeyListener = new KeyBoardListener();
        myPauser = new PauseListener();
        myTimer = new Timer(STARTING_TIMER, new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.step();
            }
        });
    }

    /**
     * Sets up the panel.
     */
    public void setup() {
        setOpaque(true);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setBackground(Color.BLUE);
        setFocusable(true);
        myBoard.addObserver(this);
    }
    
    /** 
     * Changes timer delay.
     * @param theTime is the new delay length.
     */
    public void setTimer(final int theTime) {
        myTimer.setDelay(theTime);
    }
    
    /**
     * Pauses the game. 
     */
    public void setPaused() {
        myTimer.stop();
        removeKeyListener(myKeyListener);
        addKeyListener(myPauser);
    }

    /** 
     * Unpauses the game. 
     */
    public void setUnpaused() {
        myTimer.start();
        addKeyListener(myKeyListener);
    }

    /**
     * Either paints the board with a new string, or establishes the game over
     * condition.
     * @param theObservable is the object being observed.
     * @param theObject is the data being passed from observers.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof String) {
            myBoardGraphic = (String) theObject;
            repaint();
        } else if (theObject instanceof Boolean) {
            final boolean gameOver = (boolean) theObject;
            if (gameOver) {
                JOptionPane.showMessageDialog(null, "Game Over Man!", "Game Over!",
                                              JOptionPane.OK_OPTION);
                myMenuItem.setEnabled(false);
                myNewMenuItem.setEnabled(true);
            }
        }            
    }

    /**
     * Ends the game, displays dialog for scoring algorithm.
     */
    public void endGame() {
        setPaused();
        removeKeyListener(myPauser);
        myNewMenuItem.setEnabled(true);
        JOptionPane.showMessageDialog(null, "Click New Game to begin\n\n\n"
                        + "Scoring:\n\n"
                        + "10 points per line cleared\n"
                        + "Every 5 lines increses level by 1\n"
                        + "Every level increases speed by 10%",
                        "Welcome!",
                        JOptionPane.OK_OPTION);
    }

    /**
     * Paints the tetris board and all the blocks.
     * @param theGraphics is the graphics object being painted.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2 = (Graphics2D) theGraphics;
        final String[] thisBoard = myBoardGraphic.split("\n");
        g2.setColor(Color.WHITE);
        g2.drawRect(1 * BLOCK_SIZE, 0, DEFAULT_WIDTH * BLOCK_SIZE,
                    DEFAULT_HEIGHT * BLOCK_SIZE);
        for (int i = 0; i < thisBoard.length; i++) {
            for (int j = 1; j < DEFAULT_WIDTH + 1; j++) {
                if (Character.isLetter(thisBoard[i].charAt(j)) 
                                || Character.isDigit(thisBoard[i].charAt(j))) {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(j * BLOCK_SIZE, (i - Y_OFFSET) * BLOCK_SIZE, 
                                BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(j * BLOCK_SIZE, (i - Y_OFFSET) * BLOCK_SIZE, 
                                BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                }
            }
        }
    }

    /**
     * Key listener for paused game state.
     * @author Clay Blair
     * @version 3 June 2016
     */
    class PauseListener extends KeyAdapter {
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int key = theEvent.getKeyCode();
            if (key == KeyEvent.VK_P) {
                setUnpaused();
            }
        }
    }

    /**
     * Key listener for game commands.
     * @author Clay Blair
     * @version 3 June 2016
     */
    class KeyBoardListener extends KeyAdapter {

        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int key = theEvent.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    myBoard.left();
                    break;
                case KeyEvent.VK_PAGE_DOWN:
                    myBoard.rotateCCW();
                    break;
                case KeyEvent.VK_RIGHT:
                    myBoard.right();
                    break;
                case KeyEvent.VK_DOWN:
                    myBoard.down();
                    break;
                case KeyEvent.VK_SPACE:
                    myBoard.drop();
                    break;
                case KeyEvent.VK_PAGE_UP:
                    myBoard.rotateCW();
                    break;
                case KeyEvent.VK_P:
                    setPaused();
                    break;
                default:
                    break;
            }
        }
    }
}