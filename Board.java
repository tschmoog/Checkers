
import java.util.ArrayList;
import java.util.Random;

/**
 * The Board class: This class holds a representation of the board and the corresponding legal moves
 * @author 163318
 * @version 1
 */
public class Board {
    public int[][] board; //an array that will represent the board

    /**
     * Constructor for objects of class Board
     * 
     * During creation the board is populated with the pieces in their starting positions. 
     * The array is populated with positive integers 1 & 2 for normal and king checkers respectivley. 
     * Negative integers are used for the second player. 0 represents a free square. 
     * 
     */
    
    public Board(){
        board = new int[8][8]; //create an 8x8 array representing all the squares of the board. 
        for(int a = 0; a < board.length; a++){
            for(int b = 0; b < board[0].length; b++){
                if(b < 3 && (b + a) % 2 == 0){// adds pieces on alternating squares for the first 3 rows
                    board[a][b] = 1;
                }
                else if(b > 4 && (b + a)%2 == 0){//adds second players pieces on opposiing end of the board
                    board[a][b] = -1;
                }
                else{// leaves middle rows empty 
                    board[a][b] = 0;
                }
            }
        }
    }

    /**
     * Copies the current board and returns it
     * @return Board a copy of the board in it's current state 
     */
    public Board copy(){
        Board copy = new Board();
        for(int a = 0; a < board.length; a++){
            for(int b = 0; b < board[0].length; b++){
                copy.board[a][b] = board[a][b];
            }
        }
        return copy;
    }

    /**
     * creates a new Board from the current state
     * @param Board BP the current board
     * @param int A
     * @param int B int A and B are the coordinates of the piece to be moved.
     * @param nextMove, an integer representing the type of move to take
     */
    public Board(Board BP, int A, int B, int nextMove){
        board = new int[8][8];
        for(int a = 0; a < board.length; a++){
            for(int b = 0; b < board[0].length; b++){
                board[a][b] = BP.board[a][b];  //copy the board to the new one
            }
        }
        int cType = board[A][B]; //stores the type of checker at the position
        board[A][B] = 0; //clear the position that the checker is moving from 
        switch(nextMove){ //the move that the checker is taking 
            case(0): 
            A --;
            B ++; 
            break; //step right up
            
            case(1): 
            A ++;
            B ++; 
            break; //step right down
            
            case(2): //jump right up
            board[A-1][B+1] = 0;  
            A -= 2;
            B += 2; 
            break;
            
            case(3): //jump right down
            board[A+1][B+1] = 0;
            A += 2;
            B += 2; 
            break;
            
            case(4):
            A --;
            B --;
            break; //step right up
            
            case(5):
            A ++;
            B --; 
            break; //step right down
            
            case(6): //jump right up
            board[A-1][B-1] = 0;
            A -= 2;
            B -= 2;  
            break;
            
            case(7): //jump right down
            board[A+1][B-1] = 0;
            A += 2;
            B -= 2;
            break;
            
            default: break;
        }
        board[A][B] = cType; //stores the type of checker 
        if((B == 0 && cType == -1) || (B == 7 && cType == 1)){  //if the checker is normal and reaches the final row, it is made a king
            board[A][B] *= 2;
        }
    }

    /**
     * returns true if the game is over, due to there being no more avaliable moves 
     * @return boolean 
     */
    public boolean gameOver(boolean turn){
        return getMoves(turn).isEmpty();
    }



    /**
     * similar to the method above, however returns a boolean when given a specific piece. 
     * @param a row of piece to move
     * @param b column of piece to move
     * @return boolean true if the piece can make the move, false if it is unable. 
     */
    public boolean canJump(int a, int b){
        int peiceType = board[a][b];
        if((peiceType > 0 || peiceType == -2) && (jumpOK(a, b, a-1,b+1) || jumpOK(a, b, a+1,b+1))){  //jump down direction for king and red pawns 
            return true;
        }
        if((peiceType < 0 || peiceType == 2) &&(jumpOK(a, b, a-1,b-1) || jumpOK(a, b, a+1,b-1))){  //jump up direction for king and black pawns
            return true;
        }
        return false;
    }

