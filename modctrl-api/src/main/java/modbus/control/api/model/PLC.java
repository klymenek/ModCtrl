package modbus.control.api.model;

public interface PLC {
    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getIp();

    public void setIp(String ip);

    public int getPort();

    public void setPort(int port);
}
