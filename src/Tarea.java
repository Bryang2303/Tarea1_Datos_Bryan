import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;

import javax.swing.*;

public class Tarea extends Application {
    /***
     * El cuadro de mensajeria del chat
     */
    private TextArea mensajes = new TextArea();

    private Parent createContent(){
        mensajes.setPrefHeight(550);
        TextField input = new TextField();
        VBox root = new VBox(20,mensajes,input);
        root.setPrefSize(600,600);
        return root;


    }
    @Override
    /***
     * La ventana Principal
     */
    public void start(Stage MainStage) throws Exception {
        MainStage.setTitle("Chatting");
        MainStage.setScene(new Scene(createContent()));
        MainStage.setWidth(400);
        MainStage.setHeight(500);
        MainStage.show();

    }

    public static void main(String[]args){
        launch(args);

    }
}