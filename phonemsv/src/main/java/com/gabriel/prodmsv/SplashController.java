package com.gabriel.prodmsv;

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

    }

   @javafx.fxml.FXML
    public void onProceed(ActionEvent actionEvent) {
        System.out.println("SplashApp:onClose ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("phonebook-view.fxml"));
            Parent root = (Parent)fxmlLoader.load();
            PhoneBookController phoneBookController = fxmlLoader.getController();
            phoneBookController.setStage(stage);
            Scene scene = new Scene(root, 360, 600);
            String css=this.getClass().getResource("/css/main.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Phone Management");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println("Error occured" + ex.getMessage());
            //print stack
            ex.printStackTrace();
        }

    }

}


