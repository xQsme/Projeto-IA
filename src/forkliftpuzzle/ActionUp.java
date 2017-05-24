package forkliftpuzzle;

import agent.Action;

public class ActionUp extends Action<ForkliftPuzzleState>{

    public ActionUp(int id){
        super(1, id);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveUp(id);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveUp(id);
    }
}