/*
 * TetrisGUI.java
 * 
 * TCSS 305 - Spring 2016
 * Assignment 6 - Tetris
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Board;

/**
 * This class handles the basic operations for the outer
 * tetris frame.
 * @author Clay Blair
 * @version 3 June 2016
 */
public class TetrisGUI extends JFrame implements Observer {

    /**
     * Generated serial number.
     */
    private static final long serialVersionUID = 106474944905625785L;
    /** Initial size upon opening. **/
    private static final Dimension INITIAL_SIZE = new Dimension(900, 1000);
    /** Width of the tetris board. **/
    private static final int DEFAULT_WIDTH = 10;
    /** Height of the tetris board. **/
    private static final int DEFAULT_HEIGHT = 20;
    /** Constant for various dimensions. **/
    private static final int EIGHT_HUNDRED = 800;
    /** Constant for various dimensions. **/    
    private static final int FIFTY = 50;
    /** Number of grid rows for layout. **/
    private static final int GRID_ROWS = 3;
    /** Constant for various dimensions. **/
    private static final int HUNDRED_DIMENSION = 100;
    /** Large font size. **/
    private static final int LARGE_FONT = 40;
    /** Medium font size. **/
    private static final int MEDIUM_FONT = 36;
    /** Constant for various dimensions. **/
    private static final int ONE = 1;
    /** Constant for various dimensions. **/
    private static final int SEVENTY_FIVE = 75;
    /** Small font size. **/
    private static final int SMALL_FONT = 24;
    /** Constant for various dimensions. **/
    private static final int TEN = 10;
    /** Constant for various dimensions. **/
    private static final int TWO_HUNDRED = 200;
    /** The tetris board object. **/
    private Board myBoard;
    /** Panel for displaying tetris board. **/
    private GamePanel myGamePanel;
    /** The total score for this game. **/
    private int myScore; 
    /** Label for displaying score. **/
    private JLabel myLabel;
    /** Label for displaying level. **/
    private JLabel myLevel;
    /** Label for displaying lines completed. **/
    private JLabel myLines;
    /** Label for displaying next piece. **/
    private JLabel myNext;
    /** Label for displaying frame title. **/
    private JLabel myTitle;
    /** Used for creating menus. **/
    private JMenu myMenu;
    /** Used for the menu bar. **/
    private JMenuBar myMenuBar;
    /** Generic menu item. **/
    private JMenuItem myMenuItem;
    /** The new game menu item. **/
    private JMenuItem myNewMenuItem;
    /** Title panel. **/
    private JPanel myBlankPanel;
    /** Panel for holding the other panels. **/
    private JPanel mySidePanel;
    /** Panel for spacing other panels. **/
    private JPanel mySpacePanel;
    /** Panel for holding statistic labels. **/
    private JPanel myStatPanel;
    /** Panel for holding right side panels. **/
    private JPanel myWordPanel;
    /** Next piece object. **/
    private NextPiece myNextPiece;
    /** String for score label. **/
    private String myScoreString;
    /**
     * Constructor, creates game panel and board, no arguments. 
     */
    public TetrisGUI() {
        super("Tetris");
        myScoreString = "Score: ";
        myMenuItem = new JMenuItem("End Game");
        myNewMenuItem = new JMenuItem("New Game");
        myBoard = new Board(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        myGamePanel = new GamePanel(myBoard, myMenuItem, myNewMenuItem);
    }
    
    /** 
     * Creates the main frame by creating all of the individual pieces, starts
     * the first game. 
     */
    public void setupFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(menuMaker());
        myMenuBar.setVisible(true);
        myBlankPanel = new JPanel();
        myBlankPanel.setPreferredSize(new Dimension(TWO_HUNDRED, HUNDRED_DIMENSION));
        myBlankPanel.setBackground(Color.BLACK);
        add(myGamePanel, BorderLayout.CENTER);
        add(statSetup(), BorderLayout.EAST);
        add(myBlankPanel, BorderLayout.NORTH);
        myTitle = new JLabel("TETRIS");
        myTitle.setFont(new Font(Font.SERIF, Font.BOLD, LARGE_FONT));
        myTitle.setForeground(Color.RED);
        myBlankPanel.add(myTitle);
        mySidePanel = new JPanel();
        mySidePanel.setPreferredSize(new Dimension(SEVENTY_FIVE, EIGHT_HUNDRED));
        mySidePanel.setBackground(Color.BLUE);
        add(mySidePanel, BorderLayout.WEST);
        myGamePanel.setVisible(true);
        setSize(new Dimension(INITIAL_SIZE));
        setResizable(false);
        setVisible(true);
        myBoard.addObserver(this);
        myBoard.newGame();
        myGamePanel.endGame();
    }
    
