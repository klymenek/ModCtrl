package modbus.control.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import modbus.control.client.rpc.PLCServiceAsync;
import modbus.control.shared.ProcessVarJso;

/**
 * TODO aktualisieren der Ausgaenge vom Server aus!
 * 
 * 
 * @author ares
 * 
 */
public class DigitalOutControl extends VerticalPanel {

    final PLCServiceAsync plc;
    final ProcessVarJso var;
    // UI Elements
    final OutputButton output;
    final Label description;

    class OutputButton extends ToggleButton {

        ProcessVarJso var;

        public OutputButton(String upText) {
            super(upText);
        }

        public ProcessVarJso getVar() {
            return var;
        }

        public void setVar(ProcessVarJso v) {
            this.var = v;
        }
    }

    public DigitalOutControl(PLCServiceAsync p, ProcessVarJso v) {
        super();
        this.plc = p;
        this.var = v;

        output = new OutputButton(var.getDescription());
        output.setVar(var);

        plc.readCoil(var, new AsyncCallback<Boolean>() {

            @Override
            public void onFailure(Throwable caught) {
                ModbusControl.showErrorPanel(ModbusControl.RPC_ERROR_TITLE,
                        ModbusControl.SERVER_ERROR);

            }

            @Override
            public void onSuccess(Boolean result) {
                output.setValue(result);
            }
        });

        description = new Label(var.getDescription());

        addStyleName("dialogVPanel");

        // add(new HTML("<b></b>"));
        //add(description);
        setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        add(output);

        class ModbusHandler implements ValueChangeHandler<Boolean> {

            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                OutputButton outputButton = (OutputButton) event.getSource();

                plc.writeCoil(event.getValue(), outputButton.getVar(),
                        new AsyncCallback<Integer>() {

                    @Override
                            public void onFailure(Throwable caught) {
                                ModbusControl.showErrorPanel(
                                        ModbusControl.RPC_ERROR_TITLE,
                                        ModbusControl.SERVER_ERROR);
                            }

                    @Override
                            public void onSuccess(Integer result) {
                                if (result != 1) {
                                    ModbusControl.showErrorPanel(
                                            ModbusControl.MODBUS_ERROR_TITLE,
                                            ModbusControl.MODBUS_ERROR_001);
                                }
                            }
                        });
            }
        }

        ModbusHandler wagoHandler = new ModbusHandler();
        output.addValueChangeHandler(wagoHandler);
    }
}