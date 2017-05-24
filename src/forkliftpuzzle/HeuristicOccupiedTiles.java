package forkliftpuzzle;

import agent.Heuristic;

public class HeuristicOccupiedTiles extends Heuristic<ForkliftPuzzleProblem, ForkliftPuzzleState> {

    public double compute(ForkliftPuzzleState state) {
        return state.computeOccupiedTiles();
        
    }

    @Override
    public String toString() {
        return "Occupied tiles to goal";
    }
}
