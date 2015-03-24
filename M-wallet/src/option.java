
import javax.microedition.lcdui.*;

public class option implements CommandListener {

    Display display;
    public splash splash;
    private List menu;
    private Command exit, select;
    private transaction trans;
    private creditcard card;
    private license li;
    initialoption in;

    option(splash splash) {

        this.splash = splash;
        menu = new List("select option", List.IMPLICIT);
        exit = new Command("Logout", Command.EXIT, 1);
        select = new Command("Select", Command.OK, 2);
        System.out.println("before display");


        menu.append("Perform Transaction", null);
        menu.append("View Credit Card Details", null);
        menu.append("License", null);
        menu.addCommand(exit);
        menu.addCommand(select);
        Display.getDisplay(splash).setCurrent(menu);
        System.out.println("after display");
        menu.setCommandListener(this);

        System.out.println("after here");

    }

    public void commandAction(Command command, Displayable displayable) {

        if (command == List.SELECT_COMMAND || command == select) {
            String selection = menu.getString(menu.getSelectedIndex());
            if (selection.equals("Perform Transaction")) {
                trans = new transaction(splash);
            }
            if (selection.equals("View Credit Card Details")) {
                card = new creditcard(splash);
            }
           
            if (selection.equals("License")) {
                li = new license(splash);
            }
        }
        if (command == exit) {
            in=new initialoption(splash);
        }

    }
}
