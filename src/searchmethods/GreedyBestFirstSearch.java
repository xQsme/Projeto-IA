package searchmethods;

import agent.State;

import java.util.List;

public class GreedyBestFirstSearch extends InformedSearch {

    //f = h
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        for (State s : successors) {
            double g = parent.getG() + s.getAction().getCost();
            if (!frontier.containsState(s)) {
                if (!explored.contains(s)) {
                    frontier.add(new Node(s, parent, g, heuristic.compute(s)));
                }
            }else if (frontier.getNode(s).getG()>g){
                frontier.removeNode(s);
                frontier.add(new Node (s, parent, g, heuristic.compute(s)));
            }

        }
    }

    @Override
    public String toString() {
        return "Greedy best first search";
    }
}
