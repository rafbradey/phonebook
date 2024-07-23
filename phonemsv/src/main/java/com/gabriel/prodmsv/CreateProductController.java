package com.gabriel.prodmsv;

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
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class CreateProductController implements Initializable {
    @Setter
    ProdManController prodManController;
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

    @FXML
    private Button btnBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("CreateProductController: initialize");
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
        System.out.println("CreateProductController:onBack ");
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

        System.out.println("Phone details before save: " + phone + " group: " + group + " social: " + social);

        try {
            phone = phoneService.create(phone);
            prodManController.refresh();
            onBack(actionEvent);
        } catch (Exception ex) {
            System.out.println("CreateProductController:onSubmit Error: " + ex.getMessage());
        }
    }


    @FXML
    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreateProductController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }
}

