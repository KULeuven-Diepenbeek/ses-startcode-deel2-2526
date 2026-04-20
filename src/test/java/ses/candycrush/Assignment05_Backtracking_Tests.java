package ses.candycrush;

import org.junit.jupiter.api.Test;
import ses.candycrush.board.Position;
import ses.candycrush.model.CandyCrushGame;
import ses.candycrush.model.Switch;
import ses.candycrush.model.Util;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class Assignment05_Backtracking_Tests {

    private static void applyMoves(CandyCrushGame game, List<Switch> moves) {
        for (var move : moves) {
            game.selectCandy(move.first());
            game.selectCandy(move.second());
        }
    }

    @Test
    public void max_score_empty() {
        CandyCrushGame game = Util.createBoardFromString("""
            ...
            ...""");
        var solution = game.maximizeScore();
        assertThat(solution).isNotNull();
        assertThat(solution).isEmpty();
        applyMoves(game, solution);
        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    public void max_score_L_match() {
        CandyCrushGame game = Util.createBoardFromString("""
            ..*.
            #.*.
            **#*""");
        var solution = game.maximizeScore();
        var pos1 = new Position(2, 2, game.getSize());
        var pos2 = new Position(2, 3, game.getSize());
        var expectedSwitch = new Switch(pos1, pos2);
        assertThat(solution).isNotNull();
        assertThat(solution).hasSize(1).containsExactly(expectedSwitch);
        applyMoves(game, solution);
        assertThat(game.score()).isEqualTo(5);
    }

    @Test
    public void example_model1() {
        CandyCrushGame game = Util.createBoardFromString("""
                @@o#
                o*#o
                @@**
                *#@@""");
        System.out.println(game);
        var result = game.maximizeScore();
        System.out.println(result);
        assertThat(result).hasSize(4);
        applyMoves(game, result);
        assertThat(game.score()).isEqualTo(16);
    }

    @Test
    public void example_model2() {
        CandyCrushGame game = Util.createBoardFromString("""
                #oo##
                #@o@@
                *##o@
                @@*@o
                **#*o""");
        var result = game.maximizeScore();
        System.out.println(result);
        assertThat(result).hasSize(7);
        applyMoves(game, result);
        assertThat(game.score()).isEqualTo(23);
    }

    @Test
    public void example_model_3() {
        CandyCrushGame game = Util.createBoardFromString("""
                #@#oo@
                @**@**
                o##@#o
                @#oo#@
                @*@**@
                *#@##*""");
        var result = game.maximizeScore();
        System.out.println(result);
        System.out.println(game);
        assertThat(result).hasSize(9);
        applyMoves(game, result);
        assertThat(game.score()).isEqualTo(33);
    }
}