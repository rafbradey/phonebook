package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

@Setter
public class DeletePhoneController implements Initializable {
    public TextField tfId;
    @javafx.fxml.FXML
    public TextField tfName;
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    PhoneService phoneService;
    @Setter
    PhoneBookController controller;
    @javafx.fxml.FXML
    private Button btnBack;
    @javafx.fxml.FXML
    private Button btnSubmit;
    @javafx.fxml.FXML
    private TextField tfPhoneNumber;
    @javafx.fxml.FXML
    private TextField tfGroup;
    @javafx.fxml.FXML
    private TextField tfSocial;
    @javafx.fxml.FXML
    private ImageView contactImage;
    @javafx.fxml.FXML
    private TextField tfEmail;
    @javafx.fxml.FXML
    private TextField tfBirthDate;
    @javafx.fxml.FXML
    private TextField tfAccount;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public void refresh(){

        Phone phone = PhoneBookController.phone;
        tfId.setText(Integer.toString(phone.getId()));
        tfName.setText(phone.getName());
        tfPhoneNumber.setText(phone.getPhoneNumber());
        tfGroup.setText(phone.getGroupName());
        tfSocial.setText(phone.getSocialName());
        tfEmail.setText(phone.getEmail());

        if (phone.getBirthday() != null){
            Date birthday = phone.getBirthday(); // Get the Date object
            LocalDate localDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convert to LocalDate
            tfBirthDate.setText(localDate.format(formatter)); // Format and set the text
        }
        else {
            tfBirthDate.setText("");
        }

        tfAccount.setText(phone.getAccount());
        String ImageUri = phone.getImageURL();

        if(ImageUri!=null){
            contactImage.setImage(new ImageView(ImageUri).getImage());
        }
        else{
            Image defaultImage = new Image(getClass().getResourceAsStream("images/Default.jpg"));
            contactImage.setImage(defaultImage);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("DeletePhoneController: initialize");
        tfId=new TextField("");
        refresh();
    }

    @javafx.fxml.FXML
    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreatePhoneController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }


    @javafx.fxml.FXML
    public void onSubmit(ActionEvent actionEvent) {
        //Show alert

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Are you sure you want to delete this phone?");
            alert.setContentText("This action cannot be undone.");

            if(alert.showAndWait().get() == ButtonType.CANCEL){
                return;
            }


        try {
            Phone phone = toObject(true);
            PhoneService.getService().delete(phone.getId());
            controller.refresh();
            controller.clearControlTexts();
            Node node = ((Node) (actionEvent.getSource()));
            Window window = node.getScene().getWindow();
            window.hide();
            stage.setTitle("Manage Phone");
            stage.setScene(parentScene);
            stage.show();
        }
        catch (Exception e){
            String message="Error encountered deleting phone";
            showErrorDialog(message,e.getMessage());
        }
    }

    protected Phone toObject(boolean isEdit){
        Phone phone = new Phone();
        try {
            if(isEdit) {
                phone.setId(Integer.parseInt(tfId.getText()));
            }
            phone.setName(tfName.getText());

            //tfPhoneNumber talaga to dapat --raf
            phone.setPhoneNumber(tfPhoneNumber.getText());
        }catch (Exception e){
            showErrorDialog("Error" ,e.getMessage());
        }
        return phone;
    }

    public void showErrorDialog(String message,String addtlMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(addtlMessage)));
        alert.showAndWait();
    }
}
