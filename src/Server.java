import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends Network {

    private int NumPuerto;
    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     *
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Server(int NumPuerto, Consumer<Serializable> onRecieveCallBack) {
        super(onRecieveCallBack);
        this.NumPuerto=NumPuerto;
    }

    /**
     * Establecera si es un servidor
     * @return True indicara que se trata del Servidor
     */
    @Override
    protected boolean isServer() {
        return true;
    }

    /**
     * @return El Servidor no necesita obtener la ip
     */
    @Override
    protected String getIp() {
        return null;
    }

    /**
     * El servidor necesita saber el numero de puerto
     * @return Obtiene el numero de puerto
     */
    @Override
    protected int getPort() {
        return NumPuerto;
    }
}
