package forkliftpuzzle;

import agent.Action;
import agent.State;

import java.util.ArrayList;
import java.util.Arrays;

public class ForkliftPuzzleState extends State implements Cloneable {

    private int[][] matrix;
    private int[][] ids;
    private int lineForklift;
    private int columnForklift;

    public ForkliftPuzzleState(int[][] matrix, int[][] ids) {
        this.matrix = new int[matrix.length][matrix[0].length];
        this.ids = new int[matrix.length][matrix[0].length];
        if(ids == null) {
            int id=0;
            for (int i = 0; i < getNumLines(); i++) {
                for (int j = 0; j < getNumColumns(); j++) {
                    this.matrix[i][j] = matrix[i][j];
                    this.ids[i][j] = id++;
                    if (matrix[i][j] == 1) {
                        lineForklift = i;
                        columnForklift = j;
                    }
                }
            }
        }else{
            for (int i = 0; i < getNumLines(); i++) {
                for (int j = 0; j < getNumColumns(); j++) {
                    this.matrix[i][j] = matrix[i][j];
                    this.ids[i][j] = ids[i][j];
                    if (matrix[i][j] == 1) {
                        lineForklift = i;
                        columnForklift = j;
                    }
                }
            }
        }
    }

    public boolean isGoal(){
        return columnForklift == getNumColumns()-1;
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
        return position[1] != getNumColumns() - 1 && (matrix[position[0]][position[1]+1]
                    == 0 || (matrix[position[0]][position[1]] == 1 && matrix[position[0]][position[1]+1] == -1));
    }

    public boolean canMoveDown(int id) {
        int position[]=findById(id);
        return position[0] != getNumLines() - 1 && matrix[position[0]+1][position[1]] == 0;
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
        int i, count=(matrix[position[0]][position[1]]-1)/2;
        for(i = 0; i < count; i++){
            matrix[position[0]-1+i][position[1]] = matrix[position[0]+i][position[1]];
            ids[position[0]-1+i][position[1]] = ids[position[0]+i][position[1]];
        }
        matrix[position[0]+i-1][position[1]] = 0;
        ids[position[0]+i-1][position[1]] = 9999;
    }

    public void moveRight(int id) {
        int position[]=findById(id);
        int i, count=matrix[position[0]][position[1]]/2;
        if(count == 0)
            count=1;
        for(i = 0; i < count; i++){
            matrix[position[0]][position[1]+1-i] = matrix[position[0]][position[1]-i];
            ids[position[0]][position[1]+1-i] = ids[position[0]][position[1]-i];
        }
        matrix[position[0]][position[1]-i+1] = 0;
        ids[position[0]][position[1]-i+1] = 9999;
        if (matrix[position[0]][position[1]+1] == 1) {
            columnForklift = position[1]+1;
        }
    }

    public void moveDown(int id) {
        int position[]=findById(id);
        int i, count=(matrix[position[0]][position[1]]-1)/2;
        for(i = 0; i < count; i++){
            matrix[position[0]+1-i][position[1]] = matrix[position[0]-i][position[1]];
            ids[position[0]+1-i][position[1]] = ids[position[0]-i][position[1]];
        }
        matrix[position[0]-i+1][position[1]] = 0;
        ids[position[0]-i+1][position[1]] = 9999;
    }

    public void moveLeft(int id) {
        int position[]=findById(id);
        int i, count=matrix[position[0]][position[1]]/2;
        if(count == 0)
            count=1;
        for(i = 0; i < count; i++){
            matrix[position[0]][position[1]-1+i] = matrix[position[0]][position[1]+i];
            ids[position[0]][position[1]-1+i] = ids[position[0]][position[1]+i];
        }
        matrix[position[0]][position[1]+i-1] = 0;
        ids[position[0]][position[1]+i-1] = 9999;
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

    public double computeOccupiedRight(){
        double h = 0;
        for(int i = 0; i<getNumLines(); i++){
            for(int j = getNumColumns()/2-1; j<getNumColumns(); j++){
                if(matrix[i][j] > 1)
                    h++;
            }
        }
        return h;
    }

    public double computeSizeObjectsOnPath(){
        double h = 0;
        for (int i = columnForklift+1; i < getNumColumns(); i++) {
            for(int j=lineForklift+1; j< getNumLines(); j++) {
                if (matrix[j][i] == matrix[lineForklift][i]) {
                    h++;
                } else {
                    break;
                }
            }
            for(int j=lineForklift; j >= 0; j--) {
                if (matrix[j][i] == matrix[lineForklift][i]) {
                    h++;
                } else {
                    break;
                }
            }
        }
        return h;
    }

    public double computeOccupiedAndDistance(){
        return computeOccupiedTiles()+computeDistance();
    }

    public double computeOccupiedTiles() {
        double h = 0;
        for (int i = columnForklift+1; i < getNumColumns(); i++) {
                if (matrix[lineForklift][i] != 0) {
                    h++;
                }
        }
        return h;
    }
    
    public double computeDistance() {
        return getNumColumns() - columnForklift - 1;
    }

    public int getTileValue(int line, int column) {
        if (!(line >= 0 && line < getNumLines() && column >= 0 && column < getNumColumns())) {
            throw new IndexOutOfBoundsException("Invalid position!");
        }
        return matrix[line][column];
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ForkliftPuzzleState)) {
            return false;
        }

        ForkliftPuzzleState o = (ForkliftPuzzleState) other;
        if (getNumLines() != o.getNumLines()) {
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
        for (int i = 0; i < getNumLines(); i++) {
            buffer.append('\n');
            for (int j = 0; j < getNumColumns(); j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public Object clone() {
        return new ForkliftPuzzleState(matrix, ids);
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

    public ArrayList<Integer> getTypes() {
        ArrayList<Integer> types=new ArrayList<>();
        for (int i = 0; i < getNumLines(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                if(matrix[i][j] > 0)
                    types.add(matrix[i][j]);
            }
        }
        return types;
    }

    public ArrayList<Integer> getIds() {
        ArrayList<Integer> idArray=new ArrayList<>();
        for (int i = 0; i < getNumLines(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                if(matrix[i][j] > 0)
                    idArray.add(ids[i][j]);
            }
        }
        return idArray;
    }

    private int[] findById(int id){
        for (int i = 0; i < getNumLines(); i++){
            for(int j = 0; j < getNumColumns(); j++){
                if(ids[i][j] == id){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }
}
