package forkliftpuzzle;

import agent.Heuristic;

public class HeuristicTilesOutOfPlace extends Heuristic<ForkliftPuzzleProblem, ForkliftPuzzleState> {

    public double compute(ForkliftPuzzleState state) {
        return state.computeTilesOutOfPlace();
        
    }

    @Override
    public String toString() {
        return "Tiles out of place";
    }
}
