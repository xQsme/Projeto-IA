package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionRight extends Action<ForkliftPuzzleState>{

    public ActionRight(int line, int column){
        super(1, line, column);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveRight(line, column);
        column=column+1;
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveRight(line, column);
    }
}