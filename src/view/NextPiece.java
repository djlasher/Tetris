/*
 * NextPiece.java
 * 
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JPanel;

import model.Board;
import model.Point;
import model.TetrisPiece;

/** 
 * This class takes care of the panel which displays the next
 * tetris piece that will be used.
 * @author Clay Blair
 * @version 3 June 2016
 */
public class NextPiece extends JPanel implements Observer {

    /**
     * Generated serial number.
     */
    private static final long serialVersionUID = 4046351382248456258L;
    /** The the tetris block size. **/
    private static final int BLOCK_SIZE = 40;
    /** The panel height. **/
    private static final int DEFAULT_HEIGHT = 400;
    /** The panel width. **/
    private static final int DEFAULT_WIDTH = 300;
    /** The offset for the x orientation. **/
    private static final int X_OFFSET = 10;
    /** The tetrisboard in use. **/
    private Board myBoard;
    /** The next tetris piece to be used. **/
    private TetrisPiece myNext;

    /**
     * Constructor, creates panel and initializes fields.
     * @param theBoard is the game board in use.
     */
    public NextPiece(final Board theBoard) {
        myBoard = theBoard;
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        myBoard.addObserver(this);
        setBackground(Color.LIGHT_GRAY);
    }

    /** Overrides update method, sets the next piece and paints the panel.
     * @param theObservable is the object being observed.
     * @param theObject is the data being passed to observers.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof TetrisPiece) {
            myNext = pieceChecker((TetrisPiece) theObject);
            repaint();
        }
    }

    /**
     * Fixes glitch with reversed pieces and returns correct piece.
     * @param thePiece is the piece that was passed to this object.
     * @return returns the correct orientation of the piece.
     */
    public TetrisPiece pieceChecker(final TetrisPiece thePiece) {
        myNext = thePiece;
        if (myNext == TetrisPiece.J) {
            myNext = TetrisPiece.L;
        } else if (myNext == TetrisPiece.L) {
            myNext = TetrisPiece.J;
        } else if (myNext == TetrisPiece.S) {
            myNext = TetrisPiece.Z;
        } else if (myNext == TetrisPiece.Z) {
            myNext = TetrisPiece.S;
        } else {
            return myNext;
        }
        return myNext;
    }
        
    /** 
     * Paints the panel with the tetris piece.
     * @param theGraphics is the graphics object for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Random random = new Random();
        final float r = random.nextFloat();
        final float g = random.nextFloat();
        final float b = random.nextFloat();
        final Graphics2D g2 = (Graphics2D) theGraphics;
        final Point[] newPoints = myNext.getPoints(); 
        for (final Point p : newPoints) {
            g2.setColor(new Color(r, g, b));
            g2.fillRect((p.x() + 2) * BLOCK_SIZE + X_OFFSET, p.y() * BLOCK_SIZE, 
                        BLOCK_SIZE - 1, BLOCK_SIZE - 1);
            g2.setColor(Color.BLACK);
            g2.drawRect((p.x() + 2) * BLOCK_SIZE + X_OFFSET, p.y() * BLOCK_SIZE, 
                        BLOCK_SIZE - 1, BLOCK_SIZE - 1);
            //            }
        }
    }


}
