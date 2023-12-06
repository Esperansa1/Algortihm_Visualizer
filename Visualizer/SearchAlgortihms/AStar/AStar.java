package Visualizer.SearchAlgortihms.AStar;

import Visualizer.Cell;
import Visualizer.SearchAlgortihms.SearchAlgorithm;

import java.util.Arrays;

public class AStar extends SearchAlgorithm<AStarCell> {

    private AStarCell startCell, endCell;
    private AStarCell[][] cells;

    public void initializeNeighbours(){

        boolean allowDiagonals = false;;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j].setupNeighbours(cells, allowDiagonals);
            }
        }

    }

    private void copyCellData(Cell[][] starting_data, int i, int j){
//        Cell.CellType type = starting_data[i][j].getCellType();
        this.cells[i][j] = new AStarCell(starting_data[i][j]);
        starting_data[i][j] = this.cells[i][j];
//        starting_data[i][j].setCellType(type);

    }


    private void initializeCellArray(Cell[][] starting_cells, Cell startCell, Cell endCell){
        this.cells = new AStarCell[starting_cells.length][starting_cells[0].length];

        starting_cells[startCell.getRow()][startCell.getCol()] = startCell;
        starting_cells[endCell.getRow()][endCell.getCol()] = endCell;

        for (int i = 0; i < starting_cells.length; i++) {
            for (int j = 0; j < starting_cells[i].length; j++) {
                copyCellData(starting_cells, i, j);
            }
        }



        this.startCell = this.cells[startCell.getRow()][startCell.getCol()];
        this.endCell = this.cells[endCell.getRow()][endCell.getCol()];

        this.openSet.add(this.startCell);

    }

    private void initializeCellValues(){
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                AStarCell cell = cells[i][j];

                cell.g = 0;
                cell.h = cell.euclideanDist(endCell);
                cell.f = cell.g + cell.h;
            }
        }
    }

    @Override
    public void initializeSearch(Cell[][] starting_cells, Cell startCell, Cell endCell) {
        super.initializeSearch(starting_cells, startCell, endCell);

        System.out.println(Arrays.toString(startCell.getWalls()));
        System.out.println(Arrays.toString(starting_cells[startCell.getRow()][startCell.getCol()].getWalls()));

        initializeCellArray(starting_cells, startCell, endCell);
        initializeCellValues();
        initializeNeighbours();

        System.out.println("Initialized");

    }

    public AStarCell getLowestFScore() {
        if (openSet.isEmpty()) {
            return null;
        }

        AStarCell bestCell = openSet.get(0);

        for (AStarCell cell : openSet) {
            System.out.println(cell);
            if (cell.f < bestCell.f) {
                bestCell = cell;
            }
        }

        return bestCell;
    }

    @Override
    public void stepSearch() {
        if (isFinished || openSet.isEmpty()) {
            isFinished = true;
            System.out.println("Finished");
            return;
        }

        AStarCell currentCell = getLowestFScore();

        openSet.remove(currentCell);
        closedSet.add(currentCell);
        setupCellTypes(startCell, endCell);

        if (isGoal(endCell, currentCell)) {
            reconstructPath(endCell, startCell);
            isFinished = true;
            return;
        }

        for (AStarCell neighbour : currentCell.getNeighbours()) {
            if (neighbour.getCellType() == Cell.CellType.WALL || closedSet.contains(neighbour) || !isPossibleToMove(currentCell, neighbour))
                continue;

            double tentativeGScore = currentCell.g + currentCell.euclideanDist(neighbour);

            if (!openSet.contains(neighbour)) {
                openSet.add(neighbour);
            }else if(tentativeGScore >= neighbour.g){
                continue;
            }

            neighbour.setCameFrom(currentCell);
            neighbour.g = tentativeGScore;
            neighbour.f = tentativeGScore + neighbour.h;

        }
        reconstructPath(currentCell, startCell);


    }
}
