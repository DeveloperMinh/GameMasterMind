package mastermindgame.gui;

import javax.swing.*;

public class MasterMindGuessCell extends JButton {
    public int colorIndex;

    public MasterMindGuessCell(int aColorIndex) {
        colorIndex = aColorIndex;
        setSize(50, 50);
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int aColorIndex) {
        colorIndex = aColorIndex;
    }
}
