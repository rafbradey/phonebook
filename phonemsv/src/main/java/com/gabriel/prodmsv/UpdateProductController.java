package com.gabriel.prodmsv;

import com.gabriel.prodmsv.ServiceImpl.ProductService;
import com.gabriel.prodmsv.ServiceImpl.UomService;
import com.gabriel.prodmsv.model.Product;
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
        Product product=ProdManController.product;
        tfId.setText(Integer.toString(product.getId()));
        tfName.setText(product.getName());
        tfDesc.setText(product.getDescription());
        cbUom.getItems().clear();
        Uom[] uoms =  (Uom[]) UomService.getService().getUoms();
        cbUom.getItems().addAll(uoms);
        cbUom.getSelectionModel().select(UomService.getService().getUom(product.getUomId()));
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
        Product product = new Product();
        product.setId(Integer.parseInt(tfId.getText()));
        product.setName(tfName.getText());
        product.setDescription(tfDesc.getText());
        Uom uom = cbUom.getSelectionModel().getSelectedItem();
        product.setUomId(uom.getId());
        product.setUomName(uom.getName());

        try{
            product= ProductService.getService().update(product);
            controller.refresh();
            controller.setControlTexts(product);
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
