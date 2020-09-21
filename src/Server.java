import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends Connection {

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

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIp() {
        return null;
    }

    @Override
    protected int getPort() {
        return NumPuerto;
    }
}
