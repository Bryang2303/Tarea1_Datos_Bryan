import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer

public abstract class Conexion {
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
    }
    public void InicioConexion() throws Exception{

    }
    public void enviar(Serializable data) throws Exception{

    }
    public void TerminarConexion() throws Exception{

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
        /**
         * Permite ejecutar el hilo(conexion)
         */
        @Override
        public void run() {
            try(ServerSocket server = EsServidor() ? new ServerSocket(Puerto()) : null;
                Socket socket = EsServidor() ? server.accept() : new Socket(ObtenerIp(),Puerto());
                ObjectOutputStream salida = new ObjectOutputStream());
                ObjectInputStream)

        }
    }



}
