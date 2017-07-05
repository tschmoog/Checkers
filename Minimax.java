
import java.util.ArrayList;
/**
 * The Minimax class evaluates a given board state in the game 
 * @author 163318
 * @version 1
 */
public class Minimax {
    public ArrayList <Minimax> sucessorPositions;  //the next potential moves
    public Board positions;  //the current board state and positions 

    /**
     * Constructor for objects of class Minimax
     * Creates an arraylist which will hold potnetial board positions to be evaluated 
     * Creates a board with the current positions  
     */
    public Minimax(Board positions){
        sucessorPositions = new ArrayList <Minimax> ();
        this.positions = positions;
    }

    /**
     * Moves through possible positions, and adds further positions on top of the newly created positions
     * Called by the computer player which will call this method as many times as the depth set
     * Depth is set by player choosing difficulty
     * @param turn whoevers turn it currently is 
     */
    public void getAvaliableStates(boolean turn){
        for(Minimax pos : sucessorPositions){
            pos.getAvaliableStates(!turn);
        }

        if(sucessorPositions.isEmpty()){
            ArrayList <Board> moves = positions.getMoves(turn);
            for(Board brd : moves){
                sucessorPositions.add(new Minimax(brd));  //add them all to the list
            }
        }
    }

    /**
     * Returns the value of the best move in this part of the Tree
     */
    public int minimax(boolean turn){
        if(sucessorPositions.isEmpty()){ 
            return positions.boardValue();
        }

        if(turn){  //AI wants to max score
            int score = Integer.MIN_VALUE;
            for(Minimax pos : sucessorPositions){  //cycle through all of the possible resulting moves
                score = Math.max(score, pos.minimax(!turn));
            }
            return score;
        }
        else{   //if it is human player, we minimize the score
            int score = Integer.MAX_VALUE;
            for(Minimax pos : sucessorPositions){  //cycle through all of the possible resulting moves
                score = Math.min(score, pos.minimax(!turn));
            }
            return score;
        }
    }

    /**
     * For all sucessive positions, evalate the scores and return the highest scoring for an optimal move. 
     * @param boolean turn
     * @return Minimax the best board position
     */
    public Minimax getMove(boolean turn){
        if(sucessorPositions.isEmpty()){
            return null;
        }

        Minimax best = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        if(turn){  //if we are turn, we want to maximize the board score
            int score = Integer.MIN_VALUE;
            for(Minimax pos : sucessorPositions){  //cycle through all of the possible resulting moves
                int value = pos.minimax(!turn);  //get the value of the taking the other move
                if(best == null || value > score){
                    score = value;
                    best = pos;
                    alpha = value;
                }
                //if (alpha > beta)
                //{
                //    break;
                //}
            }
        }
        else{  //if we are not turn, we want to minimize the board score
            int score = Integer.MAX_VALUE;
            for(Minimax pos : sucessorPositions){  //cycle through all of the possible resulting moves
                int value = pos.minimax(!turn);  //get the value of the taking the other move
                if(best == null || value < score){
                    score = value;
                    best = pos;
                    beta = value;
                }
                //if (alpha < beta)
                //{
                //    break;
                //}
            }
        }
        
        return best;
    }
}
