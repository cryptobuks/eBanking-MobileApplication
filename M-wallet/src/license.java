
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class license implements CommandListener {

    Alert error,info;
    public splash splash;
    private Form license;
    private Command back, add;
    private option opt;
    RecordStore li;
    TextField lino, lidetails;
    private DateField expirydate, dob;
    private ChoiceGroup genderlist;
    private String gender[] = new String[2];

    license(splash splash) {
        this.splash = splash;
        gender[0] = "Male";
        gender[1] = "Female";
        try {
            li = RecordStore.openRecordStore("license", true);
            byte data[] = new byte[1000];
            if (li.getNumRecords() == 0) {
                genderlist = new ChoiceGroup("Select Choice", Choice.POPUP, gender,
                        null);
                license = new Form("Add License details");
                add = new Command("Add", Command.OK, 1);
                back = new Command("Back", Command.BACK, 2);
                lino = new TextField("Enter License number", "", 30, TextField.ANY);
                expirydate = new DateField("Date of Expiry:", DateField.DATE, TimeZone.getTimeZone("GMT"));
                dob = new DateField("Date of Birth:", DateField.DATE, TimeZone.getTimeZone("GMT"));
                license.addCommand(add);
                license.addCommand(back);
                license.append(lino);
                license.append(expirydate);
                license.append(dob);
                license.append(genderlist);
            } else {
                license = new Form("License details");
                li.getRecord(1, data, 0);
                String dt = new String(data);
                back = new Command("Back", Command.BACK, 0);
                lidetails = new TextField("DETAILS:", "", 1000, TextField.ANY);
                lidetails.setPreferredSize(100, 150);
                license.append(lidetails);
                license.addCommand(back);
                lidetails.setString(dt);

            }
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

        Display.getDisplay(splash).setCurrent(license);
        license.setCommandListener(this);
        try {
            li.closeRecordStore();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == back) {
            opt = new option(splash);
        }
        if (command == add) {
            try {

                if (lino.getString().equals("") || lino.getString().length() != 16) {
                    error = new Alert("Error", "please enter 16 digit license number", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }
                String sex = null;
                li = RecordStore.openRecordStore("license", true);
                String no = lino.getString();
                Date ed = expirydate.getDate();
                String expdate = ed.toString();
                Date db = dob.getDate();
                String birthdate = db.toString();
                if (genderlist.getSelectedIndex() == 0) {
                    sex = "Male";
                }
                if (genderlist.getSelectedIndex() == 1) {
                    sex = "Female";
                }

                String details = new String("License no.: " + no + "\nExpiry Date: " + expdate + "\nDate of Birth : " + birthdate + "\nSex :" + sex);
                byte dat[] = new byte[details.length()];
                dat = details.getBytes();
                li.addRecord(dat, 0, dat.length);
                info = new Alert("Info", "License information Added", null, AlertType.CONFIRMATION);
                info.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(info);

            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
    }
}
