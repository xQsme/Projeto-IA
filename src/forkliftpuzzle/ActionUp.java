package forkliftpuzzle;

import agent.Action;
import agent.Piece;

public class ActionUp extends Action<ForkliftPuzzleState>{

    public ActionUp(Piece piece){
        super(1, piece);
    }

    public void execute(ForkliftPuzzleState state){
        state.moveUp(piece);
        state.setAction(this);
    }

    public boolean isValid(ForkliftPuzzleState state){
        return state.canMoveUp(piece);
    }
}