<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="text" id="sctId" hidden>
<div class="container-fluid content displayNone" id="medicalProcedurePage">
    <div class="wall">
        <div class="pull-left">
            <h2>
                처치 및 시술 목록
            </h2>
        </div>
        <form id="mediProc_searchForm" method="post" action="/excelDownload.xlsx">
            <div class="row" style="margin-bottom: 10px;">
                <input type="hidden" name="mapVer" id="mediProc_mapVer" value="0">
                <!--div class="form-inline col-xl-3">
                    <label for="version">ver : </label>
                    <select class="form-control kcdSearchOption" name="mapVer" id="version" style="width: 300px;">
                        <option value="0">0 : 최초매핑버전</option>
                        <option value="1">1 : 추가임시버전</option>
                    </select>
                </div-->
                <div class="form-inline col-xl-3">
                    <label for="mediProc_listOption">매핑상태 : </label>
                    <select class="form-control kcdSearchOption" name="listOption" id="mediProc_listOption" style="width: 90px;">
                        <option value="All" selected>전체</option>
                        <option value="Mapping">매핑</option>
                        <option value="NotMapping">비매핑</option>
                    </select>
                    <input type="hidden" name="mapStatCd" id="mediProc_mapStatCd" value="All">
                    <!--label for="mapStatCd" style="margin-left: 20px;">MapStatCD : </label>
                    <select class="form-control kcdSearchOption" name="mapStatCd" id="mapStatCd" style="width: 170px;"></select-->
                    <div class="form-inline" style="padding-left: 15px; margin-bottom: 10px;">
                        <label for="mediProc_searchToKcdCd">검색: </label>
                        <input name="kcdCd" id="mediProc_searchToKcdCd" class="form-control" type="text" placeholder="코드 검색.">
                    </div>
                </div>
                <div class="totalCnt-css col-xl-3">
                    <div>
                        <span><b>코드기준 Total : </b></span>
                        <span id="kcdTotalCnt"></span>
                    </div>
                    <div>
                        <span><b>매핑 Total : </b></span>
                        <span id="totalCnt"></span>
                    </div>
                </div>

                <div class="col-xl-3">
                    <div style="display: inline-block; margin-top: 20px;">
                        <span style="width: 50px; display: inherit;">
                            <button class="btn btn-light" id="mediProc_prev">
                                <<</button> </span> <span id="mediProc_currentPage"></span>
                        <span style="width: 50px; display: inherit;">
                            <button class="btn btn-light" id="mediProc_next">>></button>
                        </span>
                    </div>
                    <div style="display: inline-block; margin-top: 20px;">
                        <button class="btn btn-outline-danger" id="mediProc_excelDownloadBtn">Excel Download</button>
                    </div>

                </div>
            </div>
            <div class="row">

            </div>
            <input type="text" hidden name="limit" id="mediProc_limit">
            <input type="text" hidden name="offset" id="mediProc_offset">
        </form>
    </div>
    <div class="mediProc_listTableDiv">
        <table class="table table-striped" id="mediProc_procListTable">
            <thead>
            <tr>
                <th scope="col">코드</th>
                <th scope="col">한글명/영문명</th>
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