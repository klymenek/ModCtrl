package modbus.control.api.gwtbp;

import java.util.ArrayList;

public class Contact {

    String name;
    ArrayList<ContactDetailId> detailIds;

    ArrayList<ContactDetailId> getDetailIds() {
        return detailIds;
    }

    String getName() {
        return name;
    }
}
