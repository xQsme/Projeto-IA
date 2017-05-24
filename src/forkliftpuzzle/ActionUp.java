package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionUp extends Action<ForkliftPuzzleState>{

    public ActionUp(int line, int column){
        super(1, line, column);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveUp(line, column);
        line=line-1;
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveUp(line, column);
    }
}