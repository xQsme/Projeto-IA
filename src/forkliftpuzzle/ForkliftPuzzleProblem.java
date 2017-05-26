package forkliftpuzzle;

import agent.Action;
import agent.Problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ForkliftPuzzleProblem extends Problem<ForkliftPuzzleState> {



    public ForkliftPuzzleProblem(ForkliftPuzzleState initialState) {
        super(initialState, new ArrayList<Action>());
        ArrayList<Integer> types;
        ArrayList<Integer> ids;
        types = initialState.getTypes();
        ids = initialState.getIds();
        int i = 0;
        for(int type : types){
            int id=ids.get(i++);
            if(type == 1 || type%2 == 0){
                 actions.add(new ActionLeft(id));
                 actions.add(new ActionRight(id));
            }else{
                 actions.add(new ActionUp(id));
                 actions.add(new ActionDown(id));
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
