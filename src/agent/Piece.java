package agent;

import java.util.ArrayList;

/**
 * Created by The Law on 22/05/2017.
 */
public class Piece {

    private int type, id, line, column;

    public Piece(int type, int id, int line, int column) {
        this.type = type;
        this.id = id;
        this.line = line;
        this.column = column;
    }

    public int getType() {
        return type;
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

    public int getId() {
        return id;
    }
}
