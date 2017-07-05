

import java.awt.event.*;
/**
 * The Computer class: This class manages the AI player. It is closely linked with the Minimax class,
 * using its methods to generate the optimum next move and then playing it. 
 * @author 163318
 * @verson 1
 */
public class Computer {

    public Minimax myTree;  //position tree
    public boolean turn;  
    int depth; // level of difficulty

    /**
     * Constructor for objects of class Computer
     */
    public Computer(Board positions, int Depth, boolean turn){
        myTree = new Minimax(positions);
        this.turn = turn;
        this.depth = Depth;
        while(Depth > 0){  //iterates depth number of times to get future moves
            myTree.getAvaliableStates(true); 
            Depth --;
        }
    }

    public Board getMove(){

        return myTree.getMove(turn).positions;  //tell it to become the next node down in its minimax list
    }

    public void update(Board positions, boolean turn){

        String currentBoard = positions.toString();
        boolean switched = false;
        int a = 0;
        while(a < myTree.sucessorPositions.size() && !switched){
            if((myTree.sucessorPositions.get(a).positions.toString()).equals(currentBoard)){  //if we found a match
                myTree = myTree.sucessorPositions.get(a);
                switched = true;
            }
            a++;
        }

        if(!switched){

            myTree = new Minimax(positions);
            for(int b = 0; b < depth - 1; b++){ //CARE getAvaliableStates is recursive
                myTree.getAvaliableStates(!turn);
            }
        }
        myTree.getAvaliableStates(!turn);
    }

}
