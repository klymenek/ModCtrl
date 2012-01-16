package modbus.control.api.model;

public class ProcessVarImpl implements ProcessVar, java.io.Serializable {

    private static final long serialVersionUID = 8444919865841636943L;
    int id;
    int modbusaddr;
    String name;
    String description;
    Category category;
    PLC plc;

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
    public int getModbusaddr() {
        return modbusaddr;
    }

    @Override
    public void setModbusaddr(int modbusaddr) {
        this.modbusaddr = modbusaddr;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public PLC getPlc() {
        return plc;
    }

    @Override
    public void setPlc(PLC plc) {
        this.plc = plc;
    }

    @Override
    public String toString() {
        return "id=" + id + ", modbusaddr=" + modbusaddr
                + ", name=" + name + ", description=" + description;
    }
}
