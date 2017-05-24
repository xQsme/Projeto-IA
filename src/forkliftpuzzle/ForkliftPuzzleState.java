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

    public ForkliftPuzzleState(int[][] matrix, boolean first) {
        this.matrix = new int[matrix.length][matrix[0].length];
        int[] count = new int[11];
        if(first) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if(matrix[i][j] < 2) {
                        this.matrix[i][j] = matrix[i][j];
                        if (matrix[i][j] == 1) {
                            lineForklift = i;
                            columnForklift = j;
                        }
                    }
                    else
                        this.matrix[i][j] = matrix[i][j] + 10*count[matrix[i][j]+1]++;
                }
            }
        }else{
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    this.matrix[i][j] = matrix[i][j];
                    if (matrix[i][j] == 1) {
                        lineForklift = i;
                        columnForklift = j;
                    }
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

    public boolean canMoveUp(int id) {
        int position[]=findById(id);
        return position[0] != 0 && matrix[position[0]-1][position[1]] == 0;
    }

    public boolean canMoveRight(int id) {
        int position[]=findById(id);
        return position[1] != matrix[0].length - 1 && (matrix[position[0]][position[1]+1]
                    == 0 || (matrix[position[0]][position[1]] == 1 && matrix[position[0]][position[1]+1] == -1));
    }

    public boolean canMoveDown(int id) {
        int position[]=findById(id);
        return position[0] != matrix.length - 1 && matrix[position[0]+1][position[1]] == 0;
    }

    public boolean canMoveLeft(int id) {
        int position[]=findById(id);
        return position[1] != 0 && matrix[position[0]][position[1]-1] == 0;
    }

    /*
     * In the next four methods we don't verify if the actions are valid.
     * This is done in method executeActions in class ForkliftPuzzleProblem.
     * Doing the verification in these methods would imply that a clone of the
     * state was created whether the operation could be executed or not.
     */
    public void moveUp(int id) {
        int position[]=findById(id);
        int count=1;
        for(int i = 1; i < matrix[0].length; i++){
            if(position[0]+i < matrix.length){
                if(matrix[position[0]+i][position[1]]%10 == matrix[position[0]][position[1]]%10)
                    count++;
                else
                    break;
            }else{
                break;
            }
        }
        int i;
        for(i = 0; i < count; i++){
            matrix[position[0]-1+i][position[1]] = matrix[position[0]+i][position[1]];
        }
        matrix[position[0]+i-1][position[1]] = 0;
    }

    public void moveRight(int id) {
        int position[]=findById(id); //1,3
        int count=1;
        for(int i = 1; i < matrix[0].length; i++){
            if(position[1]-i >= 0){
                if(matrix[position[0]][position[1]-i]%10 == matrix[position[0]][position[1]]%10)
                    count++;
                else
                    break;
            }else{
                break;
            }
        }
        int i;
        for(i = 0; i < count; i++){
            matrix[position[0]][position[1]+1-i] = matrix[position[0]][position[1]-i];
        }
        matrix[position[0]][position[1]-i+1] = 0;
        if (matrix[position[0]][position[1]+1] == 1) {
            columnForklift = position[1]+1;
        }
    }

    public void moveDown(int id) {
        int position[]=findById(id);
        int count=1;
        for(int i = 1; i < matrix.length; i++){
            if(position[0]-i >= 0){
                if(matrix[position[0]-i][position[1]]%10 == matrix[position[0]][position[1]]%10)
                    count++;
                else
                    break;
            }else{
                break;
            }
        }
        int i;
        for(i = 0; i < count; i++){
            matrix[position[0]+1-i][position[1]] = matrix[position[0]-i][position[1]];
        }
        matrix[position[0]-i+1][position[1]] = 0;
    }

    public void moveLeft(int id) {
        int position[]=findById(id);
        int count=1;
        for(int i = 1; i < matrix[0].length; i++){
            if(position[1]+i < matrix[0].length){
                if(matrix[position[0]][position[1]+i]%10 == matrix[position[0]][position[1]]%10)
                    count++;
                else
                    break;
            }else{
                break;
            }
        }
        int i;
        for(i = 0; i < count; i++){
            matrix[position[0]][position[1]-1+i] = matrix[position[0]][position[1]+i];
        }
        matrix[position[0]][position[1]+i-1] = 0;
        if (matrix[position[0]][position[1]-1] == 1) {
            columnForklift = position[1]-1;
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
        for (int i = lineForklift+1; i < getNumColumns(); i++) {
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
        return new ForkliftPuzzleState(matrix, false);
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
                if(matrix[i][j] > 0)
                    pieces.add(new Piece(matrix[i][j]));
            }
        }
        return pieces;
    }

    private int[] findById(int id){
        for (int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(matrix[i][j] == id){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }
}
