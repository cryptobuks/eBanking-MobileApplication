
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class server {

    public static void main(String args[]) {
        //String msg = new String("a1234123412341p9999999999");

        String clientSentence;
        String p, a, mid = new String(), cname = null, branch = null, cadd = null, creditcardno = null, balance = null, cvc = null, cdob = null, expirydate = null, issuedate = null, response;
        int flag = 0;
        Date dob, exdate, idate;
        ServerSocket welcomesocket = null;
        String accno = new String("");
        String phno = new String("");
        try {
            welcomesocket = new ServerSocket(24000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        while (true) {
            try {
                System.out.println("1");
                Socket connectionSocket = welcomesocket.accept();
                System.out.println("accepted");
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                clientSentence = inFromClient.readLine();
                System.out.println(clientSentence);
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                Connection con = DriverManager.getConnection("jdbc:odbc:connect", "scott", "tiger");
                System.out.println("coming after connection");
                Statement st = con.createStatement();
                int i = clientSentence.lastIndexOf('p');
                int l = clientSentence.length();
                p = clientSentence.substring(i + 1, l);
                a = clientSentence.substring(0, i);
                p = p.trim();
                a = a.trim();
                System.out.println(p);
                System.out.println(a);



                ResultSet rs = st.executeQuery("select * from hdfc");
                while (rs.next()) {
                    accno = rs.getString("accno");
                    phno = rs.getString("phno");
                    System.out.println("" + accno + "" + phno);
                    if (accno.equals(a) && phno.equals(p)) {
                        flag = 1;
                        break;
                    }
                }
                System.out.println("" + flag);
                rs.close();
                if (flag == 1) {

                    ResultSet rs1 = st.executeQuery(" select * from hdfc where accno="+a);
                    while (rs1.next()) {
                        mid = rs1.getString("mid");
                        cname = rs1.getString("cname");
                        branch = rs1.getString("branch");
                        dob = rs1.getDate("cdob");
                        cdob = dob.toString();
                        cadd = rs1.getString("cadd");
                        creditcardno = rs1.getString("creditcardno");
                        exdate = rs1.getDate("exiprydate");
                        expirydate = exdate.toString();
                        idate = rs1.getDate("issuedate");
                        issuedate = idate.toString();
                        cvc = rs1.getString("cvc");
                        balance = rs1.getString("balance");

                    }
                }
                System.out.println("cname=" + cname);
                System.out.println("" + mid);
                response = new String(flag + ")" + cname + "!" + branch + "@" + cdob + "#" + cadd + "$" + creditcardno + "%" + expirydate + "^" + issuedate + "&" + cvc + "*" + balance + "(" + mid + "\n");
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeUTF(response);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}