    /**
     * Checks whether the piece can make a move (same as above, step instead of jump)
     * @param a row of piece to move
     * @param b column of piece to move
     * @return boolean true if the piece can make the move, false if it is unable. 
     */
    public boolean canStep(int a, int b){
        int peiceType = board[a][b];
        if((peiceType > 0 || peiceType == -2) && (open(a-1,b+1) || open(a+1,b+1))){  //check steps down
            return true;
        }
        if((peiceType < 0 || peiceType == 2) &&(open(a-1,b-1) || open(a+1,b-1))){  //check steps up
            return true;
        }
        return false;
    }

    /**
     * Compiles a list of all the board moves possible and adds to an array list
     * @return ArrayList of moves that can be made
     */
    public ArrayList <Board> getMoves(boolean turn){
        ArrayList <Board> moves = new ArrayList <Board> ();

        for(int a = 0; a < board.length; a++){
            for(int b = 0; b < board[0].length; b++){
                if((turn && board[a][b] > 0) || (!turn && board[a][b] < 0)){
                    moves.addAll(getJumpMoves(a, b, board[a][b]));
                }
            }
        }

        if(moves.isEmpty()){  //only add step moves if there are no killing moves
            for(int a = 0; a < board.length; a++){
                for(int b = 0; b < board[0].length; b++){
                    int peiceType = board[a][b];
                    if(turn && board[a][b] > 0){
                        if(open(a-1, b+1)){  //move right and up is open
                            moves.add(new Board(this, a, b, 0));
                        }
                        if(open(a+1, b+1)){  //move right and down is open
                            moves.add(new Board(this, a, b, 1));
                        }
                        if(board[a][b] == 2){
                            if(open(a-1, b-1)){  //move left and up is open
                                moves.add(new Board(this, a, b, 4));
                            }
                            if(open(a+1, b-1)){  //move leftt and down is open
                                moves.add(new Board(this, a, b, 5));
                            }
                        }
                    }
                    if(!turn && board[a][b] < 0){
                        if(open(a-1, b-1)){  //move left and up is open
                            moves.add(new Board(this, a, b, 4));
                        }
                        if(open(a+1, b-1)){  //move leftt and down is open
                            moves.add(new Board(this, a, b, 5));
                        }
                        if(board[a][b] == -2){
                            if(open(a-1, b+1)){  //move right and up is open
                                moves.add(new Board(this, a, b, 0));
                            }
                            if(open(a+1, b+1)){  //move right and down is open
                                moves.add(new Board(this, a, b, 1));
                            }
                        }
                    }
                }
            }
        }

        return moves;
    }

    /**
     * returns the jump moves for the given piece
     * @param a
     * @param b
     * @return
     */
    public ArrayList <Board> getJumpMoves(int a, int b, int peiceType){
        ArrayList <Board> moves = new ArrayList <Board> ();
        if(peiceType > 0 || peiceType == -2){
            if(jumpOK(a, b, a-1,b+1)){
                Board newBP = new Board(this, a, b, 2);
                //this ensures that as many jumps as possible are made
                ArrayList <Board> nextJumps = newBP.getJumpMoves(a-2,b+2,peiceType);
                if(nextJumps.isEmpty()){
                    moves.add(newBP);
                }
                else{
                    moves.addAll(nextJumps);
                }
            }
            if(jumpOK(a, b, a+1,b+1)){
                Board newBP = new Board(this, a, b, 3);
                ArrayList <Board> nextJumps = newBP.getJumpMoves(a+2,b+2,peiceType);
                if(nextJumps.isEmpty()){
                    moves.add(newBP);
                }
                else{
                    moves.addAll(nextJumps);
                }

            }
        }
        if(peiceType < 0 || peiceType == 2){
            if(jumpOK(a, b, a-1,b-1)){
                Board newBP = new Board(this, a, b, 6);
                ArrayList <Board> nextJumps = newBP.getJumpMoves(a-2,b-2,peiceType);
                if(nextJumps.isEmpty()){
                    moves.add(newBP);
                }
                else{
                    moves.addAll(nextJumps);
                }

            }
            if(jumpOK(a, b, a+1,b-1)){
                Board newBP = new Board(this, a, b, 7);
                ArrayList <Board> nextJumps = newBP.getJumpMoves(a+2,b-2,peiceType);
                if(nextJumps.isEmpty()){
                    moves.add(newBP);
                }
                else{
                    moves.addAll(nextJumps);
                }

            }
        }

        return moves;
    }

