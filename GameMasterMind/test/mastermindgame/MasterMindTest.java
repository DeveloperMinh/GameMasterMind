package mastermindgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import static mastermindgame.MasterMind.Result.*;
import static mastermindgame.MasterMind.GameState.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MasterMindTest {
    private MasterMind mastermind;

    @BeforeEach
    void init(){
        mastermind = new MasterMind();
        mastermind.setColorCombinationIndices(List.of(1, 2, 3, 4, 5, 6));
    }

    @Test
    void canary() {
        assert(true);
    }

    @Test
    void playerMakesIncorrectGuess(){
        assertEquals(Map.of(
                INPOSITION, 0,
                MATCH, 0,
                NOMATCH, 6),
                mastermind.guess(List.of(0, 0, 0, 0, 0, 0)));
    }

    @Test
    void playerMakesCorrectGuess(){
        assertEquals(Map.of(
                INPOSITION, 6,
                MATCH, 0,
                NOMATCH, 0),
                mastermind.guess(List.of(1, 2, 3, 4, 5, 6)));
    }

    @Test
    void oneColorNonPositionalMatch(){
        assertEquals(Map.of(
                INPOSITION, 0,
                MATCH, 1,
                NOMATCH, 5),
                mastermind.guess(List.of(0, 0, 0, 0, 0, 1)));
    }

    @Test
    void twoColorsNonPositionalMatch(){
        assertEquals(Map.of(
                INPOSITION, 0,
                MATCH, 2,
                NOMATCH, 4),
                mastermind.guess(List.of(0, 0, 0, 0, 2, 1)));
    }
    @Test
    void oneColorPositionalAndOneNonPositionalMatch(){
        assertEquals(Map.of(
                INPOSITION, 1,
                MATCH, 1,
                NOMATCH, 4),
                mastermind.guess(List.of(1, 0, 0, 0, 0, 2)));
    }

    @Test
    void twoSameColors(){
        assertEquals(Map.of(
                INPOSITION, 1,
                MATCH, 0,
                NOMATCH, 5),
                mastermind.guess(List.of(1, 1, 0, 0, 0, 0)));
    }

    @Test
    void threeColorNonPositionalMatch() {
        assertEquals(Map.of(
                INPOSITION, 0,
                MATCH, 3,
                NOMATCH, 3),
                mastermind.guess(List.of(0, 0, 0, 3, 2, 1)));
    }

    @Test
    void twoColorFirstPositionalWrongAndSecondPositionalRight() {
        assertEquals(Map.of(
                INPOSITION, 1,
                MATCH, 0,
                NOMATCH, 5),
                mastermind.guess(List.of(2, 2, 0, 0, 0, 0)));
    }

    @Test
    void gameStateAtStart(){
        assertEquals(INPROGESS, mastermind.getGameState());
    }

    @Test
    void gameStateAfterOneWrongGuess(){
        mastermind.guess(List.of(0, 2, 0, 0, 0, 1));
        assertEquals(INPROGESS, mastermind.getGameState());
    }

    @Test
    void getGameStateAfterCorrectGuess() {
        mastermind.guess(List.of(1, 2, 3, 4, 5, 6));
        assertEquals(WON, mastermind.getGameState());
    }

    @Test
    void getGameStateWhenLastGuessIsWrong(){
        mastermind.numberOfTries = 19;
        mastermind.guess(List.of(0, 2, 0, 0, 0, 1));
        assertEquals(LOST, mastermind.getGameState());
    }

    @Test
    void gameStateStayWonOnceWon(){
        mastermind.guess(List.of(1, 2, 3, 4, 5, 6));
        mastermind.guess(List.of(0, 0, 0, 0, 0, 0));
        assertEquals(WON, mastermind.getGameState());
    }

    @Test
    void gameStateStayLoseOnceLose(){
        mastermind.numberOfTries = 19;
        mastermind.guess(List.of(0, 2, 0, 0, 0, 1));
        mastermind.guess(List.of(1, 2, 3, 4, 5, 6));
        assertEquals(LOST, mastermind.getGameState());
    }

    @Test
    void getGameStateWhenLastGuessCorrect(){
        mastermind.numberOfTries = 19;
        mastermind.guess(List.of(1, 2, 3, 4, 5, 6));
        assertEquals(WON, mastermind.getGameState());
    }

    @Test
    void randomlySelectedIndicesHaveSixValues() {
      assertEquals(6, mastermind.createRandomColorIndices(10, 6, 1).size());
    }

    @Test
    void randomlySelectedIndicesHaveDistinctValues() {
      List<Integer> selectedIndices = mastermind.createRandomColorIndices(10, 6, 1);
      assertEquals(6, new HashSet<Integer>(selectedIndices).size());
    }
    
    @Test
    void randomlySelectedIndicesAreSameForSameSeed() {
      assertEquals(
        mastermind.createRandomColorIndices(10, 6, 1),
        mastermind.createRandomColorIndices(10, 6, 1)
      );
    }

    @Test
    void randomlySelectedIndicesAreDifferentForDifferentSeed() {
      assertNotEquals(
        mastermind.createRandomColorIndices(10, 6, 1),
        mastermind.createRandomColorIndices(10, 6, 2)
      );
    }
}
