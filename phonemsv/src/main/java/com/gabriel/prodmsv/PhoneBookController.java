package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Data;
import lombok.Setter;

import java.awt.*;
import java.net.URL;
import java.util.*;
@Data
public class PhoneBookController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    Scene createViewScene;
    @Setter
    Scene updateViewScene;
    @Setter
    Scene deleteViewScene;

    @FXML
    public TextField tfId;
    @FXML
    public TextField tfName;
    @FXML
    public TextField tfPhoneNumber;
    @FXML
    public ImageView productImage;
    public VBox prodman;
    @FXML
    public TextField tfGroup;

    Image puffy;
    Image wink;

    @FXML
    public Button createButton;
    @FXML
    public Button updateButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button closeButton;
    @FXML
    public Button contactButton;
    @FXML
    public Button favoriteButton;
    @FXML
    public Button settingsButton;

    public static Phone phone;
    @FXML
    private ListView<Phone> lvProducts;

    UpdatePhoneController updatePhoneController;
    DeletePhoneController deletePhoneController;
    CreatePhoneController createPhoneController;
    PhoneService phoneService;
    @FXML
    private TextField txsearch;
    @FXML
    private Button onSearch;
    @FXML
    private TextField tfSocial;
    @FXML
    private Label txtitle;
    @FXML
    private Rectangle cbGroup;
    private Rectangle cbSocial;

    void refresh() throws Exception{
        phoneService = PhoneService.getService();
        Phone[] phones = phoneService.getPhones();
        lvProducts.getItems().clear();
        lvProducts.getItems().addAll(phones);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("PhoneBookController: initialize");
        disableControls();

        try {
            refresh();
            try {
                puffy = new Image(getClass().getResourceAsStream("images/splashIcon.png"));
                wink = new Image(getClass().getResourceAsStream("/images/wink.gif"));
                productImage.setImage(puffy);

                // Load the edit icon and set it to the update button
                Image contactsIcon = new Image(getClass().getResourceAsStream("images/contacts.jpg"));
                ImageView contactsIconView = new ImageView(contactsIcon);
                contactsIconView.setFitWidth(18);
                contactsIconView.setFitHeight(18);
                contactButton.setGraphic(contactsIconView);
                contactButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

                // Load the edit icon and set it to the update button
                Image updateIcon = new Image(getClass().getResourceAsStream("images/edit.jpg"));
                ImageView updateIconView = new ImageView(updateIcon);
                updateIconView.setFitWidth(18);
                updateIconView.setFitHeight(18);
                updateButton.setGraphic(updateIconView);
                updateButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

                // Load the edit icon and set it to the update button
                Image deleteIcon = new Image(getClass().getResourceAsStream("images/delete.png"));
                ImageView deleteIconView = new ImageView(deleteIcon);
                deleteIconView.setFitWidth(18);
                deleteIconView.setFitHeight(18);
                deleteButton.setGraphic(deleteIconView);
                deleteButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

                // Load the edit icon and set it to the update button
                Image favoriteIcon = new Image(getClass().getResourceAsStream("images/favorite.png"));
                ImageView favoriteIconView = new ImageView(favoriteIcon);
                favoriteIconView.setFitWidth(18);
                favoriteIconView.setFitHeight(18);
                favoriteButton.setGraphic(favoriteIconView);
                favoriteButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

                // Load the edit icon and set it to the update button
                Image settingsIcon = new Image(getClass().getResourceAsStream("images/settings.jpg"));
                ImageView settingsIconView = new ImageView(settingsIcon);
                settingsIconView.setFitWidth(16);
                settingsIconView.setFitHeight(16);
                settingsButton.setGraphic(settingsIconView);
            }
            catch(Exception ex){
                System.out.println("Error with image: " + ex.getMessage());
            }
        }
        catch (Exception ex){
            showErrorDialog("Message: " + ex.getMessage());
        }
    }

    public  void disableControls(){
        tfId.editableProperty().set(false);
        tfName.editableProperty().set(false);
        tfPhoneNumber.editableProperty().set(false);
        tfGroup.editableProperty().set(false);
        tfSocial.editableProperty().set(false);
    }

    public void setControlTexts(Phone phone){
        tfName.setText(phone.getName());
       tfPhoneNumber.setText(phone.getPhoneNumber());
     tfGroup.setText(phone.getGroupName());
        tfSocial.setText(phone.getSocialName());
    }

    public void clearControlTexts(){
        tfId.setText("");
        tfName.setText("");
        tfPhoneNumber.setText("");
        tfGroup.setText("");
        tfSocial.setText("");
    }

    @FXML
    public void onMouseClicked(MouseEvent mouseEvent) {
        phone = lvProducts.getSelectionModel().getSelectedItem();
        if(phone == null) {
            return;
        }
        tfId.setText(Integer.toString(phone.getId()));
        setControlTexts(phone);
        System.out.println("clicked on " + phone);
        //show phone number social and group in soupln
        System.out.println("clicked on " + phone.getPhoneNumber());
        System.out.println("clicked on " + phone.getSocialName());
        System.out.println("clicked on " + phone.getGroupName());
    }

    @FXML
    public void onCreate(ActionEvent actionEvent) {
        System.out.println("ProdmanController:onNewProduct ");
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if(createViewScene ==null) {
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("create-phone.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                createPhoneController = fxmlLoader.getController();
                createPhoneController.setStage(this.stage);
                createPhoneController.setParentScene(currentScene);
                createPhoneController.setPhoneService(phoneService);
                createPhoneController.setPhoneBookController(this);
                createPhoneController.setParentScene(currentScene);
                createViewScene = new Scene(root, 300, 600);
                stage.setTitle("Manage Phone");
                stage.setScene(createViewScene);
                stage.show();
            }
            else{
                stage.setScene(createViewScene);
                stage.show();
            }
            createPhoneController.clearControlTexts();
            clearControlTexts();
        }
        catch(Exception ex){
            System.out.println("ProdmanController: "+ ex.getMessage());
        }
    }

    @FXML
    public void onUpdate(ActionEvent actionEvent) {
        System.out.println("ProdmanController:onUpdate ");
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if(updateViewScene ==null) {
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("update-phone.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                updatePhoneController = fxmlLoader.getController();
                updatePhoneController.setController(this);
                updatePhoneController.setStage(this.stage);
                updatePhoneController.setParentScene(currentScene);
                updateViewScene = new Scene(root, 300, 600);
            }
            else{
                updatePhoneController.refresh();
            }
            stage.setTitle("Create Phone");
            stage.setScene(updateViewScene);
            stage.show();
        }
        catch(Exception ex){
            System.out.println("ProdmanController: "+ ex.getMessage());
        }
    }
    @FXML
    public void onDelete(ActionEvent actionEvent) {
        System.out.println("ProdmanController:onDelete ");
        Node node = ((Node) (actionEvent.getSource()));
        Scene currentScene = node.getScene();
        Window window = currentScene.getWindow();
        window.hide();
        try {
            if(deleteViewScene  ==null) {
                FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("delete-phone.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                deletePhoneController = fxmlLoader.getController();
                deletePhoneController.setController(this);
                deletePhoneController.setStage(this.stage);
                deletePhoneController.setParentScene(currentScene);
                deleteViewScene = new Scene(root, 300, 600);
            }
            else{
                deletePhoneController.refresh();
            }
            stage.setTitle("Delete Phone");
            stage.setScene(deleteViewScene);
            stage.show();
        }
        catch(Exception ex){
            System.out.println("ProdmanController: "+ ex.getMessage());
            ex.printStackTrace();  //print stack error; -raf

        }
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? " , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    void showErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        // alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(message)));
        alert.showAndWait();
    }

    public void addItem(Phone phone){
        lvProducts.getItems().add(phone);
    }

}
