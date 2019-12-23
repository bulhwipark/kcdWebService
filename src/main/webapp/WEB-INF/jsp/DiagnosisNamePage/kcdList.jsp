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
        <form id="searchForm" method="post" action="/excelDownload.xlsx">
            <div class="row" style="margin-bottom: 10px;">
                <input type="hidden" name="mapVer" id="version" value="0">
                <!--div class="form-inline col-xl-3">
                    <label for="version">ver : </label>
                    <select class="form-control kcdSearchOption" name="mapVer" id="version" style="width: 300px;">
                        <option value="0">0 : 최초매핑버전</option>
                        <option value="1">1 : 추가임시버전</option>
                    </select>
                </div-->
                
                <div class="totalCnt-css col-xl-12">
                    <div>
                        <span><b>KCD 코드기준 Total : </b></span>
                        <span id="kcdTotalCnt"></span>
                    </div>
                </div>

                
            </div>
            <div class="row">
            	<div class="form-inline col-xl-6">
                    <label for="listOption">매핑상태 : </label>
                    <select class="form-control kcdSearchOption" name="listOption" id="listOption" style="width: 90px;">
                        <option value="All">전체</option>
                        <option value="Mapping">매핑</option>
                        <option value="NotMapping">비매핑</option>
                        <option value="IcdNotMapping" selected>ICD</option>
                    </select>
                    <input type="hidden" name="mapStatCd" id="mapStatCd" value="All">
                    <!--label for="mapStatCd" style="margin-left: 20px;">MapStatCD : </label>
                    <select class="form-control kcdSearchOption" name="mapStatCd" id="mapStatCd" style="width: 170px;"></select-->
                    <div class="form-inline" style="padding-left: 15px;">
                        <label for="searchToKcdCd">검색: </label>
                        <input name="kcdCd" id="searchToKcdCd" class="form-control" type="text" placeholder="KCD코드 검색.">
                    </div>
                </div>
				<div class="col-xl-6" style="text-align: right;">
                    <div style="display: inline-block; margin-top: 20px;">
                        <span style="width: 50px; display: inherit;">
                            <button class="btn btn-light" id="prev">
                                <<</button> </span> <span id="currentPage"></span>
                        <span style="width: 50px; display: inherit;">
                            <button class="btn btn-light" id="next">>></button>
                        </span>
                    </div>
                    <div style="display: inline-block; margin-top: 20px;">
                        <button class="btn btn-outline-danger" id="excelDownloadBtn">Excel Download</button>
                    </div>

                </div>
            </div>
            <input type="text" hidden name="limit" id="limit">
            <input type="text" hidden name="offset" id="offset">
        </form>
    </div>
    <div class="kcdListTableDiv">
        <table class="table table-striped" id="kcdListTable">
            <thead>
                <tr>
                    <th scope="col">KCD코드</th>
                    <th scope="col">KCD 한글명/영문명</th>
                    <th scope="col">Snomed ID</th>
                    <th scope="col">Snomed CT Term</th>
                    <th scope="col">매핑상태</th>
                    <th scope="col">매핑일자</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
</div>

<%--
<div class="container-fluid content displayNone" id="default2">
    <h2>Default2</h2>
</div>
--%>
