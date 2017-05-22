package forkliftpuzzle;

import agent.Action;

public class ActionRight extends Action<ForkliftPuzzleState>{

    public ActionRight(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state, int line, int column){
        state.moveRight(line, column);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state, int column){
        return state.canMoveRight(column);
    }
}