<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.io.*,java.util.*, java.net.*" %>
<%@ page import="com.sun.deploy.net.*" %>
<%@ page import="jp.coolfactory.data.s2s.TalkingDataTrackingLink" %>
<%@ page import="java.net.URLEncoder" %>
<%
    TalkingDataTrackingLink tdLink = new TalkingDataTrackingLink();
    String current_url = request.getRequestURL().toString();
    URL url = new URL(current_url);
    String path = url.getPath();
    int index = path.lastIndexOf('/');
    String new_path = "/tk";
    if ( index > 0 ) {
        new_path = path.substring(0, index)+"/tk";
    }
    String inputURL = request.getParameter("inputURLField");
    String publisher_id = request.getParameter("publisherIdField");
    String site_id = request.getParameter("siteIdField");
    String outputURL = "Hello";
    if (inputURL != null) {
        outputURL = tdLink.translateThirdPartyLink(url.getProtocol(), url.getHost(), new_path, publisher_id, site_id, inputURL);
    }
%>
<!DOCTYPE html>
<html lang="en">
<!-- begin::Head -->
<head>
    <meta charset="utf-8"/>
    <title>
        TalkingData URL Translator
    </title>
    <meta name="description" content="Latest updates and statistic charts">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--begin::Web font -->
    <script src="http://cdn.qikuyx.com/metronic5/assets/ajax/libs/webfront/1.6.16/webfont.js"></script>
    <script>
        WebFont.load({
            google: {"families": ["Poppins:300,400,500,600,700", "Roboto:300,400,500,600,700"]},
            active: function () {
                sessionStorage.fonts = true;
            }
        });
    </script>
    <!--end::Web font -->        <!--begin::Base Styles -->                           <!--begin::Page Vendors -->
    <link href="http://cdn.qikuyx.com/metronic5/assets/vendors/custom/fullcalendar/fullcalendar.bundle.css"
          rel="stylesheet" type="text/css"/>
    <!--end::Page Vendors -->
    <link href="http://cdn.qikuyx.com/metronic5/assets/vendors/base/vendors.bundle.css" rel="stylesheet"
          type="text/css"/>
    <link href="http://cdn.qikuyx.com/metronic5/assets/demo/default/base/style.bundle.css" rel="stylesheet"
          type="text/css"/>
    <!--end::Base Styles -->
    <link rel="shortcut icon" href="http://cdn.qikuyx.com/metronic5/assets/demo/default/media/img/logo/favicon.ico"/>
</head>
<!-- end::Head -->        <!-- end::Body -->
<body class="m-page--fluid m--skin- m-content--skin-light2 m-header--fixed m-header--fixed-mobile m-aside-left--enabled m-aside-left--skin-dark m-aside-left--offcanvas m-footer--push m-aside--offcanvas-default">
<div class="m-grid m-grid--hor m-grid--root m-page">
    <div class="m-container m-container--fluid m-container--full-height" style="margin-top: 5%">
        <form class="m-form m-form--fit m-form--label-align-right" action="talkingdata.jsp">
            <div class="form-group m-form__group">
                <label for="publisherIdField">
                    选择渠道
                </label>
                <select class="form-control m-input" name="publisherIdField" id="publisherIdField">
                    <option value="351244">今日头条</option>
                </select>
            </div>
            <div class="form-group m-form__group">
                <label for="siteIdField">
                    选择游戏
                </label>
                <select class="form-control m-input" name="siteIdField" id="siteIdField">
                    <option value="134022">帝国海战-IOS</option>
                    <option value="138269">帝国海战-Android</option>
                    <option value="141656">现代海战-IOS</option>
                </select>
            </div>
            <div class="m-portlet__body">
                <div class="form-group m-form__group">
                    <label for="inputURLText"><h3>输入第三方地址</h3></label>
                    <input type="text" class="form-control m-input" name="inputURLField" id="inputURLText"
                           placeholder="<%=inputURL%>">
                </div>
            </div>
            <div class="m-portlet__foot m-portlet__foot--fit">
                <div class="m-form__actions">
                    <button type="submit" class="btn btn-primary">转换统计地址</button>
                </div>
            </div>
            <div class="m-portlet__body">
                <div class="form-group m-form__group">
                    <label for="outputURLText"><h3>转换后的地址</h3></label>
                    <input type="text" readonly="readonly" class="form-control m-input" name="outputURLField"
                           id="outputURLText" value="<%=outputURL%>">
                </div>
            </div>
        </form>
    </div>
</div>
<!--[html-partial:include:{"file":"_layout.html"}]/-->
<!--begin::Base Scripts -->
<script src="http://cdn.qikuyx.com/metronic5/assets/vendors/base/vendors.bundle.js" type="text/javascript"></script>
<script src="http://cdn.qikuyx.com/metronic5/assets/demo/default/base/scripts.bundle.js"
        type="text/javascript"></script>
<!--end::Base Scripts -->                    <!--begin::Page Vendors -->
<script src="http://cdn.qikuyx.com/metronic5/assets/vendors/custom/fullcalendar/fullcalendar.bundle.js"
        type="text/javascript"></script>
<!--end::Page Vendors -->                                                        <!--begin::Page Snippets -->
<script src="http://cdn.qikuyx.com/metronic5/assets/app/js/dashboard.js" type="text/javascript"></script>
<!--end::Page Snippets -->
</body>
<!-- end::Body -->
</html>
