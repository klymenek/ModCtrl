/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbus.control.api.gwtbp;

import com.google.gwt.core.client.EntryPoint;
import java.util.ArrayList;

/**
 *
 * @author ag
 */
public class MyEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        showContact(null);
    }

    void showContact(final Contact contact) {
//        service.execute(new GetDetails(contact.getDetailIds()),
//                new GotDetails() {
//
//                    public void got(ArrayList<ContactDetail> details) {
//                        renderContact(contact);
//                        renderDetails(details);
//                    }
//                });
    }
}
