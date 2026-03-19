package ses.candycrush;

import org.junit.jupiter.api.Test;
import ses.candycrush.board.Board;
import ses.candycrush.board.BoardSize;
import ses.candycrush.board.Position;
import ses.candycrush.model.Candy;

import java.util.Set;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

public class Assignment02_Generics_Tests {

    @Test
    public void has_expected_methods() {
        assertThat(Board.class).hasPublicMethods(
                "getSize",
                "isValidPosition",
                "getCellAt",
                "replaceCellAt",
                "fill",
                "copyTo");
    }
    @Test
    public void size_is_stored() {
        var size = new BoardSize(10, 20);
        var board = new Board<>(size);
        assertThat(board.getSize()).isEqualTo(size);
    }

    @Test
    public void valid_position() {
        var size = new BoardSize(10, 20);
        var board = new Board<>(size);
        assertThat(board.isValidPosition(new Position(2, 3, size))).isTrue();
    }

    @Test
    public void get_cell_initial_null() {
        var size = new BoardSize(10, 20);
        var board = new Board<>(size);
        assertThat(board.getCellAt(new Position(2, 3, size))).isNull();
    }

    @Test
    public void replace_cell() {
        var size = new BoardSize(10, 20);
        var board = new Board<Candy>(size);
        var pos = new Position(2, 3, size);
        var newCandy = new Candy.NormalCandy(0);
        board.replaceCellAt(pos, newCandy);
        assertThat(board.getCellAt(pos)).isEqualTo(newCandy);
    }

    @Test
    public void fill() {
        var size = new BoardSize(10, 20);
        var board = new Board<Candy>(size);
        Function<Position, Candy> fillFunction = (pos) -> switch(pos) {
            case Position(var row, var _, var _) when row % 2 == 0 -> new Candy.NormalCandy(1);
            default -> new Candy.NormalCandy(0);
        };
        board.fill(fillFunction);
        for (var pos : size.positions()) {
            var expectedCandy = fillFunction.apply(pos);
            assertThat(board.getCellAt(pos)).isEqualTo(expectedCandy);
        }
    }

    @Test
    public void copyTo_rejects_board_of_different_size() {
        var size = new BoardSize(10, 20);
        var board = new Board<Candy>(size);
        var otherSize = new BoardSize(12, 25);
        var target = new Board<Candy>(otherSize);
        assertThatIllegalArgumentException().isThrownBy(() -> board.copyTo(target));
    }

    interface Cell {
        record A() implements Cell {}
        record B() implements Cell {}
    }

    @Test
    public void get_positions_of_test() {
        var size = new BoardSize(5, 10);
        var board = new Board<>(size);
        var posB1 = new Position(1, 3, size);
        var posB2 = new Position(1, 8, size);
        board.replaceCellAt(posB1, new Cell.B());
        board.replaceCellAt(posB2, new Cell.B());
        board.replaceCellAt(new Position(2, 2, size), new Cell.A());
        board.replaceCellAt(new Position(2, 3, size), new Cell.A());

        var result = board.getPositionsOfElement(new Cell.B());
        // is it a set?
        assertThat(result).isInstanceOf(Set.class);
        // size
        assertThat(result).hasSize(2);
        // does it contain the right elements?
        assertThat(result).containsExactlyInAnyOrder(posB1, posB2);
        // is it unmodifiable?
        assertThatException().isThrownBy(() -> result.add(new Position(2, 2, size)));
    }

}