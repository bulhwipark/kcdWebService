<%--
  Created by IntelliJ IDEA.
  User: saltlux
  Date: 2019-10-18
  Time: 오전 9:13
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 속성추가 modal -->
<div class="modal fade" id="attrValSetting_modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">속성 추가</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">

                <div>
                    <span><b>KCD코드 : </b><span id="modal_kcdCd">${kcdCd}</span></span>
                    <div>
                        <div><b>SCT ID : </b><span id="modal_sctId"></span></div>
                        <div><b>한글명 : </b><span id="modal_kcdKor"></span></div>
                        <div><b>영문명 : </b><span id="modal_kcdEng"></span></div>
                    </div>
                </div>
                <hr>
                <div style="width: 300px; display: inline-block; margin-left: 30px;">
                    <label for="attr_select">Attribute</label>
                    <select class="form-control" name="" id="attr_select">

                    </select>
                </div>
                <div style="width: 300px; display: inline-block; margin-left: 30px;">
                    <label for="val_select">Value</label>
                    <select class="form-control" name="" id="val_select" disabled>
                    </select>
                </div>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="attr_val_save()">적용</button>
               <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- 속성추가 modal end -->
