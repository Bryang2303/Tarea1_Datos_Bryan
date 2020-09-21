import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class Conexion {
    /**
     * Creacion del hilo
     */
    private HiloConexion HiloConexion2 = new HiloConexion();
    /**
     * Función llamada cuando se recibe un mensaje
     */
    private Consumer<Serializable>onRecieveCallBack;

    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Conexion(Consumer<Serializable>onRecieveCallBack){
        this.onRecieveCallBack = onRecieveCallBack;
        this.HiloConexion2.setDaemon(true);
    }
    public void InicioConexion() throws Exception{
        HiloConexion2.start();
    }
    /**
     * Permite enviar el objeto
     * @param data corresponde al mensaje
     */
    public void enviar(Serializable data) throws Exception{
        HiloConexion2.out.writeObject(data);
    }

    /**
     *Cierre de conexion
     */
    public void TerminarConexion() throws Exception{
        HiloConexion2.socket.close();

    }

    /**
     *Identificar que es el Servidor o el Cliente
     */
    protected abstract boolean EsServidor();
    /**
     *Obtener la dirección Ip para la conexión
     */
    protected abstract String ObtenerIp();
    /**
     *Obtener el número de puerto para la conexión
     */
    protected abstract int Puerto();
    /**
     * Hilo que permite leer y escribir simultaneamente
     */
    private class HiloConexion extends Thread{
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
            try(ServerSocket server = EsServidor() ? new ServerSocket(Puerto()) : null;
                Socket socket = EsServidor() ? server.accept() : new Socket(ObtenerIp(),Puerto());
                ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                this.socket=socket;
                this.out=out;
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
