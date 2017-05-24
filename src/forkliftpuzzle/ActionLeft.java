package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionLeft extends Action<ForkliftPuzzleState>{

    public ActionLeft(int line, int column){
        super(1, line, column);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveLeft(line, column);
        column = column-1;
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveLeft(line, column);
    }
}
