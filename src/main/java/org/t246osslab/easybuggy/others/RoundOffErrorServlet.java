package org.t246osslab.easybuggy.others;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pmw.tinylog.Logger;
import org.t246osslab.easybuggy.utils.Closer;
import org.t246osslab.easybuggy.utils.MessageUtils;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/roe" })
public class RoundOffErrorServlet extends HttpServlet {

    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = null;
        double number = -1;
        String errorMessage = "";
        try {
            Locale locale = req.getLocale();
            res.setContentType("text/html");
            res.setCharacterEncoding("UTF-8");
            writer = res.getWriter();
            writer.write("<HTML>");
            writer.write("<HEAD>");
            writer.write("<TITLE>" + MessageUtils.getMsg("title.round.off.error.page", locale) + "</TITLE>");
            writer.write("</HEAD>");
            writer.write("<BODY>");
            writer.write("<form action=\"/roe\" method=\"post\">");
            writer.write("<input type=\"text\" name=\"number\" size=\"1\" maxlength=\"1\">");
            writer.write(" - 0.9 = ");
            String strNumber = req.getParameter("number");
            if (strNumber != null) {
                try {
                    number = Integer.parseInt(strNumber);
                } catch (NumberFormatException e) {
                    // ignore
                }
                if (1 <= number && number <= 9) {
                    writer.write(String.valueOf(number - 0.9));
                } else {
                    errorMessage = "<font color=\"red\">" + MessageUtils.getMsg("msg.enter.positive.number", locale)
                            + "</font>";
                }
            }
            writer.write("<br>");
            writer.write("<input type=\"submit\" value=\"" + MessageUtils.getMsg("label.calculate", locale) + "\">");
            writer.write("<br>");
            writer.write(errorMessage);
            writer.write("<br>");
            writer.write(MessageUtils.getMsg("msg.note.enter.one", locale));
            writer.write("</form>");
            writer.write("</BODY>");
            writer.write("</HTML>");
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            Closer.close(writer);
        }
    }
}