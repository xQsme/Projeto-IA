package forkliftpuzzle;

import agent.Action;

public class ActionUp extends Action<ForkliftPuzzleState>{

    public ActionUp(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state, int line, int column){
        state.moveUp(line, column);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state, int line, int column){
        return state.canMoveUp(line, column);
    }
}