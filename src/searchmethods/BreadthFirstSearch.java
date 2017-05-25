package searchmethods;

import agent.State;
import utils.NodeLinkedList;

import java.util.List;

public class BreadthFirstSearch extends GraphSearch<NodeLinkedList> {

    public BreadthFirstSearch() {
        frontier = new NodeLinkedList();
    }
    
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        for(State s: successors){
            if(!frontier.containsState(s) && !explored.contains(s)){
                frontier.addLast(new Node(s, parent));
            } 
        }
    }

    @Override
    public String toString() {
        return "Breadth first search";
    }
}