    /**
     * returns a list of coordinates the given peice can step to
     * NOTE: this does not check if the peice has a legal jump
     * @return
     */
    public ArrayList <int[]> getStepMoves(int a, int b){
        ArrayList <int[]> stepMoves = new ArrayList <int[]> ();
        int peiceType = board[a][b];
        if(peiceType > 0 || peiceType == -2){
            if(open(a-1,b+1)){
                stepMoves.add(new int[]{a-1,b+1});
            }
            if(open(a+1,b+1)){
                stepMoves.add(new int[]{a+1,b+1});
            }
        }
        if(peiceType < 0 || peiceType == 2){
            if(open(a-1,b-1)){
                stepMoves.add(new int[]{a-1,b-1});
            }
            if(open(a+1,b-1)){
                stepMoves.add(new int[]{a+1,b-1});
            }
        }
        return stepMoves;
    }

    /**
     * returns a list of coordinates the given peice can jump to
     * NOTE: this does not check if the peice has a legal jump
     * @return
     */
    public ArrayList <int[]> getJumpMoves(int a, int b){
        ArrayList <int[]> jumpMoves = new ArrayList <int[]> ();
        int peiceType = board[a][b];
        if(peiceType > 0 || peiceType == -2){  //if it moves forward
            if(jumpOK(a, b, a-1,b+1)){
                jumpMoves.add(new int[]{a-2,b+2});
            }
            if(jumpOK(a, b, a+1,b+1)){
                jumpMoves.add(new int[]{a+2,b+2});
            }
        }
        if(peiceType < 0 || peiceType == 2){ //if it moves backward
            if(jumpOK(a, b, a-1,b-1)){
                jumpMoves.add(new int[]{a-2,b-2});
            }
            if(jumpOK(a, b, a+1,b-1)){
                jumpMoves.add(new int[]{a+2,b-2});
            }
        }
        return jumpMoves;
    }

    /**
     * returns if the given player has available jumps
     * @return boolean true if jumps are abaliable, false if they are not 
     */
    public boolean jumpsAvailable(boolean turn){
        for(int a = 0; a < board.length; a++){
            for(int b = 0; b < board[0].length; b++){
                if(((turn && board[a][b] > 0) || (!turn && board[a][b] < 0)) && (canJump(a,b))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * returns true if you can jump from a1, b1, over a2, b2
     * @param a
     * @param b
     * @return
     */
    public boolean jumpOK(int a1, int b1, int a2, int b2){
        return open(2*a2 - a1, 2*b2 - b1) && inBounds(a2, b2) && (Math.signum(board[a2][b2]) == -Math.signum(board[a1][b1]));
    }

    /**
     * true if the given coordinates are open
     * @param a
     * @param b
     * @return
     */
    public boolean open(int a, int b){
        return inBounds(a,b) && board[a][b] == 0;
    }

    /**
     * Returns true if the desired move is within the boundaires of the board
     */
    public boolean inBounds(int a, int b){
        return a >= 0 && a < board.length && b >= 0 && b < board[0].length;
    }

    /**
     * what the score is for this board
     * @return int the board value
     */
    public int boardValue(){
        int value = 0;
        for(int a = 0; a < board.length; a++){
            for(int b = 0; b < board[0].length; b++){
                value += board[a][b] * board[a][b];
            }
        }
        return value;
    }

}
