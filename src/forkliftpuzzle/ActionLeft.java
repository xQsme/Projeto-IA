package forkliftpuzzle;

import agent.Action;

public class ActionLeft extends Action<ForkliftPuzzleState>{

    public ActionLeft(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state, int line, int column){
        state.moveLeft(line,column);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state, int column){
        return state.canMoveLeft(column);
    }
}
