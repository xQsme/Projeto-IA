package forkliftpuzzle;

import agent.Action;

public class ActionDown extends Action<ForkliftPuzzleState>{

    public ActionDown(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveDown();
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveDown();
    }
}