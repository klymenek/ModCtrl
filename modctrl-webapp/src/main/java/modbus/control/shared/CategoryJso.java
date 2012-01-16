package modbus.control.shared;

import modbus.control.api.model.Category;

public class CategoryJso implements Category, java.io.Serializable {

    private static final long serialVersionUID = -612132013583310346L;
    int id;
    String name;

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
    public String toString() {
        return name;
    }
}
