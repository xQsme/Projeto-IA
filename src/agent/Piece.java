package agent;

import java.util.ArrayList;

/**
 * Created by The Law on 22/05/2017.
 */
public class Piece {

    private int type;
    private ArrayList<Action> actions;

    public Piece(int type) {
        this.type = type;
        actions = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public boolean equals(Piece piece){
        return type==piece.getType();
    }
}
