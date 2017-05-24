package forkliftpuzzle;

import agent.Action;

public class ActionLeft extends Action<ForkliftPuzzleState>{

    public ActionLeft(int id){
        super(1, id);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveLeft(id);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveLeft(id);
    }
}
