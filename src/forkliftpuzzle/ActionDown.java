package forkliftpuzzle;

import agent.Action;

public class ActionDown extends Action<ForkliftPuzzleState>{

    public ActionDown(int id){
        super(1, id);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveDown(id);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveDown(id);
    }
}