
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.util.*;

public class icicitrans {

    String mid;
    splash splash;
    int n;
    Form icici;
    Command ok, back;
    TextField amount, password;
    transaction t;
    RecordStore ici;
    int am;
    Calendar cal;
    DateField exp;
    Alert expalert, passalert, amountalert, error,cerror;
    static int count = 0;
    selectstore selstore;
    amount amtt;

    icicitrans(splash splash, int n) {
        this.splash = splash;
        this.n = n;
        icici = new Form("HDFC Transaction");
        amount = new TextField("Enter amount", "", 30, TextField.NUMERIC);
        exp = new DateField("Expirydate", DateField.DATE);
        password = new TextField("Enter Password of card", "", 30, TextField.PASSWORD);
        back = new Command("back", Command.BACK, 1);
        ok = new Command("ok", Command.OK, 2);
        icici.append(amount);
        icici.addCommand(ok);
        icici.addCommand(back);
        Display.getDisplay(splash).setCurrent(icici);
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == back) {
            t = new transaction(splash);
        }
        if (command == ok) {
            try {
                if (count == 3) {
                    error = new Alert("Error", "You have Exceeded minimum number of attempts\n Please Contact admin", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }

                ici = RecordStore.openRecordStore("icici", true);
                if(ici.getNumRecords()==0){
                    cerror = new Alert("Error", "No record Exist\n please contact admin to configure your account", null, AlertType.ERROR);
                    cerror.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(cerror);
                    return;
                }


                String amo = amount.getString();
                am = Integer.parseInt(amo);
                byte[] data = new byte[1000];

                ici.getRecord(n, data, 0);

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
               
                int amt = Integer.parseInt(st);
                String p = s.substring(1, 5);
                String pass = password.getString();
                System.out.println(str);
                System.out.println(st);
                if (ex.equals(str)) {
                    expalert = new Alert("Error", "Your Card is expired on" + str, null, AlertType.ERROR);
                    expalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(expalert, icici);

                }

                if (!p.equals(pass)) {
                    passalert = new Alert("Error", "Invalid password", null, AlertType.ERROR);
                    passalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(passalert, icici);
                    count++;

                }
                 if (!ex.equals(str) && p.equals(pass)) {
                  
                    amtt = new amount(splash, mid, amt,n);
                }


            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
    }
}
