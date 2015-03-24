
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.util.*;

public class hdfctrans implements CommandListener {

    splash splash;
    Display display;
    int n;
    Calendar cal;
    DateField exp;
    Form hdfc;
    TextField password;
    Command ok, back;
    RecordStore hdf;
    int am;
    transaction t;
    Alert expalert, passalert, amountalert, error, cerror;
    static int count = 0;
    selectstore selstore;
    amount amtt;
    String mid;

    hdfctrans(splash splash, int n) {
        System.out.println("hdfctrans");
        this.splash = splash;
        this.n = n;
        hdfc = new Form("HDFC Transaction");
        //amount = new TextField("Enter amount", "", 30, TextField.NUMERIC);
        exp = new DateField("Expirydate", DateField.DATE);
        password = new TextField("Enter Password of card", "", 30, TextField.NUMERIC + TextField.PASSWORD);
        back = new Command("back", Command.BACK, 2);
        ok = new Command("Next", Command.OK, 1);
        // hdfc.append(amount);
        hdfc.append(password);
        hdfc.addCommand(ok);
        hdfc.addCommand(back);
        Display.getDisplay(splash).setCurrent(hdfc);
        hdfc.setCommandListener(this);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == back) {
            t = new transaction(splash);
        }
        if (command == ok) {
            try {
                System.out.println("inside ok");
                if (count == 3) {
                    error = new Alert("Error", "You have Exceeded minimum number of attempts\n Please Contact admin", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }

                if (password.getString().equals("")) {
                    passalert = new Alert("Error", "Invalid password", null, AlertType.ERROR);
                    passalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(passalert, hdfc);

                }


                hdf = RecordStore.openRecordStore("hdfc", true);

                if (hdf.getNumRecords() == 0) {
                    cerror = new Alert("Error", "No record Exist\n please contact admin to configure your account", null, AlertType.ERROR);
                    cerror.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(cerror);
                    return;
                }

                //String amo = amount.getString();
                //am = Integer.parseInt(amo);
                byte[] data = new byte[1000];

                hdf.getRecord(n, data, 0);

                String s = new String(data);
                System.out.println("comingggg1");
                cal = Calendar.getInstance();
                cal.setTime(new Date());
                System.out.println("comingggg2");
                cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
                exp.setDate(cal.getTime());
                System.out.println("comingggg");
                Date expr = exp.getDate();
                String ex = expr.toString();
                System.out.println("comingggg");
                int i = s.lastIndexOf('\t');
                int inid = s.lastIndexOf('s');
                int inam = s.lastIndexOf('a');
                String str = s.substring(i, inid);
                int ia=s.lastIndexOf('c');
                String st = s.substring(inam + 1, ia);
                mid = s.substring(inid + 1, inam);
                System.out.println(mid);
                mid = mid.trim();
                str = str.trim();
                st = st.trim();
                //String stramount = amount.getString();
                //System.out.println("error here");
                //int typedamount = Integer.parseInt(stramount);
                System.out.println("no error");
                
                
                int amt = Integer.parseInt(st);
                System.out.println("after st");
                String p = s.substring(1, 5);
                String pass = password.getString();
                System.out.println(str);
                System.out.println(st);
                if (ex.equals(str)) {
                    expalert = new Alert("Error", "Your Card is expired on" + str, null, AlertType.ERROR);
                    expalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(expalert, hdfc);

                }

                if (!p.equals(pass)) {
                    passalert = new Alert("Error", "Invalid password", null, AlertType.ERROR);
                    passalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(passalert, hdfc);
                    count++;

                }
                if (!ex.equals(str) && p.equals(pass)) {


                    amtt = new amount(splash, mid, amt, n);




                }


            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
    }
}
