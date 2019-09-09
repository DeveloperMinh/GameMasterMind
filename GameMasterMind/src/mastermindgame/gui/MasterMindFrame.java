package mastermindgame.gui;

import mastermindgame.MasterMind;
import mastermindgame.MasterMind.Result;
import mastermindgame.MasterMind.GameState;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class MasterMindFrame extends JFrame {
    private MasterMind masterMind;
    private List<MasterMindGuessCell> guessCellList;
    private List<MasterMindColorCodeCell> colorCodeCellList;
    private List<List<MasterMindGuessCell>> previousGuessCellList;
    private List<List<MasterMindColorCodeCell>> previousColorCodeCellList;
    protected JLabel remainingTurnsLabel;

    @Override
    protected void frameInit() {
        super.frameInit();
        masterMind = new MasterMind();
        guessCellList = new ArrayList<>();
        colorCodeCellList = new ArrayList<>();
        previousGuessCellList = new ArrayList<>();
        previousColorCodeCellList = new ArrayList<>();
        remainingTurnsLabel = new JLabel("Remaining Turns: 20", JLabel.CENTER);

        long seed = System.nanoTime();
        masterMind.setColorCombinationIndices(masterMind.createRandomColorIndices(10, 6, seed));
        setLayout(new GridBagLayout());

        createGameTitle();
        createGuessPanel();
        createColorCodePanel();
        createColorChoicePanel();
        createSubmitGuessButton();
        createLoseButton();
        addRemainingTurnsLabel();
        createPreviousResultPanel();
    }

    private void createPreviousResultPanel() {
        JPanel previousResultPanel = new JPanel();
        JPanel previousGuessPanel = createPreviousGuessPanel();
        JPanel previousColorCodePanel = createPreviousColorCodePanel();

        GridLayout layout = new GridLayout(1, 2, 50, 0);

        int width = previousGuessPanel.getWidth() + previousColorCodePanel.getWidth() + layout.getHgap();
        int height = previousGuessPanel.getHeight() > previousColorCodePanel.getHeight() ? previousGuessPanel.getHeight() : previousColorCodePanel.getHeight();
        previousResultPanel.setPreferredSize(new Dimension(width, height));
        previousResultPanel.setLayout(layout);
        previousResultPanel.add(previousGuessPanel);
        previousResultPanel.add(previousColorCodePanel);

        GridBagConstraints constraints= new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0,25);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        getContentPane().add(previousResultPanel, constraints);
    }

    private JPanel createPreviousColorCodePanel() {
        JPanel previousColorCodePanel = new JPanel();
        ArrayList<MasterMindColorCodeCell> newColorCodeCellList;
        Border cellBorder = new LineBorder(Color.black, 2, true);

        int cellSize = 22;
        GridLayout layout = new GridLayout(20, 6, 5, 5);
        int rows = layout.getRows();
        int cols = layout.getColumns();

        previousColorCodePanel.setLayout(layout);
        for(int i = 0; i < rows; i++){
            newColorCodeCellList = new ArrayList<>();
            for(int j = 0; j < cols; j++){
                MasterMindColorCodeCell cell = new MasterMindColorCodeCell(null);
                cell.setSize(cellSize,cellSize);
                cell.setBorder(cellBorder);
                cell.setBackground(Color.white);
                previousColorCodePanel.add(cell);
                newColorCodeCellList.add(cell);
            }
            previousColorCodeCellList.add(newColorCodeCellList);
        }

        int height = cellSize * rows + (rows - 1) * layout.getHgap();
        int width = cellSize * cols + (cols - 1) * layout.getVgap();
        previousColorCodePanel.setSize(new Dimension(width, height));

        return previousColorCodePanel;
    }

    private JPanel createPreviousGuessPanel() {
        JPanel previousGuessPanel = new JPanel();
        ArrayList<MasterMindGuessCell> newGuessCellList;
        Border cellBorder = new LineBorder(Color.black, 2, true);

        int cellSize = 22;
        GridLayout layout = new GridLayout(20, 6, 5, 5);
        int rows = layout.getRows();
        int cols = layout.getColumns();

        previousGuessPanel.setLayout(layout);
        for(int i = 0; i < rows; i++){
            newGuessCellList = new ArrayList<>();
            for(int j = 0; j < cols; j++){
                MasterMindGuessCell cell = new MasterMindGuessCell(0);
                cell.setSize(cellSize, cellSize);
                cell.setBorder(cellBorder);
                previousGuessPanel.add(cell);
                newGuessCellList.add(cell);
            }
            previousGuessCellList.add(newGuessCellList);
        }

        int height = cellSize * rows + (rows - 1) * layout.getHgap();
        int width = cellSize * cols + (cols - 1) * layout.getVgap();
        previousGuessPanel.setSize(new Dimension(width, height));

        return previousGuessPanel;
    }

    private void addRemainingTurnsLabel() {
        GridBagConstraints constraints= new GridBagConstraints();
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_END;
        getContentPane().add(remainingTurnsLabel, constraints);
    }

    private void createLoseButton() {
        JButton loseButton = new JButton("Give Up");
        loseButton.addActionListener(new loseSubmitHandler());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0,50);
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;

        getContentPane().add(loseButton, constraints);
    }

    private void createSubmitGuessButton() {
        JButton submitGuess = new JButton("Guess!");
        submitGuess.addActionListener(new GuessSubmitHandler());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 50, 0,0);
        constraints.gridx = 4;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;

        getContentPane().add(submitGuess, constraints);
    }

    private void createGameTitle(){
        JLabel gameTitle = new JLabel("Master Mind", JLabel.CENTER);
        gameTitle.setFont(new Font(gameTitle.getName(), Font.BOLD, 24));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(25, 0, 25,0);
        constraints.gridx = 3;
        constraints.gridy = 0;

        getContentPane().add(gameTitle, constraints);
    }

    private void createColorChoicePanel() {
        JPanel colorChoicePanel = new JPanel();
        int rows = 2;
        int cols = 5;
        colorChoicePanel.setLayout(new GridLayout(rows, cols));
        int cellColorIndex = 1;
        for(int i = 0; i < rows; i ++){
            for(int j = 0; j < cols; j++){
                MasterMindColorCell cell = new MasterMindColorCell(cellColorIndex);
                setCellColor(cell, cellColorIndex);
                cellColorIndex++;
                colorChoicePanel.add(cell);
                cell.addActionListener(new ColorCellClickedHandler());
            }
        }

        colorChoicePanel.setPreferredSize(new Dimension(250, 100));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(25, 0, 25,0);
        constraints.gridx = 3;
        constraints.gridy = 3;

        getContentPane().add(colorChoicePanel, constraints);
    }

    private void createGuessPanel() {
        JPanel guessPanel = new JPanel();
        int cols = 6;
        guessPanel.setLayout(new GridLayout(1, cols));
        for (int i = 0; i < cols; i++) {
            MasterMindGuessCell cell = new MasterMindGuessCell(0);
            guessPanel.add(cell);
            guessCellList.add(cell);
            cell.addActionListener(new GuessCellClickedHandler());
        }

        guessPanel.setPreferredSize(new Dimension(300, 50));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(25, 0, 25,0);
        constraints.gridx = 3;
        constraints.gridy = 1;

        getContentPane().add(guessPanel, constraints);
    }

    private void createColorCodePanel() {
        JPanel colorCodePanel = new JPanel();
        int cols = 6;
        colorCodePanel.setLayout(new GridLayout(1, cols));
        for (int i = 0; i < cols; i++) {
            MasterMindColorCodeCell cell = new MasterMindColorCodeCell(null);
            colorCodePanel.add(cell);
            colorCodeCellList.add(cell);
        }

        colorCodePanel.setPreferredSize(new Dimension(300, 50));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(25, 0, 25,0);
        constraints.gridx = 3;
        constraints.gridy = 2;

        getContentPane().add(colorCodePanel, constraints);
    }

    private void setCellColor(JButton cell, int cellColorIndex) {
        switch(cellColorIndex){
            case 0:
                cell.setBackground(null);
                break;
            case 1:
                cell.setBackground(Color.red);
                break;
            case 2:
                cell.setBackground(Color.blue);
                break;
            case 3:
                cell.setBackground(Color.green);
                break;
            case 4:
                cell.setBackground(Color.magenta);
                break;
            case 5:
                cell.setBackground(Color.yellow);
                break;
            case 6:
                cell.setBackground(Color.cyan);
                break;
            case 7:
                cell.setBackground(Color.pink);
                break;
            case 8:
                cell.setBackground(Color.orange);
                break;
            case 9:
                cell.setBackground(Color.black);
                break;
            case 10:
                cell.setBackground(Color.white);
                break;
            default:
                cell.setBackground(null);
                break;
        }
    }

    private class ColorCellClickedHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MasterMindColorCell cell = (MasterMindColorCell) actionEvent.getSource();

            for(MasterMindGuessCell guessCell : guessCellList){
                if(guessCell.getColorIndex() == 0){
                    guessCell.setColorIndex(cell.getColorIndex());
                    setCellColor(guessCell, cell.getColorIndex());
                    break;
                }
            }
        }
    }

    private class GuessCellClickedHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent){
            MasterMindGuessCell cell = (MasterMindGuessCell) actionEvent.getSource();
            cell.setColorIndex(0);
            setCellColor(cell, 0);
        }
    }

    private class GuessSubmitHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            List<Integer> guessIndexList = guessListToIndexList(guessCellList);
            if(guessIndexList.size() == 6){
                if(masterMind.numberOfTries < 20){
                    Map<Result, Integer> guessResults = masterMind.guess(guessIndexList);
                    DisplayResults(guessResults);
                    DisplayPrevious(guessIndexList);
                }
            }
            else if (masterMind.numberOfTries < 20){
                JOptionPane.showMessageDialog(getContentPane(), "Please choose 6 colors.");
            }
        }

        private List<Integer> guessListToIndexList(List<MasterMindGuessCell> guessCellList) {
            ArrayList<Integer> guessIndexList = new ArrayList<>();
            for(MasterMindGuessCell guessCell : guessCellList){
                if(guessCell.colorIndex > 0){
                    guessIndexList.add(guessCell.colorIndex);
                }
            }

            return  guessIndexList;
        }
    }

    private void DisplayPrevious(List<Integer> guessIndexList) {
        int previousTryNum = masterMind.numberOfTries - 1;
        List<MasterMindGuessCell> guessCells = previousGuessCellList.get(previousTryNum);
        List<MasterMindColorCodeCell> colorCodeCells = previousColorCodeCellList.get(previousTryNum);

        int numCells = guessCells.size();
        for(int i = 0; i < numCells; i++){
            MasterMindGuessCell guessCell = guessCells.get(i);
            MasterMindColorCodeCell colorCodeCell = colorCodeCells.get(i);

            guessCell.setColorIndex(guessIndexList.get(i));
            setCellColor(guessCell, guessCell.getColorIndex());

            colorCodeCell.setCellState(colorCodeCellList.get(i).result);
        }
    }

    private void DisplayResults(Map<Result, Integer> guessResults) {
        GameState state = masterMind.getGameState();
        if(state != GameState.INPROGESS){
            if(masterMind.numberOfTries == 20 && guessResults != null){
                setColorCodePanel(guessResults);
            }

            remainingTurnsLabel.setText("Remaining Turns: 0");
            JOptionPane.showMessageDialog(getContentPane(), "You " + masterMind.getGameState().toString() + "!");

            if(state == GameState.LOST){
                DisplayAnswer();
            }
        }
        else if(guessResults != null){
            remainingTurnsLabel.setText("Remaining Turns: " + Integer.toString(20 - masterMind.numberOfTries));
            setColorCodePanel(guessResults);
        }
    }

    private void setColorCodePanel(Map<Result, Integer> guessResults) {
        int cellNum = 0;
        for(int i = 0; i < guessResults.get(Result.INPOSITION); i++){
            colorCodeCellList.get(cellNum).setCellState(Result.INPOSITION);
            cellNum++;
        }

        for(int i = 0; i < guessResults.get(Result.MATCH); i++){
            colorCodeCellList.get(cellNum).setCellState(Result.MATCH);
            cellNum++;
        }

        for(int i = 0; i < guessResults.get(Result.NOMATCH); i++){
            colorCodeCellList.get(cellNum).setCellState(Result.NOMATCH);
            cellNum++;
        }
    }

    private void DisplayAnswer() {
        JPanel answerPanel = new JPanel();
        List<Integer> answers = masterMind.selectedColorIndices;
        int numCells = 6;

        answerPanel.setLayout(new GridLayout(1,numCells));
        for(int i = 0; i < numCells; i++){
            int answerIndex = answers.get(i);
            MasterMindGuessCell cell = new MasterMindGuessCell(answerIndex);
            setCellColor(cell, answerIndex);
            answerPanel.add(cell);
        }

        JOptionPane.showMessageDialog(null, answerPanel, "Master Mind Answer", JOptionPane.PLAIN_MESSAGE);
    }

    private class loseSubmitHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Map<Result, Integer> guessResults = null;
            while(masterMind.numberOfTries < 20){
                guessResults = masterMind.guess(List.of(0, 0, 0, 0, 0, 0));
            }

            DisplayResults(guessResults);
        }
    }

    public static void main(String[] args){
        JFrame frame = new MasterMindFrame();
        frame.setSize(1200,650);
        frame.setVisible(true);
    }
}