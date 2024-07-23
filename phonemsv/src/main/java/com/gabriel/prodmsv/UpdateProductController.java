package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.ServiceImpl.UomService;
import com.gabriel.prodmsv.model.Phone;
import com.gabriel.prodmsv.model.Uom;
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
    private TextField tfUom;
    @FXML
    private ComboBox<Uom> cbUom;

    public void refresh() throws Exception{
        Phone phone =ProdManController.phone;
        tfId.setText(Integer.toString(phone.getId()));
        tfName.setText(phone.getName());
        tfDesc.setText(phone.getPhoneNumber());
        cbUom.getItems().clear();
        Uom[] uoms =  (Uom[]) UomService.getService().getUoms();
        cbUom.getItems().addAll(uoms);
        cbUom.getSelectionModel().select(UomService.getService().getUom(phone.getGroupId()));
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
        Uom uom = cbUom.getSelectionModel().getSelectedItem();
        phone.setGroupId(uom.getId());
        phone.setGroupName(uom.getName());

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
