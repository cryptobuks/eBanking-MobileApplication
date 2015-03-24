
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class transaction implements CommandListener {

    Alert error;
    int i;
    public splash splash;
    sbitrans sb;
    icicitrans ic;
    hdfctrans hd;
    private Form transform;
    private Command back;
    private Command select;
    TextField cardno;
    private option opt;
    private String card[] = new String[3];
    private ChoiceGroup creditlist;
    RecordStore hdfc, icici, sbi;

    transaction(splash splash) {
        this.splash = splash;
        card[0] = "HDFC";
        card[1] = "ICICI";
        card[2] = "SBI";
        creditlist = new ChoiceGroup("Select Choice", Choice.POPUP, card,
                null);
        transform = new Form("Select a credit card for transaction");
        select = new Command("Select", Command.OK, 1);
        back = new Command("Back", Command.BACK, 2);
        cardno = new TextField("enter serial number of card", "", 30, TextField.NUMERIC);
        transform.addCommand(back);
        transform.addCommand(select);
        transform.append(creditlist);
        transform.append(cardno);
        Display.getDisplay(splash).setCurrent(transform);
        transform.setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == back) {
            opt = new option(splash);
        }
        if (command == select) {
            if (creditlist.getSelectedIndex() == 0) {
                String s = cardno.getString();
                int n = Integer.parseInt(s);
                try {
                    hdfc=RecordStore.openRecordStore("hdfc", true);
                    i = hdfc.getNumRecords();
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
                System.out.println("error here");
                if (i < n) {

                    error = new Alert("Error", "Record Number Exceeded", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }
                hd = new hdfctrans(splash, n);

            }
            if (creditlist.getSelectedIndex() == 1) {
                String s = cardno.getString();
                int n = Integer.parseInt(s);
                try {
                    icici=RecordStore.openRecordStore("icici", true);
                    i = icici.getNumRecords();
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
                if (i < n) {

                    error = new Alert("Error", "Record Number Exceeded", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }
                ic = new icicitrans(splash, n);

            }
            if (creditlist.getSelectedIndex() == 2) {
                String s = cardno.getString();
                int n = Integer.parseInt(s);
                try {
                    sbi=RecordStore.openRecordStore("sbi", true);
                    i = sbi.getNumRecords();
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
                if (i < n) {

                    error = new Alert("Error", "Record Number Exceeded", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }
                sb = new sbitrans(splash, n);

            }
        }
    }
}
