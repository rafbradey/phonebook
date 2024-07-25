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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
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

    int id;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private ComboBox cbGroup;
    @FXML
    private Button btnBack;
    @FXML
    private ComboBox cbSocial;
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

    public void refresh() throws Exception{

        Phone phone = PhoneBookController.phone;
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

        try {
            // Initialize groups
            Group[] groups = GroupService.getService().getGroups();
            cbGroup.getItems().clear();
            cbGroup.getItems().addAll(groups);

            // Initialize socials
            Social[] socials = SocialService.getService().getSocials();
            cbSocial.getItems().clear();
            cbSocial.getItems().addAll(socials);

            for (Group group : groups) {
                if (group.getId() == phone.getGroupId()) {
                    cbGroup.getSelectionModel().select(group);
                    break;
                }
            }

            // Select the appropriate social
            for (Social social : socials) {
                if (social.getId() == phone.getSocialId()) {
                    cbSocial.getSelectionModel().select(social);
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("UpdatePhoneController: " + ex.getMessage());
            ex.printStackTrace();
        }




        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdatePhoneController: initialize");
      //  tfId=new TextField();
        try {
            refresh();

        }
        catch(Exception ex){
            System.out.println("UpdatePhoneController: " + ex.getMessage());
            //print stack error
            ex.printStackTrace();
        }
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
        Phone phone = new Phone();
        //get current id but its not in text, but in the phone object
        phone.setId(id);
        System.out.println("UpdatePhoneController:onSubmit id: " + id);
        phone.setName(tfName.getText());
        phone.setPhoneNumber(tfPhoneNumber.getText());
        phone.setAccount(tfAccountName.getText());
       // phone.setBirthday(java.sql.Date.valueOf(dpBirthDate.getValue()));
        phone.setEmail(tfEmail.getText());
        phone.setImageURL(newImageUri);


        //might cAUSE PROBLEM
        Group group = (Group) cbGroup.getSelectionModel().getSelectedItem();
        if (group != null) {
            phone.setGroupName(group.getName());
        }

        Social social = (Social) cbSocial.getSelectionModel().getSelectedItem();
        if (social != null) {
            phone.setSocialName(social.getName());
        } else {
            System.out.println("No social selected or social is null.");
            return;
        }


        try{
            phone = PhoneService.getService().update(phone);
            controller.refresh();
            controller.setControlTexts(phone);
            onBack(actionEvent);
        }
        catch(Exception ex){
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
