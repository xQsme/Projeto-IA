package forkliftpuzzle;

import agent.Action;
import agent.Problem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class ForkliftPuzzleProblem extends Problem<ForkliftPuzzleState> {

    ArrayList<Integer> pieces;

    public ForkliftPuzzleProblem(ForkliftPuzzleState initialState) {
        super(initialState, new ArrayList<Action>());
         pieces = new ArrayList<>();
         pieces = initialState.getPieces();
         for(int piece : pieces){
             if(piece == 1 || piece%10%2 == 0){
                 actions.add(new ActionLeft(piece));
                 actions.add(new ActionRight(piece));
             }else{
                 actions.add(new ActionUp(piece));
                 actions.add(new ActionDown(piece));
             }
        }
    }
    
    @Override
    public boolean isGoal(ForkliftPuzzleState state) {
        return state.isGoal();
    }
    
    @Override
    public List<ForkliftPuzzleState> executeActions(ForkliftPuzzleState state) {
        List<ForkliftPuzzleState> successors
                = new LinkedList<ForkliftPuzzleState>();
        for (Action a : actions) {
            if (a.isValid(state)) {
                ForkliftPuzzleState successor = (ForkliftPuzzleState) state.clone();
                a.execute(successor);
                //System.out.println(successor);
                successors.add(successor);
            }
        }
        return successors;
    }
    
    @Override
    public int computePathCost(List<Action> path) {
        return path.size();
    }
    
}
