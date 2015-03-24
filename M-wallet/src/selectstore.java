
import java.io.DataOutputStream;

import java.util.Vector;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.Alert;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;

public class selectstore implements Runnable, CommandListener,
        DiscoveryListener {

    splash splash;
    private LocalDevice local_device;
    private UUID uuid;
    private DiscoveryAgent disc_agent;
    private Vector devices;
    private Command cmd_ok;
    private List deviceList;
    private ServiceRecord service;
    private RemoteDevice otherDevice;
    DataOutputStream output;
    private boolean deviceChosen;
    private boolean connect;
    Form messageForm;
    private Form list;
    Thread t;
    String url;
    StreamConnection conn;
    private Command cmd_back;
    private String message;
    int n;
    option opt;
    int amt;
    String amount, mid;
    String shopname;

    public selectstore(splash splash, String mid, UUID uuid, int n, int amt, String shopname) {
        this.shopname = shopname;
        this.n = n;
        this.mid = mid;
        this.splash = splash;
        this.amt = amt;
        amount = Integer.toString(amt);

        devices = new Vector();
        deviceChosen = false;
        messageForm = new Form("");
        connect = false;

        deviceList = new List("Devices discovered", List.EXCLUSIVE);
        cmd_ok = new Command("ok", Command.OK, 1);

        cmd_back = new Command("back", Command.BACK, 1);
        t = new Thread(this);
        t.start();
    }

    public void run() {

        try {
            messageForm.append("Waiting...");
            Display.getDisplay(splash).setCurrent(messageForm);
            local_device = LocalDevice.getLocalDevice();
            disc_agent = local_device.getDiscoveryAgent();
            local_device.setDiscoverable(DiscoveryAgent.GIAC);
            disc_agent.startInquiry(DiscoveryAgent.GIAC, this);
            uuid = new UUID(0x0003);
            // wait until the user choose one remote device to be the server
            while (!deviceChosen) {
            }

            //verify the service
            if (connect) {
                try {
                    int transationID = disc_agent.searchServices(
                            new int[]{0x0100}, new UUID[]{uuid}, otherDevice,
                            this);
                } catch (Exception e) {
                }
            }

        } catch (BluetoothStateException e) {
            e.printStackTrace();
        } catch (Exception f) {
            f.printStackTrace();
        }
    }

    public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
        devices.addElement(arg0);

    }

    public void inquiryCompleted(int discType) {
        switch (discType) {
            case INQUIRY_COMPLETED:
                if (devices.size() > 0) {

                    deviceList.deleteAll();
                    for (int i = 0; i < devices.size(); i++) {
                        try {
                            RemoteDevice rd = (RemoteDevice) devices.elementAt(i);
                            deviceList.append(rd.getFriendlyName(false), null);

                        } catch (Exception e) {
                        }

                    }

                    deviceList.addCommand(cmd_ok);
                    deviceList.addCommand(cmd_back);
                    deviceList.setCommandListener(this);
                    Display.getDisplay(splash).setCurrent(deviceList);

                } else {
                    deviceChosen = true;


                }

                break;

            case INQUIRY_TERMINATED:


                break;

            case INQUIRY_ERROR:

                break;
        }

    }

    public void serviceSearchCompleted(int transID, int respCode) {

        switch (respCode) {
            case SERVICE_SEARCH_TERMINATED:

                break;

            case SERVICE_SEARCH_ERROR:

                break;

            case SERVICE_SEARCH_NO_RECORDS:

                break;

            case SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
                Display.getDisplay(splash).setCurrent(new Alert("The remote device isn't reachable!"),
                        list);
                break;

            case SERVICE_SEARCH_COMPLETED:

                try {

                    createConnection();

                    output.writeUTF(shopname+"m"+mid + "a" + amount);
                    output.flush();
                    Form messageForm = new Form("");
                    messageForm.append("Message sent");
                    messageForm.addCommand(cmd_back);
                    messageForm.setCommandListener(this);
                    Display.getDisplay(splash).setCurrent(messageForm);


                    output.close();
                    conn.close();
                } catch (Exception e) {
                }

                break;
        }

    }

    // establish a connection and create an DataOutputStream to send a string
    private void createConnection() {
        try {
            url = service.getConnectionURL(
                    ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
            conn = (StreamConnection) Connector.open(url);

            output = conn.openDataOutputStream();
        } catch (Exception e) {
        }

    }

    public void servicesDiscovered(int arg0, ServiceRecord[] servRecord) {
        service = servRecord[0];

    }

    public void commandAction(Command arg0, Displayable arg1) {
        if (arg1 == deviceList && arg0 == cmd_ok) {
            otherDevice = (RemoteDevice) devices.elementAt(deviceList.getSelectedIndex());

            messageForm.deleteAll();
            messageForm.append("Waitting...");
            Display.getDisplay(splash).setCurrent(messageForm);

            deviceChosen = true;
            connect = true;
        }
        if (arg0 == cmd_back) {
            if (n == 0) {
                opt = new option(splash);
            }
            if (n == 1) {
                opt = new option(splash);
            }
            if (n == 2) {
                opt = new option(splash);
            }
        }

    }
}
