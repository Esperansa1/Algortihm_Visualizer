package Visualizer.Model;

import Visualizer.Cell;
import Visualizer.Model.MazeAlgorithms.MazeAlgorithm;
import Visualizer.Model.MazeAlgorithms.Prims;
import Visualizer.Model.MazeAlgorithms.SAW;
import Visualizer.Observable;

import java.util.ArrayList;

public class MazeManager extends Observable {
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
        notifyObservers();
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
