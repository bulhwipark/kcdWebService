<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-fluid content displayNone" id="medicinePage">
    <div class="wall">
        <div class="pull-left">
            <h2>
                약제 목록
            </h2>
        </div>
        <form id="searchForm" method="post" action="/excelDownload.xlsx">
            <div class="row" style="margin-bottom: 10px;">
                <input type="hidden" name="mapVer" id="version" value="0">
                <div class="form-inline col-xl-3">
                    <label for="listOption">매핑상태 : </label>
                    <select class="form-control kcdSearchOption" name="listOption" id="listOption" style="width: 90px;">
                        <option value="All">전체</option>
                        <option value="Mapping">매핑</option>
                        <option value="NotMapping">비매핑</option>
                        <option value="IcdNotMapping" selected>ICD 비매핑</option>
                    </select>
                    <input type="hidden" name="mapStatCd" id="mapStatCd" value="All">
                    <div class="form-inline" style="padding-left: 15px; margin-bottom: 10px;">
                        <label for="searchToKcdCd">검색: </label>
                        <input name="kcdCd" id="searchToKcdCd" class="form-control" type="text" placeholder="KCD코드 검색.">
                    </div>
                </div>
                <div class="totalCnt-css col-xl-3">
                    <div>
                        <span><b>약제 코드기준 Total : </b></span>
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
            <div class="row">

            </div>
            <input type="text" hidden name="limit" id="limit">
            <input type="text" hidden name="offset" id="offset">
        </form>
    </div>
    <div class="kcdListTableDiv">
        <table class="table table-striped" id="">
            <thead>
            <tr>
                <th scope="col">약제코드</th>
                <th scope="col">약제 한글명/영문명</th>
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
