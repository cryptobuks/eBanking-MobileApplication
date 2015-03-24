
import javax.microedition.lcdui.*;

public class initialoption implements CommandListener {

    public splash splash;
    private List menu;
    private Command exit, select;
    private banklogin banklogin;
    private login login;

    public initialoption(splash splash) {

        this.splash = splash;
        menu = new List("select option", List.IMPLICIT);
        exit = new Command("Exit", Command.EXIT, 1);
        select = new Command("Select", Command.OK, 2);
        Display.getDisplay(splash).setCurrent(menu);
        menu.append("Customer Login", null);
        menu.append("Bank Login", null);

        menu.addCommand(exit);
        menu.addCommand(select);
        menu.setCommandListener(this);

    }

    public void commandAction(Command command, Displayable displayable) {

        if (command == List.SELECT_COMMAND || command == select) {
            String selection = menu.getString(menu.getSelectedIndex());
            if (selection.equals("Customer Login")) {
                login = new login(splash);
            }
            if (selection.equals("Bank Login")) {
                banklogin = new banklogin(splash);
            }

        }
        if (command == exit) {
            splash.destroyApp(true);
            splash.notifyDestroyed();
        }

    }
}
