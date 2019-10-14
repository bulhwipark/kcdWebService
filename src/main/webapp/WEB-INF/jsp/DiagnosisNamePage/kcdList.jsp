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
        <div>
            <div class="form-group">
                <select class="form-control" id="listOption">
                    <option value="/selectAll">전체</option>
                    <option value="/selectMapping">매핑</option>
                    <option value="/selectNotMapping">미매핑</option>
                </select>
            </div>
        </div>
    </div>
    <div class="kcdListTableDiv">
        <table class="table table-striped" id="kcdListTable">
            <thead>
            <tr>
                <th scope="col">KCD코드</th>
                <th scope="col">한글명</th>
                <th scope="col">영문명</th>
                <th scope="col">SCTID</th>
                <th scope="col">Description</th>
                <th scope="col">Flag</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<div class="container-fluid content displayNone" id="default">
    <h2>default</h2>
</div>
