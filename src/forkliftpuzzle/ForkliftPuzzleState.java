package forkliftpuzzle;

import agent.Action;
import agent.Piece;
import agent.State;
import java.util.ArrayList;
import java.util.Arrays;

public class ForkliftPuzzleState extends State implements Cloneable {

    private Piece[][] matrix;
    private int lineForklift;
    private int columnForklift;

    public ForkliftPuzzleState(int[][] matrix) {
        this.matrix = new Piece[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                this.matrix[i][j] = new Piece(matrix[i][j], i, j);
                if (matrix[i][j] == 1) {
                    lineForklift = i;
                    columnForklift = j;
                }
            }
        }
    }

    public boolean isGoal(){
        return columnForklift == matrix.length-1;
    }

    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp(Piece piece) {
        return piece.getLine() != 0 && matrix[piece.getLine()-1][piece.getColumn()].getType() == 0;
    }

    public boolean canMoveRight(Piece piece) {
        return piece.getColumn() != matrix[0].length - 1 && (matrix[piece.getLine()][piece.getColumn()+1].getType()
                    == 0 || (piece.getType() == 1 && matrix[piece.getLine()][piece.getColumn()+1].getType() == -1));
    }

    public boolean canMoveDown(Piece piece) {
        return piece.getLine() != matrix.length - 1 && matrix[piece.getLine()+1][piece.getColumn()].getType() == 0;
    }

    public boolean canMoveLeft(Piece piece) {
        return piece.getColumn() != 0 && matrix[piece.getLine()][piece.getColumn()-1].getType() == 0;
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class ForkliftPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp(Piece piece) {
        int line = piece.getLine();
        int column = piece.getColumn();
        if(line+1 < matrix.length -1) {
            if (!matrix[line+1][column].equals(piece)) {
                matrix[line-1][column] = piece;
                matrix[line-1][column].setLine(line-1);
                matrix[line][column] = new Piece(0, line, column);

            } else {
                matrix[line-1][column] = piece;
                matrix[line-1][column].setLine(line-1);
                matrix[line][column] = matrix[line+1][column];
                matrix[line][column].setLine(line);
                matrix[line+1][column] = new Piece(0, line+1, column);
            }
        }else{
            matrix[line-1][column] = piece;
            matrix[line-1][column].setLine(line-1);
            matrix[line][column] = new Piece(0, line, column);
        }
    }

    public void moveRight(Piece piece) {
        int line = piece.getLine();
        int column = piece.getColumn();
        if(column-1 > 0) {
            if (!matrix[line][column-1].equals(piece)) {
                matrix[line][column+1] = piece;
                matrix[line][column+1].setColumn(column+1);
                matrix[line][column] = new Piece(0, line, column);
            } else {
                matrix[line][column+1] = piece;
                matrix[line][column+1].setColumn(column+1);
                matrix[line][column] = matrix[line][column-1];
                matrix[line][column].setLine(column);
                matrix[line][column-1] = new Piece(0, line, column-1);
            }
        }else{
            matrix[line][column+1] = piece;
            matrix[line][column+1].setColumn(column+1);
            matrix[line][column] = new Piece(0, line, column);
        }
        if (line == lineForklift && column == columnForklift) {
            columnForklift = column+1;
        }
    }

    public void moveDown(Piece piece) {
        int line = piece.getLine();
        int column = piece.getColumn();
        if(line-1 > 0) {
            if (!matrix[line-1][column].equals(piece)) {
                matrix[line+1][column] = piece;
                matrix[line+1][column].setLine(line+1);
                matrix[line][column] = new Piece(0, line, column);
            } else {
                matrix[line+1][column] = piece;
                matrix[line+1][column].setLine(line+1);
                matrix[line][column] = matrix[line-1][column];
                matrix[line][column].setLine(line);
                matrix[line-1][column] = new Piece(0, line-1, column);
            }
        }else{
            matrix[line+1][column] = piece;
            matrix[line+1][column].setLine(line+1);
            matrix[line][column] = new Piece(0, line, column);
        }
    }

    public void moveLeft(Piece piece) {
        int line = piece.getLine();
        int column = piece.getColumn();
        if(column+1 < matrix[0].length) {
            if (!matrix[line][column+1].equals(piece)) {
                matrix[line][column-1] = piece;
                matrix[line][column-1].setColumn(column-1);
                matrix[line][column] = new Piece(0, line, column);
            } else {
                matrix[line][column-1] = piece;
                matrix[line][column-1].setColumn(column-1);
                matrix[line][column] = matrix[line][column+1];
                matrix[line][column].setColumn(column);
                matrix[line][column+1] = new Piece(0, line, column+1);
            }
        }else{
            matrix[line][column-1] = piece;
            matrix[line][column-1].setColumn(column-1);
            matrix[line][column] = new Piece(0, line, column);
        }
        if (line == lineForklift && column == columnForklift) {
            columnForklift = column-1;
        }
    }

    public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }

    public double computeOccupiedTiles() {
        double h = 0;
        for (int i = 0; i < getNumColumns(); i++) {
                if (matrix[lineForklift][i].getType() != 0) {
                    h++;
                }
        }
        return h;
    }
    
    public double computeDistance() {
        return matrix.length - columnForklift;
    }

    public int getTileValue(int line, int column) {
        if (!(line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length)) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column].getType();
    }

    public boolean isValidPosition(int line, int column) {
        return line >= 0 && line < matrix.length && column >= 0 && column < matrix[0].length;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ForkliftPuzzleState)) {
            return false;
        }

        ForkliftPuzzleState o = (ForkliftPuzzleState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public Object clone() {
        int[][] matrix = new int[this.matrix.length][this.matrix[0].length];
        for(int i = 0; i<this.matrix.length; i++){
            for(int j = 0; j<this.matrix[0].length; j++){
                matrix[i][j]=this.matrix[i][j].getType();
            }
        }
        return new ForkliftPuzzleState(matrix);
    }
    //Listeners
    private transient ArrayList<ForkliftPuzzleListener> listeners = new ArrayList<ForkliftPuzzleListener>(3);

    public synchronized void removeListener(ForkliftPuzzleListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(ForkliftPuzzleListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(ForkliftPuzzleEvent pe) {
        for (ForkliftPuzzleListener listener : listeners) {
            listener.puzzleChanged(null);
        }
    }

    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces=new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                pieces.add(matrix[i][j]);
            }
        }
        return pieces;
    }
}
