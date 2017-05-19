package forkliftpuzzle;

import agent.Action;

public class ActionRight extends Action<ForkliftPuzzleState>{

    public ActionRight(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveRight();
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveRight();
    }
}