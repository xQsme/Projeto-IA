package agent;

public abstract class State{

    /**
     * Action that generated this state.
     */
    protected Action action;

    public State(){
    }

    public abstract void executeAction(Action action, int line, int column);
    
    public Action getAction(){
        return action;
    }

    public void setAction(Action action){
        this.action = action;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}