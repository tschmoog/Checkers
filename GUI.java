
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * The GUI Class displays the checkers board and allows the user to ineract with the pieces via drag and drop
 * using a mouse listener
 * @author 163318
 * @version 1
 */
public class GUI extends JPanel implements KeyListener, FocusListener, MouseMotionListener, MouseListener{

    public long sleepTime;
    public JFrame myJFrame;
    public Game myGame;
    public boolean hints = false;

    /**
     * Constructor for objects of class GUI
     */
    public GUI(long sleepTime, Game myGame){
        myJFrame = new JFrame("Checkers");
        myJFrame.setSize(600,600);
        myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenuBar(myJFrame);
        showHelpDialogue();
        myJFrame.add(this);  //add the viewer (itself) into the frame
        myJFrame.setVisible(true);
        
        
        //JLabel label = new JLabel("TEST LABEL MY NAME IS TIM LALALA");
        //contentPane.add(label);
        
        
        myJFrame.addFocusListener(this);
        myJFrame.addKeyListener(this);
        myJFrame.addMouseMotionListener(this);
        myJFrame.addMouseListener(this);
        myJFrame.requestFocus();  //get focus
        //myJFrame.pack(); REVISIT WHEN UNDERSTOOD
        this.sleepTime = sleepTime;
        this.myGame = myGame;
    }
    
    
    /**
     * Creates the menubar that hold the functions avaliable to the player. 
     */
    private void makeMenuBar(JFrame frame)
    {
        JMenuBar myMenuBar = new JMenuBar();
        frame.setJMenuBar(myMenuBar);
        
        JMenu hintMenu = new JMenu("Hints");
        myMenuBar.add(hintMenu);
        
        JMenuItem hintOnItem = new JMenuItem("On"); //Turns on hints
        hintMenu.add(hintOnItem);
        hintOnItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    showHintDialogue();
                    hints = true;
                }
            });
        
        JMenuItem hintOffItem = new JMenuItem("Off");//Turns off hints
        hintMenu.add(hintOffItem);
        hintOffItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    hints = false;
                }
            });
        
        JMenu difficultyMenu = new JMenu("Difficulty"); //Adds the various difficulty levels to the menu
        myMenuBar.add(difficultyMenu);
        
        JMenuItem difficulty1Item = new JMenuItem("1 - Too easy");
        difficultyMenu.add(difficulty1Item);
        difficulty1Item.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    myGame.setDifficulty(1);
                    
                }
            });
        
        JMenuItem difficulty2Item = new JMenuItem("2 - Normal");
        difficultyMenu.add(difficulty2Item);
        difficulty2Item.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    myGame.setDifficulty(2);
                }
            });
        
        JMenuItem difficulty3Item = new JMenuItem("3 - Challenging");
        difficultyMenu.add(difficulty3Item);
        difficulty3Item.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    myGame.setDifficulty(4);
                }
            });
        
        JMenuItem difficulty4Item = new JMenuItem("4 - Disgusting");
        difficultyMenu.add(difficulty4Item);
        difficulty4Item.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    myGame.setDifficulty(7);
                }
            });
        
        
        JMenuItem helpItem = new JMenuItem("Help"); //Adds a button to open the help dialogue
        myMenuBar.add(helpItem);
        helpItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                
                    showHelpDialogue();
                }
            });
        
    }
    
    private void showHintDialogue()
    {
        JOptionPane.showMessageDialog(null, "Stuck are we? \n Pieces that can move will be highlighted green. \n Pieces that can jump will be highlighted yellow \n Acess this menu again to turn off hints.");
    }
    
    private void showHelpDialogue()
    {
        JOptionPane.showMessageDialog(null, "Welcome to Impossible Checkers! Please use the menu bar to select difficulty and turn hints on or off. \n TO PLAY: Click and hold to drag and drop each checker \n RULES: \n To win the game all opponents checkers must be eliminated. \n Only diagonal moves towards the opposing side of the board can be made.\n If a oposing checker is diagonally adjacent to one of your own, you MUST jump it if the square beyond it is free. \n Multiple jumps can and must be made in one turn if possible. \n Upon reaching the opposing size of the board a checker becomes a 'King'. \n Kings can move both backwards and forwards, making them crucial to winning the game! \n Good luck!  ");
    }

    /**
     * Paints the board and pieces 
     */
    @Override
    public void paintComponent(Graphics g){
        setBackground(Color.GRAY);
        super.paintComponent(g);

        if(sleepTime >= 0 && myGame != null){
            double screenDimensions[] = {getWidth(), getHeight()};
            for(int a = 0; a < myGame.board.board.length; a++){
                for(int b = 0; b < myGame.board.board[0].length; b++){
                    
                    if ((a + b)%2 == 0)
                    {
                        if ((myGame.board.canJump(a,b)) && hints)
                        {
                            g.setColor(Color.YELLOW);
                        }
                        else if ((myGame.board.canStep(a,b)) && hints)
                        {
                            g.setColor(Color.GREEN);
                        }
                        else 
                        {
                            g.setColor(Color.WHITE);
                    
                        }
                    }

                    else
                    {
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(a*50+100,b*50+200,50,50);
                    switch(myGame.board.board[a][b]){ //paints checker
                        case(-2): g.setColor(Color.RED); g.drawString("K", a*50+120, b*50+220);
                        case(-1): g.setColor(new Color(0,0,0,180)); g.fillOval(a*50+100, b*50+200,50,50); break;
                        case(2):  g.setColor(Color.BLACK); g.drawString("K", a*50+120, b*50+220);
                        case(1):  g.setColor(new Color(255,0,0,180)); g.fillOval(a*50+100, b*50+200,50,50); break;
                        default: break;
                    }
                }
            }
            g.drawString("" + sleepTime, 0, 10);
            
        }

        repaint();


    }

    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    public void focusLost(FocusEvent e){}
    public void focusGained(FocusEvent e){}
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
    public void mousePressed(MouseEvent e)
    {
            myGame.BLACK.onMousePressed(e);  
    }
    public void mouseReleased(MouseEvent e)
    {
            myGame.BLACK.onMouseReleased(e);
    }
    }


