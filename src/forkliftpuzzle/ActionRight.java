package forkliftpuzzle;

import agent.Action;

public class ActionRight extends Action<ForkliftPuzzleState>{

    public ActionRight(int id){
        super(1, id);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveRight(id);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveRight(id);
    }
}