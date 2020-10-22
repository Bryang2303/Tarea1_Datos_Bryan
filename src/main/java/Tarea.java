import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//Prueba de branch 2
public class Tarea extends Application {
    /**
     * La variable que controla si se ejecuta el Servidor o el Cliente.
     * Se debe ejecutar primero como true y despues como false. De manera que el Servidor se crea primero.
     * Si el cliente es creado antes del Servidor, la conexion falla.
     */
    private boolean Rol =true;

    static final Logger logger = LogManager.getLogger(Tarea.class);

    /***
     * El cuadro de mensajeria del chat
     */
    private TextArea Mensajes = new TextArea();
    /**
     * La conexion detecta cuando es un Servidor, si no, crea un cliente
     */
    private Network comunicacion = Rol ? createServer():createClient();

    /**
     * Parte de la interfaz y activadores del envio de mensajes
     * @return La ventana del chat(root)
     */
    private Parent ContenidoScene(){
        Mensajes.setPrefHeight(500);
        Mensajes.setId("textarea-messages");
        TextField input = new TextField();
        input.setOnAction(event ->{
            String mensaje = Rol ? "Servidor: " : "Cliente: ";
            mensaje+=input.getText();
            input.clear();
            Mensajes.appendText(mensaje+"\n");
            /**
             * Se mantiene en constante comunicacion cada vez que se envia un mensaje, si falla, un mensaje de error sera enviado
             */
            try {
                comunicacion.send(mensaje);
                logger.info("Mensaje enviado");
            } catch (Exception e) {
                Mensajes.appendText("Fallo del envio"+"\n");
                logger.error("Error al enviar");
            }

        });
        VBox root = new VBox(20, Mensajes,input);
        root.setPrefSize(600,600);
        Label but = new Label("Escriba su mensaje");
        root.getChildren().add(but);
        root.getStylesheets().add("Styles/style1.css");
        return root;
    }
    /**
     * Iniciador de la concexion
     */
    @Override
    public void init() throws Exception {
        comunicacion.Iniciar_C();
    }
    @Override
    /**
     * Las ventanas principales(Stages), las cuales se crean al ejecutar el programa.
     * Al crear el Servidor, una ventana previa pide al usuario ingresar el numero de puerto de la conexion
     */
    public void start(Stage MainStage) throws Exception {
        if (Rol ==false){
            MainStage.setTitle("Dirección IP del Servidor");
            MainStage.setWidth(300);
            MainStage.setHeight(118);
            VBox root2 = new VBox();
            Label label1 = new Label("Ingrese una dirección ip válida");
            Button continuar = new Button("Continuar");
            TextField introPort = new TextField();
            root2.getChildren().addAll(label1,introPort,continuar);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                /**
                 *Evento que toma el puerto escrito en la ventana activada por el Servidor
                 */
                public void handle(ActionEvent e)
                {

                    try {
                        String p=introPort.getText();
                        String[] parts = p.split("[.]");
                        String part1 = parts[0];
                        String part2 = parts[1];
                        String part3 = parts[2];
                        String part4 = parts[3];
                        int p1 = Integer.parseInt(part1);
                        int p2 = Integer.parseInt(part2);
                        int p3 = Integer.parseInt(part3);
                        int p4 = Integer.parseInt(part4);
                        int min=1;
                        int max=256;
                        if (p1<max&&p1>min && p2<max&&p2>min && p3<max&&p3>min && p4<max&&p4>min){
                            logger.info("Direccion ip valida");
                            Stage stage2 = new Stage();
                            stage2.setScene(new Scene(ContenidoScene()));
                            stage2.setHeight(500);
                            stage2.setWidth(300);
                            stage2.setTitle("Cliente");
                            stage2.show();
                        }else{
                            int pf = p2/0;

                        }
                    } catch (ArithmeticException ex){
                        label1.setText("Direccion ip invalida");
                        logger.error("Direccion ip invalida");

                    }

                }
            };
            continuar.setOnAction(event);
            Scene scene2 = new Scene(root2);
            scene2.getStylesheets().add("Styles/style1.css");
            MainStage.setScene(scene2);
            MainStage.show();
        }
        /**
         * La ventana inicial del Servidor difiere de la del Cliente
         */
        if (Rol ==true){
            MainStage.setTitle("Indique el puerto");
            MainStage.setWidth(200);
            MainStage.setHeight(118);
            VBox root2 = new VBox();
            Label label1 = new Label("Ingrese el numero de puerto");
            Button continuar = new Button("Continuar");
            TextField introPort = new TextField();
            root2.getChildren().addAll(label1,introPort,continuar);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                /**
                 *Evento que toma el puerto escrito en la ventana activada por el Servidor
                 */
                public void handle(ActionEvent e)
                {
                    try {
                        String text=introPort.getText();
                        int number = Integer.parseInt(text);
                        try {
                            String p=introPort.getText();
                            int p2 = Integer.parseInt(p);
                            if (p2>65536){
                                int pf = p2/0;

                            }else{
                                logger.info("Puerto seleccionado correctamente");
                                label1.setText("Puerto conectado: "+introPort.getText());

                                Stage stage2 = new Stage();
                                stage2.setScene(new Scene(ContenidoScene()));
                                stage2.setHeight(500);
                                stage2.setWidth(300);
                                stage2.setTitle("Servidor");
                                stage2.show();
                            }
                        } catch (ArithmeticException ex){
                            label1.setText("El puerto debe ser menor a 65536 y no debe estar ocupado");
                            logger.error("Puerto invalido");

                        }
                    }
                    catch(NumberFormatException ex){
                        label1.setText("El puerto debe tener valores numericos");
                        logger.error("El puerto debe ser un numero valido");
                    }
                }
            };
            continuar.setOnAction(event);
            Scene scene2 = new Scene(root2);
            scene2.getStylesheets().add("Styles/style1.css");
            MainStage.setScene(scene2);
            MainStage.show();
        }
    }

    /**
     *Cerrar el hilo(comunicacion)
     */
    public void stop() throws Exception{
        comunicacion.Cerrar_C();
    }
    /**
     * El nuevo servidor
     * El contenido del mensaje se agrega en el espacio de mensajeria
     * @return utiliza este numero de puerto para realizar la comunicacion
     */
    private Server createServer(){
        return new Server(55555, data->{
            Platform.runLater(() ->{
                Mensajes.appendText(data.toString()+"\n");

            });
        });
    }

    /**
     * El nuevo cliente
     * El contenido del mensaje se agrega en el espacio de mensajeria
     * @return utiliza este numero de puerto y la direccion ip para realizar la comunicacion
     */
    private Client createClient(){
        return new Client("127.0.0.1", 55555, data->{
            Platform.runLater(() ->{
                Mensajes.appendText(data.toString()+"\n");

            });
        });
    }
    public static void main(String[]args){
        launch(args);

    }
}