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
                    <label for="attr_select1">Attribute</label>
                    <select class="form-control attrSelect" name="attr" id="attr_select1" data-num="1"></select>
                </div>
                <label for="div1" style="margin-left:20px;">Value : </label>
                <div style="width: 260px; display: inline-block; margin-left: 30px;" id="div1"></div>
                <button class="btn btn-sm btn-danger attrRemove" id="attr_remove1" data-num="1">삭제</button>

                <hr>

                <div style="width: 300px; display: inline-block; margin-left: 30px;">
                    <label for="attr_select2">Attribute</label>
                    <select class="form-control attrSelect" name="attr" id="attr_select2"  data-num="2"></select>
                </div>
                <label for="div2" style="margin-left: 20px;">Value : </label>
                <div style="width: 260px; display: inline-block; margin-left: 30px;" id="div2"></div>
                <button class="btn btn-sm btn-danger attrRemove" id="attr_remove2" data-num="2">삭제</button>

                <hr>

                <div style="width: 300px; display: inline-block; margin-left: 30px;">
                    <label for="attr_select3">Attribute</label>
                    <select class="form-control attrSelect" name="" id="attr_select3"  data-num="3"></select>
                </div>
                <label for="div3" style="margin-left: 20px;">Value : </label>
                <div style="width: 260px; display: inline-block; margin-left: 30px;" id="div3"></div>
                <button class="btn btn-sm btn-danger attrRemove" id="attr_remove3" data-num="3">삭제</button>

                <hr>

                <div style="width: 300px; display: inline-block; margin-left: 30px;">
                    <label for="attr_select4">Attribute</label>
                    <select class="form-control attrSelect" name="" id="attr_select4"  data-num="4"></select>
                </div>
                <label for="div4" style="margin-left: 20px;">Value : </label>
                <div style="width: 300px; display: inline-block; margin-left: 30px;" id="div4"></div>
                <button class="btn btn-sm btn-danger attrRemove" id="attr_remove4" data-num="4">삭제</button>

                <hr>

                <div style="width: 300px; display: inline-block; margin-left: 30px;">
                    <label for="attr_select5">Attribute</label>
                    <select class="form-control attrSelect" name="" id="attr_select5" data-num="5"></select>
                </div>
                <label for="div5" style="margin-left: 20px;">Value : </label>
                <div style="width: 260px; display: inline-block; margin-left: 30px;" id="div5"></div>
                <button class="btn btn-sm btn-danger attrRemove" id="attr_remove5" data-num="5">삭제</button>
            </div>

            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="attrSaveBtn" data-dismiss="modal" onclick="attr_val_save()" disabled>적용</button>
                <button type="button" class="btn btn-warning" id="attrUpdateBtn"  data-dismiss="modal" onclick="attr_val_update()">수정</button>
               <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- 속성추가 modal end -->
