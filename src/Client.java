import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends Connection {

    private String ip;
    private int NumPuerto;

    /**
     * Se establece el constructor que tomará la función, permitiendo el envío de datos por la red.
     *
     * @param onRecieveCallBack cuando se recibe un mensaje
     */
    public Client(String ip, int NumPuerto, Consumer<Serializable> onRecieveCallBack) {
        super(onRecieveCallBack);
        this.ip=ip;
        this.NumPuerto=NumPuerto;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIp() {
        return ip;
    }

    @Override
    protected int getPort() {
        return NumPuerto;
    }
}
