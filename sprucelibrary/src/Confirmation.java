/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Touhidul_MTI
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

public class Confirmation{
    static boolean confirmResult;
    
    public static boolean confirm(String message){
        Stage confirmStage = new Stage();
        confirmStage.setTitle("Confirmation Request");
        confirmStage.initModality(Modality.APPLICATION_MODAL);
        confirmStage.setMinHeight(200);
        confirmStage.setMinWidth(300);
        
        Label messageL = new Label(message);
        messageL.setStyle("-fx-text-fill:#DC9656;-fx-font-weight:bold");
        Button yes = new Button("YES");
        yes.setStyle("-fx-background-color:#993333");//css
        Button no = new Button("NO");
        no.setStyle("-fx-background-color:#666633");//css
        
        yes.setOnAction(e ->{
            confirmResult = true;
            confirmStage.close();
        });
        no.setOnAction(e ->{
            confirmResult = false;
            confirmStage.close();
        });
        
        VBox vx = new VBox(20);
        vx.getChildren().addAll(messageL, yes, no);
        vx.setAlignment(Pos.CENTER);
        
        Scene confirmScene = new Scene(vx);
        confirmScene.getStylesheets().add("WelcomeSceneStyle.css");//css set
        confirmStage.setScene(confirmScene);
        confirmStage.showAndWait();
        
        return confirmResult;  //return boolean
    }
}
