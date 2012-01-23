package modbus.control.api.gwtbp;

import java.util.ArrayList;

public class GetDetails implements Action<GetDetailsResponse> {
  private final ArrayList<ContactDetailId> ids;
  public GetDetails(ArrayList<ContactDetailId> ids) {
    this.ids = ids;
  }
  public ArrayList<ContactDetailId> getIds() {
    return ids;
  }
}

