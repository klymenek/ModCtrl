package modbus.control.shared;

public class ProcessVar implements java.io.Serializable {

    private static final long serialVersionUID = 8444919865841636943L;
    int id;
    int modbusaddr;
    String name;
    String description;
    Category category;
    PLC plc;

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

    public int getModbusaddr() {
        return modbusaddr;
    }

    public void setModbusaddr(int modbusaddr) {
        this.modbusaddr = modbusaddr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PLC getPlc() {
        return plc;
    }

    public void setPlc(PLC plc) {
        this.plc = plc;
    }

    @Override
    public String toString() {
        return "id=" + id + ", modbusaddr=" + modbusaddr
                + ", name=" + name + ", description=" + description;
    }
}
