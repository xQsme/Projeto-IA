package searchmethods;

import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.NodeCollection;

public abstract class GraphSearch<L extends NodeCollection> implements SearchMethod {

    protected L frontier;
    protected Set<State> explored = new HashSet<State>();
    protected Statistics statistics = new Statistics();    
    protected boolean stopped;

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        return graphSearch(problem);
    }

    /*
     function GRAPH-SEARCH(problem) returns a solution, or failure
        initialize the frontier using the initial state of problem
        initialize the explored set to be empty
        while(frontier is not empty)
            remove the first node from the frontier
            if the node contains a goal state then return the corresponding solution
            add the node to the explored set
            expand the node, adding the resulting nodes to the frontier only if
                not in the frontier or explored set
        return failure
     */
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        explored.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node n = frontier.poll();
            if (problem.isGoal(n.getState())) {
                statistics.end();
                return new Solution(problem, n);
            }
            explored.add(n.getState());
            List<State> successors = problem.executeActions(n.getState());
            addSuccessorsToFrontier(successors, n);
            computeStatistics(successors.size());
        }
        return null;
    }

    public abstract void addSuccessorsToFrontier(List<State> successors, Node parent);

    protected void computeStatistics(int successorsSize) {
        statistics.numExpandedNodes++;
        statistics.numGeneratedNodes += successorsSize;
        statistics.maxFrontierSize = Math.max(statistics.maxFrontierSize, frontier.size());
    }
    
    public Statistics getStatistics(){
        return statistics;
    }

    public void stop() {
        stopped = true;
    }

    public boolean hasBeenStopped() {
        return stopped;
    }
}
    