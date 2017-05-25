package forkliftpuzzle;

import agent.Heuristic;

public class HeuristicSizeObjectsOnPath extends Heuristic<ForkliftPuzzleProblem, ForkliftPuzzleState> {

    public double compute(ForkliftPuzzleState state) {
        return state.computeSizeObjectsOnPath();
        
    }

    @Override
    public String toString() {
        return "Size of objects on path";
    }
}
