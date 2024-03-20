package Visualizer.View;

import Visualizer.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Screen extends JFrame implements BoardObserver, HighlightObserver {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int GRID_WIDTH = 800;
    public static final int GRID_HEIGHT = 800;

    private final JPanel drawingPanel;
    private boolean isClickHeld;
    private final Controller controller;

    private Cell highlight;
    private JLabel stateLabel;
    private boolean visualize = true;

    public Screen(Controller controller) {
        super("Algorithm Visualizer");

        this.controller = controller;
        controller.subscribeToBoardModel(this);
        controller.subscribeToHighlightModel(this);



        setLayout(null);

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Cell[][] cells = controller.getModel().getCells();
                drawCells(g, cells);
                highlightCell(g);
                drawGrid(g);
                drawWalls(g, cells);
                updateStateLabel();

            }
        };

        isClickHeld = false;
        addMouseListeners();

        int padding = Cell.CELL_SIZE;
        drawingPanel.setBounds(0, 0, GRID_WIDTH + padding, GRID_HEIGHT + padding);

        setFrameProperties();
        initializeComponents(controller);

    }

    private void updateStateLabel() {
        stateLabel.setText("Currently Placing: " +controller.getModel().getNextPlace().toString());
    }

    private void highlightCell(Graphics g) {
        if(this.highlight == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(CellColors.getCellColor(Cell.CellType.HIGHLIGHT));

        int size = Cell.CELL_SIZE;

        g2d.fillRect(highlight.getCol() * size, highlight.getRow() * size, size, size);
        this.highlight = null;
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.LIGHT_GRAY);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));

        int size = Cell.CELL_SIZE;
        for(int i = 0; i < GRID_WIDTH + size; i += size){
            g2d.drawLine(0, i, GRID_WIDTH, i);
        }

        for(int i = 0; i < GRID_HEIGHT + size; i += size){
            g2d.drawLine(i, 0, i, GRID_HEIGHT);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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
    private void initializeComponents(Controller controller) {
        int padding = 40;
        JButton startSearchBtn = getButton("Start Pathfinding", 50, padding);
        JButton startMazeBtn = getButton("Generate Maze", 100, padding);

        JLabel pathfindingLabel = getLabel(controller.getCurrentPathfindingAlgorithmName(), 150, padding);
        JButton nextSearchBtn = getButton("Change Pathfinding", 180, padding);

        JLabel mazeLabel = getLabel(controller.getCurrentMazeAlgorithmName(), 220, padding);
        JButton nextMazeBtn = getButton("Change Maze", 250, padding);

        JLabel sliderLabel = getLabel("Cell size:", 310, padding);
        JSlider slider = getSlider(padding);

        stateLabel = getLabel("Current State:" +controller.getModel().getNextPlace().toString(), 410, padding);

        JLabel madeByLabel = getLabel("Made By: Or Esperansa", HEIGHT - 50, padding);

        JButton userManualBtn = getButton("User Manual", HEIGHT - 100, padding);

        JButton colorPickerBtn = getButton("Color picker", HEIGHT - 150, padding);

        JCheckBox checkbox = getCheckBox(controller, padding);

        userManualBtn.addActionListener(e -> userManual());

        colorPickerBtn.addActionListener(e -> colorPicker());

        startSearchBtn.addActionListener(e -> controller.visualizeSearch(visualize));

        startMazeBtn.addActionListener(e -> controller.visualizeMaze());
        nextSearchBtn.addActionListener(e -> {
            controller.nextPathfindingAlgorithm();
            pathfindingLabel.setText(controller.getCurrentPathfindingAlgorithmName());
        });

        nextMazeBtn.addActionListener(e -> {
            controller.nextMazeAlgorithm();
            mazeLabel.setText(controller.getCurrentMazeAlgorithmName());
        });

        // Add components to the content pane
        getContentPane().add(drawingPanel);

        getContentPane().add(startSearchBtn);
        getContentPane().add(startMazeBtn);

        getContentPane().add(pathfindingLabel);
        getContentPane().add(nextSearchBtn);

        getContentPane().add(mazeLabel);
        getContentPane().add(nextMazeBtn);
        getContentPane().add(checkbox);
        getContentPane().add(stateLabel);

        getContentPane().add(sliderLabel);
        getContentPane().add(slider);

        getContentPane().add(userManualBtn);
        getContentPane().add(colorPickerBtn);

        getContentPane().add(madeByLabel);



    }

    private JCheckBox getCheckBox(Controller controller, int padding) {
        JCheckBox checkbox = new JCheckBox("Turn on/off cell colors", true);
        int checkboxAlignment = GRID_WIDTH + (WIDTH - GRID_WIDTH - 165 + padding) / 2;

        checkbox.setBounds(checkboxAlignment, 470, 165, 40);
        checkbox.addChangeListener(e -> {
            if(controller.isBusy()) {
                checkbox.setSelected(visualize);
                return;
            }

            if(checkbox.isSelected() != visualize) {
                visualize = checkbox.isSelected();
                drawingPanel.repaint();
            }
        });
        return checkbox;
    }

    private void userManual() {
        JDialog popup = new JDialog(this, "User Manual", true);

        JPanel panel = new JPanel();
        JLabel instructionsLabel = new JLabel("<html><div style='text-align: center;'>" +
                "Welcome to the Pathfinding / Maze Generation project!<br/><br/>" +
                "<p style='margin-bottom: 10px;'><b>Pathfinding Instructions:</b><br/>" +
                "1. <b>Starting Pathfinding:</b> to initiate pathfinding, press the \"Start Pathfinding\" button.</p>" +
                "<p style='margin-bottom: 10px;'><b>2. Selecting a Pathfinding Algorithm:</b> Click \"Change Pathfinding Algorithm\"<br/>Choose from A*, GreedyBFS, Pledge's Algorithm, Tremaux's Algorithm.</p><br/>" +
                "<b>Maze Generation Instructions:</b><br/>" +
                "1. <b>Generating a Maze:</b> to generate a maze, click on the \"Start Maze Generation\" button.</p>" +
                "<p style='margin-bottom: 10px;'><b>2. Selecting a Maze Generation Algorithm:</b> Press \"Change Maze Generation Algorithm\"<br/>Choose from Prime's Algorithm, Self-Avoiding Walk.</p><br/>" +
                "Thank you for using the application!</div></html>"
        );

        instructionsLabel.setBorder(new EmptyBorder(20, 10, 20, 10));
        panel.add(instructionsLabel);

        popup.getContentPane().add(panel);
        popup.pack();

        popup.setLocationRelativeTo(this);
        popup.setResizable(false);
        popup.setVisible(true);
    }


    private void colorPicker(){

        JDialog popup = new JDialog(this, "Color picker", true);

        JPanel panel = new JPanel();
        popup.setSize(400,200);
        panel.setSize(400,200);


        Cell.CellType[] types = Cell.CellType.values();

        for(int i = 0; i < types.length; i++){

            // Formatting the string for first letter to be capitalized and rest not
            String[] splitString = types[i].toString().split("_");
            String type = String.join(" ", splitString);
            String finalType = type.substring(0,1).toUpperCase() + type.substring(1);

            JButton button = getButton(finalType, i * 50, 60);

            int finalI = i;
            button.addActionListener(e -> openColorPicker(types[finalI]));

            panel.add(button);
        }

        popup.add(panel);
        popup.setLocationRelativeTo(this);
        popup.setResizable(false);
        popup.setVisible(true);
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

    private JSlider getSlider(int padding) {

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 20, 100, 40);

        Dimension preferredSize = new Dimension(150, slider.getPreferredSize().height);
        slider.setPreferredSize(preferredSize);

        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.addChangeListener(e -> {

            JSlider source = (JSlider) e.getSource();
            int value = source.getValue();

            if(controller.isBusy()){
                slider.setValue(Cell.CELL_SIZE);
                return;
            }

            if(GRID_WIDTH % value == 0 && GRID_HEIGHT % value == 0) {
                controller.updateCellsArray(source.getValue());
            }
        });

        int sliderWidth = 100;
        int sliderAlignment = GRID_WIDTH + (WIDTH - GRID_WIDTH - sliderWidth + padding)/2;
        slider.setBounds(sliderAlignment, 350, sliderWidth, 40);
        return slider;
    }

    private void drawWalls(Graphics g, Cell[][] cells) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        int size = Cell.CELL_SIZE;
        
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length ; j++) {
                boolean[] walls = cells[i][j].getWalls();
                for (int k = 0; k < walls.length; k++) {
                    if(walls[k]){
                        switch (k) {
                            case 0 ->
                                    g2d.drawLine(j * size, i * size, j * size + size, i * size);
                            case 1 ->
                                    g2d.drawLine(j * size + size, i * size, j * size + size, i * size + size);
                            case 2 ->
                                    g2d.drawLine(j * size, i * size + size, j * size + size, i * size + size);
                            case 3 ->
                                    g2d.drawLine(j * size, i * size, j * size, i * size + size);
                        }
                    }
                }
            }
        }
    }

    private void drawLine(Graphics g, Cell c1, Cell c2){
        Graphics2D g2d = (Graphics2D) g;

        int size = Cell.CELL_SIZE;

        int x1 = (c1.getCol() + 1) * size - size / 2;
        int y1 = (c1.getRow() + 1) * size - size / 2;

        int x2 = (c2.getCol() + 1) * size - size / 2;
        int y2 = (c2.getRow() + 1) * size - size / 2;

        g2d.setColor(CellColors.getCellColor(Cell.CellType.PATH));
        g2d.setStroke(new BasicStroke( Cell.CELL_SIZE /4));
        g2d.drawLine(x1, y1, x2, y2);
    }

    public void drawPath(Cell startCell, Cell endCell){

        Graphics g = drawingPanel.getGraphics();
        new Thread(() -> {
            controller.setBusy(true);
            Stack<Cell> path = new Stack<>();
            Cell current = endCell;

            if(current.getCameFrom() == null) {
                controller.setBusy(false);
                return;
            }

            while (!startCell.equals(current)) {
                path.push(current);
                current = current.getCameFrom();
            }

            // I use the stack to make it, so I will create the path starting from the start cell
            path.push(startCell);
            Cell previous = startCell;

            while (!path.isEmpty()) {
                try {
                    current = path.pop();
                    drawLine(g, previous, current);
                    previous = current;
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            controller.setBusy(false);
        }).start();
    }


    private void drawCells(Graphics g, Cell[][] cells){

        Graphics2D g2d = (Graphics2D) g;

        int size = Cell.CELL_SIZE;

        int rows = GRID_HEIGHT / size;
        int cols = GRID_WIDTH / size;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                Cell.CellType type = cells[i][j].getCellType();
                Color currentColor = CellColors.getCellColor(type);
                if(visualize || type == Cell.CellType.START_POINT || type == Cell.CellType.END_POINT)
                    g2d.setColor(currentColor);
                else
                    g2d.setColor(CellColors.getCellColor(Cell.CellType.EMPTY));

                g2d.fillRect(j * size, i * size, size, size);
            }
        }
    }

    private void onLeftClick(int x, int y){
        int size = Cell.CELL_SIZE;

        int row = y / size;
        int col = x / size;

        int max_rows = GRID_HEIGHT / size;
        int max_cols = GRID_WIDTH / size;

        if(max_rows <= row || max_cols <= col) return;

        controller.onCellSelected(row,col);
    }
    
    private void onRightClick(int x, int y){
        int size = Cell.CELL_SIZE;

        int row = y / size;
        int col = x / size;

        int max_rows = GRID_HEIGHT / size;
        int max_cols = GRID_WIDTH / size;

        if(max_rows <= row || max_cols <= col) return;

        controller.onCellDeselect(row, col);
    }

    private void openColorPicker(Cell.CellType typeToChange){
        Color chosenColor = JColorChooser.showDialog(null, "Pick a color", CellColors.getCellColor(typeToChange));
        CellColors.changeColor(typeToChange, chosenColor);
        drawingPanel.repaint();
    }

    @Override
    public void onBoardChanged() {
        drawingPanel.repaint();
    }



    @Override
    public void onMazeHighlight(Cell cell) {
        this.highlight = cell;
        drawingPanel.repaint();
    }

}

