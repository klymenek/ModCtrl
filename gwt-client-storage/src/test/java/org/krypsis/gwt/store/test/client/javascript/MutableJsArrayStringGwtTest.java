package org.krypsis.gwt.store.test.client.javascript;

import com.google.gwt.junit.client.GWTTestCase;
import org.krypsis.gwt.store.client.javascript.MutableJsArrayString;

public class MutableJsArrayStringGwtTest extends GWTTestCase {

    public void testRemove() {
        final MutableJsArrayString array = (MutableJsArrayString) MutableJsArrayString.createArray();
        array.set(0, "1");
        array.set(1, "2");
        assertEquals(2, array.length());
        array.remove(0);
        assertEquals(1, array.length());
        assertEquals("2", array.get(0));
    }

    public void testIndexOf() {
        final MutableJsArrayString array = (MutableJsArrayString) MutableJsArrayString.createArray();
        array.add("1");
        array.add("2");
        array.add("3");
        assertEquals(0, array.indexOf("1"));
        assertEquals(1, array.indexOf("2"));
        assertEquals(2, array.indexOf("3"));
        assertEquals(-1, array.indexOf("4"));
    }

    @Override
    public String getModuleName() {
        return "org.krypsis.gwt.store.test.StoreTest";
    }
}
