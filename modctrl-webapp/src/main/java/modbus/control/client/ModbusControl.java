package modbus.control.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.List;
import modbus.control.api.model.Category;
import modbus.control.api.model.ProcessVar;
import modbus.control.client.rpc.DatabaseService;
import modbus.control.client.rpc.DatabaseServiceAsync;
import modbus.control.client.rpc.PLCService;
import modbus.control.client.rpc.PLCServiceAsync;
import org.krypsis.gwt.store.example.client.gin.AppGinjector;
import org.krypsis.gwt.store.example.client.service.AirlineServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirplaneServiceAsync;
import org.krypsis.gwt.store.example.client.service.AirportServiceAsync;

/**
 * Entry point classes define
 * <code>onModuleLoad()</code>.
 */
public class ModbusControl implements EntryPoint {

    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    public static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";
    public static final String MODBUS_ERROR_001 = "Error xx";
    public static final String RPC_ERROR_TITLE = "Remote Procedure Call - Failure";
    public static final String MODBUS_ERROR_TITLE = "Modbus Error - Failure";
    /**
     * Create a remote service proxy to talk to the server-side DigitalInOut
     * service.
     */
    private final PLCServiceAsync plc = GWT.create(PLCService.class);
    /**
     * Create a remote service proxy to talk to the server-side DigitalInOut
     * service.
     */
    private final DatabaseServiceAsync database = GWT.create(DatabaseService.class);
    private List<Category> CategoryJsos = null;
    private final AppGinjector ginjector = GWT.create(AppGinjector.class);
    private final AirlineServiceAsync airlineService;
    private final AirplaneServiceAsync airplaneService;
    private final AirportServiceAsync airportService;

    public ModbusControl() {
        this.airlineService = ginjector.getAirlineService();
        this.airplaneService = ginjector.getAirplaneService();
        this.airportService = ginjector.getAirportService();
    }

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        final ListBox select = new ListBox();

//  Beispielcode:
//        
        final Label airportLabel = new Label();
//        Airport ap = new Airport();
//        ap.setCountry("Germany");
//        ap.setId("FRA");
//        
//        airportService.save(ap, new SuccessCallback<Airport>() {
//
//            @Override
//            public void onSuccess(Airport result) {
//                airportLabel.setText(result.toString());
//            }
//        });
//
//        airportService.load("FRA", new SuccessCallback<Airport>() {
//
//            @Override
//            public void onSuccess(Airport result) {
//                airportLabel.setText(result.toString());
//            }
//        });

        select.setVisibleItemCount(1);

        class CategoryJsoSelectChangeHandler implements ChangeHandler {

            @Override
            public void onChange(ChangeEvent event) {
                refreshSelection(select);
            }
        }

        final CategoryJsoSelectChangeHandler csHandler = new CategoryJsoSelectChangeHandler();
        select.addChangeHandler(csHandler);

        database.getCategory(new AsyncCallback<List<Category>>() {

            @Override
            public void onFailure(Throwable caught) {
                showErrorPanel(RPC_ERROR_TITLE, SERVER_ERROR);
            }

            @Override
            public void onSuccess(List<Category> result) {
                CategoryJsos = result;

                for (Category r : result) {
                    select.addItem(r.toString());
                    select.setItemSelected(0, true);
                    refreshSelection(select);
                }
            }
        });

        // We can add style names to widgets
        //sendButton.addStyleName("sendButton");

        // Add the Control Elements to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("errorLabelContainer").add(airportLabel);
        RootPanel.get("selectContainer").add(select);
    }

    private void initializeProcessVarJsos(List<ProcessVar> vars) {
        if (vars == null) {
            return;
        }

        VerticalPanel varPanel = new VerticalPanel();

        for (ProcessVar var : vars) {
            varPanel.add(new DigitalOutControl(plc, var));
        }

        RootPanel.get("controlContainer").clear();
        RootPanel.get("controlContainer").add(varPanel);
    }

    public static void showErrorPanel(String title, String msg) {
        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        // Show the error message to the user
        dialogBox.setText(title);
        serverResponseLabel.addStyleName("serverResponseLabelError");
        serverResponseLabel.setHTML(msg);
        dialogBox.center();
        closeButton.setFocus(true);
    }

    void refreshSelection(ListBox s) {
        if (CategoryJsos == null) {
            return;
        }

        String cat = s.getItemText(s.getSelectedIndex());

        Category selectedCategoryJso = null;
        for (Category CategoryJso : CategoryJsos) {
            if (CategoryJso.toString().equals(cat)) {
                selectedCategoryJso = CategoryJso;
                break;
            }
        }

        if (selectedCategoryJso == null) {
            return;
        }

        database.getVarsByCategory(selectedCategoryJso, new AsyncCallback<List<ProcessVar>>() {

            @Override
            public void onFailure(Throwable caught) {
                showErrorPanel(RPC_ERROR_TITLE, SERVER_ERROR);
            }

            @Override
            public void onSuccess(List<ProcessVar> result) {
                initializeProcessVarJsos(result);
            }
        });
    }
}
