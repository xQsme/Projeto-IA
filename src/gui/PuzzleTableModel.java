 package gui;

import eightpuzzle.EightPuzzleEvent;
import eightpuzzle.EightPuzzleListener;
import eightpuzzle.EightPuzzleState;
import javax.swing.table.AbstractTableModel;

public class PuzzleTableModel extends AbstractTableModel implements EightPuzzleListener{

    private EightPuzzleState puzzle;

    public PuzzleTableModel(EightPuzzleState puzzle) {
        if(puzzle == null){
            throw new NullPointerException("Puzzle cannot be null");
        }
        this.puzzle = puzzle;
        this.puzzle.addListener(this);
    }

    public int getColumnCount() {
        return puzzle.getNumLines();
    }

    public int getRowCount() {
        return puzzle.getNumColumns();
    }

    public Object getValueAt(int row, int col) {
        return new Integer(puzzle.getTileValue(row, col));
    }

    public void puzzleChanged(EightPuzzleEvent pe){
        fireTableDataChanged();
        try{
            Thread.sleep(500);
        }catch(InterruptedException ignore){
        }
    }

    public void setPuzzle(EightPuzzleState puzzle){
        if(puzzle == null){
          throw new NullPointerException("Puzzle cannot be null");
        }
        this.puzzle.removeListener(this);
        this.puzzle = puzzle;
        puzzle.addListener(this);
        fireTableDataChanged();
    }
}
