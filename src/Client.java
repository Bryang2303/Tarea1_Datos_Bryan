import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Clase cliente
 */
public class Client extends Network {
    private String ip;
    private int NumPuerto;

    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Client(String ip, int NumPuerto, Consumer<Serializable> onRecieveCallBack) {
        super(onRecieveCallBack);
        this.ip=ip;
        this.NumPuerto=NumPuerto;
    }
    /**
     * Establecera si es un cliente
     * @return False indica que se trata del Cliente
     */
    @Override
    protected boolean Rol() {
        return false;
    }
    /**
     * El cliente necesita saber la direccion ip del servidor
     * @return Obtiene la direccion ip
     */
    @Override
    protected String ObtenerIp() {
        return ip;
    }
    /**
     * El cliente necesita saber el numero de puerto
     * @return Obtiene el numero de puerto
     */
    @Override
    protected int ObtenerPuerto() {
        return NumPuerto;
    }
}
