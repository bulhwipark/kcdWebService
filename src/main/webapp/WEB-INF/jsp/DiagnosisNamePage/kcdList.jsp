<%--
  Created by IntelliJ IDEA.
  User: saltlux
  Date: 2019-10-11
  Time: 오전 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="text" id="sctId" hidden>
<div class="container-fluid content" id="kcdListPage">
    <div class="wall">
        <div class="pull-left">
            <h2>
                KCD 목록
            </h2>
        </div>
        <div class="row" style="margin-bottom: 10px;">
            <div class="form-inline col-xl-3">
                <label for="version">ver : </label>
                <select class="form-control kcdSearchOption" id="version" style="width: 300px;">
                    <option value="0">0 : 최초매핑버전</option>
                    <option value="1">1 : 추가임시버전</option>
                </select>
            </div>
            <div class="form-inline col-xl-3">
                <label for="listOption">매핑상태 : </label>
                <select class="form-control kcdSearchOption" id="listOption" style="width: 90px;">
                    <option value="All">전체</option>
                    <option value="Mapping">매핑</option>
                    <option value="NotMapping">비매핑</option>
                </select>
                <label for="mapStatCd" style="margin-left: 20px;">MapStatCD : </label>
                <select class="form-control kcdSearchOption" id="mapStatCd" style="width: 170px;">

                </select>
            </div>
            <div class="totalCnt-css col-xl-3">
                <div>
                    <span><b>KCD_CD Total : </b></span>
                    <span id="kcdTotalCnt"></span>
                </div>
                <div>
                    <span><b>Total : </b></span>
                    <span id="totalCnt"></span>
                </div>
            </div>

            <div class="col-xl-3">
                <button id="prev"><<</button>
                <span id="currentPage"></span>
                <button id="next">>></button>
            </div>
        </div>
    </div>
    <div class="kcdListTableDiv">
        <table class="table table-striped" id="kcdListTable">
            <thead>
            <tr>
                <th scope="col">KCD코드</th>
                <th scope="col">한글명/영문명</th>
                <th scope="col">SCTID</th>
                <th scope="col">Description</th>
                <th scope="col">Flag</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<div class="container-fluid content displayNone" id="default1">
    <h2>Default1</h2>
</div>

<div class="container-fluid content displayNone" id="default2">
    <h2>Default2</h2>
</div>
