package mastermindgame.gui;

import mastermindgame.MasterMind.Result;
import javax.swing.*;
import java.awt.*;

public class MasterMindColorCodeCell extends JButton {
    public Result result;

    public MasterMindColorCodeCell(Result aResult) {
        result = aResult;
        setCellState(aResult);
        setSize(50, 50);
    }

    public void setCellState(Result aResult) {
        result = aResult;
        if(result == Result.INPOSITION){
            setBackground(Color.black);
        }
        else if(result == Result.MATCH){
            setBackground(new Color(192,192,192));
        }
        else{
            setBackground(null);
        }
    }
}
