package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ProductService;
import com.gabriel.prodmsv.model.Product;
import com.gabriel.prodmsv.model.Uom;
import com.gabriel.prodmsv.ServiceImpl.UomService;

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
    private ComboBox<Uom> cbUom;
    public Button btnSubmit;
    public Button btnNext;

    @Setter
    Stage stage;
    @Setter
    Scene parentScene;
    @Setter
    ProductService productService;
    @Setter
    UomService  uomService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        System.out.println("CreateProductController: initialize");

        try{
        Uom[] uoms =  (Uom[]) UomService.getService().getUoms();
        cbUom.getItems().clear();;
        cbUom.getItems().addAll(uoms);

        tfName.setText("");
        tfDesc.setText("");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void clearControlTexts(){
        tfName.setText("");
        tfDesc.setText("");
        cbUom.getSelectionModel().clearSelection();
    }

    public void onNext(ActionEvent actionEvent) {
        System.out.println("CreateProductController:onBack ");
        Node node = ((Node) (actionEvent.getSource()));
        Window window = node.getScene().getWindow();
        window.hide();

        stage.setScene(parentScene);
        stage.show();
    }

    public void onSubmit(ActionEvent actionEvent) throws Exception{
        Product product = new Product();
        product.setName(tfName.getText());
        product.setPhoneNumber(tfDesc.getText());
        Uom uom = cbUom.getSelectionModel().getSelectedItem();
        product.setGroupId(uom.getId());
        product.setGroupName(uom.getName());
        try{
            product=productService.create(product);
            prodManController.refresh();
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

