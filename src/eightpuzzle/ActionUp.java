package eightpuzzle;

import agent.Action;

public class ActionUp extends Action<EightPuzzleState>{

    public ActionUp(){
        super(1);
    }

    public void execute(EightPuzzleState state){
        state.moveUp();
        state.setAction(this);
    }

    public boolean isValid(EightPuzzleState state){
        return state.canMoveUp();
    }
}