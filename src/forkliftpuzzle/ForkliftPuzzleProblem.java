package forkliftpuzzle;

import agent.Action;
import agent.Piece;
import agent.Problem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ForkliftPuzzleProblem extends Problem<ForkliftPuzzleState> {

    ArrayList<Piece> pieces;

    public ForkliftPuzzleProblem(ForkliftPuzzleState initialState) {
        super(initialState, new ArrayList<Action>());
         pieces= new ArrayList<>();
         pieces= initialState.getPieces();
         for(Piece piece : pieces){
             if(piece.getId() == 1 || piece.getId()%10%2 == 0){
                 actions.add(new ActionLeft(piece.getId()));
                 actions.add(new ActionRight(piece.getId()));
             }else{
                 actions.add(new ActionUp(piece.getId()));
                 actions.add(new ActionDown(piece.getId()));
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
    public double computePathCost(List<Action> path) {
        return path.size();
    }
    
}
