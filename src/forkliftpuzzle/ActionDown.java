package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionDown extends Action<ForkliftPuzzleState>{

    public ActionDown(Piece piece){
        super(1, piece);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveDown(piece);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveDown(piece);
    }
}