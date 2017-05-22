package forkliftpuzzle;

import agent.Action;

public class ActionDown extends Action<ForkliftPuzzleState>{

    public ActionDown(){
        super(1);
    }

    public void execute(ForkliftPuzzleState state, int line, int column){
        state.moveDown(line, column);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state, int line){
        return state.canMoveDown(line);
    }
}