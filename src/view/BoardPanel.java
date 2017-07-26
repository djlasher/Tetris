/**
 * TCSS305 - Winter 2016
 * Assignment 6 - Tetris 
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;
import model.Point;
import model.TetrisPiece;

/**
 * This class makes a panel to utilize the various objects and methods associated 
 * with the Board and the other pieces in the model package.
 * @author Clayton Blair
 * @version 4 March 2016
 */
public class BoardPanel extends JPanel implements Observer {

    /** Sets the serial number. **/
    private static final long serialVersionUID = 1L;    
    /** Sets the block size. **/
    private static final int BLOCK_SIZE = 40;
    /** Sets the default grid width. **/
    private static final int DEFAULT_WIDTH = 10;
    /** Sets the default grid height. **/
    private static final int DEFAULT_HEIGHT = 20;
    /** Sets the delay time for the timer object. **/
    private static final int DELAY_TIME = 40;
    /** Initializes a Board object. **/
    private Board myBoard;
    /** Initializes a next piece object. **/
    private NextPiece myNextPiece;
    /** Initializes a tetrispiece. **/
    private TetrisPiece myPiece;
    /** Initializes a timer. **/
    private Timer myTimer;
    /** Initializes a listener for the timer. **/
    private TimerListener myTimeListener;
    /** 
     * Constructor, gives some of the fields values and sets up the panel. 
     * @param theBoard is the Board being used
     */
    public BoardPanel(final Board theBoard) {
        myBoard = theBoard;
        myTimeListener = new TimerListener(myBoard);
        setSize(new Dimension(DEFAULT_WIDTH * BLOCK_SIZE, 
                                       DEFAULT_HEIGHT * BLOCK_SIZE));
        myTimer = new Timer(DELAY_TIME, myTimeListener);
        setBackground(Color.WHITE);
        myBoard = new Board(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        myBoard.addObserver(this);
        myBoard.newGame();
        setFocusable(true);
        addKeyListener(new KeyBoardListener());
    }
        
    /**
     * Update method, responds to observers from Board class;
     * update responds implements observer.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof List<?>) {
        } else if (theObject instanceof TetrisPiece) {
            if (myPiece == null) {
                myPiece = (TetrisPiece) theObject;
            } else {
                myPiece = myNextPiece.getPiece();
            }
        }
    }
   
    /**
     * This method draws the rectangles that make up the tetris pieces and delivers them
     * to the panel. 
     * @param theGraphics is the graphic component being used
     */
     
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Random random = new Random();
        final float r = random.nextFloat();
        final float g = random.nextFloat();
        final float b = random.nextFloat();
        final Graphics2D g2 = (Graphics2D) theGraphics;
        if (myPiece != null) {
            final Point[] newPoints = myPiece.getPoints(); 
            for (final Point p : newPoints) {
                g2.setColor(new Color(r, g, b));
                g2.drawRect((p.x() + 2) * BLOCK_SIZE, p.y() * BLOCK_SIZE, 
                        BLOCK_SIZE - 1, BLOCK_SIZE - 1);
                g2.fillRect((p.x() + 2) * BLOCK_SIZE, p.y() * BLOCK_SIZE, 
                        BLOCK_SIZE - 1, BLOCK_SIZE - 1);
            }
            myTimer.start();
        }
    }

    /**
     * This class sets up a listener for the timer object.    
     * @author Clay Blair
     * @version 4 March 2016
     */
    class TimerListener extends AbstractAction {
            /** Sets the serial number. **/
        private static final long serialVersionUID = 1L;
        /** Creates a new board object. **/    
        private Board myBoard = new Board();
        /** Constructor, sets up the listener.
         *    
         * @param theBoard is the board being used.
         */
        public TimerListener(final Board theBoard) {
            myBoard = theBoard;
        }
        /** 
         * This implements the action for the timer.
         *  @param theEvent is the timer firing.
         */
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
    }
        /**
         * This class creates a listener for the keyboard
         * in order to move tetris blocks.
         * @author Clay Blair
         * @version 4 March 2016
         */
    class KeyBoardListener extends KeyAdapter {
            /** 
             * This method preforms a function based on which key is pressed. 
             */
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
                default:
                    break;
            }
        }
    }
}