
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import java.io.*;

public class banklogin implements CommandListener {

    bank bank;
    public String user, pass, u, p, ruser, puser;
    int uindex, pindex, enter = 0;
    private Display display;
    private Form bankloginform;
    private Command login, back;
    private Alert ualert, lalert, palert, openalert, passalert, error;
    public TextField username, password;
    private RecordStore blogin;
    splash splash;
    private initialoption initialoption;
    int id[] = new int[3];
    static int count = 0;

    public banklogin(splash splash) {
        this.splash = splash;
        login = new Command("Login", Command.OK, 1);
        back = new Command("Back", Command.BACK, 2);
        bankloginform = new Form("Bank Login");
        username = new TextField("Bank Code", "", 30, TextField.ANY);
        password = new TextField("Password", "", 30, TextField.PASSWORD + TextField.NUMERIC);
        display = Display.getDisplay(splash);
        bankloginform.append(username);
        bankloginform.append(password);
        bankloginform.addCommand(back);
        bankloginform.addCommand(login);
        bankloginform.setCommandListener(this);
        display.getDisplay(splash).setCurrent(bankloginform);
        try {

            blogin = RecordStore.openRecordStore("blogin", true);

            byte[] outputrecord;
            String bankcode[] = new String[3];

            bankcode[0] = "hdfc 123";
            bankcode[1] = "icici 456";
            bankcode[2] = "sbi 789";


            //ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            //DataOutputStream outputdatastream = new DataOutputStream(outputstream);

            for (int x = 0; x < bankcode.length; x++) {
                //outputdatastream.writeUTF(bankcode[x]);
                byte data[] = bankcode[x].getBytes();

                // outputdatastream.writeUTF(pass[x]);
                //outputdatastream.flush();
                //outputrecord = outputstream.toByteArray();
                id[x] = blogin.addRecord(data, 0, bankcode[x].length());

            }

            //outputstream.close();
            //outputdatastream.close();

        } catch (Exception e) {
            Alert error = new Alert("Error", e.getMessage(), null, AlertType.INFO);
            //System.out.println("hiee");
            error.setTimeout(Alert.FOREVER);
            display.setCurrent(error);
        }
    }

    public boolean validateuser(String username, String password) {
        if (username.equals("")) {
            ualert = new Alert("Warning", "Enter User Name", null, AlertType.WARNING);
            AlertType.WARNING.playSound(display);
            ualert.setTimeout(Alert.FOREVER);
            display.setCurrent(ualert, bankloginform);
            System.out.println("hiee");
            return false;
        }
        if (password.equals("")) {
            palert = new Alert("Warning", "Enter Password", null, AlertType.WARNING);
            AlertType.WARNING.playSound(display);
            palert.setTimeout(Alert.FOREVER);
            display.setCurrent(palert, bankloginform);
            //System.out.println("hiee");
            return false;
        }
        if (username.equals("") && password.equals("")) {
            //optionform=new Form("option");
            lalert = new Alert("Invalid login", "please provide valid data", null, AlertType.ERROR);
            lalert.setTimeout(Alert.FOREVER);
            display.setCurrent(lalert, bankloginform);
            //System.out.println("hiee");
            //transaction trans = new transaction();
            //trans.startApp();

            return false;

        } else {
            return true;
        }
    }

    void bankk() {
        System.out.println("in bank func");
        //   b=new bank(splash);
    }

    public void commandAction(Command command, Displayable displayable) {

        if (command == back) {

            initialoption = new initialoption(splash);

        }
        if (command == login) {

            if (count == 3) {
                error = new Alert("Error", "You have Exceeded minimum number of attempts\n Please Contact admin", null, AlertType.ERROR);
                error.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(error);
                return;
            }
            boolean valid = validateuser(username.getString(), password.getString());
            System.out.println(valid);
            if (valid == false) {
                return;
            } else {
                try {



                    for (int x = 0; x <= blogin.getNumRecords(); x++) {
                        //System.out.println("test");
                        byte[] buser = new byte[blogin.getRecordSize(id[x])];
                        //user = inputdatastream.readUTF();
                        blogin.getRecord(id[x], buser, 0);
                        user = new String(buser);
                        //System.out.println(user);
                        uindex = user.indexOf(" ");
                        ruser = user.substring(0, uindex);
                        //System.out.println(ruser);
                        puser = user.substring(uindex + 1, user.length());
                        System.out.println(puser);

                        u = username.getString();
                        p = password.getString();
                        u = u.trim();
                        p = p.trim();
                        ruser = ruser.trim();
                        puser = puser.trim();
                        if (u.equals(ruser) && p.equals(puser)) {
                            bank b;
                            System.out.println("username:" + ruser);
                            System.out.println("password:" + puser);
                            System.out.println("aavi gayu");
                            enter = 1;
                            if (enter == 1) {
                                b = new bank(splash, u);
                                enter = 0;
                                break;
                            }
                            //b.equals(b);
                            // bankk();

                        } else {
                            passalert = new Alert("Error", "Invalid password", null, AlertType.ERROR);
                            passalert.setTimeout(Alert.FOREVER);
                            Display.getDisplay(splash).setCurrent(passalert);

                            count++;
                            return;
                        }

                        // inputstream.reset();
                    }

                } catch (Exception e) {
                   e.printStackTrace();

                    // bank = new bank(splash);

                }


            }
        }
    }
}
