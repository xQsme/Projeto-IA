package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionRight extends Action<ForkliftPuzzleState>{

    public ActionRight(Piece piece){
        super(1, piece);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveRight(piece);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveRight(piece);
    }
}