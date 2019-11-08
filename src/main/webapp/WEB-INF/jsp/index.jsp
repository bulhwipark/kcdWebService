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
    <link rel="stylesheet" type="text/css" href="/static/css/kcdWebService_style.css">
</head>
<body>
<%@ include file="/WEB-INF/jsp/header/headerMenu.jsp" %>
<%@ include file="/WEB-INF/jsp/DiagnosisNamePage/kcdList.jsp" %>
<%@include file="/WEB-INF/jsp/medicinePage/medList.jsp" %>
<%@include file="/WEB-INF/jsp/medicalProcedurePage/medicalProcedureList.jsp" %>
<%@include file="/WEB-INF/jsp/medicalCheckPage/medicalCheckList.jsp" %>
</body>

<script src="/static/lib/jQuery-3.4.1.min.js"></script>
<script src="/static/lib/bootstrap.min.js"></script>
<script src="/static/js/global-variable.js"></script>
<script src="/static/js/headerMenuHandle.js"></script>
<script src="/static/js/menu_kcdList.js"></script>
<script src="/static/js/medicinePage/menu_medicineList.js"></script>
<script src="/static/js/medicalProcedurePage/menu_medicalProcedureList.js"></script>
<script src="/static/js/medicalCheckPage/menu_medicalCheckList.js"></script>
<script type="text/javascript">
    $(function(){
        static_function();
        //상단메뉴
        if(!sessionStorage.getItem("mainPage") || sessionStorage.getItem("mainPage") === 'null'){
            pageRuting('kcdListPage');
        }else{
            pageRuting(sessionStorage.getItem('mainPage'));
        }
    });
</script>
</html>

