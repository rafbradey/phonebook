package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class ViewPhoneController implements Initializable {
    @Setter
    private Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    PhoneBookController controller;
    @Setter
    PhoneService phoneService;
    @Setter
    private Phone phone;
    @FXML
    private Button btnBack;
    @FXML
    private ImageView phoneImage;
    @FXML
    private Button btnEdit;
    @FXML
    private ImageView contactImage;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfBirthDate;
    @FXML
    private TextField tfSocial;
    @FXML
    private TextField tfPhoneNumber;
    @FXML
    private TextField tfGroup;
    @FXML
    private Button closeButton;
    @FXML
    private Label txtitle;

    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh();
    }

    public void refresh() {

        tfName.setEditable(false);
        tfPhoneNumber.setEditable(false);
        tfGroup.setEditable(false);
        tfSocial.setEditable(false);
        tfEmail.setEditable(false);
        tfBirthDate.setEditable(false);
        tfAccount.setEditable(false);

        //ERROR HAPPENS HERE WHERE IT CAUSES APPLICATION TO CRASH
       // try{
        /*
        tfId.setText(String.valueOf(phone.getId()));
        tfName.setText(phone.getName());
        tfPhoneNumber.setText(phone.getPhoneNumber());
        tfGroup.setText(phone.getGroupName());
        tfEmail.setText(phone.getEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (phone.getBirthday() != null) {
            Date birthday = phone.getBirthday();
            LocalDate localDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convert to LocalDate
            tfBirthDate.setText(localDate.format(formatter));
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

         */
    }

    @FXML
    public void onBack(ActionEvent actionEvent) {
    }

    @FXML
    public void onClose(ActionEvent actionEvent) {
    }

    @FXML
    public void onSubmit(ActionEvent actionEvent) {
    }
}
