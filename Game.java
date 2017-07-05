
import java.util.ArrayList;
/**
 * The game class: The main class through which the game is run. 
 * @author 163318
 * @version final
 */
public class Game {

    public GUI gui; // creates the GUI
    public Board board; //creates the board
    public Computer RED; //Red player (red plays first)
    public Human BLACK;//Black player (human)
    public boolean turn; //true if AI's turn, false if humans
    public int depth = 2; //the depth to which the algorithim searches (default is 2)

    /**
     * Constructor for objects of class Game
     */
    
    public Game(){
        turn = true; //starts red player (AI)
        board = new Board(); //creates board
        gui = new GUI(10, this); //creates GUI
        RED = new Computer(board, depth, turn); //creates computer player
        BLACK = new Human(board, !turn); //creates human interface to play

        while(!board.gameOver(turn)) //Runs while gameOver == false
        {
            if (turn == true) //if red's turn, get red move
            {
                board = RED.getMove();
            }
            else //else get blacks move
            {
                board = BLACK.getMove();
            }
            RED.update(board, turn); //update new board with new positions
            BLACK.update(board, turn);
            turn = !turn; //change to next players turn
        }
        if (board.gameOver(turn)){
            System.out.println("Game Over!");
        }
    }
    
    /**
     * This method sets the difficulty of the AI. The depth is chosen in the JmMenu bar
     * in the checkers GUI.
     */
    public void setDifficulty(int x)
    {
        depth = x; 
    }
    
    /**
     * Tester method to test depth of search
     * 
     *
     */
    public void testDepth(){
        System.out.println(depth);
    }
}
