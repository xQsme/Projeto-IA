package forkliftpuzzle;

import agent.Action;
import agent.State;
import java.util.ArrayList;
import java.util.Arrays;

public class ForkliftPuzzleState extends State implements Cloneable {

    private int[][] matrix;
    private int lineForklift;
    private int columnForklift;

    public ForkliftPuzzleState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == 0) {
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

    public boolean canMoveUp(int line) {
        return line != 0;
    }

    public boolean canMoveRight(int column) {
        return column != matrix.length - 1;
    }

    public boolean canMoveDown(int line) {
        return line != matrix.length - 1;
    }

    public boolean canMoveLeft(int column) {
        return column != 0;
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class ForkliftPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp(int line, int column) {
        matrix[line][column] = matrix[line+1][column];
        matrix[line+1][column] = 0;
        if(line+1 == lineForklift && column == columnForklift){
            lineForklift=line;
        }
    }

    public void moveRight(int line, int column) {
        matrix[line][column] = matrix[line][column-1];
        matrix[line][column-1] = 0;
        if(line == lineForklift && column-1 == columnForklift){
            columnForklift=column;
        }
    }

    public void moveDown(int line, int column) {
        matrix[line][column] = matrix[line-1][column];
        matrix[line-1][column] = 0;
        if(line-1 == lineForklift && column == columnForklift){
            lineForklift=line;
        }
    }

    public void moveLeft(int line, int column) {
        matrix[line][column] = matrix[line][column+1];
        matrix[line][column+1] = 0;
        if(line == lineForklift && column+1 == columnForklift){
            columnForklift=column;
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
        if (!isValidPosition(line, column)) {
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
}
