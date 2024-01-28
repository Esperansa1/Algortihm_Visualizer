package Visualizer.Managers;

import Visualizer.Cell;
import Visualizer.MazeAlgorithms.MazeAlgorithm;
import Visualizer.MazeAlgorithms.Prims;
import Visualizer.MazeAlgorithms.SAW;

import java.util.ArrayList;

public class MazeManager {
    private final ArrayList<MazeAlgorithm> mazeAlgorithm;
    private int currentAlgorithmIndex;

    public MazeManager() {
        mazeAlgorithm = new ArrayList<>();

        mazeAlgorithm.add(new SAW());
        mazeAlgorithm.add(new Prims());
    }

    public boolean isRunning(){
        return getCurrentAlgorithm().isRunning();
    }
    private MazeAlgorithm getCurrentAlgorithm(){
        return mazeAlgorithm.get(currentAlgorithmIndex);
    }

    public void stepMazeGeneration(Cell[][] cells){
        getCurrentAlgorithm().stepMazeGeneration(cells);
    }

    public void initializeMazeGeneration(Cell[][] cells){
        getCurrentAlgorithm().initializeMazeGeneration(cells);
    }

    public String getCurrentAlgorithmName(){
        return getCurrentAlgorithm().toString();
    }

    public void nextAlgorithm(){
        currentAlgorithmIndex++;
        currentAlgorithmIndex %= mazeAlgorithm.size();
    }

}
