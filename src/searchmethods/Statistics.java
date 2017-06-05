package searchmethods;

public class Statistics {
    public long numExpandedNodes;
    public long numGeneratedNodes = 1; //due to the initial node
    public long maxFrontierSize;
    public long time;
    
    public void reset(){
        numExpandedNodes = 0;
        numGeneratedNodes = 1;
        maxFrontierSize = 0;
        time = System.nanoTime();
    }

    public void end(){
        time = (System.nanoTime()-time) / 1000000;
    }
}
