package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionLeft extends Action<ForkliftPuzzleState>{

    public ActionLeft(Piece piece){
        super(1, piece);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveLeft(piece);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveLeft(piece);
    }
}
