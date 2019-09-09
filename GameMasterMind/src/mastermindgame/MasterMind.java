package mastermindgame;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class MasterMind {
    public enum Result{ INPOSITION, MATCH, NOMATCH }
    public enum GameState{ INPROGESS, WON, LOST }

    public List<Integer> selectedColorIndices;
    public int numberOfTries = 0;
    private GameState gameState = GameState.INPROGESS;

    public void setColorCombinationIndices(List<Integer> colorIndices) {
        selectedColorIndices = colorIndices;
    }

    public Map<Result, Integer> guess(List<Integer> guessColorIndices){
        int inposition = 0;
        int match = 0;

        for (int i = 0; i < selectedColorIndices.size(); i++) {
            if (selectedColorIndices.get(i) == guessColorIndices.get(i)) {
                inposition++;
                continue;
            }

            if (guessColorIndices.contains(selectedColorIndices.get(i))) {
                match++;
            }
        }                

        updateGameState(inposition);

        return Map.of(
                Result.INPOSITION, inposition,
                Result.MATCH, match,
                Result.NOMATCH, selectedColorIndices.size() - inposition - match);
    }

    public GameState getGameState() { return gameState; }

    private void updateGameState(int inposition){
        numberOfTries++;

        if(numberOfTries <= 20 && inposition == 6){
            gameState = GameState.WON;
        }
        else if(numberOfTries >= 20){
            gameState = GameState.LOST;
        }
    }

    public List<Integer> createRandomColorIndices(int sizeOfPool, int sizeOfSelection, long seed) {
        return new Random(seed)
          .ints(0, sizeOfPool)
          .map(value -> value % sizeOfSelection)
          .distinct()
          .boxed()
          .limit(sizeOfSelection)
          .collect(toList());
    }
}
