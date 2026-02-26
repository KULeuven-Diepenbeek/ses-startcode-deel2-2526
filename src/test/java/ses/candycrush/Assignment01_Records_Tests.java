package ses.candycrush;

import ses.candycrush.board.BoardSize;
import ses.candycrush.board.Position;
import ses.candycrush.model.Candy;
import ses.candycrush.model.CandyCrushGame;
import ses.candycrush.model.Switch;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ses.candycrush.model.Util;

import static org.assertj.core.api.Assertions.*;

public class Assignment01_Records_Tests {
    /*************************************************************************
     * DO NOT MODIFY THE CODE OF THESE TESTS!
     * If these tests do not compile/run, your assignment will not be graded.
     ************************************************************************/

    @Nested
    class BoardSize_Tests {
        @Test
        public void is_a_record() {
            assertThat(BoardSize.class).isRecord();
        }

        @Test
        public void has_all_methods() {
            assertThat(BoardSize.class).hasPublicMethods(
                    "rows",
                    "columns",
                    "positions");
        }

        @Test
        public void stores_number_of_rows_and_columns() {
            BoardSize boardSize = new BoardSize(10, 20);
            assertThat(boardSize.rows()).isEqualTo(10);
            assertThat(boardSize.columns()).isEqualTo(20);
        }

        @Test
        public void throws_if_rows_negative() {
            assertThatThrownBy(() -> new BoardSize(-1, 20)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void throws_if_cols_negative() {
            assertThatThrownBy(() -> new BoardSize(10, -2)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void returns_all_positions() {
            BoardSize boardSize = new BoardSize(2, 3);
            assertThat(boardSize.positions()).containsExactlyInAnyOrder(
                    new Position(0, 0, boardSize),
                    new Position(0, 1, boardSize),
                    new Position(0, 2, boardSize),
                    new Position(1, 0, boardSize),
                    new Position(1, 1, boardSize),
                    new Position(1, 2, boardSize)
            );
        }
    }

    @Nested
    class Position_Tests {
        @Test
        public void is_a_record() {
            assertThat(Position.class).isRecord();
        }

        @Test
        public void has_all_methods() {
            assertThat(Position.class)
                    .hasPublicMethods(
                            "row",
                            "column",
                            "boardSize",
                            "toIndex",
                            "neighbors",
                            "isNeighborOf",
                            "isFirstRow",
                            "isLastRow",
                            "isFirstColumn",
                            "isLastColumn");
        }

        @Test
        public void stores_row_column_and_boardsize() {
            BoardSize boardSize = new BoardSize(10, 20);
            Position position = new Position(2, 5, boardSize);
            assertThat(position.row()).isEqualTo(2);
            assertThat(position.column()).isEqualTo(5);
            assertThat(position.boardSize()).isEqualTo(boardSize);
        }

        @Test
        public void throws_if_invalid_position() {
            BoardSize boardSize = new BoardSize(10, 20);
            assertThatThrownBy(() -> new Position(10, 5, boardSize)).isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> new Position(3, 20, boardSize)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void to_index_correct() {
            /* 0 1 2 3
               4 5 6 7 */
            BoardSize boardSize = new BoardSize(2, 4);
            Position pos = new Position(1, 2, boardSize);
            assertThat(pos.toIndex()).isEqualTo(6);
        }

        @Test
        public void from_index_correct() {
            /* 0 1 2 3
               4 5 6 7 */
            BoardSize boardSize = new BoardSize(2, 4);
            assertThat(Position.fromIndex(6, boardSize)).isEqualTo(new Position(1, 2, boardSize));
            assertThatThrownBy(() -> Position.fromIndex(8, boardSize)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        public void is_first_row_correct() {
            BoardSize boardSize = new BoardSize(5, 4);
            assertThat(new Position(1, 2, boardSize).isFirstRow()).isFalse();
            assertThat(new Position(0, 2, boardSize).isFirstRow()).isTrue();
        }

        @Test
        public void is_last_row_correct() {
            BoardSize boardSize = new BoardSize(5, 4);
            assertThat(new Position(1, 2, boardSize).isLastRow()).isFalse();
            assertThat(new Position(4, 2, boardSize).isLastRow()).isTrue();
        }

        @Test
        public void is_first_column_correct() {
            BoardSize boardSize = new BoardSize(5, 4);
            assertThat(new Position(1, 2, boardSize).isFirstColumn()).isFalse();
            assertThat(new Position(1, 0, boardSize).isFirstColumn()).isTrue();
        }

        @Test
        public void is_last_column_correct() {
            BoardSize boardSize = new BoardSize(5, 4);
            assertThat(new Position(1, 2, boardSize).isLastColumn()).isFalse();
            assertThat(new Position(1, 3, boardSize).isLastColumn()).isTrue();
        }

        @Test
        public void is_neighbor_of_left() {
            BoardSize boardSize = new BoardSize(10, 20);
            Position pos = new Position(2, 4, boardSize);
            Position pos2 = new Position(2, 3, boardSize);
            assertThat(pos.isNeighborOf(pos2)).isTrue();
        }

        @Test
        public void neighbors_correct() {
            BoardSize boardSize = new BoardSize(10, 20);
            Position pos = new Position(2, 4, boardSize);
            assertThat(pos.neighbors()).containsExactlyInAnyOrder(
                    new Position(1, 4, boardSize),
                    new Position(2, 5, boardSize),
                    new Position(3, 4, boardSize),
                    new Position(2, 3, boardSize)
            );
        }

    }

    @Nested
    class Switch_Tests {
        @Test
        public void is_a_record() {
            assertThat(BoardSize.class).isRecord();
        }
        @Test
        public void first_second_correct_order() {
            BoardSize boardSize = new BoardSize(10, 20);
            Position pos1 = new Position(2, 5, boardSize);
            Position pos2 = new Position(3, 5, boardSize);
            Switch sw = new Switch(pos1, pos2);
            assertThat(sw.first()).isEqualTo(pos1);
            assertThat(sw.second()).isEqualTo(pos2);
        }

        @Test
        public void first_second_opposite_order() {
            BoardSize boardSize = new BoardSize(10, 20);
            Position pos1 = new Position(3, 5, boardSize);
            Position pos2 = new Position(2, 5, boardSize);
            Switch sw = new Switch(pos1, pos2);
            assertThat(sw.first()).isEqualTo(pos2);
            assertThat(sw.second()).isEqualTo(pos1);
        }

    }

    @Nested
    class Candy_Tests {
        @Test
        public void is_interface() {
            assertThat(Candy.class).isInterface();
        }

        @Test
        public void all_candies_are_records() {
            assertThat(Candy.NoCandy.class).isRecord();
            assertThat(Candy.NormalCandy.class).isRecord();
            assertThat(Candy.RowSnapper.class).isRecord();
            assertThat(Candy.MultiCandy.class).isRecord();
            assertThat(Candy.RareCandy.class).isRecord();
            assertThat(Candy.TurnMaster.class).isRecord();
        }

        @Test
        public void normal_has_color() {
            var normal = new Candy.NormalCandy(2);
            assertThat(normal.color()).isEqualTo(2);
        }

    }

    @Nested
    class Game_Tests {
        @Test
        public void board_methods_modified() {
            BoardSize boardSize = new BoardSize(5, 4);
            CandyCrushGame game = new CandyCrushGame(boardSize);
            Candy candy = new Candy.NormalCandy(1);
            Position pos = new Position(1, 2, boardSize);
            game.setCandyAt(pos, candy);
            assertThat(game.getCandyAt(pos)).isEqualTo(candy);
        }
    }
}