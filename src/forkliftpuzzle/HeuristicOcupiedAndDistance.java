package forkliftpuzzle;

import agent.Heuristic;

public class HeuristicOcupiedAndDistance extends Heuristic<ForkliftPuzzleProblem, ForkliftPuzzleState>{

    public double compute(ForkliftPuzzleState state){
        return state.computeOccupiedAndDistance();
        
    }
    
    @Override
    public String toString(){
        return "Tiles distance plus occupied tiles";
    }
}