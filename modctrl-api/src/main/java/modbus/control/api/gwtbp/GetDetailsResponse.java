package modbus.control.api.gwtbp;

import java.util.ArrayList;


class GetDetailsResponse implements Response {
  private final ArrayList<ContactDetail> details;
  public GetDetailsResponse(ArrayList<ContactDetail> details) {
    this.details = details;
  }
  public ArrayList<ContactDetail> getDetails() {
    return new ArrayList<ContactDetail>(details);
  }
}

