package eightpuzzle;

import agent.Agent;
import java.io.File;
import java.io.IOException;

public class EightPuzzleAgent extends Agent<EightPuzzleState>{
    
    protected EightPuzzleState initialEnvironment;    
    
    public EightPuzzleAgent(EightPuzzleState environemt) {
        super(environemt);
        initialEnvironment = (EightPuzzleState) environemt.clone();
        heuristics.add(new HeuristicTileDistance());
        heuristics.add(new HeuristicTilesOutOfPlace());
        heuristic = heuristics.get(0);
    }
            
    public EightPuzzleState resetEnvironment(){
        environment = (EightPuzzleState) initialEnvironment.clone();
        return environment;
    }

    public EightPuzzleState readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);
        int size=scanner.nextInt();
        int[][] matrix = new int [size][size];
        scanner.nextLine();

        int linha=-1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = scanner.nextInt();
                if(matrix[i][j] == 1){
                    linha = i;
                }
            }
            scanner.nextLine();
        }
        if(linha != -1)
            matrix[linha][size-1]=10;

        initialEnvironment = new EightPuzzleState(matrix);
        resetEnvironment();

        return environment;
    }
}
