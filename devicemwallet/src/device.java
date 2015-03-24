
import javax.microedition.midlet.*;
import javax.bluetooth.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;
import java.util.*;
import javax.wireless.messaging.*;

public class device extends MIDlet implements CommandListener, Runnable {

    private StreamConnectionNotifier con;
    private Form list;
    Vector conections, inputs;
    Thread t;
    private Command cmd_exit;
    private Command cmd_back;
    hdfchttp hdfchttp;
    icicihttp icicihttp;
    sbihttp sbihttp;
    MessageConnection clientConn;
    Alert alert;

    public device() {

        cmd_exit = new Command("exit", Command.EXIT, 1);


        conections = new Vector();
        inputs = new Vector();

    }

    public void startApp() {
        t = new Thread(this);
        t.start();
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {

        if (c == cmd_exit) {
            this.destroyApp(true);
            this.notifyDestroyed();
        }
    }

    private void createConnections() {

        UUID uuid = new UUID(0x0009);
        LocalDevice localDevice;
        try {
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);
            con = (StreamConnectionNotifier) Connector.open("btspp://localhost:" + uuid
                    + ";name=batalha_emulator;authorize=false");


            StreamConnection conn = con.acceptAndOpen();
            DataInputStream in = conn.openDataInputStream();

            conections.addElement(conn);
            inputs.addElement(in);

        } catch (Exception e) {
            System.out.println("lancei exceptions!");
        }
    }

    public String receiveMessageFromAll() {
        String answer ="";
        for (int i = 0; i < inputs.size(); i++) {
            try {
                DataInputStream input = (DataInputStream) inputs.elementAt(i);
                answer += input.readUTF();
            } catch (IOException e) {
            }
        }
        return answer.trim();
    }

    public void run() {
        Form messageForm = new Form("");
        messageForm.append("Waiting...");
        messageForm.addCommand(cmd_exit);
        Display.getDisplay(this).setCurrent(messageForm);

        createConnections();
        messageForm.deleteAll();
        String ans = receiveMessageFromAll();
        messageForm.append(ans);
        Display.getDisplay(this).setCurrent(messageForm);
        int i = ans.lastIndexOf('m');
        String ids = ans.substring(i + 1, i + 2);
        System.out.println("bank bit" + ids);
        int id = Integer.parseInt(ids);
        if (id == 1) {
            HttpConnection hc = null;
            InputStream in = null;
            OutputStream out = null;
            try {
                String message = "string=" + ans + "%21";
                String url = "http://59.181.113.78:8084/simpl/PostServlet";
                hc = (HttpConnection) Connector.open(url);
                hc.setRequestMethod(HttpConnection.POST);
                hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                hc.setRequestProperty("Content-Length", Integer.toString(message.length()));
                out = hc.openOutputStream();
                out.write(message.getBytes());
                System.out.println("msg sent");

                in = hc.openInputStream();
                int length = (int) hc.getLength();
                byte[] data = new byte[length];
                in.read(data);
                String response = new String(data);
                System.out.println("received" + response);
                
                int ind = response.lastIndexOf('?');
                String no = response.substring(0, ind);
                String msg = response.substring(ind + 1, response.length());
                try {
                clientConn = (MessageConnection) Connector.open("sms://" + no);
                } catch (Exception e) {
                alert = new Alert("Alert");
                alert.setString("Unable to connect to Station because of network problem");
                alert.setTimeout(2000);
                Display.getDisplay(this).setCurrent(alert);
                }
                try {
                TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                textmessage.setAddress("sms://" + no);
                textmessage.setPayloadText(msg);
                clientConn.send(textmessage);
                } catch (Exception e) {
                alert = new Alert("Alert", "", null, AlertType.INFO);
                alert.setTimeout(Alert.FOREVER);
                alert.setString("Unable to send");
                Display.getDisplay(this).setCurrent(alert);
                }



            } catch (Exception ioe) {
                ioe.printStackTrace();
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
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            t.run();
        } else if (id == 2) {

            HttpConnection hc = null;
            InputStream in = null;
            OutputStream out = null;
            try {
                String message = "string=" + ans + "%21";
                String url = "http://59.181.113.78:8084/simpl/PostServlet";
                hc = (HttpConnection) Connector.open(url);
                hc.setRequestMethod(HttpConnection.POST);
                hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                hc.setRequestProperty("Content-Length", Integer.toString(message.length()));
                out = hc.openOutputStream();
                out.write(message.getBytes());
                System.out.println("msg sent");
                Thread.sleep(9000);
                in = hc.openInputStream();
                int length = (int) hc.getLength();
                byte[] data = new byte[length];

                in.read(data);
                String response = new String(data);
                System.out.println("received" + response);
                int ind = response.lastIndexOf('?');
                String no = response.substring(0, ind);
                String msg = response.substring(ind + 1, response.length());
                try {
                    clientConn = (MessageConnection) Connector.open("sms://" + no);
                } catch (Exception e) {
                    alert = new Alert("Alert");
                    alert.setString("Unable to connect to Station because of network problem");
                    alert.setTimeout(2000);
                    Display.getDisplay(this).setCurrent(alert);
                }
                try {
                    TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                    textmessage.setAddress("sms://" + no);
                    textmessage.setPayloadText(msg);
                    clientConn.send(textmessage);
                } catch (Exception e) {
                    alert = new Alert("Alert", "", null, AlertType.INFO);
                    alert.setTimeout(Alert.FOREVER);
                    alert.setString("Unable to send");
                    Display.getDisplay(this).setCurrent(alert);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
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
            t.run();
        } else if (id == 3) {
            HttpConnection hc = null;
            InputStream in = null;
            OutputStream out = null;
            try {
                String message = "string=" + ans + "%21";
                String url = "http://59.181.113.78:8084/simpl/PostServlet";
                hc = (HttpConnection) Connector.open(url);
                hc.setRequestMethod(HttpConnection.POST);
                hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                hc.setRequestProperty("Content-Length", Integer.toString(message.length()));
                out = hc.openOutputStream();
                out.write(message.getBytes());
                System.out.println("msg sent");
                Thread.sleep(9000);
                in = hc.openInputStream();
                int length = (int) hc.getLength();
                byte[] data = new byte[length];
                in.read(data);
                String response = new String(data);
                System.out.println("received" + response);
                int ind = response.lastIndexOf('?');
                String no = response.substring(0, ind);
                String msg = response.substring(ind + 1, response.length());
                try {
                    clientConn = (MessageConnection) Connector.open("sms://" + no);
                } catch (Exception e) {
                    alert = new Alert("Alert");
                    alert.setString("Unable to connect to Station because of network problem");
                    alert.setTimeout(2000);
                    Display.getDisplay(this).setCurrent(alert);
                }
                try {
                    TextMessage textmessage = (TextMessage) clientConn.newMessage(MessageConnection.TEXT_MESSAGE);
                    textmessage.setAddress("sms://" + no);
                    textmessage.setPayloadText(msg);
                    clientConn.send(textmessage);
                } catch (Exception e) {
                    alert = new Alert("Alert", "", null, AlertType.INFO);
                    alert.setTimeout(Alert.FOREVER);
                    alert.setString("Unable to send");
                    Display.getDisplay(this).setCurrent(alert);
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();
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
            t.run();
        }

    }
}
