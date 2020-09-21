import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Tarea extends Application {

    private boolean isServer =false;
    /***
     * El cuadro de mensajeria del chat
     */
    private TextArea messages = new TextArea();
    /**
     * La conexion detecta cuando es un Servidor, si no, crea un cliente
     */
    private Connection connection = isServer ? createServer():createClient();

    private Parent createContent(){
        messages.setPrefHeight(550);
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
        return root;
    }
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
        MainStage.show();

    }
    public void stop() throws Exception{
        connection.closeConnection();
    }
    /**
     * El contenido del mensaje se agrega en el espacio de mensajeria
     * El nuevo servidor
     * @return utiliza este numero de puerdo para realizar la comunicacion
     */
    private Server createServer(){
        return new Server(55555, data->{
            Platform.runLater(() ->{
                messages.appendText(data.toString()+"\n");

            });
        });
    }

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