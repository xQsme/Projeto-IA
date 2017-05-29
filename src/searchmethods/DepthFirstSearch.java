package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import utils.NodeLinkedList;

import java.util.List;

public class DepthFirstSearch extends GraphSearch<NodeLinkedList> {

    public DepthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    //Graph Search without explored list
    @Override
    protected Solution graphSearch(Problem problem) {
        statistics.reset();
        frontier.clear();

        frontier.add(new Node (problem.getInitialState()) );
        while(!frontier.isEmpty() && !stopped){
            Node n= frontier.poll();
            if (problem.isGoal(n.getState())){
                statistics.end();
                return new Solution(problem, n);
            }
           
            List<State> successors= problem.executeActions(n.getState());
            addSuccessorsToFrontier(successors, n);
            computeStatistics(successors.size());
            
        }
        statistics.end();
        return null;
    }

    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
    //    for (int i = (successors.size() - 1); i >= 0; i--) {//começa à esquerda
             for (State s: successors) {// começa pela direita
           // State s = successors.get(i);
            if (!frontier.containsState(s)) {
                Node n = new Node(s, parent);
                if (!n.isCycle()) {
                    frontier.addFirst(n);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Depth first search";
    }
}
