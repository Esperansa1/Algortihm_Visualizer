package Visualizer.Managers;

import Visualizer.Cell;
import Visualizer.MazeAlgorithms.MazeAlgorithm;
import Visualizer.MazeAlgorithms.Prims;
import Visualizer.MazeAlgorithms.SAW;
import Visualizer.SearchAlgortihms.Pledge;

import java.util.ArrayList;

public class MazeManager {
    private final ArrayList<MazeAlgorithm> mazeAlgorithm;
    private int currentAlgorithmIndex;

    public MazeManager() {
        mazeAlgorithm = new ArrayList<>();

        mazeAlgorithm.add(new Prims());
        mazeAlgorithm.add(new SAW());
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


    public void nextAlgorithm(){
        currentAlgorithmIndex++;
        currentAlgorithmIndex %= mazeAlgorithm.size();
    }

}
