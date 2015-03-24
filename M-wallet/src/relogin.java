
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class relogin implements CommandListener {

    public String cno;
    creditcard c;
    int index;
    int count = 1;
    repassword repassword;
    private Display display;
    private Form reloginform;
    private Command login;
    private Alert ualert, lalert, palert, openalert, error, passalert;
    private Command back;
    private TextField username, password;
    private option opt;
    RecordStore rlogin = null;
    private RecordEnumeration recenu = null;
    private splash splash;
    initialoption initialoption;
    int uid[] = new int[3];
    static int countatt = 0;

    public relogin(splash splash, int index, String cno) {
        this.index = index;
        this.splash = splash;
        this.cno = cno;
        back = new Command("Back", Command.BACK, 1);
        login = new Command("Login", Command.OK, 2);
        username = new TextField("User name", "", 30, TextField.ANY);
        password = new TextField("Password", "", 30, TextField.PASSWORD);
        reloginform = new Form("Login");
        reloginform.append(username);
        reloginform.append(password);
        reloginform.addCommand(back);
        reloginform.addCommand(login);

        Display.getDisplay(splash).setCurrent(reloginform);
        reloginform.setCommandListener(this);


        //outputstream.reset();



    }

    public boolean validateuser(String username, String password) {
        if (username.equals("")) {
            ualert = new Alert("Warning", "Enter User Name", null, AlertType.WARNING);
            AlertType.WARNING.playSound(display);
            ualert.setTimeout(Alert.FOREVER);
            display.setCurrent(ualert, reloginform);
            System.out.println("hiee");
            return false;
        }
        if (password.equals("")) {
            palert = new Alert("Warning", "Enter Password", null, AlertType.WARNING);
            AlertType.WARNING.playSound(display);
            palert.setTimeout(Alert.FOREVER);
            display.setCurrent(palert, reloginform);
            System.out.println("hiee");
            return false;
        }
        if (username.equals("") && password.equals("")) {
            //optionform=new Form("option");
            lalert = new Alert("Invalid login", "please provide valid data", null, AlertType.ERROR);
            lalert.setTimeout(Alert.FOREVER);
            display.setCurrent(lalert, reloginform);
            System.out.println("hiee");
            //transaction trans = new transaction();
            //trans.startApp();

            return false;

        } else {
            return true;
        }
    }

    public void commandAction(Command command, Displayable d) {
        if (command == back) {
            c = new creditcard(splash);
        }
        if (command == login) {
            try {
                if (countatt == 3) {
                    error = new Alert("Error", "You have Exceeded minimum number of attempts\n Please Contact admin", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }
                rlogin = RecordStore.openRecordStore("ulogin", true);
                System.out.println("opening");
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
            String user, pass, u, p;
            boolean valid = validateuser(username.getString(), password.getString());
            if (valid == false) {
                return;
            }
            try {


                // System.out.println("coming in try     "+rlogin.getNumRecords());
                for (int x = 0; x <= 3; x++) {
                    byte[] buser = new byte[200];
                    System.out.println("coming test");
                    //user = inputdatastream.readUTF();
                    rlogin.getRecord(count, buser, index);
                    count++;
                    System.out.println("coming after reading record");
                    user = new String(buser);
                    //System.out.println(user);
                    int uindex = user.indexOf(" ");
                    String ruser = user.substring(0, uindex);
                    System.out.println("checking entrance");
                    String puser = user.substring(uindex + 1, user.length());
                    System.out.println("password  " + puser);
                    System.out.println("username" + ruser);
                    String us = username.getString();
                    String ps = password.getString();
                    System.out.println("coming after String");
                    us = us.trim();

                    ps = ps.trim();
                    ruser = ruser.trim();
                    puser = puser.trim();
                    if (us.equals(ruser) && ps.equals(puser)) {
                        System.out.println("coming in if");
                        repassword = new repassword(splash, index, cno);
                        break;

                    } else {
                        passalert = new Alert("Error", "Invalid password", null, AlertType.ERROR);
                        passalert.setTimeout(Alert.FOREVER);
                        Display.getDisplay(splash).setCurrent(passalert);
                        us = "";
                        ps = "";
                        countatt++;


                    }
                    //inputstream.reset();
                }

            } catch (Exception e) {
                error = new Alert("Error", e.getMessage(), null, AlertType.INFO);
                System.out.println("errrrr");
                error.setTimeout(2000);
                //Display.getDisplay(splash).setCurrent(error);
            }
        }
    }
}
