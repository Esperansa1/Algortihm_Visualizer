package Visualizer.View;

import Visualizer.Cell;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The CellColors class provides a centralized way to manage the color scheme used for
 * rendering different types of cells within the Visualizer application. It defines a
 * mapping between Cell.CellType values and their corresponding Colors.
 */
public class CellColors {

    /**
     * A map storing the default color associations for each Cell.CellType.
     */
    private static final Map<Cell.CellType, Color> cellColorMap = new HashMap<>();

    /**
     * Static initializer block populates the cellColorMap with default color values.
     */
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

    /**
     * Retrieves the Color associated with a specific Cell object.
     * @param cell The Cell object whose color is needed.
     * @return The Color corresponding to the cell's CellType.
     */
    public static Color getCellColor(Cell cell) {
        return getCellColor(cell.getCellType());
    }

    /**
     * Retrieves the Color associated with a Cell.CellType.
     * @param type The Cell.CellType whose color is needed.
     * @return The Color corresponding to the provided type.
     */
    public static Color getCellColor(Cell.CellType type) {
        return cellColorMap.get(type);
    }

    /**
     * Updates the Color associated with a specific Cell.CellType.
     * @param type The Cell.CellType whose color is to be changed.
     * @param color The new Color to associate with the type.
     */
    public static void changeColor(Cell.CellType type, Color color) {
        cellColorMap.put(type, color);
    }
}
