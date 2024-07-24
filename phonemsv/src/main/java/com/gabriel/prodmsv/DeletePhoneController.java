package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.PhoneService;
import com.gabriel.prodmsv.model.Phone;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Setter;

import java.net.URL;
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

    public void refresh(){

        //kailangan pa to palitan pag natapos na yung frontend.. --raf
        Phone phone = PhoneBookController.phone;
        tfId.setText(Integer.toString(phone.getId()));
        tfName.setText(phone.getName());
        tfPhoneNumber.setText(phone.getPhoneNumber());
        tfGroup.setText(phone.getGroupName());
        tfSocial.setText(phone.getSocialName());

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
