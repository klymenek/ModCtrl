/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modbus.control.api.gwtbp;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;

abstract class GotDetails implements
        AsyncCallback<GetDetailsResponse> {

    @Override
    public void onFailure(Throwable oops) {
        /*
         * default appwide failure handling
         */
    }

    @Override
    public void onSuccess(GetDetailsResponse result) {
        got(result.getDetails());
    }

    public abstract void got(ArrayList<ContactDetail> details);
}