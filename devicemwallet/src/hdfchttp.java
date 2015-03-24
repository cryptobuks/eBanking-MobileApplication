
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

public class hdfchttp implements Runnable{

    Display display;
    device device;
    String ans;
    HttpConnection http = null;
    OutputStream oStrm = null;

    public hdfchttp(device device, String ans) {
        this.device = device;
        this.ans = ans;
        Thread t = new Thread(device);
        t.start();

    }

    public void run() {
        HttpConnection hc = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            String message = ans+"%21";
            String url = "http://59.181.113.78:8084/simple/PostServlet";
            hc = (HttpConnection) Connector.open(url);
            hc.setRequestMethod(HttpConnection.POST);
            hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            hc.setRequestProperty("Content-Length", Integer.toString(message.length()));
            out = hc.openOutputStream();
            out.write(message.getBytes());
            System.out.println("msg sent");
            
        } catch (IOException ioe) {
            
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (hc != null) {
                    hc.close();
                }
            } catch (IOException ioe) {
            }
        }
    }
}
