package agent;


public abstract class Action <S extends State>{
    private double cost;

    public Action(double cost){
        this.cost = cost;
    }

    public abstract void execute(S State, int line, int column);

    public abstract boolean isValid(S State, int line, int column);

    public double getCost(){
        return cost;
    }
}