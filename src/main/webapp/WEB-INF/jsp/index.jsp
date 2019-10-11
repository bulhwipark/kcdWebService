<%--
  Created by IntelliJ IDEA.
  User: saltlux
  Date: 2019-10-10
  Time: 오후 1:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>KCD Web Service</title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css/">
    <link rel="stylesheet" type="text/css" href="/static/css/headerMenuCss.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/header/headerMenu.jsp" %>
<%@ include file="/WEB-INF/jsp/DiagnosisNamePage/kcdList.jsp" %>
</body>
<script src="/static/lib/jQuery-3.4.1.min.js"></script>
<script src="/static/lib/bootstrap.min.js"></script>
<script src="/static/js/headerMenuHandle.js"></script>
<script src="/static/js/menu_kcdList.js"></script>
<script type="text/javascript">
    $(function(){
        static_function();
        menu_kcdList_staticFunc();
    });
</script>
</html>

