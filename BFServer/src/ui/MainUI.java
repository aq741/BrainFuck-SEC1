package ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.HttpService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebView;
import utils.LogUtils;

/**
 * Created by adn55 on 16/5/13.
 */
public class MainUI extends Stage {
    public Scene scene;

    public MainUI() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainUI.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        scene = new Scene(root, 600, 450);
        this.setScene(scene);
        this.setTitle("BrainFuck Server");
        this.setup();
    }

    private void setup() {
        LogUtils.init(this.textLogs);

        choiceLogLevel.setItems(FXCollections.observableArrayList("Debug", "Info", "Warning", "Error"));
        choiceLogLevel.getSelectionModel().select(0);
        choiceLogLevel.getSelectionModel().selectedIndexProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        LogUtils.setLogLevel((int) newValue);
                    }
                }
        );

        webView.getEngine().load(getClass().getResource("ServerDebugger.html").toString());
    }

    private HttpService mainService;

    @FXML
    private ToggleButton btnStartStop;
    @FXML
    private TextField inputPort;
    @FXML
    private TextArea textLogs;
    @FXML
    private ChoiceBox choiceLogLevel;
    @FXML
    private WebView webView;

    @FXML
    protected void onExitAction() {
        System.exit(0);
    }

    @FXML
    protected void onStartStopClicked() {
        if (btnStartStop.isSelected()) {
            int port;
            btnStartStop.setSelected(false);
            try {
                port = Integer.parseInt(inputPort.getText());
            } catch (Exception e) {
                return;
            }
            mainService = new HttpService(port);
            if (mainService.start()) {
                btnStartStop.setSelected(true);
                btnStartStop.setText("Stop");
            }
        } else {
            mainService.stop();
            btnStartStop.setSelected(false);
            btnStartStop.setText("Start");
        }
    }
}
