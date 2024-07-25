package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.ServiceImpl.GroupService;
import com.gabriel.prodmsv.ServiceImpl.SocialService;
import com.gabriel.prodmsv.model.Phone;
import com.gabriel.prodmsv.model.Group;
import com.gabriel.prodmsv.model.Social;
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

    Image defaultImage = new Image(getClass().getResourceAsStream("images/Default.jpg"));

    public static Phone phone;
    @FXML
    private Button btnUpload;

    public void refresh() throws Exception {
        Phone phone = PhoneBookController.phone;

        currentImageUri = phone.getImageURL();
        System.out.println("Current image URI: " + currentImageUri);

        currentSocial = phone.getSocialName();
        currentGroup = phone.getGroupName();

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
        System.out.println("UpdatePhoneController: initialize");
        try {
            refresh();
        } catch (Exception ex) {
            System.out.println("UpdatePhoneController: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        Phone phone = new Phone();
        phone.setId(id);
        System.out.println("UpdatePhoneController:onSubmit id: " + id);
        phone.setName(tfName.getText());
        phone.setPhoneNumber(tfPhoneNumber.getText());
        phone.setAccount(tfAccountName.getText());
        phone.setBirthday(java.sql.Date.valueOf(dpBirthDate.getValue()));
        phone.setEmail(tfEmail.getText());

        if (phone.getImageURL() != null) {
            System.out.println("Phone has image URL: " + phone.getImageURL());
            System.out.println("Submitting will not change the image.");
        } else if (newImageUri != phone.getImageURL() && newImageUri != null) {
            phone.setImageURL(newImageUri);
            contactImage.setImage(new Image(newImageUri));
            System.out.println("NEW IMAGE DETECTED! switching to the new image------00000----" +
                    "-------------." + ":" + phone.getImageURL());
        } else if (newImageUri == null) {
            System.out.println("No new image detected. Using the old image ------" +
                    "------------------. " + ":" + phone.getImageURL());
            phone.setImageURL(currentImageUri);
        } else {
            System.out.println("No new image detected?. <<!@<!<!<!<!<.");
            phone.setImageURL(phone.getImageURL());
        }

        LocalDate birthDate = dpBirthDate.getValue();
        if (birthDate != null) {
            phone.setBirthday(java.sql.Date.valueOf(birthDate));
        } else {
            phone.setBirthday(null);
        }

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

        try {
            phone = PhoneService.getService().update(phone);
            controller.refresh();
            controller.setControlTexts(phone);
            onBack(actionEvent);
            newImageUri = null;
        } catch (Exception ex) {
            System.out.println("CreatePhoneController:onSubmit Error: " + ex.getMessage());
        }
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
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
}
