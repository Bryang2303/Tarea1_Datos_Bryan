import java.io.Serializable;
import java.util.function.Consumer;

public class Servidor extends Conexion{

    private int NumPuerto;
    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     *
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Servidor(int NumPuerto,Consumer<Serializable> onRecieveCallBack) {
        super(onRecieveCallBack);
        this.NumPuerto=NumPuerto;
    }

    @Override
    protected boolean EsServidor() {
        return true;
    }

    @Override
    protected String ObtenerIp() {
        return null;
    }

    @Override
    protected int Puerto() {
        return NumPuerto;
    }
}
