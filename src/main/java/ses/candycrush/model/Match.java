package ses.candycrush.model;

import ses.candycrush.board.Position;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public record Match(List<Position> positions) {

    public Match {
        if (positions == null) throw new NullPointerException("positions");
        if (positions.isEmpty()) throw new IllegalArgumentException("match has no positions");
        if (containsNull(positions)) throw new IllegalArgumentException("positions contains null elements");
        if (!areAdjacentFromLeftToRight(positions) && !areAdjacentFromTopToBottom(positions)) throw new IllegalArgumentException("invalid positions");
    }

    private static boolean containsNull(List<Position> positions) {
        // TODO
        return false;
    }

    private static boolean areAdjacentFromLeftToRight(List<Position> positions) {
        // TODO
        return false;
    }

    private static boolean areAdjacentFromTopToBottom(List<Position> positions) {
        // TODO
        return false;
    }

    public int length() {
        return positions.size();
    }
}
