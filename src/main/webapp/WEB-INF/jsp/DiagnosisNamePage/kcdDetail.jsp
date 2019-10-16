<%--
  Created by IntelliJ IDEA.
  User: saltlux
  Date: 2019-10-11
  Time: 오후 4:06
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
<%@ include file="/WEB-INF/jsp/header/detailHeader.jsp" %>
<div class="container-fluid content" id="kcdListPage">
    <div class="alert alert-primary" role="alert">
        <span><b>KCD코드 : </b><span id="kcdCd">${kcdCd}</span></span>
        <div>
            <div><b>한글명 : </b><span id="kcdKor"></span></div>
            <div><b>영문명 : </b><span id="kcdEng"></span></div>
        </div>
    </div>
    <div class="row">
        <table class="table table-striped kcdDetailTableCss" id="kcdDetailTable">
            <thead>
                <tr>
                    <th scope="col">SCT_ID</th>
                    <th scope="col">MAP_VER</th>
                    <th scope="col">MAP_STAT_CD</th>
                    <th scope="col">RV_STAT_CD</th>
                    <th scope="col">DISP_ODR</th>
                    <th scope="col">UDT_DT</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <div class="alert alert-primary" role="alert">
        <h4>search menu</h4>
        <div>
            <div>
                <label for="ecl"><b>ecl : </b></label>
                <input type="text" id="ecl" readonly value="<64572001">
            </div>
            <div>
                <label for="term"><b>term : </b></label>
                <input type="text" id="term" style="width: 500px;">
            </div>
            <div>
                <label>and :</label> <input type="checkbox" value="and" name="searchMenu"> |
                <label>with :</label> <input type="checkbox" value="with" name="searchMenu"> |
                <label>other :</label> <input type="checkbox" value="other" name="searchMenu"> |
                <label>unspecified :</label> <input type="checkbox" value="unspecified" name="searchMenu"> |
                <label>alone :</label> <input type="checkbox" value="alone" name="searchMenu"> |
                <label>without complication :</label> <input type="checkbox" value="without complication" name="searchMenu"> |
                <label>single :</label> <input type="checkbox" value="single" name="searchMenu"> |
            </div>
            <button class="btn btn-lg btn-warning">Clean</button>
            <button class="btn btn-lg btn-info" onclick="search_req()">Search</button>
        </div>
    </div>
    <div class="row">
        <table class="table table-striped" id="searchResultTable">
            <thead>
                <tr>
                    <th>conceptId</th>
                    <th>active</th>
                    <th>term</th>
                    <th>moduleId</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        <div id="saveBtnDiv" class="save-btn-div displayNone">
            <button class="btn btn-lg btn-info" disabled>저장</button>
        </div>
    </div>
</div>
</body>
<script src="/static/lib/jQuery-3.4.1.min.js"></script>
<script src="/static/lib/bootstrap.min.js"></script>
<script src="/static/js/kcd_detail.js"></script>
<script type="text/javascript">
    $(function(){
        kcd_detail_static_func();
    })
</script>
</html>
