package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by adn55 on 16/5/20.
 */
public class SplashUI extends Stage {
    public Scene scene;

    public SplashUI() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assets/SplashUI.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        this.scene = new Scene(root);
        this.setScene(this.scene);
        this.setTitle("BrainFuck IDE");
        this.initStyle(StageStyle.TRANSPARENT);
    }
}
