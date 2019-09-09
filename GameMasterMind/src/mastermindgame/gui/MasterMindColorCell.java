package mastermindgame.gui;

import javax.swing.*;

public class MasterMindColorCell extends JButton {
    public final Integer colorIndex;

    public MasterMindColorCell(Integer aColorIndex) {
        colorIndex = aColorIndex;
        setSize(50, 50);
    }

    public Integer getColorIndex() {
        return colorIndex;
    }
}