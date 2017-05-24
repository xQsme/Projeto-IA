package forkliftpuzzle;

import agent.Action;
import agent.Piece;
import agent.State;
import java.util.ArrayList;
import java.util.Arrays;

public class ForkliftPuzzleState extends State implements Cloneable {

    private int[][] matrix;
    private int lineForklift;
    private int columnForklift;

    public ForkliftPuzzleState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                this.matrix[i][j]=matrix[i][j];
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

    public boolean canMoveUp(int line, int column) {
        return line != 0 && matrix[line-1][column] == 0;
    }

    public boolean canMoveRight(int line, int column) {
        return column != matrix[0].length - 1 && (matrix[line][column+1]
                    == 0 || (matrix[line][column] == 1 && matrix[line][column+1] == -1));
    }

    public boolean canMoveDown(int line, int column) {
        return line != matrix.length - 1 && matrix[line+1][column] == 0;
    }

    public boolean canMoveLeft(int line, int column) {
        return column != 0 && matrix[line][column-1] == 0;
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class ForkliftPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp(int line, int column) {
        matrix[line-1][column] = matrix[line][column];
        matrix[line][column] = 0;
        /*if(line+1 < matrix.length -1) {
            if (matrix[line+1][column] != type) {


            } else {
                matrix[line-1][column] = type;
                matrix[line-1][column].setLine(line-1);
                matrix[line][column] = matrix[line+1][column];
                matrix[line][column].setLine(line);
                matrix[line+1][column] = 0;
            }
        }else{
            matrix[line-1][column] = piece;
            matrix[line-1][column].setLine(line-1);
            matrix[line][column] = new Piece(0, line, column);
        }*/
    }

    public void moveRight(int line, int column) {
        matrix[line][column+1] = matrix[line][column];
        matrix[line][column] = 0;
        /*if(column-1 > 0) {
            if (!matrix[line][column-1].equals(piece)) {

            } else {
                backup = matrix[line][column+1];
                matrix[line][column+1] = piece;
                matrix[line][column+1].setColumn(column+1);
                matrix[line][column] = matrix[line][column-1];
                matrix[line][column].setColumn(column);
                matrix[line][column] = backup;
                matrix[line][column].setColumn(column);
            }
        }else {
            backup = matrix[line][column + 1];
            matrix[line][column + 1] = piece;
            matrix[line][column + 1].setColumn(column + 1);
            matrix[line][column] = backup;
            matrix[line][column].setColumn(column);
        }*/
        if (matrix[line][column+1] == 1) {
            columnForklift = column+1;
        }
        //System.out.println("Moving (" + line + ", " + column + ") to (" + line + ", " + column+1 + ")");
    }

    public void moveDown(int line, int column) {
        matrix[line+1][column] = matrix[line][column];
        matrix[line][column] = 0;

        /*if(line-1 > 0) {
            if (!matrix[line-1][column].equals(piece)) {
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
        }*/
    }

    public void moveLeft(int line, int column) {
        matrix[line][column-1] = matrix[line][column];
        matrix[line][column] = 0;
        /*if(column+1 < matrix[0].length) {
            if (!matrix[line][column+1].equals(piece)) {

            } else {
                backup = matrix[line][column-1];
                matrix[line][column-1] = piece;
                matrix[line][column-1].setColumn(column-1);
                matrix[line][column] = matrix[line][column+1];
                matrix[line][column].setColumn(column);
                matrix[line][column] = backup;
                matrix[line][column].setColumn(column);            }
        }else{
            backup = matrix[line][column-1];
            matrix[line][column-1] = piece;
            matrix[line][column-1].setColumn(column-1);
            matrix[line][column] = backup;
            matrix[line][column].setColumn(column);
        }*/
        if (matrix[line][column-1] == 1) {
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
                if (matrix[lineForklift][i] != 0) {
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
        return matrix[line][column];
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
        int id=0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] > 0)
                    pieces.add(new Piece(matrix[i][j], id++, i, j));
            }
        }
        return pieces;
    }
}
