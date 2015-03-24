
import javax.microedition.lcdui.*;
import javax.bluetooth.*;

public class amount implements CommandListener {

    splash splash;
    int id, amt;
    String mid;
    Display display;
    TextField amount;
    Command connect;
    Form amountform;
    Alert amountalert;
    selectstore selstore;
    private ChoiceGroup shoplist;
    private String[] shop = new String[3];
    UUID uuid;
    int n;
    String shopname;

    public amount(splash splash, String mid, int amt, int n) {
        this.splash = splash;
        this.mid = mid;
        this.amt = amt;
        this.n = n;
        shop[0] = "Pantaloons";
        shop[1] = "ShopperStop";
        shop[2] = "Life Style";
        shoplist = new ChoiceGroup("Select Choice", Choice.POPUP, shop,
                null);
        amountform = new Form("Enter Amount");
        amount = new TextField("Enter amount", "", 30, TextField.NUMERIC);

        connect = new Command("Connect", Command.OK, 0);
        amountform.append(shoplist);
        amountform.append(amount);
        amountform.addCommand(connect);
        amountform.setCommandListener(this);
        Display.getDisplay(splash).setCurrent(amountform);
    }

    public void commandAction(Command command, Displayable d) {
        if (command == connect) {

            String s = amount.getString();
            int a = Integer.parseInt(s);

            if (amount.getString().equals("")) {
                amountalert = new Alert("Error", "Enter amount", null, AlertType.ERROR);
                amountalert.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(amountalert, amountform);
            }
            if (amt < a) {
                amountalert = new Alert("Error", "Not sufficient amount in your card", null, AlertType.ERROR);
                amountalert.setTimeout(Alert.FOREVER);
                Display.getDisplay(splash).setCurrent(amountalert, amountform);

            }
            switch (shoplist.getSelectedIndex()) {
                case 0:
                    shopname="p1b2";
                    uuid = new UUID(0x0009);
                    break;
                case 1:
                     shopname="s1b1";
                    uuid = new UUID(0x00010);
                    break;
                case 2:
                     shopname="l1b1";
                    uuid = new UUID(0x00011);

            }
            String amountstr=amount.getString();
            int amo=Integer.parseInt(amountstr);
            selstore = new selectstore(splash, mid, uuid, n,amo,shopname);

        }

    }
}
