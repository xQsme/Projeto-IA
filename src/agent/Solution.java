package agent;

import java.util.LinkedList;
import java.util.List;
import searchmethods.Node;

public class Solution {
    private Problem problem;
    private LinkedList<Action> actions = new LinkedList<Action>();

    public Solution(Problem problem, Node goalNode){
        this.problem = problem;
        Node node = goalNode;
        while(node.getParent() != null){
            actions.addFirst(node.getState().getAction());
            node = node.getParent();
        }
    }

    public double getCost(){
        return problem.computePathCost(actions);
    }

    public List<Action> getActions(){
        return actions;
    }
}