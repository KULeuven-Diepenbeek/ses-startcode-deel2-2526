package ses.candycrush;

import ses.candycrush.model.Util;
import ses.candycrush.ui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CandyCrushApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var gameModel = Util.createBoardFromString("""
                #@#oo@
                @**@**
                o##@#o
                @#oo#@
                @*@**@
                *#@##*""");
        Controller controller = new Controller(gameModel);
        gameModel.start();

        FXMLLoader fxmlLoader = new FXMLLoader(CandyCrushApplication.class.getResource("/candycrush-view.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("SES - CandyCrush");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}