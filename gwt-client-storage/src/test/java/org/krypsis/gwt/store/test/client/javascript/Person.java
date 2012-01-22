package org.krypsis.gwt.store.test.client.javascript;

import com.google.gwt.core.client.JsArray;
import java.util.Date;
import org.krypsis.gwt.store.client.javascript.JsStorable;

public final class Person extends JsStorable {
  protected Person() {}

  public final native String getName() /*-{ return this.name; }-*/;

  public final native void setName(String name) /*-{ this.name = name; }-*/;

  public final native String getBusId() /*-{ return this.busId; }-*/;

  public final native void setBusId(String busId) /*-{ this.busId = busId; }-*/;

  public final native JsArray<Address> getAddresses() /*-{ if (!this.addresses) { this.addresses = []; }; return this.addresses; }-*/;

  public final native int getAddressCount() /*-{ return this.addresses ? this.addresses.length : 0; }-*/;

  public final native void setAddresses(JsArray<Address> addresses) /*-{ this.addresses = addresses; }-*/;

  private native void setBirthDay0(double birthday) /*-{ this.birthday = birthday; }-*/;

  private native double getBirthDay0() /*-{ return this.birthday; }-*/;

  public static native Person build(String json) /*-{ return eval('(' + json + ')'); }-*/;

  public final void setBirthday(Date date) {
    setBirthDay0(date.getTime());
  }

  public final Date getBirthDay() {
    final double milis = getBirthDay0();
    return new Date((long) milis);
  }

  public final void addAddress(Address address) {
    final JsArray<Address> addresses = getAddresses();
    addresses.set(addresses.length(), address);
  }

  public static Person build() {
    return build("{}");
  }
}