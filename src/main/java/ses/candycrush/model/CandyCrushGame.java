package ses.candycrush.model;

import java.util.*;

public class CandyCrushGame {
    private int score;
    private final int[][] speelbord;

    private final Random random = new Random();

    public CandyCrushGame(int rows, int columns) {
        this(rows, columns, 0);
    }

    public CandyCrushGame(int rows, int columns, int score) {
        this.score = score;
        speelbord = new int[rows][columns];
    }

    public int getCandyAt(int[] pos) {
        var row = pos[0];
        var col = pos[1];
        return speelbord[row][col];
    }

    public int[] getSize() {
        return new int[] { speelbord.length, speelbord[0].length };
    }

    private int[] previousSelected = null;

    public boolean isSelected(int[] pos) {
        if (previousSelected == null) return false;
        var row = pos[0];
        var col = pos[1];
        return row == previousSelected[0] && col == previousSelected[1];
    }

    /**
     * Update the game state by selecting the candy on the given position.
     * If no candy was selected, this becomes the selected candy.
     * If another candy was already selected, both are switched (if they're adjacent).
     */
    public void selectCandy(int[] pos) {
        if (!hasCandyAt(pos)) {
            previousSelected = null;
            return;
        }
        if (!hasAnySelected()) {
            previousSelected = pos;
        } else {
            var row = pos[0];
            var col = pos[1];
            var oldCandy = speelbord[row][col];
            speelbord[row][col] = speelbord[previousSelected[0]][previousSelected[1]];
            speelbord[previousSelected[0]][previousSelected[1]] = oldCandy;
            previousSelected = null;
        }
    }

    public void start() {
    }

    private boolean hasCandyAt(int[] pos) {
        return getCandyAt(pos) != 0;
    }

    public boolean hasAnySelected() {
        return previousSelected != null;
    }

    public void setCandyAt(int row, int col, int candy) {
        speelbord[row][col] = candy;
    }

    public int score() {
        return score;
    }

    public boolean isValidPosition(int[] pos) {
        int[] size = getSize();
        var row = pos[0];
        var column = pos[1];
        return 0 <= row && row < size[0] && 0 <= column && column < size[1];
    }

    public Collection<int[]> getPositions() {
        ArrayList<int[]> result = new ArrayList<>();
        int[] size = getSize();
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < size[1]; col++) {
               result.add(new int[] { row, col });
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        var size = getSize();
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < size[1]; col++) {
                int[] pos = new int[] { row, col };
                result.append(Util.candyToString(getCandyAt(pos)));
            }
            result.append("\n");
        }
        return result.toString();
    }

    public Collection<Switch> getPotentialSwitchesOf(int[] pos) {
        // TODO
        return null;
    }
}