package ses.candycrush;

import org.junit.jupiter.api.Test;
import ses.candycrush.board.BoardSize;
import ses.candycrush.board.Position;
import ses.candycrush.model.*;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class Assignment03_Streams_Tests {

    @Test
    public void test_walk_left() {
        BoardSize size = new BoardSize(6, 6);
        Position pos = new Position(2, 2, size);
        assertThatStream(pos.walkLeft()).containsExactly(
                pos,
                new Position(2, 1, size),
                new Position(2, 0, size));
    }

    @Test
    public void test_walk_right() {
        BoardSize size = new BoardSize(6, 6);
        Position pos = new Position(2, 2, size);
        assertThatStream(pos.walkRight()).containsExactly(
                pos,
                new Position(2, 3, size),
                new Position(2, 4, size),
                new Position(2, 5, size));
    }

    @Test
    public void test_walk_up() {
        BoardSize size = new BoardSize(6, 6);
        Position pos = new Position(2, 2, size);
        assertThatStream(pos.walkUp()).containsExactly(
                pos,
                new Position(1, 2, size),
                new Position(0, 2, size));
    }

    @Test
    public void test_walk_down() {
        BoardSize size = new BoardSize(6, 6);
        Position pos = new Position(2, 2, size);
        assertThatStream(pos.walkDown()).containsExactly(
                pos,
                new Position(3, 2, size),
                new Position(4, 2, size),
                new Position(5, 2, size));
    }

    @Test
    public void test_match_rejects_not_all_on_same_row_or_column() {
        var size = new BoardSize(6, 6);
        var positions = List.of(
                new Position(0, 0, size),
                new Position(0, 1, size),
                new Position(1, 1, size));
        assertThatIllegalArgumentException().isThrownBy(() -> new Match(positions)).withMessage("invalid positions");
    }

    @Test
    public void test_match_rejects_non_adjacent_on_same_row() {
        var size = new BoardSize(6, 6);
        var positions = List.of(
                new Position(0, 0, size),
                new Position(0, 1, size),
                new Position(0, 3, size));
        assertThatIllegalArgumentException().isThrownBy(() -> new Match(positions)).withMessage("invalid positions");
    }


    @Test
    public void test_match_accepts_vertical_match_of_size_2() {
        var size = new BoardSize(6, 6);
        var positions = List.of(
                new Position(0, 0, size),
                new Position(1, 0, size));
        assertThat(new Match(positions).positions()).isEqualTo(positions);
    }

    @Test
    public void test_first_two_candies_equal_true() {
        var candy0 = new Candy.NormalCandy(0);
        var candy1 = new Candy.NormalCandy(1);
        var stream = Stream.of(candy0, candy0, candy1);

        assertThat(CandyCrushGame.firstTwoEqual(candy0, stream)).isTrue();
    }

    @Test
    public void test_first_two_candies_equal_false() {
        var candy0 = new Candy.NormalCandy(0);
        var candy1 = new Candy.NormalCandy(1);
        var stream = Stream.of(candy0, candy1, candy1);

        assertThat(CandyCrushGame.firstTwoEqual(candy0, stream)).isFalse();
    }

    @Test
    public void test_vertical_starting_positions() {
        var gameModel = Util.createBoardFromString("""
                #@..#
                @***#
                o##@#
                @##*#""");
        var size = gameModel.getSize();

        Set<Position> positions = new HashSet<>(size.positions());
        positions.removeAll(Set.of(
                new Position(0, 2, size),
                new Position(0, 3, size),
                new Position(1, 4, size),
                new Position(2, 4, size),
                new Position(3, 1, size),
                new Position(3, 2, size),
                new Position(3, 4, size)));

        assertThatStream(gameModel.verticalStartingPositions()).containsExactlyInAnyOrderElementsOf(positions);
    }

    @Test
    public void test_longest_match_to_right_length_5() {
        var gameModel = Util.createBoardFromString("""
                *****#""");
        var size = gameModel.getSize();

        assertThat(gameModel.longestMatchToRight(new Position(0, 0, size)))
                .isEqualTo(List.of(
                        new Position(0, 0, size),
                        new Position(0, 1, size),
                        new Position(0, 2, size),
                        new Position(0, 3, size),
                        new Position(0, 4, size)));
    }

    @Test
    public void test_longest_match_down_length_2() {
        var gameModel = Util.createBoardFromString("""
                *
                *
                *
                *
                *
                #""");
        var size = gameModel.getSize();

        assertThat(gameModel.longestMatchDown(new Position(3, 0, size)))
                .isEqualTo(List.of(new Position(3, 0, size), new Position(4, 0, size)));
    }

    @Test
    public void test_find_all_matches() {
        var gameModel = Util.createBoardFromString("""
                #@..#
                @***#
                o##@#
                @##*#""");

        var redMatch1 = new Position(1, 1, gameModel.getSize());
        var redMatch2 = new Position(1, 2, gameModel.getSize());
        var redMatch3 = new Position(1, 3, gameModel.getSize());

        var greenMatch1 = new Position(0, 4, gameModel.getSize());
        var greenMatch2 = new Position(1, 4, gameModel.getSize());
        var greenMatch3 = new Position(2, 4, gameModel.getSize());
        var greenMatch4 = new Position(3, 4, gameModel.getSize());

        assertThat(gameModel.findAllMatches()).containsExactlyInAnyOrder(
                new Match(List.of(redMatch1, redMatch2, redMatch3)),
                new Match(List.of(greenMatch1, greenMatch2, greenMatch3, greenMatch4))
        );
    }

    @Test
    public void test_potential_switches() {
        BoardSize boardSize = new BoardSize(3, 4);
        CandyCrushGame game = Util.createBoardFromString("""
                    ..x.
                    oox.
                    xxox""");
        var pos = new Position(2, 2, boardSize);
        assertThat(game.getPotentialSwitchesOf(pos)).containsExactlyInAnyOrder(
                new Switch(pos, new Position(1, 2, boardSize)),
                new Switch(pos, new Position(2, 3, boardSize)),
                new Switch(pos, new Position(2, 1, boardSize))
        );

    }
}
