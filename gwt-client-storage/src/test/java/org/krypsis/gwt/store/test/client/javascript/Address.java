package org.krypsis.gwt.store.test.client.javascript;

import com.google.gwt.core.client.JavaScriptObject;

public final class Address extends JavaScriptObject {

    protected Address() {
    }

    public final native String getStreet() /*
     * -{ return this.street; }-
     */;

    public final native void setStreet(String street) /*
     * -{ this.street = street; }-
     */;

    public final native String getZip() /*
     * -{ return this.zip; }-
     */;

    public final native void setZip(String zip) /*
     * -{ this.zip = zip; }-
     */;

    public final native String getCity() /*
     * -{ return this.city; }-
     */;

    public final native void setCity(String city) /*
     * -{ this.city = city; }-
     */;

    public static native Address build(String json) /*
     * -{ return eval('(' + json + ')'); }-
     */;

    public static Address build() {
        return build("{}");
    }
}