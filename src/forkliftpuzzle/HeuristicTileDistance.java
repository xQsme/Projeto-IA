package forkliftpuzzle;

import agent.Heuristic;

public class HeuristicTileDistance extends Heuristic<ForkliftPuzzleProblem, ForkliftPuzzleState>{

    public double compute(ForkliftPuzzleState state){
        return state.computeDistance();
        
    }
    
    @Override
    public String toString(){
        return "Tiles distance to final position";
    }
}