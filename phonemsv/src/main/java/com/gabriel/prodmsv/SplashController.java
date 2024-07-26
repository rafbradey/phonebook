package com.gabriel.prodmsv;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Data;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Data
public class SplashController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    @javafx.fxml.FXML
    private Button btnProceed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start();
    }

    // Automatically press onProceed in # seconds
    public void start() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> onProceed());
                    }
                },
                3800
        );
    }

    @javafx.fxml.FXML
    public void onProceed(ActionEvent actionEvent) {
        proceed();
    }

    // Overloaded method without ActionEvent parameter
    public void onProceed() {
        proceed();
    }

    // Common method for both onProceed methods
    private void proceed() {
        System.out.println("SplashApp:onClose ");
        stage.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("phonebook-view.fxml"));
            Parent root = fxmlLoader.load();
            PhoneBookController phoneBookController = fxmlLoader.getController();
            phoneBookController.setStage(stage);
            Scene scene = new Scene(root, 360, 600);
            String css = this.getClass().getResource("/css/main.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Phone Management");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Error occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
