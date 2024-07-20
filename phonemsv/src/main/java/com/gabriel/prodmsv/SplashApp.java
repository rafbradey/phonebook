package com.gabriel.prodmsv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashApp extends Application {

     public void start(Stage stage) throws IOException {
        System.out.println("SplashApp:start ");
        FXMLLoader fxmlLoader = new FXMLLoader(SplashApp.class.getResource("splash-view.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        SplashController productView = fxmlLoader.getController();
        productView.setStage(stage);
        Scene scene = new Scene(root, 300, 600);
        String css=this.getClass().getResource("/css/splash.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Production Management!");
        stage.setScene(scene);
        stage.show();
    }
}
