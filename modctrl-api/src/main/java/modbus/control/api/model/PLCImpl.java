package modbus.control.api.model;

public class PLCImpl implements PLC, java.io.Serializable {

    private static final long serialVersionUID = -612132013583310346L;
    int id;
    String name;
    String ip;
    int port;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }
}
