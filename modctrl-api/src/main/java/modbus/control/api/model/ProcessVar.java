package modbus.control.api.model;

public interface ProcessVar {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public int getModbusaddr();

    public void setModbusaddr(int modbusaddr);

    public String getDescription();

    public void setDescription(String description);

    public Category getCategory();

    public void setCategory(Category category);

    public PLC getPlc();

    public void setPlc(PLC plc);
}
