package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;
import utils.NodeLinkedList;

public class DepthFirstSearch extends GraphSearch<NodeLinkedList> {

    public DepthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    //Graph Search without explored list
    @Override
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node n = frontier.poll();
            if (problem.isGoal(n.getState())) {
                statistics.end();
                return new Solution(problem, n);
            }
            List<State> successors = problem.executeActions(n.getState());
            addSuccessorsToFrontier(successors, n);
            computeStatistics(successors.size());
        }
        return null;
    }

    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        for (State s : successors) {
            if (!frontier.containsState(s)) {
                Node node = new Node(s, parent);
                if (!node.isCycle()) {
                    frontier.addFirst(node);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Depth first search";
    }
}
