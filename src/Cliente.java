import java.io.Serializable;
import java.util.function.Consumer;

public class Cliente extends Conexion {

    private String ip;
    private int NumPuerto;

    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     *
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Cliente(String ip, int NumPuerto,Consumer<Serializable> onRecieveCallBack) {
        super(onRecieveCallBack);
        this.ip=ip;
        this.NumPuerto=NumPuerto;
    }

    @Override
    protected boolean EsServidor() {
        return false;
    }

    @Override
    protected String ObtenerIp() {
        return null;
    }

    @Override
    protected int Puerto() {
        return 0;
    }
}
