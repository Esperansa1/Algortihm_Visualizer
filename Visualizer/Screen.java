package Visualizer;

import Visualizer.Managers.BoardManager;
import Visualizer.Managers.MazeManager;
import Visualizer.Managers.SearchManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Screen extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int GRID_WIDTH = 800;
    public static final int GRID_HEIGHT = 800;
    private final JPanel drawingPanel;
    private boolean isClickHeld;
    private final SearchManager searchManager;
    private final MazeManager mazeManager;

    private boolean isBusy;

    public Screen() {
        super("Algorithm Visualizer");

        isBusy = false;

        // Set layout to null
        setLayout(null);

        // Initialize managers
        searchManager = new SearchManager();
        mazeManager = new MazeManager();

        // Get instance of BoardManager
        BoardManager boardManager = BoardManager.getInstance();

        // Initialize drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Cell[][] cells = boardManager.getCells();
                drawCells(g, cells);
                drawWalls(g, cells);
            }
        };

        // Set click held flag to false
        isClickHeld = false;

        // Add mouse listeners for handling clicks and drags
        addMouseListeners();

        // Set bounds for drawing panel
        int padding = Cell.CELL_SIZE;
        drawingPanel.setBounds(0, 0, GRID_WIDTH + padding, GRID_HEIGHT + padding);

        // Set frame properties
        setFrameProperties();

        // Initialize buttons, labels, and slider
        initializeComponents();


    }

    // Method to add mouse listeners for handling clicks and drags
    private void addMouseListeners() {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isClickHeld = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDrag(e);
            }
        });
    }

    // Method to handle mouse press event
    private void handleMousePress(MouseEvent e) {

        if(isBusy) return;

        int x = e.getX();
        int y = e.getY();
        
        if (SwingUtilities.isLeftMouseButton(e)) {
            isClickHeld = true;
            onLeftClick(x, y);
        }
        if(SwingUtilities.isRightMouseButton((e))){
            isClickHeld = true;
            onRightClick(x, y);
        }
    }

    // Method to handle mouse drag event
    private void handleMouseDrag(MouseEvent e) {

        if(isBusy) return;

        if (isClickHeld) {
            int x = e.getX();
            int y = e.getY();
            if (SwingUtilities.isLeftMouseButton(e)) {
                onLeftClick(x, y);
            }
            if(SwingUtilities.isRightMouseButton(e)){
                onRightClick(x, y);
            }
        }
    }

    // Method to initialize buttons, labels, and slider
    private void initializeComponents() {
        int padding = Cell.CELL_SIZE;

        JButton startSearchBtn = getButton("Start Pathfinding", 50, padding);
        JButton startMazeBtn = getButton("Generate Maze", 100, padding);

        JLabel pathfindingLabel = getLabel(searchManager.getCurrentAlgorithmName(), 150, padding);
        JButton nextSearchBtn = getButton("Change Pathfinding", 180, padding);

        JLabel mazeLabel = getLabel(mazeManager.getCurrentAlgorithmName(), 220, padding);
        JButton nextMazeBtn = getButton("Change Maze", 250, padding);

        JLabel sliderLabel = getLabel("Cell size:", 310, padding);
        JSlider slider = getSlider(350, padding);


        startSearchBtn.addActionListener(e -> visualizeSearch());

        startMazeBtn.addActionListener(e -> visualizeMaze());

        nextSearchBtn.addActionListener(e -> {
            searchManager.nextAlgorithm();
            pathfindingLabel.setText(searchManager.getCurrentAlgorithmName());
        });

        nextMazeBtn.addActionListener(e -> {
            mazeManager.nextAlgorithm();
            mazeLabel.setText(mazeManager.getCurrentAlgorithmName());
        });

        // Add components to the content pane
        getContentPane().add(drawingPanel);

        getContentPane().add(startSearchBtn);
        getContentPane().add(startMazeBtn);

        getContentPane().add(pathfindingLabel);
        getContentPane().add(nextSearchBtn);

        getContentPane().add(mazeLabel);
        getContentPane().add(nextMazeBtn);

        getContentPane().add(sliderLabel);
        getContentPane().add(slider);
    }

    // Method to set frame properties
    private void setFrameProperties() {
        int padding = Cell.CELL_SIZE;
        setSize(WIDTH + padding, HEIGHT + padding);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }



    private JLabel getLabel(String labelText, int y, int padding){
        JLabel label = new JLabel(labelText);
        int labelWidth = 150;
        int labelAlignment = GRID_WIDTH + (WIDTH - GRID_WIDTH - labelWidth + padding)/2;
        label.setBounds(labelAlignment, y, labelWidth, 40);
        return label;
    }

    private JButton getButton(String buttonText, int y, int padding){
        JButton button = new JButton(buttonText);

        int buttonWidth = 150;
        int buttonAlignment = GRID_WIDTH + (WIDTH - GRID_WIDTH - buttonWidth + padding)/2;
        button.setBounds(buttonAlignment, y, buttonWidth, 40);
        return button;
    }

    private JSlider getSlider(int y, int padding) {

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 20, 100, 40);

        Dimension preferredSize = new Dimension(150, slider.getPreferredSize().height);
        slider.setPreferredSize(preferredSize);

        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(e -> {

            if(isBusy){
                slider.setValue(Cell.CELL_SIZE);
                return;
            }

            JSlider source = (JSlider) e.getSource();

            int value = source.getValue();
            if(GRID_WIDTH % value == 0 && GRID_HEIGHT % value == 0) {
                Cell.CELL_SIZE = source.getValue();

                int rows = GRID_HEIGHT / Cell.CELL_SIZE;
                int cols = GRID_WIDTH / Cell.CELL_SIZE;

                Cell[][] cells = BoardManager.getInstance().getCells();

                if(cells.length == rows && cells[0].length == cols) return;

                BoardManager.getInstance().updateCellsArray(rows, cols);
                drawingPanel.repaint();

            }
        });

        int sliderWidth = 100;
        int sliderAlignment = GRID_WIDTH + (WIDTH - GRID_WIDTH - sliderWidth + padding)/2;
        slider.setBounds(sliderAlignment, y, sliderWidth, 40);
        return slider;
    }

    private void drawWalls(Graphics g, Cell[][] cells) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length ; j++) {
                boolean[] walls = cells[i][j].getWalls();
                for (int k = 0; k < walls.length; k++) {
                    if(walls[k]){
                        switch (k) {
                            case 0 ->
                                    g2d.drawLine(j * Cell.CELL_SIZE, i * Cell.CELL_SIZE, j * Cell.CELL_SIZE + Cell.CELL_SIZE, i * Cell.CELL_SIZE);
                            case 1 ->
                                    g2d.drawLine(j * Cell.CELL_SIZE + Cell.CELL_SIZE, i * Cell.CELL_SIZE, j * Cell.CELL_SIZE + Cell.CELL_SIZE, i * Cell.CELL_SIZE + Cell.CELL_SIZE);
                            case 2 ->
                                    g2d.drawLine(j * Cell.CELL_SIZE, i * Cell.CELL_SIZE + Cell.CELL_SIZE, j * Cell.CELL_SIZE + Cell.CELL_SIZE, i * Cell.CELL_SIZE + Cell.CELL_SIZE);
                            case 3 ->
                                    g2d.drawLine(j * Cell.CELL_SIZE, i * Cell.CELL_SIZE, j * Cell.CELL_SIZE, i * Cell.CELL_SIZE + Cell.CELL_SIZE);
                        }
                    }
                }
            }
        }
    }

    public void drawPath(Graphics g, Cell startCell, Cell endCell){

        new Thread(() -> {
            Stack<Cell> path = new Stack<>();
            Cell current = endCell;
            while (!startCell.equals(current)) {
                path.push(current);
                current = current.getCameFrom();
            }
            // I use the stack to make it so I will create the path starting from the start cell
            Cell previous = startCell;

            while (!path.isEmpty()) {
                current = path.pop();
                drawLine(g, previous, current);
                previous = current;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            isBusy = false;
        }).start();
    }

    private void drawLine(Graphics g, Cell c1, Cell c2){
        Graphics2D g2d = (Graphics2D) g;

        int x1 = (c1.getCol() + 1) * Cell.CELL_SIZE - Cell.CELL_SIZE / 2;
        int y1 = (c1.getRow() + 1) * Cell.CELL_SIZE - Cell.CELL_SIZE / 2;

        int x2 = (c2.getCol() + 1) * Cell.CELL_SIZE - Cell.CELL_SIZE / 2;
        int y2 = (c2.getRow() + 1) * Cell.CELL_SIZE - Cell.CELL_SIZE / 2;

        g2d.setColor(CellColors.getCellColor(Cell.CellType.PATH));
        g2d.setStroke(new BasicStroke(10));
        g2d.drawLine(x1, y1, x2, y2);
    };


    private void drawCells(Graphics g, Cell[][] cells){
        Graphics2D g2d = (Graphics2D) g;

        int rows = GRID_HEIGHT / Cell.CELL_SIZE;
        int cols = GRID_WIDTH / Cell.CELL_SIZE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g2d.setColor(CellColors.getCellColor(cells[i][j]));
                g2d.fillRect(j * Cell.CELL_SIZE, i * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
            }
        }

    }

    private void onLeftClick(int x, int y){
        int row = y / Cell.CELL_SIZE;
        int col = x / Cell.CELL_SIZE;

        int max_rows = GRID_HEIGHT / Cell.CELL_SIZE;
        int max_cols = GRID_WIDTH / Cell.CELL_SIZE;

        if(max_rows <= row || max_cols <= col) return;

        BoardManager.getInstance().onCellSelected(row,col);

        drawingPanel.repaint();
    }
    
    private void onRightClick(int x, int y){
        int row = y / Cell.CELL_SIZE;
        int col = x / Cell.CELL_SIZE;

        int max_rows = GRID_HEIGHT / Cell.CELL_SIZE;
        int max_cols = GRID_WIDTH / Cell.CELL_SIZE;

        if(max_rows <= row || max_cols <= col) return;

        BoardManager.getInstance().onCellDeselect(row, col);

        drawingPanel.repaint();
    }

    private void visualizeSearch(){

        new Thread(() -> {
            BoardManager manager = BoardManager.getInstance();
            if(manager.getStartCell() == null || manager.getEndCell() == null) return;

            Cell[][] cells = manager.getCells();
            searchManager.initializeSearch(cells);

            isBusy = true;

            while (searchManager.isRunning()) {
                try {
                    searchManager.stepSearch();
                    drawingPanel.repaint();
                    Thread.sleep(1);
                } catch (InterruptedException exception) {
                    break;
                }
            }
            drawPath(drawingPanel.getGraphics(), BoardManager.getInstance().getStartCell(), BoardManager.getInstance().getEndCell());
        }).start();
    }

    private void visualizeMaze(){
        new Thread(() -> {
            if(isBusy) return;
            isBusy = true;
            Cell[][] cells = BoardManager.getInstance().getCells();
            mazeManager.initializeMazeGeneration(cells);
            while (mazeManager.isRunning()) {
                mazeManager.stepMazeGeneration(cells);
                drawingPanel.repaint();
            }
            isBusy = false;
        }).start();
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(Screen::new);
    }


}

class CellColors {
    public static Color getCellColor(Cell cell){
        return getCellColor(cell.getCellType());
    }

    public static Color getCellColor(Cell.CellType type){

        return switch (type) {
            case EMPTY -> Color.WHITE;
            case WALL -> Color.GRAY;
            case PATH -> Color.MAGENTA;
            case OPEN_SET -> Color.GREEN;
            case CLOSE_SET -> Color.RED;
            case END_POINT, START_POINT -> Color.YELLOW;
            case HIGHLIGHT -> Color.PINK;
        };
    }
}
