package jp.coolfactory.data.server;

import jp.coolfactory.data.controller.AdAppController;
import jp.coolfactory.data.controller.AdCommandController;
import jp.coolfactory.data.controller.AdParamMapController;
import jp.coolfactory.data.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by wangqi on 22/2/2017.
 */
@WebServlet(name = "ReloadServlet", urlPatterns = {"/reload"})
public class ReloadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        StringBuilder buf = new StringBuilder(200);
        String controller = request.getParameter("controller");
        if (StringUtil.isNotEmptyString(controller)) {
            if ( "all".equals(controller) ) {
                AdParamMapController.getInstance().reload();
                buf.append("AdParamMapController reloaded! \r\n");
                AdAppController.getInstance().reload();
                buf.append("AdAppControler reloaded! \r\n");
                AdCommandController.getInstance().reload();
                buf.append("AdCommandController reloaded! \r\n");
            } else if ( "adparam".equals(controller) ) {
                AdParamMapController.getInstance().reload();
                buf.append("AdParamMapController reloaded! \r\n");
            } else if ( "adapp".equals(controller) ) {
                AdAppController.getInstance().reload();
                buf.append("AdAppController reloaded! \r\n");
            } else if ( "adcmd".equals(controller) ) {
                AdCommandController.getInstance().reload();
                buf.append("AdCommandController reloaded! \r\n");
            }
        }
        buf.append("OK");
        response.getWriter().print(buf.toString());
        response.getWriter().close();
    }
}
