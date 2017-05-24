package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionDown extends Action<ForkliftPuzzleState>{

    public ActionDown(int line, int column){
        super(1, line, column);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveDown(line, column);
        line=line+1;
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveDown(line, column);
    }
}