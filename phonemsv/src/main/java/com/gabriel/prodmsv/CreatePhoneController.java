package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ImageService;
import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;
import com.gabriel.prodmsv.model.Group;
import com.gabriel.prodmsv.ServiceImpl.GroupService;
import com.gabriel.prodmsv.ServiceImpl.SocialService;
import com.gabriel.prodmsv.model.Social;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    public TextField tfDesc;
    @FXML
    private ComboBox<Group> cbGroup;
    @FXML
    private ComboBox<Social> cbSocial;
    @FXML
    public Button btnSubmit;
    @FXML
    public Button btnNext;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("CreatePhoneController: initialize");
        //set image of contactImage to null every time the page is loaded
        Image icon = new Image(getClass().getResourceAsStream("images/Default.jpg"));

        contactImage.setImage(icon);


        try{
        Group[] groups =  (Group[]) GroupService.getService().getGroups();
        cbGroup.getItems().clear();
        cbGroup.getItems().addAll(groups);

        Social[] socials =  (Social[]) SocialService.getService().getSocials();
        cbSocial.getItems().clear();
        cbSocial.getItems().addAll(socials);

        tfName.setText("");
        tfDesc.setText("");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }





    public void clearControlTexts(){
        tfName.setText("");
        tfDesc.setText("");
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

    //palitan mamaya yung tfDesc etc after mabago yung UI design --raf
    @FXML
    public void onSubmit(ActionEvent actionEvent) throws Exception {
        Phone phone = new Phone();
        phone.setName(tfName.getText());
        phone.setPhoneNumber(tfDesc.getText());

        Group group = cbGroup.getSelectionModel().getSelectedItem();
        if (group != null) {
            phone.setGroupId(group.getId());
            phone.setGroupName(group.getName());
        }

        Social social = cbSocial.getSelectionModel().getSelectedItem();
        if (social != null) {
            phone.setSocialId(social.getId());
            phone.setSocialName(social.getName());
        } else {
            System.out.println("No social selected or social is null.");
            return;
        }



                //AutoIncrement ID for the phone
              phone.setImageId(phoneService.getPhones().length + 1);
                //set URL same as the filename, (gagamitin ko to mamaya para sa contact images)
                phone.setImageURL(imageUri);

        System.out.println("Phone details before save: " + phone + " group: " + group + " social: " + social + "imageURL: " + phone.getImageURL());

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



/* nasayng na code
        // Show the file chooser dialog and get the selected file
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Get the original filename and its extension
            String originalFilename = file.getName();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));

            // Generate a unique image ID
            String uniqueImageId = UUID.randomUUID().toString();

            // Create a new filename with the unique image ID
            String newFilename = uniqueImageId + fileExtension;
            System.out.println("Selected file: " + originalFilename);
            System.out.println("New filename: " + newFilename);

            try {
                // target dir for the file
                Path targetDir = Path.of(fileDir);

                // recreate directory if it does not exist
                Files.createDirectories(targetDir);

                Path targetFile = targetDir.resolve(newFilename);

                // copy selected file to target dir
                Files.copy(file.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);

                // Set the image view with current selected image --p.s alas natapos na rin after 3 hrs -raf



                contactImage.setImage(new javafx.scene.image.Image(file.toURI().toString()));
                System.out.println("Temp File saved to: " + targetFile.toAbsolutePath());
                System.out.println("File Name with UUID is : " + newFilename);



            } catch (IOException e){
                System.out.println("Error saving file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
*/
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


}

