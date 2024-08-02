package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ImageService;
import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;
import com.gabriel.prodmsv.model.Group;
import com.gabriel.prodmsv.model.Social;
import com.gabriel.prodmsv.ServiceImpl.GroupService;
import com.gabriel.prodmsv.ServiceImpl.SocialService;
import com.gabriel.prodmsv.model.Social;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.UUID;



@Setter
public class CreatePhoneController implements Initializable {
    @Setter
    PhoneBookController phoneBookController;
    @FXML
    public TextField tfName;
    @FXML
    private ComboBox<Group> cbGroup;
    @FXML
    private ComboBox<Social> cbSocial;
    @FXML
    public Button btnSubmit;
    @FXML
    public Button closeButton;
    @FXML
    public Button btnNext;
    @FXML
    private ImageView phoneImage;

    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    PhoneService phoneService;
    @Setter
    GroupService groupService;
    @Setter
    SocialService socialService;
    @Setter
    ImageService imageService;


    @FXML
    private Button btnBack;
    @FXML
    private Button btnUpload;

    private String imageUri;

   // String tempDir = "src/main/resources/com/gabriel/prodmsv/tempImages";
  //  String finalDir = "src/main/resources/com/gabriel/prodmsv/uploadedImages";

    String fileDir = null;
    @FXML
    private ImageView contactImage;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfEmail;

    Image PhoneIcon = new Image(getClass().getResourceAsStream("images/splashIcon.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("CreatePhoneController: initialize");
        //set image of contactImage to null every time the page is loaded
        Image icon = new Image(getClass().getResourceAsStream("images/Default.jpg"));

        contactImage.setImage(icon);


        try{
        phoneImage.setImage(PhoneIcon);

        Group[] groups =  (Group[]) GroupService.getService().getGroups();
        cbGroup.getItems().clear();
        cbGroup.getItems().addAll(groups);

        Social[] socials =  (Social[]) SocialService.getService().getSocials();
        cbSocial.getItems().clear();
        cbSocial.getItems().addAll(socials);

        tfName.setText("");
        tfPhoneNumber.setText("");
        cbGroup.getSelectionModel().clearSelection();
        cbSocial.getSelectionModel().clearSelection();
        tfAccount.setText("");
        dpBirthDate.setValue(null);
        tfEmail.setText("");

        // Disable typing in the dpBirthDate DatePicker
        disableDatePickerTextField(dpBirthDate);

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }





    public void clearControlTexts(){
        tfName.setText("");
        tfPhoneNumber.setText("");
        tfAccount.setText("");
        dpBirthDate.setValue(null);
        tfEmail.setText("");
        cbGroup.getSelectionModel().clearSelection();
        cbSocial.getSelectionModel().clearSelection();
    }

    @FXML
    public void onNext(ActionEvent actionEvent) {
        System.out.println("CreatePhoneController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }

    //palitan mamaya yung tfPhoneNumber etc after mabago yung UI design --raf
    @FXML
    public void onSubmit(ActionEvent actionEvent) throws Exception {
        // Get values from input fields
        String name = tfName.getText().trim();
        String phoneNumber = tfPhoneNumber.getText().trim();
        String account = tfAccount.getText().trim();
        String email = tfEmail.getText().trim();
        java.sql.Date birthday = dpBirthDate.getValue() != null ? java.sql.Date.valueOf(dpBirthDate.getValue()) : null;
        Group group = cbGroup.getSelectionModel().getSelectedItem();
        Social social = cbSocial.getSelectionModel().getSelectedItem();

        // Validate required fields
        if (name.isEmpty() || phoneNumber.isEmpty()) {
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Missing Required Fields");
            alert.setContentText("Please provide both Name and Phone Number.");
            alert.showAndWait();
            return;
        }

        // Create a Phone object with input values
        Phone phone = new Phone();
        phone.setName(name);
        phone.setPhoneNumber(phoneNumber);
        phone.setAccount(account);
        phone.setBirthday(birthday);
        phone.setEmail(email);
        phone.setImageURL(imageUri);

        if (group != null) {
            phone.setGroupId(group.getId());
            phone.setGroupName(group.getName());
        }

        if (social != null) {
            phone.setSocialId(social.getId());
            phone.setSocialName(social.getName());
        }

        System.out.println("Phone details before save: " + phone + " group: " + group + " social: " + social + " imageURL: " + phone.getImageURL());

        phone = phoneService.create(phone);
        phoneBookController.refresh();
        onBack(actionEvent);

    }


    @FXML
    public void onBack(ActionEvent actionEvent) throws Exception {
        System.out.println("CreatePhoneController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();
        clearTemp();

        stage.setScene(parentScene);
        stage.show();
    }

    @FXML
    public void onUpload(ActionEvent actionEvent) {

        //fileDir = finalDir;
        System.out.println("CreatePhoneController:onUpload ");

        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        //Using the selected iamge, change the image of the contactImage
        File file = fileChooser.showOpenDialog(null);

        try {
            imageUri = file.toURI().toString();
            Image image = new Image(imageUri);
            contactImage.setImage(image);
            System.out.println("Selected file: " + file.getName());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public String getImageUri() {
        return imageUri;
    }

    @FXML
    public void clearTemp(){
        /*
        File tempDirs = new File("src/main/resources/com/gabriel/prodmsv/tempImages");
        File[] files = tempDirs.listFiles();
        for (File file : files) {
            if (!file.delete()) {
                System.out.println("Failed to delete " + file);
            }
        }
        */

        //set image of contactImage to null every time the page is loaded
       Image image = new Image(getClass().getResourceAsStream("images/Default.jpg"));
        contactImage.setImage(image);
        System.out.println("Temp files cleared." +"\n Image Reset to Default.jpg in creation page");
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit and loose changes? " , ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Platform.exit();
        }
    }

    private void disableDatePickerTextField(DatePicker datePicker) {
        datePicker.getEditor().setDisable(true);
        datePicker.getEditor().setStyle("-fx-opacity: 1;"); // Maintain the default appearance
    }

}

