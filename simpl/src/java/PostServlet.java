
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet(name = "PostServlet", urlPatterns = {"/PostServlet"})
public class PostServlet extends HttpServlet {

    Thread t;
    String message;
    String phone;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long tid = 0;
        String msg1 = request.getParameter("string");
        String msg = new String(msg1);
        System.out.println(request.getRemoteAddr());
        System.out.println(msg.substring(0, msg.length() - 1));
        System.out.println(msg);
        String sh, sb, b, m, a;
        int flag = 0;
        String accno = new String("");
        String saccno = new String("");
        String sname = new String("");
        String no = new String("");
        String cno1, cno2;
        String shopname = null;
        Calendar cal = Calendar.getInstance();
        java.util.Date time = cal.getTime();
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdbc:odbc:connect", "scott", "tiger");
            Statement st = con.createStatement();
            //int d = msg.lastIndexOf('p');
            int i = msg.lastIndexOf('b');
            int j = msg.lastIndexOf('m');
            int k = msg.lastIndexOf('a');
            System.out.println(k);

            int l = msg.length() - 1;
            sh = msg.substring(0, 2);
            sh = sh.trim();
            sb = msg.substring(i + 1, i + 2);
            sb = sb.trim();
            b = msg.substring(j + 1, j + 2);
            b = b.trim();
            m = msg.substring(j + 2, k);
            m = m.trim();

            a = msg.substring(k + 1, l);
            a = a.trim();

            System.out.println("sh=" + sh);
            System.out.println("m=" + m);
            System.out.println("b=" + b);
            System.out.println("sb=" + sb);
            System.out.println("a=" + a);

            if (b.equals("1")) {
                System.out.println("coming in 1st");
                ResultSet rs = st.executeQuery("select accno,balance,phno,creditcardno from hdfc where mid =" + m);
                while (rs.next()) {
                    accno = rs.getString("accno");
                    long phno = rs.getLong("phno");
                    phone = Long.toString(phno);
                    no = rs.getString("creditcardno");
                }
                st.execute("update hdfc set balance=balance-" + a + "where accno=" + accno);
                System.out.println("coming after update");
                st.execute("update hdfccust set balance=balance-" + a + "where accno=" + accno);

                flag = 0;
            }
            if (b.equals("2")) {

                ResultSet rs = st.executeQuery("select accno,balance,phno,creditcardno from icici where mid =" + m);
                while (rs.next()) {
                    accno = rs.getString("accno");
                    long phno = rs.getLong("phno");
                    phone = Long.toString(phno);
                    no = rs.getString("creditcardno");
                }
                st.execute("update icici set balance=balance-" + a + "where accno=" + accno);
                st.execute("update icicicust set balance=balance-" + a + "where accno=" + accno);
                flag = 0;
            }
            if (b.equals("3")) {
                ResultSet rs = st.executeQuery("select accno,balance,phno,creditcardno from sbi where mid =" + m);
                while (rs.next()) {
                    accno = rs.getString("accno");
                    long phno = rs.getLong("phno");
                    phone = Long.toString(phno);
                    no = rs.getString("creditcardno");
                }
                st.execute("update sbi set balance=balance-" + a + "where accno=" + accno);
                st.execute("update sbicust set balance=balance-" + a + "where accno=" + accno);
                flag = 1;
            }

            if (sb.equals("1")) {
                ResultSet rs = st.executeQuery("select balance,accno,sid from hdfcsacc where sid ='" + sh + "'");
                while (rs.next()) {
                    saccno = rs.getString("accno");
                    sname = rs.getString("sid");
                    System.out.println(sname);
                }
                st.execute("update hdfcsacc set balance=balance+" + a + "where accno=" + saccno);
                st.execute("update hdfccust set balance=balance+" + a + "where accno=" + saccno);
                flag = 1;
            }

            if (sb.equals("2")) {
                System.out.println("coming in sb");
                ResultSet rs = st.executeQuery("select accno,balance,sid from icicisacc where sid ='" + sh + "'");
                System.out.println("coming after select");
                while (rs.next()) {
                    System.out.println("coming in while");
                    saccno = rs.getString("accno");
                    sname = rs.getString("sid");
                    saccno = saccno.trim();
                }
                st.execute("update icicisacc set balance=balance+" + a + "where accno=" + saccno);
                st.execute("update icicicust set balance=balance+" + a + "where accno=" + saccno);
                flag = 1;
            }

            if (sb.equals("3")) {
                ResultSet rs = st.executeQuery("select accno from sbisacc where sid = '" + sh + "'");
                while (rs.next()) {
                    saccno = rs.getString("accno");
                    sname = rs.getString("sid");
                }
                st.execute("update sbisacc set balance=balance+" + a + "where accno=" + saccno);
                st.execute("update sbicust set balance=balance+" + a + "where accno=" + saccno);
                flag = 1;
            }
            cno1 = no.substring(0, 1);
            cno2 = no.substring(no.length() - 3, no.length());
            if (sname.equals("p1")) {
                shopname = "pantaloons";
            }
            if (sname.equals("s1")) {
                shopname = "shopper stop";
            }
            if (sname.equals("l1")) {
                shopname = "life style";
            }

            if (flag == 1) {
                ResultSet rs2 = st.executeQuery("select tid from transaction where tid in(select max(tid) from transaction)");
                while (rs2.next()) {
                    tid = rs2.getLong("tid");
                    tid = tid + 1;
                }

                st.execute("insert into transaction values(" + m + "," + accno + "," + saccno + "," + a + ",SYSDATE,1," + tid + ")");
                message = phone + "?Dear Customer,Thank You of INR " + a + " using creditcard " + cno1 + "xxx" + cno2 + " is made at " + shopname + " on " + time+" Transaction id : "+tid;
            }
            if (flag == 0) {
                st.execute("insert into transaction values(" + m + "," + accno + "," + saccno + "," + a + ",SYSDATE,0)");
                message = phone + "?unsuccessful";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        response.setContentType("text/plain");
        response.setContentLength(message.length());
        PrintWriter out = response.getWriter();
        out.println(message);

        try {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
