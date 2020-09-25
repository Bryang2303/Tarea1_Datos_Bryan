import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Tarea extends Application {

    private boolean isServer =true;
    /***
     * El cuadro de mensajeria del chat
     */
    private TextArea messages = new TextArea();
    /**
     * La conexion detecta cuando es un Servidor, si no, crea un cliente
     */
    private Network connection = isServer ? createServer():createClient();

    /**
     * El "padre" de la interfaz
     * @return el VBox creado para escribir
     */
    private Parent createContent(){
        messages.setPrefHeight(500);
        messages.setId("textarea-messages");
        TextField input = new TextField();
        input.setOnAction(event ->{
            String message = isServer ? "Servidor: " : "Cliente: ";
            message+=input.getText();
            input.clear();
            messages.appendText(message+"\n");
            try {
                connection.send(message);
            } catch (Exception e) {
                messages.appendText("Fallo del envio"+"\n");
            }

        });
        VBox root = new VBox(20, messages,input);
        root.setPrefSize(600,600);
        Label but = new Label("qfffffff");

        root.getChildren().add(but);
        root.getStylesheets().add("Styles/style1.css");
        return root;
    }

    /**
     * Iniciador de la concexion
     */
    @Override
    public void init() throws Exception {
        connection.startConnection();
    }

    @Override
    /***
     * La ventana Principal
     */
    public void start(Stage MainStage) throws Exception {
        MainStage.setScene(new Scene(createContent()));
        MainStage.setHeight(500);
        MainStage.setWidth(300);
        MainStage.setTitle("Chatting");
        MainStage.show();




    }

    /**
     *Cerrar el hilo(comunicacion)
     */
    public void stop() throws Exception{
        connection.closeConnection();
    }
    /**
     * El contenido del mensaje se agrega en el espacio de mensajeria
     * El nuevo servidor
     * @return utiliza este numero de puerto para realizar la comunicacion
     */
    private Server createServer(){
        Stage stage2 = new Stage();
        stage2.setTitle("Indique el puerto");
        stage2.setWidth(200);
        stage2.setHeight(200);

        VBox root2 = new VBox(); //Parent of the window

        Label label1 = new Label("Estoy durmiendo");
        TextField introPort = new TextField();
        root2.getChildren().addAll(label1,introPort);


        Scene scene2 = new Scene(root2);
        scene2.getStylesheets().add("Styles/style1.css");
        stage2.setScene(scene2);


        stage2.show();
        return new Server(55555, data->{
            Platform.runLater(() ->{
                messages.appendText(data.toString()+"\n");

            });
        });
    }

    /**
     * El contenido del mensaje se agrega en el espacio de mensajeria
     * El nuevo cliente
     * @return utiliza este numero de puerto y la direccion ip para realizar la comunicacion
     */
    private Client createClient(){
        return new Client("127.0.0.1", 55555, data->{
            Platform.runLater(() ->{
                messages.appendText(data.toString()+"\n");

            });
        });
    }
    public static void main(String[]args){
        launch(args);

    }
}