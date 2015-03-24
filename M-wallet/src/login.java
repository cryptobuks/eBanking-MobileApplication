
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class login implements CommandListener {

    private Display display;
    private Form loginform;
    private Command login;
    private Alert ualert, lalert, palert, openalert, error, passalert;
    private Command back;
    private TextField username, password;
    private option opt;
    public RecordStore rlogin = null;
    private RecordEnumeration recenu = null;
    private splash splash;
    initialoption initialoption;
    int uid[] = new int[3];
    static int count = 0;

    public login(splash splash) {
        this.splash = splash;
        // display.getDisplay(this).setCurrent(splash);
        back = new Command("Back", Command.BACK, 1);
        login = new Command("Login", Command.OK, 2);
        username = new TextField("User name", "", 30, TextField.ANY);
        password = new TextField("Password", "", 30, TextField.PASSWORD);
        loginform = new Form("Login");
        loginform.append(username);
        loginform.append(password);
        loginform.addCommand(back);
        loginform.addCommand(login);
        loginform.setCommandListener(this);

        Display.getDisplay(splash).setCurrent(loginform);
        System.out.println("helloo");
        try {
            //System.out.println("");
            //rlogin.closeRecordStore();
            //RecordStore.deleteRecordStore("ulogin");
            rlogin = RecordStore.openRecordStore("ulogin", true);
            // rlogin.closeRecordStore();
            //RecordStore.deleteRecordStore("ulogin");
            byte[] outputrecord;
            String uname[] = new String[3];
            uname[0] = "neel neel";
            uname[1] = "vinit vinit";
            uname[2] = "khush khush";

            //ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            //DataOutputStream outputdatastream = new DataOutputStream(outputstream);

            for (int x = 0; x < uname.length; x++) {
                //outputdatastream.writeUTF(uname[x]);
                //outputdatastream.writeUTF(pass[x]);
                //outputdatastream.flush();
                //outputrecord = outputstream.toByteArray();
                byte data[] = uname[x].getBytes();

                uid[x] = rlogin.addRecord(data, 0, uname[x].length());

                //outputstream.reset();

            }
            System.out.println("comingg");
            //outputstream.close();
            //outputdatastream.close();
        } catch (Exception e) {
            Alert error = new Alert("Error", e.getMessage(), null, AlertType.INFO);
            error.setTimeout(2000);
            display.setCurrent(error);
        }
    }

    public boolean validateuser(String username, String password) {
        if (username.equals("")||(username.equals("")&&!password.equals(""))) {
            ualert = new Alert("Warning", "Enter User Name", null, AlertType.ERROR);
           
            ualert.setTimeout(Alert.FOREVER);
            Display.getDisplay(splash).setCurrent(ualert, loginform);
            System.out.println("hiee");
            return false;
        }
        if (password.equals("")||(!username.equals("")&&password.equals(""))) {
            palert = new Alert("Warning", "Enter Password", null, AlertType.ERROR);
            
            palert.setTimeout(Alert.FOREVER);
            Display.getDisplay(splash).setCurrent(palert, loginform);
            System.out.println("hiee");
            return false;
        }
        if (username.equals("") && password.equals("")) {
            //optionform=new Form("option");
            lalert = new Alert("Invalid login", "please provide valid data", null, AlertType.ERROR);
            lalert.setTimeout(Alert.FOREVER);
            Display.getDisplay(splash).setCurrent(lalert, loginform);
            System.out.println("hiee");
            //transaction trans = new transaction();
            //trans.startApp();

            return false;

        } else {
            return true;
        }
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

            String user, pass, u, p;
            boolean valid = validateuser(username.getString(), password.getString());
            if (valid == false) {
                return;
            } else {
                try {



                    for (int x = 0; x <= rlogin.getNumRecords(); x++) {
                        byte[] buser = new byte[rlogin.getRecordSize(uid[x])];
                        //user = inputdatastream.readUTF();
                        rlogin.getRecord(uid[x], buser, 0);
                        user = new String(buser);
                        //System.out.println(user);
                        int uindex = user.indexOf(" ");
                        String ruser = user.substring(0, uindex);
                        //System.out.println(ruser);
                        String puser = user.substring(uindex + 1, user.length());
                        System.out.println(puser);
                        String us = username.getString();
                        String ps = password.getString();
                        if (us.equals(ruser) && ps.equals(puser)) {

                            opt = new option(splash);
                            break;

                        } else {
                            passalert = new Alert("Error", "Invalid password", null, AlertType.ERROR);
                            passalert.setTimeout(Alert.FOREVER);
                            Display.getDisplay(splash).setCurrent(passalert);

                            count++;
                            return;
                        }
                        //inputstream.reset();
                    }

                } catch (Exception e) {
                    error = new Alert("Error", e.getMessage(), null, AlertType.INFO);
                    //System.out.println("errrrr");
                    error.setTimeout(2000);
                    //Display.getDisplay(splash).setCurrent(error);
                }


            }
        }
    }
}
