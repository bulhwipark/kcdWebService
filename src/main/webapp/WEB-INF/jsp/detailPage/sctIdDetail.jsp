<%--
  Created by IntelliJ IDEA.
  User: saltlux
  Date: 2019-10-14
  Time: 오전 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css/">
    <link rel="stylesheet" type="text/css" href="/static/css/headerMenuCss.css">
    <link rel="stylesheet" type="text/css" href="/static/css/kcdWebService_style.css">
</head>
<body>
<nav class="navbar navbar-expand-sm bg-light">
    <ul class="navbar-nav">
        <li class="nav-item">
            <h2>
                SCT_ID Detail
                <span id="sctIdSpan"></span>
            </h2>
        </li>
    </ul>
</nav>

<div class="container-fluid content">
    <input type="text" id="sctId" hidden>
    <div class="sctIdDetailListDiv">
        <table class="table table-striped" id="detailListTable">
            <thead>
                <tr>
                    <th scope="col">sctId</th>
                    <th scope="col">term</th>
                    <th scope="col">udtDt</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>

    <div class="sctMetaLinkDiv">
        <a id=link1 href = "" target=_1>SNOMED CT Browser</a>
    </div>

    
</div>


</body>
<script src="/static/lib/jQuery-3.4.1.min.js"></script>
<script src="/static/lib/bootstrap.min.js"></script>
<script src="/static/js/sctId_detail.js"></script>
<script type="text/javascript">
    $(function () {
        sctId_detail_static_func();
    })
</script>
</html>
