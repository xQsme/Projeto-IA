package forkliftpuzzle;

import agent.Action;

public class ActionUp extends Action<ForkliftPuzzleState>{

    public ActionUp(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveUp();
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveUp();
    }
}