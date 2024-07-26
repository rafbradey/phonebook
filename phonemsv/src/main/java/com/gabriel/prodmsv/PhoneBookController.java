package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ImageService;
import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import lombok.Data;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
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

    Image PhoneIcon = new Image(getClass().getResourceAsStream("images/splashIcon.png"));

    Image uploadedImage = null;
    Image defaultImage = new Image(getClass().getResourceAsStream("images/Default.jpg"));


    @FXML
    public TextField tfId;
    @FXML
    public TextField tfName;
    @FXML
    public TextField tfPhoneNumber;
    public VBox prodman;
    @FXML
    public TextField tfGroup;

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
    private ListView<Phone> lvContacts;
    private FilteredList<Phone> filteredData;
    private ObservableList<Phone> phoneList;


    UpdatePhoneController updatePhoneController;
    DeletePhoneController deletePhoneController;
    CreatePhoneController createPhoneController;
    PhoneService phoneService;
    ImageService imageService;

    String uploadedDir = "src/main/resources/com/gabriel/prodmsv/uploadedImages";

    @FXML
    private TextField txsearch;
    @FXML
    private TextField tfSocial;
    @FXML
    private Label txtitle;
    private Rectangle cbSocial;
    @FXML
    private ImageView phoneImage;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRefresh;

    @FXML
    public void onClear(ActionEvent actionEvent) {
        txsearch.setText("");
        onSearch();
    }

    @FXML
    public void onRefresh(ActionEvent actionEvent) {
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Failed to reload the scene: " + e.getMessage());
        }
    }



    //ListView Factory for phone list para malagyan ng image :) -ty stackoverflow
    public class PhoneListCell extends ListCell<Phone> {
        @Override
        protected void updateItem(Phone phone, boolean empty) {
            super.updateItem(phone, empty);

            if (empty || phone == null) {
                setText(null);
                setGraphic(null);
            } else {
                // Set the text and image for each cell
                setText("  " + phone.getName() + "\n" + "  " + phone.getPhoneNumber());
                loadImage(phone);

            }

        }

        private void loadImage(Phone phone) {
            ImageView imageView = new ImageView();

            String imageUri = phone.getImageURL();

            if (imageUri != null && !imageUri.isEmpty()) {
                try {
                    Image image = new Image(imageUri);

                    if (image.isError()) {
                        throw new Exception("Image not found at URI: " + imageUri);
                    }
                    imageView.setImage(image);
                    System.out.println("Image found for phone: " + phone.getName());
                } catch (Exception e) {
                    // Use a default image in case of an error
                    Image defaultImage = new Image(getClass().getResourceAsStream("images/Default.jpg"));
                    imageView.setImage(defaultImage);
                    System.out.println("No image found for phone: " + phone.getName() + ", reverting to DEFAULT IMAGE.");
                }
            } else {
                // Use a default image if imageUri is null or empty
                Image defaultImage = new Image(getClass().getResourceAsStream("images/Default.jpg"));
                imageView.setImage(defaultImage);
                System.out.println("No image was set for phone: " + phone.getName() + ", reverting to DEFAULT IMAGE.");
            }

            // Set image view properties
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setClip(new Circle(25, 25, 25));
            setGraphic(imageView);
        }
    }



    void refresh() throws Exception {
        phoneService = PhoneService.getService();
        Phone[] phones = phoneService.getPhones();
        phoneList.setAll(phones); // Update the phone list
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("PhoneBookController: initialize");
        disableControls();

        txsearch.setPromptText("Search...");

        phoneList = FXCollections.observableArrayList(); // Initialize phoneList

        // Create a filtered list
        filteredData = new FilteredList<>(phoneList, p -> true);
        SortedList<Phone> sortedData = new SortedList<>(filteredData);
        lvContacts.setItems(sortedData);
        lvContacts.setCellFactory(listView -> new PhoneListCell());

        // Add an event handler for the search button
        btnSearch.setOnAction(event -> onSearch());


        try {
            refresh();
            try {
                phoneImage.setImage(PhoneIcon);

                // Load the edit icon and set it to the update button
                Image contactsIcon = new Image(getClass().getResourceAsStream("images/contactW.png"));
                ImageView contactsIconView = new ImageView(contactsIcon);
                contactsIconView.setFitWidth(18);
                contactsIconView.setFitHeight(18);

                contactButton.setGraphic(contactsIconView);
                contactButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

                // Load the edit icon and set it to the update button
                Image updateIcon = new Image(getClass().getResourceAsStream("images/editW.png"));
                ImageView updateIconView = new ImageView(updateIcon);
                updateIconView.setFitWidth(18);
                updateIconView.setFitHeight(18);
                updateButton.setGraphic(updateIconView);
                updateButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

                // Load the edit icon and set it to the update button
                Image deleteIcon = new Image(getClass().getResourceAsStream("images/deleteW.png"));
                ImageView deleteIconView = new ImageView(deleteIcon);
                deleteIconView.setFitWidth(18);
                deleteIconView.setFitHeight(18);
                deleteButton.setGraphic(deleteIconView);
                deleteButton.setContentDisplay(ContentDisplay.TOP); // Set the content display to place image above text

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
        phone = lvContacts.getSelectionModel().getSelectedItem();
        if (phone == null) {
            return;
        }
        tfId.setText(Integer.toString(phone.getId()));
        setControlTexts(phone);
        System.out.println("clicked on " + phone);
        // GO to update-phone.fxml when clicked
/*
        Scene currentScene = lvContacts.getScene();
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
                updateViewScene = new Scene(root, 360, 600);
            }
            else{
                updatePhoneController.refresh();
            }
            stage.setTitle("Create Phone");
            stage.setScene(updateViewScene);
            stage.show();

        }catch (Exception ex){
            System.out.println("PhoneBook: : "+ ex.getMessage());
        }
*/

    }


    @FXML
    public void onCreate(ActionEvent actionEvent) {
        System.out.println("PhoneBook: :onNewProduct ");
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
                createViewScene = new Scene(root, 360, 600);
                String css = this.getClass().getResource("/css/main.css").toExternalForm();
                createViewScene.getStylesheets().add(css);
                stage.setTitle("PhoneBook");
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
            System.out.println("PhoneBook: : "+ ex.getMessage());
        }
    }

    @FXML
    public void onUpdate(ActionEvent actionEvent) {
        System.out.println("PhoneBook: :onUpdate ");
        Node node = ((Node) (actionEvent.getSource()));
        //Alert if no contact is selected
        if (lvContacts.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No contact selected");
            alert.setContentText("Please select a contact to update");
            alert.showAndWait();
            return;
        }

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
                updateViewScene = new Scene(root, 360, 600);
                String css = this.getClass().getResource("/css/main.css").toExternalForm();
                updateViewScene.getStylesheets().add(css);
            }
            else{
                updatePhoneController.refresh();
            }
            stage.setTitle("PhoneBook");
            stage.setScene(updateViewScene);
            stage.show();
        }
        catch(Exception ex){
            System.out.println("PhoneBook: : "+ ex.getMessage());
            ex.printStackTrace();  //print stack error; -raf
        }
    }
    @FXML
    public void onDelete(ActionEvent actionEvent) {
        System.out.println("PhoneBook: :onDelete ");
        Node node = ((Node) (actionEvent.getSource()));
        //Alert if no contact is selected
        if(lvContacts.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No contact selected");
            alert.setContentText("Please select a contact to delete");
            alert.showAndWait();
            return;
        }
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
                deleteViewScene = new Scene(root, 360, 600);
            }
            else{
                deletePhoneController.refresh();
            }
            stage.setTitle("PhoneBook");
            stage.setScene(deleteViewScene);
            stage.show();
        }
        catch(Exception ex){
            System.out.println("PhoneBook: : "+ ex.getMessage());
            ex.printStackTrace();  //print stack error; -raf

        }

    }

    @FXML
    public void onSearch() {
        String searchText = txsearch.getText().toLowerCase();
        filteredData.setPredicate(phone -> {
            // If filter text is empty, display all phones.
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            // Compare phone name with the search text.
            String lowerCaseFilter = searchText.toLowerCase();

            // Check if the name starts with the search text.
            if (phone.getName().toLowerCase().startsWith(lowerCaseFilter) ||
                    phone.getPhoneNumber().startsWith(lowerCaseFilter)) {
                return true; // Filter matches name or phone number starting with the search text.
            }
            return false; // Does not match.
        });
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
        lvContacts.getItems().add(phone);
    }

}