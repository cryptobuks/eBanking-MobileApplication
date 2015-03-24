
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class creditcard implements CommandListener {

    int i;
    public int index;
    public String cno;
    private relogin relogin;
    static int hdc[] = new int[5], icc[] = new int[5], sbc[] = new int[5];
    public splash splash;
    Alert firstalert, error, recalert;
    private Form creditform, first;
    private Command back;
    private Command select, forgot;
    private Display display;
    private option opt;
    private String[] card = new String[3];
    private ChoiceGroup creditlist;
    RecordStore creditcard, hdfc;
    private int id[] = new int[3];
    private byte[] b;
    bank bank;
    private String bankop;
    private TextField carddetail, cardno;

    public creditcard(splash splash) {
        this.splash = splash;
        card[0] = "HDFC";
        card[1] = "ICICI";
        card[2] = "SBI";

        creditform = new Form("Select a credit card to view details");
        select = new Command("View", Command.OK, 1);
        forgot = new Command("Forgot Password", Command.OK, 1);
        back = new Command("Back", Command.BACK, 2);
        creditlist = new ChoiceGroup("Select Choice", Choice.POPUP, card,
                null);
        creditform.addCommand(back);
        creditform.addCommand(select);
        creditform.addCommand(forgot);
        carddetail = new TextField("DETAILS:", "", 1000, TextField.ANY);
        carddetail.setPreferredSize(100, 150);
        cardno = new TextField("serial number of card", "", 30, TextField.NUMERIC);
        //carddetail.setString("CREDIT CARD DETAILS");

        /*  hdfc = new String("CARD HOLDER NAME:NEEL SHAH\nCREDIT CARD NO:XXXX
        XXXX XXXX 8469\nEXPIRY DATE:09/13");
        sbi = new String("BANK NAME:SBI\nCARD HOLDER NAME:NEEL SHAH\nCREDIT
        CARD NO:XXXX XXXX XXXX 4048\nEXPIRY DATE:11/14");
        icici = new String("BANK NAME:ICICI\nCARD HOLDER NAME:NEEL SHAH
        \nCREDIT CARD NO:XXXX XXXX XXXX 7896\nEXPIRY DATE:12/15");
         */
        creditform.append(creditlist);
        creditform.append(cardno);
        creditform.append(carddetail);
        /*  try {
        
        creditcard = RecordStore.openRecordStore("creditcard", true);
        byte[] hdfcdata = hdfc.getBytes();
        byte[] sbidata = sbi.getBytes();
        byte[] icicidata = icici.getBytes();
        
        id[0] = creditcard.addRecord(hdfcdata, 0, hdfcdata.length);
        id[1] = creditcard.addRecord(sbidata, 0, sbidata.length);
        id[2] = creditcard.addRecord(icicidata, 0, icicidata.length);
        } catch (Exception e) {
        Alert error = new Alert("Error", e.getMessage(), null,
        AlertType.INFO);
        error.setTimeout(Alert.FOREVER);
        display.setCurrent(error);
        }*/
        Display.getDisplay(splash).setCurrent(creditform);
        creditform.setCommandListener(this);
    }

    void first(String d) {
        String s = new String();
        s = d.substring(1, 5);
        firstalert = new Alert("Password Info", s, null, AlertType.INFO);
        firstalert.setTimeout(Alert.FOREVER);
        Display.getDisplay(splash).setCurrent(firstalert, creditform);

    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == back) {
            opt = new option(splash);
        }

        if (command == select) {
            if (cardno.getString().equals("")) {
                error = new Alert("Error", "please enter the serial number of card", null, AlertType.ERROR);
                error.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(error);
                return;
            }
            if (creditlist.getSelectedIndex() == 0) {
                System.out.println("hdfc");
                try {

                    //RecordStore hd=bank.hdfc;
                    String s = cardno.getString();
                    int n = Integer.parseInt(s);

                    creditcard = RecordStore.openRecordStore("hdfc", true);
                    if (creditcard.getNumRecords() == 0) {
                        recalert = new Alert("Error", "No record Exist", null, AlertType.ERROR);
                        recalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(recalert);
                        return;
                    }
                    System.out.println("coming in hdfc");


                    byte data[] = new byte[1000];
                    creditcard.getRecord(n, data, 0);

                    String details = new String(data);
                    // System.out.println("first time"+bank.hdfirst[n]);
                    String str = new String();
                    str = details.substring(0, 1);
                    // i = Integer.parseInt(str);
                    System.out.println("value of first" + str);


                    if (str.equals("1") && hdc[n] == 0) {
                        str = "0";
                        first(details);
                        String reps = new String();
                        reps = details.substring(1, details.length());
                        System.out.println("coming b4 adding");
                        // String sint=Integer.toString(i);
                        String modifieddetails = new String(str + reps);
                        byte dat[] = new byte[modifieddetails.length()];
                        dat = modifieddetails.getBytes();
                        creditcard.setRecord(n, dat, 0, dat.length);
                        System.out.println("coming after adding");

                        hdc[n] = 1;
                    }
                    //System.out.println(details);
                    String subdetails = new String();
                    int in = details.indexOf('\n');
                    int lin = details.lastIndexOf('s');
                    subdetails = details.substring(in, lin);
                    carddetail.setString(subdetails);
                    /*  bank = new byte[creditcard.getRecordSize(id[0])];
                    creditcard.getRecord(id[0], bank, 0);
                    bankop = new String(bank);
                    carddetail.setString(bankop);*/
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }

            }
            if (creditlist.getSelectedIndex() == 1) {
                System.out.println("icici");
                try {

                    //RecordStore hd=bank.hdfc;
                    String s = cardno.getString();
                    int n = Integer.parseInt(s);

                    creditcard = RecordStore.openRecordStore("icici", true);
                    if (creditcard.getNumRecords() == 0) {
                        recalert = new Alert("Error", "No record Exist", null, AlertType.ERROR);
                        recalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(recalert);
                        return;
                    }
                    System.out.println("coming in icici");


                    byte data[] = new byte[1000];
                    creditcard.getRecord(n, data, 0);

                    String details = new String(data);
                    // System.out.println("first time"+bank.hdfirst[n]);
                    String str = new String();
                    str = details.substring(0, 1);
                    // i = Integer.parseInt(str);
                    System.out.println("value of first" + str);


                    if (str.equals("1") && icc[n] == 0) {
                        str = "0";
                        first(details);
                        String reps = new String();
                        reps = details.substring(1, details.length());
                        System.out.println("coming b4 adding");
                        // String sint=Integer.toString(i);
                        String modifieddetails = new String(str + reps);
                        byte dat[] = new byte[modifieddetails.length()];
                        dat = modifieddetails.getBytes();
                        creditcard.setRecord(n, dat, 0, dat.length);
                        System.out.println("coming after adding");

                        icc[n] = 1;
                    }
                    //System.out.println(details);
                    String subdetails = new String();
                    int in = details.indexOf('\n');
                    int lin = details.lastIndexOf('s');
                    subdetails = details.substring(in, lin);
                    carddetail.setString(subdetails);
                    /*  bank = new byte[creditcard.getRecordSize(id[0])];
                    creditcard.getRecord(id[0], bank, 0);
                    bankop = new String(bank);
                    carddetail.setString(bankop);*/
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
                /*bank = new byte[creditcard.getRecordSize(id[1])];
                creditcard.getRecord(id[1], bank, 0);
                bankop = new String(bank);
                carddetail.setString(bankop);*/
            }
            if (creditlist.getSelectedIndex() == 2) {
                System.out.println("sbi");
                try {

                    //RecordStore hd=bank.hdfc;
                    String s = cardno.getString();
                    int n = Integer.parseInt(s);

                    creditcard = RecordStore.openRecordStore("sbi", true);
                    System.out.println("coming in sbi");
                    if (creditcard.getNumRecords() == 0) {
                        recalert = new Alert("Error", "No record Exist", null, AlertType.ERROR);
                        recalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(recalert);
                        return;
                    }

                    byte data[] = new byte[1000];
                    creditcard.getRecord(n, data, 0);

                    String details = new String(data);
                    // System.out.println("first time"+bank.hdfirst[n]);
                    String str = new String();
                    str = details.substring(0, 1);
                    // i = Integer.parseInt(str);
                    System.out.println("value of first" + str);


                    if (str.equals("1") && sbc[n] == 0) {
                        str = "0";
                        first(details);
                        String reps = new String();
                        reps = details.substring(1, details.length());
                        System.out.println("coming b4 adding");
                        // String sint=Integer.toString(i);
                        String modifieddetails = new String(str + reps);
                        byte dat[] = new byte[modifieddetails.length()];
                        dat = modifieddetails.getBytes();
                        creditcard.setRecord(n, dat, 0, dat.length);
                        System.out.println("coming after adding");

                        sbc[n] = 1;
                    }
                    //System.out.println(details);
                    String subdetails = new String();
                    int in = details.indexOf('\n');
                    int lin = details.lastIndexOf('s');
                    subdetails = details.substring(in, lin);
                    carddetail.setString(subdetails);
                    /*  bank = new byte[creditcard.getRecordSize(id[0])];
                    creditcard.getRecord(id[0], bank, 0);
                    bankop = new String(bank);
                    carddetail.setString(bankop);*/
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
                /* bank = new byte[creditcard.getRecordSize(id[2])];
                creditcard.getRecord(id[2], bank, 0);
                bankop = new String(bank);
                carddetail.setString(bankop);*/
            }


        }
        if (command == forgot) {
            if (cardno.getString().equals("")) {
                error = new Alert("Error", "please enter the serial number of card", null, AlertType.ERROR);
                error.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(error);
                return;
            }
            index = creditlist.getSelectedIndex();
            cno = cardno.getString();
            relogin = new relogin(splash, index, cno);
        }
    }
}
