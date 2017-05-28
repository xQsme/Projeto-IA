package searchmethods;

public class Statistics {
    public int numExpandedNodes;
    public int numGeneratedNodes = 1; //due to the initial node
    public int maxFrontierSize;
    public double time;
    
    public void reset(){
        numExpandedNodes = 0;
        numGeneratedNodes = 1;
        maxFrontierSize = 0;
        time = System.nanoTime();
    }

    public void end(){
        time = (System.nanoTime()-time) / 1000000.0;
    }
}
