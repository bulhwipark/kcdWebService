function mediCheck_detail_static_func() {
    get_mediCheckObject_req();
    get_mediCheckDetail_list();
    //termSynonym();
    medicalDetail_autoRuleSet();
    mediDetail_prevBtn_ajaxReq();
    mediSearchTerm_setting();
    
    $('#mediEcl').on('click',function(){
        $('#mediDisorder').prop('checked', false);
        $('#mediClinicalFinding').prop('checked', false);
    });
    
    //disorder, clinicalfinding 클릭시 좌측 ecl 인풋에 텍스트는 삭제.
    $('input[name="mediDefaultRule"]').on('click', function(){
        $('#mediEcl').val('');
    });
}

/**
 * mediCheck info
 */
function get_mediCheckObject_req() {
    $.ajax({
        url: '/getMediCheckInfo',
        type: 'post',
        async: false,
        data: {
            kexCd: $('#kexCd').text()
        },
        dataType: 'json',
        success: function (data) {
           if (data) {
        	   medi_check.currentMediInfo = JSON.parse(JSON.stringify(data));
               $('#mediCheck_kor').text(data.kexKor?data.kexKor:"-");
               $('#mediCheck_eng').text(data.kexEng?data.kexEng:"-");
               $('#mediCheck_term').val(data.kexEng);
           }
        }
    });
}

//kexam 리스트 조회
function get_mediCheckDetail_list() {
    $.ajax({
        url:'/getMediCheckDetailList',
        type:'post',
        data:{},
        dataType:'json',
        success:function(data){
        	medi_check.mediDetailList = JSON.parse(JSON.stringify(data));
            $('#mediDetailTable tbody').empty();
            $('#mediAllSelect').prop("checked", false);
            for(var i = 0; i<data.length; i++){
                var $tr = $('<tr>').append(
                    $('<td>',{
                        class:'medi-sctIdDetail',
                        text:data[i].sctId,
                        'data-sctid':data[i].sctId
                    }),
                    $('<td>',{
                        text:data[i].sctTerm
                    }),
                    $('<td>',{
                        text:(data[i].mapStatNm?data[i].mapStatNm:'-') +"("+data[i].mapStatCd+")"
                    }),
                    $('<td>',{
                        text:data[i].afterMap
                    }),
                    $('<td>',{
                        text:data[i].udtDt
                    }),
                    $('<td>').append(
                        $('<input>',{
                            type:'checkbox',
                            name:'medi_sctListCheck',
                            id:"mediDelCheckbox_"+ data[i].sctId,
                            value:data[i].sctId,
                            disabled:data[i].mapStatCd==='0'||data[i].mapStatCd==='1'?true:false
                        })
                    ),
                    $('<td>').append(
                        $('<button>',{
                            value:data[i].sctId,
                            class:'btn btn-sm btn-info addAttrBtn',
                            text:'속성추가',
                            'data-toggle':'modal',
                            'data-target':'#attrValSetting_modal',
                            onclick:'attr_val_modalSetting('+ data[i].sctId +')'
                        })
                    )
                );
                $('#mediDetailTable tbody').append($tr);
            }
            medi_detail_dynamic_func();
        }
    })
}

function medicalDetail_autoRuleSet(){
    var param = new Object();
    param.term = $('#mediTerm').val();
    param.kdCd = $('#kdCd').text();
    // param.rules = medi.rules.join(',');
    param = JSON.parse(JSON.stringify(medi_check.currentMediInfo));
    if ($('input[name="mediDefaultRule"]:checked').val()) {
        param.ecl = $('input[name="mediDefaultRule"]:checked').val();
    } else {
        param.ecl = $('#mediEcl').val();
    }
    $.ajax({
        url: '/medicalCheckAutoRuleSet',
        type: 'post',
        async: false,
        data: param,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            medi_check.autoRuleLog = JSON.parse(JSON.stringify(data));
            var items = JSON.parse(JSON.stringify(data));
            $('#mediSearchResultTable tbody').empty();
            for(var q = 0; q<items.length; q++){

            	//중복제거.
            	if(medi_check.mediDetailList){
                    for(var i = 0; i<medi_check.mediCheckDetailList.length; i++){
                        items = items.filter(function(item, idx, arr){
                            if(item.conceptId != medi_check.mediDetailList[i].sctId){
                                return item;
                            }
                        });
                    }
                }
                medi_check.searchList = JSON.parse(JSON.stringify(items));
                
                $('#mediSearchResultTable').prop("checked", false);
                for(var i = 0; i<items.length; i++){
                    if($('#mediSearchResultTable tbody').children('#' + items[i].conceptId).length > 0){
                        $('#mediSearchResultTable tbody tr').children('#'+items[i].conceptId+'_ruleCode').text(
                            $('#mediSearchResultTable tbody tr').children('#'+items[i].conceptId+'_ruleCode').text()+ ', ' + data[q].ruleCode
                        )
                    }else{
                        var $tr = $('<tr>',{id:items[i].conceptId}).append(
                            //conceptId
                            $('<td>',{
                                class:"sctIdDetail",
                                text:items[i].conceptId
                            }),
                            //term
                            $('<td>',{
                                // text:items[i]['fsn']['term']
                                html:StringMatch_func(items[i]['fsn']['term'], $('#mediTerm').val())
                            }),
                            $('<td>',{
                                class:'autoRuleCol',
                                id:items[i].conceptId + "_ruleCode",
                                text:data[q].ruleCode
                            }),
                            //checkBox
                            $('<td>').append(
                                $('<input>',{
                                    type:'checkbox',
                                    name:'mediSearchResultSaveCheckbox',
                                    value:items[i].conceptId
                                })
                            )
                        );
                        $('#mediSearchResultTable tbody').append($tr);
                    }
                }
                $('#saveBtnDiv').removeClass('displayNone');
                $('.autoRuleCol').removeClass('displayNone');
                medi_detail_dynamic_func();
            }
            alert_timeout();
        }
    });
}