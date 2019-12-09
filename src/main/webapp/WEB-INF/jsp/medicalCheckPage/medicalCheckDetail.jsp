<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>KCD Web Service</title>
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css/">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/bootstrap-select.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/headerMenuCss.css">
    <link rel="stylesheet" type="text/css" href="/static/css/kcdWebService_style.css">
</head>
<body>
<%--<%@ include file="/WEB-INF/jsp/header/detailHeader.jsp" %>--%>
<input type="text" id="sctId" hidden>
<div class="container-fluid content" id="mediListPage">
    <div class="alert alert-primary" role="alert">
        <div class="pull-left">
            <button class="btn btn-lg btn-danger detailRoutingBtn" id="mediList_prev" data-btntype="prev"><<<<</button>
        </div>
        <div style="margin-left: 135px;margin-right: 135px;display: inline-block;">
           <div>
               <a href="/">메인으로</a>
           </div>
            <input type="hidden" value="${mapVer}" id="mapVer">
            <span><b>Kex코드 : </b><span id="kexCd">${kexCd}</span></span>
            <div>
                <div><b>한글명 : </b><span id="mediCheck_kor"></span></div>
                <div><b>영문명 : </b><span id="mediCheck_eng"></span></div>
            </div>
        </div>
        <div class="pull-right">
            <button class="btn btn-lg btn-danger detailRoutingBtn" id="mediList_next" data-btntype="next">>>>></button>
        </div>
    </div>
    <div class="row">
        <div class="alert">
            <div>
            </div>
        </div>
    </div>
    <div class="row">
        <table class="table table-striped kcdDetailTableCss" id="mediDetailTable">
            <thead>
                <tr>
                    <th scope="col">Core Concept</th>
                    <th scope="col">Concept Name</th>
                    <th scope="col">Core Map CD</th>
                    <th scope="col" width="20%">Attribute&Value</th>
                    <th scope="col">Update Time</th>
                    <th>
                        전체선택&nbsp;&nbsp;
                        <input type="checkbox" id="mediAllSelect">
                    </th>
                    <th>
                        속성 추가
                    </th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        <div id="removeBtnDiv" class="save-btn-div">
            <button id="mediRemoveBtn" class="btn btn-lg btn-danger" disabled onclick="deleteMediList_req()">삭제</button>
        </div>
    </div>
    <div class="alert alert-primary" role="alert">
        <h4>search menu</h4>
        <div>
            <div>
                <span>
                    <label for="mediEcl"><b>ecl : </b></label>
                    <input type="text" id="mediEcl">
                </span>

                <span class="margin-left-20">
                    <label for="mediDisorder">Medical : </label>
                    <input type="radio" name="mediDefaultRule" id="mediDisorder" value="<71388002" checked>
                </span>

                <%--
                <span class="margin-left-20">
                    <label for="mediClinicalFinding">clinical finding : </label>
                    <input type="radio" name="mediDefaultRule" id="mediClinicalFinding" value="<404684003" checked>
                </span>
                --%>
            </div>
            <div>
                <label for="mediCheck_term"><b>term : </b></label>
                <input type="text" id="mediCheck_term" style="width: 500px;">
                <%--
                <label for="mediSynonym" style="margin-left: 20px;"><b>동의어 : </b></label>
                <select class="form-control" name="" id="mediSynonym" style="display: inline-block; width: 700px;">
                </select>
                --%>
                <label for="mediSearchTermSelect" style="margin-left: 20px;"><b>search term : </b></label>
                <select class="form-control" name="" id="mediSearchTermSelect" style="display: inline-block; width: 700px;">
                </select>
            </div>
            <%--
            <div>
                <label>and :</label> <input type="checkbox" value="and" name="searchMenu"> |
                <label>with :</label> <input type="checkbox" value="with" name="searchMenu"> |
                <label>other :</label> <input type="checkbox" value="other" name="searchMenu"> |
                <label>unspecified :</label> <input type="checkbox" value="unspecified" name="searchMenu"> |
                <label>alone :</label> <input type="checkbox" value="alone" name="searchMenu"> |
                <label>without complication :</label> <input type="checkbox" value="without complication" name="searchMenu"> |
                <label>single :</label> <input type="checkbox" value="single" name="searchMenu"> |
            </div>
            --%>
            <button class="btn btn-lg btn-primary" onclick="medi_search_req()">Search</button> &nbsp;&nbsp;|&nbsp;&nbsp;
            <button class="btn btn-lg btn-primary" onclick="medicalDetail_autoRuleSet()">룰 기반 검색</button>&nbsp;&nbsp;|&nbsp;&nbsp;
            <button class="btn btn-lg btn-primary" onclick="medi_similaritySearch()">유사도 기반조회</button> |
            <button id="mediSaveBtn" class="btn btn-lg btn-info pull-right" disabled onclick="medi_saveBtn_req()">저장</button>
           <%-- <button class="btn btn-lg btn-warning">Clean</button>--%>
        </div>
    </div>
    <div class="row" id="mediSearchResultTableDiv">
        <table class="table table-striped" id="mediSearchResultTable">
            <thead>
                <tr>
                    <th>Concept Id</th>
                    <th>Term</th>
                    <th class="autoRuleCol">Rule Code</th>
                    <th>
                        전체선택&nbsp;&nbsp;
                        <input type="checkbox" id="mediSearchResultAllSelect">
                    </th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <%@ include file="/WEB-INF/jsp/DiagnosisNamePage/kcdDetail_modal.jsp" %>
</div>
</body>
<script src="/static/lib/jQuery-3.4.1.min.js"></script>
<script src="/static/lib/popper.js"></script>
<script src="/static/lib/bootstrap.min.js"></script>
<script src="/static/lib/bootstrap-select.min.js"></script>
<script src="/static/js/global-variable.js"></script>
<script src="/static/js/medicalCheckPage/mediCheck_detail.js"></script>
<script type="text/javascript">
    $(function(){
        medi_check.mapVer = '${mapVer}';
        medi_check.limit = '${limit}';
        medi_check.currentOffset = '${offset}';
        mediCheck_detail_static_func();
    })
</script>
</html>
