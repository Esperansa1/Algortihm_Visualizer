package Visualizer.View;

import Visualizer.Cell;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CellColors {

    private static final Map<Cell.CellType, Color> cellColorMap = new HashMap<>();

    static {
        cellColorMap.put(Cell.CellType.EMPTY, Color.WHITE);
        cellColorMap.put(Cell.CellType.WALL, Color.BLACK);
        cellColorMap.put(Cell.CellType.PATH, Color.MAGENTA);
        cellColorMap.put(Cell.CellType.OPEN_SET, Color.GREEN);
        cellColorMap.put(Cell.CellType.CLOSE_SET, Color.RED);
        cellColorMap.put(Cell.CellType.END_POINT, Color.YELLOW);
        cellColorMap.put(Cell.CellType.START_POINT, Color.YELLOW);
        cellColorMap.put(Cell.CellType.HIGHLIGHT, Color.PINK);

    }

    public static Color getCellColor(Cell cell) {
        return getCellColor(cell.getCellType());
    }

    public static Color getCellColor(Cell.CellType type) {
        return cellColorMap.get(type);
    }

    public static void changeColor(Cell.CellType type, Color color) {
        cellColorMap.put(type, color);
    }
}