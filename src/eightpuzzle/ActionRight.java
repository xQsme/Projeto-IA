package eightpuzzle;

import agent.Action;

public class ActionRight extends Action<EightPuzzleState>{

    public ActionRight(){
        super(1);
    }

    public void execute(EightPuzzleState state){
        state.moveRight();
        state.setAction(this);
    }

    public boolean isValid(EightPuzzleState state){
        return state.canMoveRight();
    }
}