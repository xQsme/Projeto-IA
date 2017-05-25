package agent;


public abstract class Action <S extends State>{
    private int cost;
    protected int id;

    public Action(int cost, int id){
        this.cost = cost;
        this.id = id;
    }

    public abstract void execute(S State);

    public abstract boolean isValid(S State);

    public double getCost(){
        return cost;
    }

    public int getId() {
        return id;
    }
}