    /**
     * Creates the menu bar for the main frame.
     * @return returns the completed menu bar.
     */
    public JMenuBar menuMaker() {
        myMenuBar = new JMenuBar();
        myMenu = new JMenu("Options");
        myNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.newGame();
                myGamePanel.setUnpaused();
                myScore = 0;
                myMenuItem.setEnabled(true);
                myNewMenuItem.setEnabled(false);
            }
        });
        
        myMenu.add(myNewMenuItem);
        myMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent theEvent) {
                myGamePanel.endGame();
                myMenuItem.setEnabled(false);
                myNewMenuItem.setEnabled(true);
            }
        });
        myMenu.add(myMenuItem);
        myMenuItem = new JMenuItem("Exit");
        myMenuItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                System.exit(0);
            }
        });
        myMenu.add(myMenuItem);
        myMenuBar.add(myMenu);
        myMenu = new JMenu("Help");
        myMenuItem = new JMenuItem("Key Bindings...");
        myMenuItem.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, "Key bindings: \n"
                                              + "Move Left: Left Arrow\n"
                                + "Move Right: Right Arrow\n"
                                              + "Move Down: Down Arrow\n"
                                + "Rotate Clockwise: Page up\n"
                                              + "Rotate Counterclockwise: Page Down\n"
                                + "Drop: Space Bar\n"
                                              + "Pause/Unpause: P", "Key Bindings", 
                                              JOptionPane.OK_OPTION);
            }
        });
        myMenu.add(myMenuItem);
        myMenuBar.add(myMenu);
        return myMenuBar;
    }

    /**
     * Creates the side statistics panel.
     * @return returns the statistics panel.
     */
    public JPanel statSetup() {
        myNextPiece = new NextPiece(myBoard);
        myStatPanel = new JPanel(new BorderLayout());
        myStatPanel.setOpaque(true);
        myStatPanel.setPreferredSize(new Dimension(TWO_HUNDRED + HUNDRED_DIMENSION, 
                                                   EIGHT_HUNDRED + TWO_HUNDRED));
        myLabel = new JLabel(myScoreString + myScore);
        myNext = new JLabel("Next Piece: ");
        myLevel = new JLabel("Level: 1");
        myLevel.setFont(new Font(Font.SERIF, Font.BOLD, MEDIUM_FONT));
        myLevel.setForeground(Color.YELLOW);
        myLines = new JLabel("Total lines: 0");
        myLines.setFont(new Font(Font.SERIF, Font.BOLD, SMALL_FONT));
        myLines.setForeground(Color.YELLOW);
        myNext.setFont(new Font(Font.SERIF, Font.BOLD, SMALL_FONT));
        myLabel.setFont(new Font("Serif", Font.BOLD, MEDIUM_FONT));
        myLabel.setForeground(Color.YELLOW);
        myWordPanel = new JPanel();
        myWordPanel.setLayout(new GridLayout(GRID_ROWS, ONE));
        myWordPanel.add(myLabel);
        myWordPanel.add(myLines);
        myWordPanel.add(myLevel);
        myWordPanel.setPreferredSize(new Dimension(TWO_HUNDRED + HUNDRED_DIMENSION, 
                                                   TWO_HUNDRED + TWO_HUNDRED));
        myWordPanel.setBackground(Color.BLACK);
        mySpacePanel = new JPanel();
        mySpacePanel.setPreferredSize(new Dimension(TWO_HUNDRED + HUNDRED_DIMENSION, 
                                                    TWO_HUNDRED));
        mySpacePanel.setBackground(Color.GRAY);
        myNextPiece.add(myNext);
        myStatPanel.add(myNextPiece, BorderLayout.NORTH);
        myStatPanel.add(mySpacePanel, BorderLayout.CENTER);
        myStatPanel.add(myWordPanel, BorderLayout.SOUTH);        
        return myStatPanel;
    }

    /**
     * Setter method, sets score value.
     * @param theScore is the new score value.
     */
    public void setScore(final int theScore) {
        myScore = theScore;
    }
    
    /**
     * Observer method, watches for specific object type.
     * @param theObservable is the object being observed.
     * @param theObject is the data being passed to observers.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof Integer[]) {
            final int newLines = ((Integer[]) theObject).length;
            myScore += newLines * TEN;
            setScore(myScore);
            myLabel.setText(myScoreString + myScore);
            myLevel.setText("Level: " + ((myScore / FIFTY) + ONE));
            myLines.setText("Lines: " + myScore / TEN);
            myGamePanel.setTimer((EIGHT_HUNDRED + TWO_HUNDRED) - (((myScore / FIFTY) + ONE)
                            * HUNDRED_DIMENSION));
        }
    }
}
