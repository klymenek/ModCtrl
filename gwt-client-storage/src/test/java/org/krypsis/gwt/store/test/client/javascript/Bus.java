package org.krypsis.gwt.store.test.client.javascript;

import com.google.gwt.core.client.JsArrayString;
import java.util.ArrayList;
import java.util.List;
import org.krypsis.gwt.store.client.javascript.JsStorable;
import org.krypsis.gwt.store.client.javascript.MutableJsArrayString;

public class Bus extends JsStorable {

    protected Bus() {
    }

    public final native void setName(String name) /*
     * -{ this.name = name; }-
     */;

    public final native String getName() /*
     * -{ return this.name; }-
     */;

    public final void addPerson(Person person) {
        final JsArrayString ids = getPersonIds0();
        for (int i = 0; i < ids.length(); i++) {
            final String id = ids.get(i);
            if (person.getId().equals(id)) {
                return;
            }
        }

        ids.set(ids.length(), person.getId());
    }

    public final void removePerson(Person person) {
        final MutableJsArrayString ids = getPersonIds0();
        for (int i = 0; i < ids.length(); i++) {
            final String id = ids.get(i);
            if (person.getId().equals(id)) {
                ids.remove(i);
                break;
            }
        }
    }

    public final List<String> getPersonIds() {
        final List<String> ids = new ArrayList<String>();
        final MutableJsArrayString array = getPersonIds0();
        for (int i = 0; i < array.length(); i++) {
            ids.add(array.get(0));
        }
        return ids;
    }

    private native MutableJsArrayString getPersonIds0() /*
     * -{ if (!this.personIds) { this.personIds = []; }; return this.personIds; }-
     */;

    public static native Bus build(String json) /*
     * -{ return eval('(' + json + ')'); }-
     */;

    public static Bus build() {
        return build("{}");
    }
}
