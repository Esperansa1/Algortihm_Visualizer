package Visualizer;

import Visualizer.Managers.BoardManager;
import Visualizer.MazeAlgorithms.SAW;
import Visualizer.SearchAlgortihms.AStar.AStar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Screen extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int GRID_WIDTH = 800;
    public static final int GRID_HEIGHT = 800;
    private final JPanel drawingPanel;

    private boolean isClickHeld;
    AStar aStar;
    SAW saw;

    public Screen() {
        super("Algorithm Visualizer");

        aStar = new AStar();
        saw = new SAW();

        setLayout(null);

        BoardManager boardManager = BoardManager.getInstance();

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Cell[][] cells = boardManager.getCells();
                drawCells(g, cells);
                drawWalls(g, cells);
            }

        };

        isClickHeld = false;
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                isClickHeld = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)) {
                    isClickHeld = true;
                    int x = e.getX();
                    int y = e.getY();
                    onMouseClick(x, y);
                }
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                onMouseClick(x, y);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isClickHeld) {
                    int x = e.getX();
                    int y = e.getY();
                    onMouseClick(x, y);
                }
            }
        });

        int padding = Cell.CELL_SIZE;
        drawingPanel.setBounds(0, 0, GRID_WIDTH+padding, GRID_HEIGHT+padding);

        JButton button1 = getButton("Start Pathfinding", 50, padding);
        JButton button2 = getButton("Generate Maze", 100, padding);

        JSlider slider = getSlider(padding);

        // Add the buttons to the content pane
        getContentPane().add(drawingPanel);
        getContentPane().add(slider);
        getContentPane().add(button1);
        getContentPane().add(button2);

        setSize(WIDTH + padding, HEIGHT + padding);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        button1.addActionListener(e -> {
                new Thread(() -> {

                    while (!aStar.isFinished) {
                        try{
                            aStar.stepSearch();
                            Thread.sleep(30);
                        }
                        catch (InterruptedException exception) {
                            break;
                        }
                    }
                }).start();

        });

        button2.addActionListener(e ->
            new Thread(() -> {
                Cell[][] cells = BoardManager.getInstance().getCells();
                try{
                        while(!saw.isFinished){
                            saw.stepMazeGeneration(cells);
                            Thread.sleep(1);
                        }

                    }
                    catch (InterruptedException exception) {

                    }
            }).start());
    }


    private JButton getButton(String buttonText, int y, int padding){
        JButton button = new JButton(buttonText);

        int buttonWidth = 150;
        int buttonAlignment = GRID_WIDTH + (WIDTH - GRID_WIDTH - buttonWidth + padding)/2;
        button.setBounds(buttonAlignment, y, buttonWidth, 40);
        return button;
    }


    private JToggleButton toggleButton(){
        JToggleButton toggleButton = new JToggleButton("Toggle Switch");
        toggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Switch is ON
                System.out.println("Switch is ON");
            } else {
                // Switch is OFF
                System.out.println("Switch is OFF");
            }
        });
        return toggleButton;
    }

    private JSlider getSlider(int padding) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 20, 100, 40);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(e -> {

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
        slider.setBounds(sliderAlignment, 150, sliderWidth, 40);
        return slider;
    }

    public void drawWalls(Graphics g, Cell[][] cells) {
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

    public void drawCells(Graphics g, Cell[][] cells){
        Graphics2D g2d = (Graphics2D) g;

        int rows = GRID_HEIGHT / Cell.CELL_SIZE;
        int cols = GRID_WIDTH / Cell.CELL_SIZE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                g2d.setColor(CellColors.getCellColor(cells[i][j]));
                g2d.fillRect(j * Cell.CELL_SIZE, i * Cell.CELL_SIZE, Cell.CELL_SIZE, Cell.CELL_SIZE);
            }
        }

        drawingPanel.repaint();
    }

    public void onMouseClick(int x, int y){
        int row = y / Cell.CELL_SIZE;
        int col = x / Cell.CELL_SIZE;

        int max_rows = GRID_HEIGHT / Cell.CELL_SIZE;
        int max_cols = GRID_WIDTH / Cell.CELL_SIZE;

        if(max_rows <= row || max_cols <= col) return;

        BoardManager.getInstance().onCellSelected(row,col);

        initializeBoard();

        drawingPanel.repaint();

    }

    private void initializeBoard(){
        if(BoardManager.getInstance().getStartCell() != null && BoardManager.getInstance().getEndCell() != null){
            aStar.initializeSearch(BoardManager.getInstance().getCells(), BoardManager.getInstance().getStartCell(), BoardManager.getInstance().getEndCell());
        }
    }

}

class CellColors {

    public static Color getCellColor(Cell cell){

        return switch (cell.getCellType()) {
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
