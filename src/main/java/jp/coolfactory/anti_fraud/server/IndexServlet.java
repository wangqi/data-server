package jp.coolfactory.anti_fraud.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wangqi on 1/12/2016.
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"/"})
public class IndexServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> maps = request.getParameterMap();
        StringBuilder buf = new StringBuilder(200);
        for (String paramName : maps.keySet()) {
            buf.append(paramName).append(":").append(Arrays.toString(maps.get(paramName))).append("\n");
        }
        if (buf.length()==0) {
            buf.append("OK");
        }
        response.getWriter().print(buf.toString());
        response.getWriter().close();
    }

}
