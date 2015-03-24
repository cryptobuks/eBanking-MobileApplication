
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class bank implements CommandListener {

    public int hdfirst[] = new int[5], icfirst[] = new int[5], sbfirst[] = new int[5];
    Calendar cal;
    private Alert cardalert, expiryalert, namealert, accalert, midalert, addalert, calert, cvvalert;
    banklogin banklogin;
    private Display display;
    private Form addcreditcard;
    private Command add;
    private Alert aalert;
    private Command logout;
    private TextField bname, custacc, cardno, cardpass, mid, minamount, cvv;
    private DateField calender,expirydate;
    private static final int DATE = 0;
    splash splash;
    initialoption initialoption;
    public RecordStore hdfc, icici, sbi, banklist, hfirst, ifirst, sfirst;
    private String name, acc, no, pass, pas, bankname, m;
    public int hdid[] = new int[5], icid[] = new int[5], sbid[] = new int[5], hdcount = 0, iccount = 0, sbcount = 0, hc, ic, sc;

    public bank(splash splash, String u) {
        bankname = u;
        System.out.println("in bank");
        this.splash = splash;
        logout = new Command("logout", Command.EXIT, 1);
        add = new Command("Add", Command.OK, 2);
        bname = new TextField("Bank name", "", 30, TextField.UNEDITABLE);
        cvv = new TextField("CVV number", "", 3, TextField.NUMERIC);
        custacc = new TextField("Customer account no", "", 30, TextField.NUMERIC);
        cardno = new TextField("Card no", "", 30, TextField.NUMERIC);
        calender = new DateField("Date of issue:", DateField.DATE, TimeZone.getTimeZone("GMT"));
        System.out.println("coming here1");
        expirydate =new DateField("Date of Expiry:", DateField.DATE, TimeZone.getTimeZone("GMT"));
        System.out.println("coming here 2");
        cardpass = new TextField("Password", "", 30, TextField.PASSWORD + TextField.UNEDITABLE);
        mid = new TextField("MID", "", 20, TextField.NUMERIC);
        minamount = new TextField("Amount", "25000", 20, TextField.NUMERIC + TextField.UNEDITABLE);
        addcreditcard = new Form("Add Credit Card");

        bname.setString(bankname);
        pas = random();
        //m = rand();
       
        cardpass.setString(pas);
        //mid.setString(m);
        addcreditcard.append(bname);
        addcreditcard.append(custacc);
        addcreditcard.append(cardno);
        addcreditcard.append(cvv);
        addcreditcard.append(calender);
        addcreditcard.append(expirydate);
        addcreditcard.append(cardpass);
        addcreditcard.append(mid);
        addcreditcard.append(minamount);
        addcreditcard.addCommand(logout);
        addcreditcard.addCommand(add);
        Display.getDisplay(splash).setCurrent(addcreditcard);
        addcreditcard.setCommandListener(this);
        System.out.println("coming at end");

    }

    public RecordStore get() {
        return hdfc;
    }

    private boolean hdvalidno(String no) {
        if (no.length() == 16) {
            return true;
        }
        cardalert = new Alert("Warning", "Card Number must of 16 digit", null, AlertType.WARNING);
        cardalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(cardalert);

        return false;
    }

    private boolean hdvalidacc(String no) {
        if (no.length() == 13) {
            return true;
        }
        accalert = new Alert("Warning", "account number must be of 13 digit", null, AlertType.WARNING);
        accalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(accalert, addcreditcard);


        return false;
    }

    private boolean icvalidno(String no) {
        if (no.length() == 16) {
            return true;
        }
        cardalert = new Alert("Warning", "Card Number must of 16 digit", null, AlertType.WARNING);
        cardalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(cardalert);

        return false;
    }

    private boolean icvalidacc(String no) {
        if (no.length() == 12) {
            return true;
        }
        accalert = new Alert("Warning", "account number must be of 12 digit", null, AlertType.WARNING);
        accalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(accalert, addcreditcard);


        return false;
    }

    private boolean sbvalidno(String no) {
        if (no.length() == 16) {
            return true;
        }
        cardalert = new Alert("Warning", "Card Number must of 16 digit", null, AlertType.WARNING);
        cardalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(cardalert);

        return false;
    }

    private boolean sbvalidacc(String no) {
        if (no.length() == 11) {
            return true;
        }
        accalert = new Alert("Warning", "account number must be of 11 digit", null, AlertType.WARNING);
        accalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(accalert, addcreditcard);


        return false;
    }

    private String random() {
        Random num = new Random();
        int p = num.nextInt(100000000);
        String ps = Integer.toString(p);
        ps = ps.substring(0, 4);

        return ps;

    }

    private String rand() {
        Random num = new Random();
        int p = num.nextInt(19999999);
        String ps = Integer.toString(p);
        ps = ps.substring(0, 3);
        return ps;
    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == logout) {
            initialoption = new initialoption(splash);
        }
        if (command == add) {
            if (mid.getString().equals("") || mid.getString().length() != 16) {
                midalert = new Alert("Error", "please enter 16 digit MID", null, AlertType.ERROR);
                midalert.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(midalert);
                return;
            }
            if (cvv.getString().equals("") || cvv.getString().length() != 3) {

                cvvalert = new Alert("Error", "please enter 3 digit CVV Number", null, AlertType.ERROR);
                cvvalert.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(cvvalert);
                return;
            }


            if (bname.getString().equals("hdfc")) {
                try {
                    //System.out.println("here");
                    hdfc = RecordStore.openRecordStore("hdfc", true);
                    // hdfc.closeRecordStore();
                    if (hdfc.getNumRecords() > 4) {
                        calert = new Alert("Error", "You are not allowed to add more than 4 cards\n", null, AlertType.ERROR);
                        calert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(calert);
                        return;
                    }

                    // RecordStore.deleteRecordStore("hdfc");
                    //System.out.println("here");
                    name = bname.getString();
                    acc = custacc.getString();
                    Date issuedate = calender.getDate();
                    String ss = issuedate.toString();
                   
                    Date exp = expirydate.getDate();
                    String ex = exp.toString();
                    no = cardno.getString();
                    pass = cardpass.getString();
                    String id = mid.getString();
                    id = "1" + id;
                    String amount = minamount.getString();
                    String cv = cvv.getString();

                    //char ch1=exp.charAt(2);
                    //   char ch2=exp.charAt(5);
                    if (!name.equals("hdfc")) {
                        namealert = new Alert("Warning", "Bank name must be ' hdfc ' ", null, AlertType.WARNING);
                        namealert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(namealert, addcreditcard);
                        hdfc.closeRecordStore();
                    }
                    /* if (exp.charAt(2) != '/' && exp.charAt(5) != '/') {
                    expiryalert = new Alert("Warning", "Enter Date in proper Format", null, AlertType.WARNING);
                    expiryalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(expiryalert);
                    
                    }*/

                    boolean valid1 = hdvalidno(no);
                    boolean valid2 = hdvalidacc(acc);
                    if (valid1 == true && valid2 == true) {
                        hdfirst[hdcount] = 1;
                        String details = new String(hdfirst[hdcount] + pass + acc + "\nBank Name   \t" + name + "\nAccount No  \t" + acc + "\nCredit Card No.   \t" + no + "\nIssue Date : " + ss + "\nExpiry Date   \t" + ex + "s" + id + "a" + amount + "c" + cv);
                        System.out.println(details);
                        byte dat[] = new byte[details.length()];
                        dat = details.getBytes();
                        System.out.println(dat);
                        String s = dat.toString();
                        System.out.println(s);
                        hdid[hdcount] = hdfc.addRecord(dat, 0, dat.length);
                        System.out.println("here");

                        //hfirst = RecordStore.openRecordStore("hdfirst", true);
                        //hdfirst[hdcount]=1;
                        //String ss= String.valueOf( hdfirst[hdcount]);
                        //byte[] d=ss.getBytes();
                        //hfirst..addRecord(d, 0, d.length);
                        hdcount += 1;
                        hc = hdfc.getNextRecordID();
                        hc = hc - 1;
                        addalert = new Alert("Info", "Entry Added! Please Check the Creditcard details\nto view password of card\nCredit Card serial number is :- " + hc, null, AlertType.CONFIRMATION);
                        addalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(addalert);
                        hdfc.closeRecordStore();
                        /* aalert = new Alert("credit card addition", "creditcard added", null, AlertType.CONFIRMATION);
                        //AlertType.ERROR.playSound(display);
                        System.out.println("hello test");
                        aalert.setTimeout(Alert.FOREVER);
                        display.setCurrent(aalert, addcreditcard);
                        //hdfc.closeRecordStore();*/
                    }

                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }




            } else if (bname.getString().equals("icici")) {
                try {
                    icici = RecordStore.openRecordStore("icici", true);
                    if (icici.getNumRecords() > 4) {
                        calert = new Alert("Error", "You are not allowed to add more than 4 cards\n", null, AlertType.ERROR);
                        calert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(calert);
                        return;
                    }
                    if (cvv.getString().equals("") || cvv.getString().length() != 3) {

                        cvvalert = new Alert("Error", "please enter 3 digit CVV Number", null, AlertType.ERROR);
                        cvvalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(cvvalert);
                        return;
                    }

                    // icici.closeRecordStore();

                    //RecordStore.deleteRecordStore("icici");
                    name = bname.getString();
                    acc = custacc.getString();
                    Date issuedate = calender.getDate();
                    String ss = issuedate.toString();
                    Date exp = expirydate.getDate();
                    String ex = exp.toString();
                    no = cardno.getString();
                    pass = cardpass.getString();
                    String id = mid.getString();
                    id = "2" + id;
                    String amount = minamount.getString();
                    String cv = cvv.getString();


                    //            char ch1=exp.charAt(2);
                    //         char ch2=exp.charAt(5);
                    if (!name.equals("icici")) {
                        namealert = new Alert("Warning", "Bank name must be ' icici ' ", null, AlertType.WARNING);
                        namealert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(namealert, addcreditcard);
                    }

                    /* if (exp.charAt(2) != '/' && exp.charAt(5) != '/') {
                    expiryalert = new Alert("Warning", "Enter Date in proper Format", null, AlertType.WARNING);
                    expiryalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(expiryalert);
                    
                    }*/
                    boolean valid1 = icvalidno(no);
                    boolean valid2 = icvalidacc(acc);
                    System.out.println("here");
                    if (valid1 == true && valid2 == true) {
                        icfirst[iccount] = 1;
                        String details = new String(icfirst[iccount] + pass + acc + "\nBank Name\t" + name + "\nAccount No\t" + acc + "\nCredit Card No.\t" + no + "\nIssue Date: " + ss + "\nExpiry Date\t" + ex + "s" + id + "a" + amount + "c" + cv);
                        System.out.println(details);
                        byte dat[] = new byte[details.length()];
                        dat = details.getBytes();
                        System.out.println(dat);
                        String s = dat.toString();
                        System.out.println(s);
                        icid[iccount] = icici.addRecord(dat, 0, dat.length);

                        System.out.println(dat);
                        iccount += 1;
                        ic = icici.getNextRecordID();
                        ic = ic - 1;
                        addalert = new Alert("Info", "Entry Added! Please Check the Creditcard details\nto view password of card\nCredit Card serial number is :- " + ic, null, AlertType.CONFIRMATION);
                        addalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(addalert);
                        icici.closeRecordStore();
                        /*aalert = new Alert("credit card addition", "creditcard added", null, AlertType.CONFIRMATION);
                        AlertType.ERROR.playSound(display);
                        aalert.setTimeout(Alert.FOREVER);
                        display.setCurrent(aalert, addcreditcard);*/
                    } else {
                        cardalert = new Alert("Warning", "Card Number must of 12 digit", null, AlertType.WARNING);
                        cardalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(cardalert);
                    }

                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }




            } else if (bname.getString().equals("sbi")) {
                try {
                    sbi = RecordStore.openRecordStore("sbi", true);

                    // sbi.closeRecordStore();

                    // RecordStore.deleteRecordStore("sbi");
                    if (sbi.getNumRecords() > 4) {
                        calert = new Alert("Error", "You are not allowed to add more than 4 cards\n", null, AlertType.ERROR);
                        calert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(calert);
                        return;
                    }
                    if (cvv.getString().equals("") || cvv.getString().length() != 3) {

                        cvvalert = new Alert("Error", "please enter 3 digit CVV Number", null, AlertType.ERROR);
                        cvvalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(cvvalert);
                        return;
                    }

                    name = bname.getString();
                    acc = custacc.getString();
                    Date issuedate = calender.getDate();
                    String ss = issuedate.toString();
                    Date exp = expirydate.getDate();
                    String ex = exp.toString();
                    no = cardno.getString();
                    pass = cardpass.getString();
                    String id = mid.getString();
                    id = "3" + id;
                    String amount = minamount.getString();
                    String cv = cvv.getString();
                    if (!name.equals("sbi")) {
                        namealert = new Alert("Warning", "Bank name must be ' sbi ' ", null, AlertType.WARNING);
                        namealert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(namealert, addcreditcard);
                    }

                    /* if (exp.charAt(2) != '/' && exp.charAt(5) != '/') {
                    expiryalert = new Alert("Warning", "Enter Date in proper Format", null, AlertType.WARNING);
                    expiryalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(expiryalert);
                    
                    }*/
                    boolean valid1 = sbvalidno(no);
                    boolean valid2 = sbvalidacc(no);
                    if (valid1 == true && valid2 == true) {
                        sbfirst[sbcount] = 1;
                        String details = new String(sbfirst[sbcount] + pass + acc + "\nBank Name\t" + name + "\nAccount No\t" + acc + "\nCredit Card No.\t" + no + "\nIssue Date : " + ss + "\nExpiry Date\t" + ex + "s" + id + "a" + amount + "c" + cv);
                        System.out.println(details);
                        byte dat[] = new byte[details.length()];
                        dat = details.getBytes();
                        System.out.println(dat);
                        String s = dat.toString();
                        System.out.println(s);
                        sbid[sbcount] = sbi.addRecord(dat, 0, dat.length);
                        sbcount += 1;
                        /*aalert = new Alert("credit card addition", "creditcard added", null, AlertType.CONFIRMATION);
                        AlertType.ERROR.playSound(display);
                        aalert.setTimeout(Alert.FOREVER);
                        display.setCurrent(aalert, addcreditcard);*/
                        sc = sbi.getNextRecordID();
                        sc = sc - 1;
                        addalert = new Alert("Info", "Entry Added! Please Check the Creditcard details\nto view password of card\nCredit Card serial number is :- " + sc, null, AlertType.CONFIRMATION);
                        addalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(addalert);
                        sbi.closeRecordStore();
                    } else {
                        cardalert = new Alert("Warning", "Card Number must of 12 digit", null, AlertType.WARNING);
                        cardalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(cardalert);
                    }

                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }




            }
        }

    }
}
