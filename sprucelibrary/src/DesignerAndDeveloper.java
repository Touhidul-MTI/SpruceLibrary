/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class DesignerAndDeveloper{
    
    public void designerAndDeveloper(){
        Stage designerAndDeveloperStage = new Stage();
        designerAndDeveloperStage.setTitle("About Designer & Developer");
        designerAndDeveloperStage.initModality(Modality.APPLICATION_MODAL);
        designerAndDeveloperStage.setMinHeight(408);
        designerAndDeveloperStage.setMinWidth(580);
        
        Image ddPicLoad = new Image(getClass().getResourceAsStream("/pic/mti.png"), 480, 368, true, true);
        ImageView ddPic = new ImageView(ddPicLoad);
        
        StackPane sp = new StackPane();
        sp.getChildren().add(ddPic);
        //sp.setPadding(new Insets(10, 10, 10, 20));
        
        Label ddInfo = new Label();
        ddInfo.setText("Muhammad Touhidul Islam\n\n"
                + "BSC in Computer Science & Engineering\n"
                + "BRAC University\n"
                + "Email: touhidul.mti@gmail.com\n"
                + "        mdtislam93@gmail.com");
        ddInfo.setId("aboutStyle");//css
        
        Button close = new Button("Close");
        close.setStyle("-fx-background-color:#383838;-fx-text-fill: #993333");//css
        close.setOnAction(e ->{
            designerAndDeveloperStage.close();
        });
        
        VBox vx = new VBox(90);
        vx.setPadding(new Insets(40,14,14,14));
        vx.getChildren().addAll(ddInfo, close);
        
        
        HBox hx = new HBox();
        hx.getChildren().addAll(sp, vx);
        vx.setAlignment(Pos.CENTER);
        
        Scene confirmScene = new Scene(hx);
        confirmScene.getStylesheets().add("WelcomeSceneStyle.css");//css set
        designerAndDeveloperStage.setScene(confirmScene);
        designerAndDeveloperStage.showAndWait();
        
    }
}
