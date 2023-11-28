package Visualizer;

public class BoardManager {

    private static BoardManager INSTANCE;
    private Cell[][] cells;
    private Cell startCell, endCell;

    public static BoardManager getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new BoardManager();

        return INSTANCE;
    }

    public BoardManager() {

        int rows = Screen.WIDTH / Cell.CELL_SIZE;
        int cols = Screen.HEIGHT / Cell.CELL_SIZE;

        updateCellsArray(rows,cols);

    }

    public Cell getStartCell() {
        return startCell;
    }

    public void setStartCell(Cell startCell) {
        this.startCell = startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public void setEndCell(Cell endCell) {
        this.endCell = endCell;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void updateCellsArray(int rows, int cols){ // Maybe instead of making new array, change it so it will only delete the sides
        this.cells = new Cell[rows][cols];
        startCell = null;
        endCell = null;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.cells[i][j] = new Cell(i,j);
            }
        }

    }

    public void onCellSelected(int row, int col){ // Initializing start, end, walls

        if(cells[row][col].getCellType() == Cell.CellType.START_POINT || cells[row][col].getCellType() == Cell.CellType.END_POINT) return;

        if(startCell == null) {
            startCell = new Cell(row,col);
            this.cells[row][col] = startCell;
            startCell.setCellType(Cell.CellType.START_POINT);
        }
        else if(endCell == null){
            endCell = new Cell(row,col);
            this.cells[row][col] = endCell;
            endCell.setCellType(Cell.CellType.END_POINT);
        }
        else{
            this.cells[row][col].setCellType(Cell.CellType.WALL);
        }
        // Later add Weights

    }

}
