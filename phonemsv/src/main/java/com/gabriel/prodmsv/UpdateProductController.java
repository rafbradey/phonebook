package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.ServiceImpl.GroupService;
import com.gabriel.prodmsv.model.Phone;
import com.gabriel.prodmsv.model.Group;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Setter
public class UpdateProductController implements Initializable {
    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ProdManController controller;

    @FXML
    private TextField tfId;
    int id;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDesc;
    @FXML
    private TextField tfGroup;
    @FXML
    private ComboBox<Group> cbGroup;

    public void refresh() throws Exception{
        Phone phone =ProdManController.phone;
        tfId.setText(Integer.toString(phone.getId()));
        tfName.setText(phone.getName());
        tfDesc.setText(phone.getPhoneNumber());
        cbGroup.getItems().clear();
        Group[] groups =  (Group[]) GroupService.getService().getGroups();
        cbGroup.getItems().addAll(groups);
        cbGroup.getSelectionModel().select(GroupService.getService().getGroup(phone.getGroupId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("UpdateProductController: initialize");
        tfId=new TextField();
        try {
            refresh();
        }
        catch(Exception ex){
            System.out.println("UpdateProductController: " + ex.getMessage());
        }
    }

    public void onSubmit(ActionEvent actionEvent) {
        Phone phone = new Phone();
        phone.setId(Integer.parseInt(tfId.getText()));
        phone.setName(tfName.getText());
        phone.setPhoneNumber(tfDesc.getText());
        Group group = cbGroup.getSelectionModel().getSelectedItem();
        phone.setGroupId(group.getId());
        phone.setGroupName(group.getName());

        try{
            phone = PhoneService.getService().update(phone);
            controller.refresh();
            controller.setControlTexts(phone);
            onBack(actionEvent);
        }
        catch(Exception ex){
            System.out.println("CreateProductController:onSubmit Error: " + ex.getMessage());
        }
    }

    public void onBack(ActionEvent actionEvent) {
        System.out.println("CreateProductController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }
}
