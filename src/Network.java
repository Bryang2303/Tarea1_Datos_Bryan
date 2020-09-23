import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class Network {
    /**
     * Creacion del hilo
     */
    private NetConnectThread connThread = new NetConnectThread();
    /**
     * Función llamada cuando se recibe un mensaje
     */
    private Consumer<Serializable>onRecieveCallBack;

    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Network(Consumer<Serializable>onRecieveCallBack){
        this.onRecieveCallBack = onRecieveCallBack;
        connThread.setDaemon(true);
    }
    public void startConnection() throws Exception{
        connThread.start();
    }
    /**
     * Permite enviar el objeto
     * @param data corresponde al mensaje
     */
    public void send(Serializable data) throws Exception{
        connThread.out.writeObject(data);
    }

    /**
     *Cierre de conexion
     */
    public void closeConnection() throws Exception{
        connThread.socket.close();

    }

    /**
     *Identificar que es el Servidor o el Cliente
     */
    protected abstract boolean isServer();
    /**
     *Obtener la dirección Ip para la conexión
     */
    protected abstract String getIp();
    /**
     *Obtener el número de puerto para la conexión
     */
    protected abstract int getPort();
    /**
     * Hilo que permite leer y escribir simultaneamente
     */
    private class NetConnectThread extends Thread{
        private Socket socket;
        private ObjectOutputStream out;
        /**
         * Permite ejecutar el hilo(conexion)
         */
        @Override
        public void run() {
            /**
             * Tratar de conectarse con el Server. Se inicia un nuevo servidor con el puerto
             * En el cliente se crea un nuevo socket con la direccion Ip y el puerto, tratando de hacer la conexion
             */
            try(ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                Socket socket = isServer() ? server.accept() : new Socket(getIp(), getPort());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket = socket;
                this.out = out;
                socket.setTcpNoDelay(true);
                /**
                 * Permite llamar a la funcion y transmitir los datos
                 */
                while(true){
                    Serializable data = (Serializable) in.readObject();
                    onRecieveCallBack.accept(data);
                }
            }
            catch (Exception e){
                onRecieveCallBack.accept("Conexion cerrada");
            }

        }
    }
}
