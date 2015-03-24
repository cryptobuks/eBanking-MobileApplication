
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class splash extends MIDlet implements CommandListener  {

    initialoption initialoption;
    private Display display;
    private Form splash;
    private Image splashimage;
    private ImageItem splashitem;

    public splash() {

       // splash=new Form("");
        //splash.setCommandListener(this);
        try{
      //    splashimage=Image.createImage("mwallet.png");
    //      splashitem=new ImageItem(null,splashimage,ImageItem.LAYOUT_DEFAULT,"splash image");
  //        splash.append(splashimage);
//splash.wait(1);
//splash.notify();

      initialoption=new initialoption(this);
        }
        catch(Exception e){

        }

    }

    public void commandAction(Command c, Displayable d) {

    }

   public void destroyApp(boolean unconditional)  {

    }

    public void pauseApp() {

    }

    public void startApp()  {
         display = Display.getDisplay(this);
        /* display.setCurrent(new canvas());
        try {

            Thread.sleep(2000);
             login=new login(this);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }*/
        


}
}
