package modbus.control.shared;

public class PLC implements java.io.Serializable {

    private static final long serialVersionUID = -612132013583310346L;
    int id;
    String name;
    String ip;
    int port;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }
}
