package ses.candycrush.ui;

import java.util.ResourceBundle;

import ses.candycrush.model.CandyCrushGame;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label score;

    @FXML
    private Pane boardPane;

    private CandyCrushGame game;
    private CandyCrushBoardUI speelbord;

    @FXML
    void initialize() {
        assert score != null : "fx:id=\"score\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        assert boardPane != null : "fx:id=\"boardPane\" was not injected: check your FXML file 'candycrush-view.fxml'.";
        speelbord = new CandyCrushBoardUI(game);
        boardPane.getChildren().add(speelbord);
        speelbord.setOnMouseClicked(this::onCandyClicked);
        updateView();
    }

    public Controller(CandyCrushGame game) {
        this.game = game;
    }

    private void updateView() {
        score.setText("Score: " + game.score());
        speelbord.update();
    }

    public void onCandyClicked(MouseEvent me){
        var pos = speelbord.getPositionOfClicked(me);
        game.selectCandy(pos);
        updateView();
    }

}