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
</head>
<body>
<%@ include file="/WEB-INF/jsp/header/detailHeader.jsp" %>
<div class="container-fluid content" id="kcdListPage">
    <div class="alert alert-primary" role="alert">
        <span>KCD코드 : <span></span></span>
        <div>
            <span>한글명 : <span></span></span>
            <span>영문명 : <span></span></span>
        </div>
    </div>
    <div class="row">
        <table class="table table-striped" style="margin-left: 10px; margin-right: 10px;">
            <thead>
                <tr>
                    <th scope="col">KCD코드</th>
                    <th scope="col">SCT_ID</th>
                    <th scope="col">STAT_ID</th>
                    <th scope="col">UDT_DT</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>Otto</td>
                </tr>
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>Otto</td>
                </tr>
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                    <td>Otto</td>
                </tr>
            </tbody>
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
                <input type="text" id="term">
            </div>
            <div>
                <label>and :</label> <input type="checkbox" value="and" name="searchMenu"> |
                <label>with :</label> <input type="checkbox" value="with" name="searchMenu"> |
                <label>other :</label> <input type="checkbox" value="other" name="searchMenu"> |
                <label>unspecified :</label> <input type="checkbox" value="unspecified" name="searchMenu"> |
                <label>alone :</label> <input type="checkbox" value="alone" name="searchMenu"> |
                <label>without complication :</label> <input type="checkbox" value="without complication" name="searchMenu"> |
                <label>single :</label> <input type="checkbox" value="single" name="searchMenu">
            </div>
            <button class="btn btn-lg btn-info">Search</button>
        </div>
    </div>
    <div class="row">
    </div>
</div>
</body>
<script src="/static/lib/jQuery-3.4.1.min.js"></script>
<script src="/static/lib/bootstrap.min.js"></script>
</html>
