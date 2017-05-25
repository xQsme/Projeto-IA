package forkliftpuzzle;

import agent.Heuristic;

public class HeuristicOccupiedRightSide extends Heuristic<ForkliftPuzzleProblem, ForkliftPuzzleState> {

    public double compute(ForkliftPuzzleState state) {
        return state.computeOccupiedRight();
        
    }

    @Override
    public String toString() {
        return "Occupied tiles on right half";
    }
}
