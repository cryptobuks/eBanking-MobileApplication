
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class repassword implements CommandListener {

    private Display display;
    String cno;
    private Form verifyform;
    private Command verify,back;
    option opt;
    int index;
    splash splash;
    Alert error;
    private TextField accountno;
    String selected = new String();
    RecordStore selbank = null;

    public repassword(splash splash, int index, String cno) {
        this.index = index;
        this.cno = cno;
        this.splash = splash;
        verify = new Command("Verify", Command.OK, 0);
        back=new Command("back",Command.BACK,1);
        System.out.println("coming in repassword");
        verifyform = new Form("Enter your verification details");
        Display.getDisplay(splash).setCurrent(verifyform);
        verifyform.setCommandListener(this);
        try {
            if (index == 0) {
                selected = "hdfc";
                selbank = RecordStore.openRecordStore("hdfc", true);
                System.out.println("hdfc opened");
            }
            if (index == 1) {
                selected = "icici";
                selbank = RecordStore.openRecordStore("icici", true);

            }
            if (index == 2) {
                selected = "sbi";
                selbank = RecordStore.openRecordStore("sbi", true);
            }
        } catch (Exception e) {
        }
        accountno = new TextField("Enter Account no. of   " + selected + "  card no.   " + cno, "", 30, TextField.NUMERIC);
        verifyform.addCommand(verify);
         verifyform.addCommand(back);
        verifyform.append(accountno);
        

    }

    public void commandAction(Command command, Displayable displayable) {
        if (command == verify) {
            System.out.println("coming in verify");
            byte data[] = new byte[500];
            System.out.println("coming before conversion");
            cno=cno.trim();
            int in = Integer.parseInt(cno);
            System.out.println("coming after conversion");
            try {
                data=selbank.getRecord(in);
                String details = new String(data);
                System.out.println(details);
                int insub = details.indexOf("\n");
                String acc = details.substring(5, insub);
                System.out.println("received acc no"+acc);
                String a = new String();
                a = accountno.getString();
                if (a.equals(acc)) {
                    String s = new String();
                    s = details.substring(1, 5);
                    Alert firstalert = new Alert("Password Information", s, null, AlertType.INFO);
                    firstalert.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(firstalert);
                }
                else{
                    error = new Alert("Error", "Account number is not valid", null, AlertType.ERROR);
                    error.setTimeout(Alert.FOREVER);
                    Display.getDisplay(splash).setCurrent(error);
                    return;
                }
            } catch (Exception e) {
            }
        }
        if(command==back){
            opt=new option(splash);
        }
    }
}
