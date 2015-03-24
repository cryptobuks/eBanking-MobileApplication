import java.io.IOException;
import javax.microedition.lcdui.*;


public class canvas extends Canvas {
private Image image;
login login;
splash splash;
public canvas(){
	try {
		image = Image.createImage("/splash.png");
	} catch (IOException e) {
		e.printStackTrace();
	}
}

protected void paint(Graphics g) {
  setFullScreenMode(true);
	g.drawImage(image, 15, 15, Graphics.TOP | Graphics.LEFT);
        try{
            Thread.sleep(2000);
        }
        catch(Exception e){
            
        }
    
}
}