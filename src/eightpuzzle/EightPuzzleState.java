package eightpuzzle;

import agent.Action;
import agent.State;
import java.util.ArrayList;
import java.util.Arrays;

public class EightPuzzleState extends State implements Cloneable {

    static final int[][] goalMatrix = {{0, 1, 2},
    {3, 4, 5},
    {6, 7, 8}};
    static final int[] linesfinalMatrix = {0, 0, 0, 1, 1, 1, 2, 2, 2};
    static final int[] colsfinalMatrix = {0, 1, 2, 0, 1, 2, 0, 1, 2};
    public static final int SIZE = 3;
    private int[][] matrix;
    private int lineBlank;
    private int columnBlank;

    public EightPuzzleState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                if (this.matrix[i][j] == 0) {
                    lineBlank = i;
                    columnBlank = j;
                }
            }
        }
    }

    public void executeAction(Action action) {
        action.execute(this);
        firePuzzleChanged(null);
    }

    public boolean canMoveUp() {
        return lineBlank != 0;
    }

    public boolean canMoveRight() {
        return columnBlank != matrix.length - 1;
    }

    public boolean canMoveDown() {
        return lineBlank != matrix.length - 1;
    }

    public boolean canMoveLeft() {
        return columnBlank != 0;
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class EightPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp() {
        matrix[lineBlank][columnBlank] = matrix[--lineBlank][columnBlank];
        matrix[lineBlank][columnBlank] = 0;
    }

    public void moveRight() {
        matrix[lineBlank][columnBlank] = matrix[lineBlank][++columnBlank];
        matrix[lineBlank][columnBlank] = 0;
    }

    public void moveDown() {
        matrix[lineBlank][columnBlank] = matrix[++lineBlank][columnBlank];
        matrix[lineBlank][columnBlank] = 0;
    }

    public void moveLeft() {
        matrix[lineBlank][columnBlank] = matrix[lineBlank][--columnBlank];
        matrix[lineBlank][columnBlank] = 0;
    }

    public int getNumLines() {
        return matrix.length;
    }

    public int getNumColumns() {
        return matrix[0].length;
    }

    public double computeTilesOutOfPlace() {
        double h = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0 &&
                        matrix[i][j] != goalMatrix[i][j]) {
                    h++;
                }
            }
        }
        return h;
    }
    
    public double computeTileDistance() {
        double h = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0) {
                    h+=Math.abs(i-linesfinalMatrix[matrix[i][j]])+
                       Math.abs(j-colsfinalMatrix[matrix[i][j]]);
                }
            }
        }
        return h;
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
        if (!(other instanceof EightPuzzleState)) {
            return false;
        }

        EightPuzzleState o = (EightPuzzleState) other;
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
        return new EightPuzzleState(matrix);
    }
    //Listeners
    private transient ArrayList<EightPuzzleListener> listeners = new ArrayList<EightPuzzleListener>(3);

    public synchronized void removeListener(EightPuzzleListener l) {
        if (listeners != null && listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public synchronized void addListener(EightPuzzleListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public void firePuzzleChanged(EightPuzzleEvent pe) {
        for (EightPuzzleListener listener : listeners) {
            listener.puzzleChanged(null);
        }
    }
}
