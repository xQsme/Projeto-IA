package agent;


public abstract class Action <S extends State>{
    private double cost;
    protected Piece piece;

    public Action(double cost, Piece piece){
        this.cost = cost;
        this.piece=piece;
    }

    public abstract void execute(S State);

    public abstract boolean isValid(S State);

    public double getCost(){
        return cost;
    }
}