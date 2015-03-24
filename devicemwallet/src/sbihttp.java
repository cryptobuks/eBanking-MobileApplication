
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

public class sbihttp{

    Display display;
    device device;
    String ans;
    HttpConnection http = null;
    OutputStream oStrm = null;

    public sbihttp(device device, String ans) {
        this.device = device;
        this.ans = ans;

        httppost();
    }

    private void httppost() {


        // Data is passed as a separate stream for POST (below)
        //String url = "https://192.68.0.1:8080";
        String url = "https://localhost:8080";

        try {
            http = (HttpConnection) Connector.open(url);
            oStrm = http.openOutputStream();

            //----------------
            // Client Request
            //----------------
            // 1) Send request type
            http.setRequestMethod(HttpConnection.POST);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte data[] = ans.getBytes();
            oStrm.write(data);
            device=new device();


        } catch (Exception e) {
        } finally {
            if (oStrm != null) {
                try {
                    oStrm.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (http != null) {
                try {
                    http.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}
