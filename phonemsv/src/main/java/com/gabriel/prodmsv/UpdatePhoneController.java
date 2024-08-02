package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.ServiceImpl.GroupService;
import com.gabriel.prodmsv.ServiceImpl.SocialService;
import com.gabriel.prodmsv.model.Phone;
import com.gabriel.prodmsv.model.Group;
import com.gabriel.prodmsv.model.Social;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

@Setter
public class UpdatePhoneController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    PhoneBookController controller;

    String newImageUri;
    String currentImageUri;

    String currentSocial;
    String currentGroup;

    int id;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private ComboBox<Group> cbGroup;
    @FXML
    private Button btnBack;
    @FXML
    private ComboBox<Social> cbSocial;
    @FXML
    private TextField tfAccountName;
    @FXML
    private Button btnSubmit;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField tfEmail;
    @FXML
    private ImageView contactImage;
    @FXML
    public Button closeButton;
    @FXML
    private ImageView phoneImage;

    Image PhoneIcon = new Image(getClass().getResourceAsStream("images/splashIcon.png"));

    Image defaultImage = new Image(getClass().getResourceAsStream("images/Default.jpg"));

    public static Phone phone;
    @FXML
    private Button btnUpload;
    @FXML
    private Label txtitle;
    @FXML
    private Button btntoggleEdit;
    @FXML
    private Button btnClear;
    @FXML
    private Button dpClear;

    public void refresh() throws Exception {


        Phone phone = PhoneBookController.phone;

        currentImageUri = phone.getImageURL();
        System.out.println("Current image URI: " + currentImageUri);

        currentSocial = phone.getSocialName();
        currentGroup = phone.getGroupName();

        btntoggleEdit.setText("Edit: OFF");
        fieldDisabler(new ActionEvent());
        txtitle.setText("View Contacts");

        System.out.println("Current id: " + id);
        id = phone.getId();

        String imageURI = phone.getImageURL();
        try {
            Image image = new Image(imageURI);
            contactImage.setImage(image);
        } catch (Exception e) {
            contactImage.setImage(defaultImage);
        }

        tfName.setText(phone.getName());
        tfPhoneNumber.setText(phone.getPhoneNumber());
        tfAccountName.setText(phone.getAccount());
        tfEmail.setText(phone.getEmail());

        if (phone.getBirthday() != null) {
            LocalDate localDate = phone.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dpBirthDate.setValue(localDate);
        } else {
            dpBirthDate.setValue(null);
        }


        try {

            cbGroup.getItems().clear();
            cbSocial.getItems().clear();
            Group groups[] = GroupService.getService().getGroups();
            System.out.println("Fetched groups: " + Arrays.toString(groups));
            cbGroup.getItems().addAll(groups);

            Social socials[] = SocialService.getService().getSocials();
            System.out.println("Fetched socials: " + Arrays.toString(socials));
            cbSocial.getItems().addAll(socials);


            if (phone.getGroupName() != null || phone.getSocialName() != null) {
                System.out.println("Group ID is NOT null.");

                for (Group group : groups) {
                    if (group.getId() == phone.getGroupId()) {
                        cbGroup.getSelectionModel().select(group);
                        break;
                    }
                }
                for (Social social : socials) {
                    if (social.getId() == phone.getSocialId()) {
                        cbSocial.getSelectionModel().select(social);
                        break;
                    }
                }
            } else {
                System.out.println("Group ID and Social ID is null.");
                cbGroup.getSelectionModel().clearSelection();
                cbSocial.getSelectionModel().clearSelection();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phoneImage.setImage(PhoneIcon);
        System.out.println("UpdatePhoneController: initialize");

        btntoggleEdit.setText("Edit: OFF");
        fieldDisabler(new ActionEvent());


        try {
            Image clear = new Image(getClass().getResourceAsStream("images/trashcanG.png"));
            ImageView clearIconView = new ImageView(clear);
            clearIconView.setFitWidth(18);
            clearIconView.setFitHeight(18);
            dpClear.setGraphic(clearIconView);
            dpClear.setContentDisplay(ContentDisplay.CENTER);

            refresh();
            // Disable typing in the dpBirthDate DatePicker
            disableDatePickerTextField(dpBirthDate);
        } catch (Exception ex) {
            System.out.println("UpdatePhoneController: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        // Get values from input fields
        String name = tfName.getText().trim();
        String phoneNumber = tfPhoneNumber.getText().trim();
        String account = tfAccountName.getText().trim();
        String email = tfEmail.getText().trim();
        LocalDate birthDate = dpBirthDate.getValue();

        // Validate required fields
        if (name.isEmpty() || phoneNumber.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Missing Required Fields");
            alert.setContentText("Please provide both Name and Phone Number.");
            alert.showAndWait();
            return;
        }

        // Create a Phone object with input values
        Phone phone = new Phone();
        phone.setId(id);
        phone.setName(name);
        phone.setPhoneNumber(phoneNumber);
        phone.setAccount(account);
        phone.setEmail(email);

        // Set birthday, handling potential null value
        phone.setBirthday(birthDate != null ? java.sql.Date.valueOf(birthDate) : null);

        /* @Depreciated -- Old image handler codde
        // Handle image URLs
        if (newImageUri != null && !newImageUri.equals(phone.getImageURL())) {
            phone.setImageURL(newImageUri);
            contactImage.setImage(new Image(newImageUri));
        } else if (newImageUri == null) {
            phone.setImageURL(currentImageUri);
        }
    */
        // Handle image URLs
        if (newImageUri != null && !newImageUri.equals(phone.getImageURL())) {
            phone.setImageURL(newImageUri);
            contactImage.setImage(new Image(newImageUri));
        } else {
            // Use the default image if no new image URI is present
            phone.setImageURL(currentImageUri != null ? currentImageUri : defaultImage.getUrl());
            contactImage.setImage(defaultImage);
        }

        // Set group and social if selected
        Group group = cbGroup.getSelectionModel().getSelectedItem();
        if (group != null) {
            phone.setGroupId(group.getId());
            phone.setGroupName(group.getName());
        }

        Social social = cbSocial.getSelectionModel().getSelectedItem();
        if (social != null) {
            phone.setSocialId(social.getId());
            phone.setSocialName(social.getName());
        }

        // Update phone and handle potential exceptions
        try {
            phone = PhoneService.getService().update(phone);
            controller.refresh();
            controller.setControlTexts(phone);
            fieldDisabler(actionEvent);
            onBack(actionEvent);
            newImageUri = null;
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText("An error occurred during the update.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
       //edit btn off by default
        btntoggleEdit.setText("Edit: OFF");
        fieldDisabler(new ActionEvent());
        txtitle.setText("View Contacts");
        System.out.println("CreatePhoneController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }

    @FXML
    public void onUpload(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            contactImage.setImage(image);
            newImageUri = file.toURI().toString();
        }
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? ", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    private void disableDatePickerTextField(DatePicker datePicker) {
        datePicker.getEditor().setDisable(true);
        datePicker.getEditor().setStyle("-fx-opacity: 1;");
    }


    @Deprecated
    public void fieldDisabler(ActionEvent actionEvent) {
        tfName.setEditable(false);
        tfPhoneNumber.setEditable(false);
        tfAccountName.setEditable(false);
        tfEmail.setEditable(false);
        dpBirthDate.setDisable(true);
        dpBirthDate.getEditor().setDisable(true);
        cbGroup.setDisable(true);
        cbSocial.setDisable(true);
        btnUpload.setDisable(true);
        btnUpload.setVisible(false);
        btnSubmit.setDisable(true);
        btnSubmit.setDisable(true);
        btnClear.setDisable(true);
        dpClear.setDisable(true);
        btnClear.setVisible(false);
        dpClear.setVisible(false);
    }

    @Deprecated
    public void fieldEnabler(ActionEvent actionEvent) {
        tfName.setEditable(true);
        tfPhoneNumber.setEditable(true);
        tfAccountName.setEditable(true);
        tfEmail.setEditable(true);
        dpBirthDate.getEditor().setDisable(false);
        dpBirthDate.setDisable(false);
        disableDatePickerTextField(dpBirthDate);
        cbGroup.setDisable(false);
        cbSocial.setDisable(false);
        btnUpload.setDisable(false);
        btnUpload.setVisible(true);
        btnSubmit.setDisable(false);
        btnSubmit.setVisible(true);
        btnClear.setVisible(true);
        dpClear.setVisible(true);
        dpClear.setDisable(false);
        if (contactImage.getImage() != defaultImage) {
            btnClear.setDisable(false);
        }
        else {
            btnClear.setDisable(true);
        }

    }


    @FXML
    public void toggleONOFF(ActionEvent actionEvent) {
        if (btntoggleEdit.getText().equals("Edit: OFF")) {
            btntoggleEdit.setText("Edit: ON");
            btnSubmit.setText("Submit");
            txtitle.setText("Edit Contacts");
            fieldEnabler(actionEvent);
        } else {
            btntoggleEdit.setText("Edit: OFF");
            btnSubmit.setText("");
            txtitle.setText("View Contacts");
            fieldDisabler(actionEvent);
        }
    }

    @FXML
    public void onClear(ActionEvent actionEvent) {
        //set image to default
        Platform.runLater(() -> contactImage.setImage(defaultImage)); //allow for change image on fx runtime?
        newImageUri = null;
        currentImageUri = defaultImage.getUrl();
    }

    @FXML
    public void ondpClear(ActionEvent actionEvent) {
        dpBirthDate.setValue(null);
    }
}




