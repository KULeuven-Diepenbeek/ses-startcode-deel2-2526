package ses.candycrush.ui;

import ses.candycrush.model.CandyCrushGame;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javax.swing.text.Position;

public class CandyCrushBoardUI extends Region {
    private final CandyCrushGame game;
    private final int candySize;

    public CandyCrushBoardUI(CandyCrushGame game) {
        this.game = game;
        candySize = 40;
        update();
    }

    public void update(){
        getChildren().clear();

        for (var pos : game.getPositions()) {
            var candy = game.getCandyAt(pos);
            Node shape = makeCandyShape(pos, candy);
            getChildren().add(shape);
            // TODO: uncomment this and the two methods below once you have implemented getPotentialSwitchesOf
            for (var sw : game.getPotentialSwitchesOf(pos)) {
                showHintToSwitch(pos, sw);
            }
        }
    }


    private void showHintToSwitch(Position pos, Switch sw) {
        getChildren().add(makeHint(pos, sw.other(pos), false));
    }

    private Circle makeHint(Position pos1, Position pos2, boolean optimal) {
        int drow = pos2.row() - pos1.row();
        int dcol = pos2.column() - pos1.column();

        var result = new Circle(pos1.column() * candySize + candySize/2.0 + dcol * candySize / 5.0, pos1.row() * candySize + candySize/2.0 - 2 + drow * candySize/5.0, candySize/10.0);
        result.setFill(optimal ? Color.LIGHTYELLOW : Color.WHITE);
        result.setOpacity(0.5);
        result.setStroke(Color.WHITE);
        result.setStrokeWidth(0.0);
        return result;
    }

    private Node makeCandyShape(int[] pos, int candy) {
        var row = pos[0];
        var col = pos[1];
        var selected = game.isSelected(pos);
        return switch(candy) {
            case 0 /* NoCandy */ -> makeRect(row, col, Color.WHITE, selected);
            case 1 /* NormalCandy, color 0 */ -> makeCircle(row, col, Color.BLUE, selected);
            case 2 /* NormalCandy, color 1 */-> makeCircle(row, col, Color.RED, selected);
            case 3 /* NormalCandy, color 2 */-> makeCircle(row, col, Color.GREEN, selected);
            case 4 /* NormalCandy, color 3 */-> makeCircle(row, col, Color.ORANGE, selected);
            case 5 /* RowSnapper */-> makeRect(row, col, Color.PURPLE, selected);
            case 6 /* MultiCandy */-> makeRect(row, col, Color.INDIANRED, selected);
            case 7 /* RareCandy */-> makeRect(row, col, Color.DARKSLATEBLUE, selected);
            case 8 /* TurnMaster */-> makeRect(row, col, Color.GREENYELLOW, selected);
            default -> throw new IllegalArgumentException("Invalid candy number");
        };
    }

    private Rectangle makeRect(int row, int col, Color color, boolean selected) {
        int margin = 2;
        Rectangle rect = new Rectangle( col * candySize + margin, row * candySize + margin, candySize - 2*margin, candySize - 2*margin);
        rect.setFill(color);
        rect.setStroke(selected ? Color.BLACK : Color.WHITE);
        rect.setStrokeWidth(2.0);
        return rect;
    }

    private Circle makeCircle(int row, int col, Color color, boolean selected) {
        int margin = 2;
        var result = new Circle( col * candySize + candySize/2.0, row * candySize + candySize/2.0 - margin, candySize/2.0 - margin);
        result.setFill(color);
        result.setStrokeWidth(2.0);
        if (selected) {
            result.setStroke(Color.BLACK);
        } else {
            result.setStroke(Color.WHITE);
        }
        return result;
    }

    public int[] getPositionOfClicked(MouseEvent me){
        int row = (int) me.getY()/candySize;
        int column = (int) me.getX()/candySize;
        int[] pos = new int[] { row, column };
        if (game.isValidPosition(pos)) {
            return pos;
        }
        return null;
    }
}