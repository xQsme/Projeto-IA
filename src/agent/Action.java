package agent;


public abstract class Action <S extends State>{
    private double cost;
    protected int line, column;

    public Action(double cost, int line, int column){
        this.cost = cost;
        this.line = line;
        this.column = column;
    }

    public abstract void execute(S State);

    public abstract boolean isValid(S State);

    public double getCost(){
        return cost;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}