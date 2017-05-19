package forkliftpuzzle;

import java.util.EventObject;

public class ForkliftPuzzleEvent extends EventObject {

    public ForkliftPuzzleEvent(ForkliftPuzzleState source) {
        super(source);
    }
}
