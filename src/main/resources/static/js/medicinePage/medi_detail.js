function medi_detail_static_func(){
    //초기 실행.
    get_mediObject_req();
    get_mediDetail_list();
    //termSynonym();
    button_medi_autoRuleSet();
    mediDetail_prevBtn_ajaxReq();
    mediSearchTerm_setting();

    //ecl 클릭시 disorder, clinicalFinding 체크해제.
    $('#mediEcl').on('click',function(){
        $('#mediDisorder').prop('checked', false);
        $('#mediClinicalFinding').prop('checked', false);
    });

    //disorder, clinicalfinding 클릭시 좌측 ecl 인풋에 텍스트는 삭제.
    $('input[name="mediDefaultRule"]').on('click', function(){
        $('#mediEcl').val('');
    });

    //medi 상세리스트 쪽 전체 선택.
    $('#mediAllSelect').on('click',function(){
        if($(this).prop('checked')){
            for(var i = 0; i<$('input[name="medi_sctListCheck"]').length; i++){
                if(!$('input[name="medi_sctListCheck"]')[i].disabled){
                    $($('input[name="medi_sctListCheck"]')[i]).prop("checked", true);
                }
            }
        }else{
            $('input[name="medi_sctListCheck"]').prop("checked", false);
        }

        if($('input[name="medi_sctListCheck"]:checked').length > 0){
            $('#removeBtn').prop('disabled', false);
        }else{
            $('#removeBtn').prop('disabled', true);
        }
    });

    //검색목록 쪽 전체선택
    $('#mediSearchResultAllSelect').on('click', function(){
        if($(this).prop('checked')){
            $('input[name="mediSearchResultSaveCheckbox"]').prop('checked', true);
        }else{
            $('input[name="mediSearchResultSaveCheckbox"]').prop('checked', false);
        }

        if($('input[name="mediSearchResultSaveCheckbox"]:checked').length > 0){
            $('#mediSaveBtn').prop('disabled', false);
        }else{
            $('#mediSaveBtn').prop('disabled', true);
        }
    });

    //동의어 이벤트
    $('#synonym').on('change', function () {
        $('#term').val($('#synonym option:selected').val());
    })
    .on('click', function () {
        $('#term').val($('#synonym option:selected').val());
    });

    //serach Term 이벤트.
    $('#mediSearchTermSelect').on('change', function () {
        $('#mediTerm').val($('#mediSearchTermSelect option:selected').val());
    })
    .on('click', function () {
        $('#mediTerm').val($('#mediSearchTermSelect option:selected').val());
    });

   $('.attrSelect').on('change', function(){
       getValueList($(this).data('num'));
   });

   $('.attrRemove').on('click', function(){
       deleteAttrVal($(this).data('num'));
   });

   $('.valSelect').on('change', function(){
       textSearchForm_setting($(this).data('num'));
   });

    /**
     * medi 상세화면 다음버튼 이벤트
     * sessionStorage 정보를 이용하여 medi리스트에서 kdcd코드로 다음것을 찾음.
     */
   $('#mediList_next').on('click', function(){
       if(parseInt(sessionStorage.getItem("medi_index")) === 49){
          sessionStorage.setItem(
              'medi_offset', parseInt(sessionStorage.getItem("medi_offset")) + parseInt(sessionStorage.getItem("medi_limit"))
          );
           mediDetail_prevBtn_ajaxReq();
           sessionStorage.setItem("medi_index", 0);
       }
       var index = parseInt(sessionStorage.getItem("medi_index"));
       var result = null;
       var checkIdx = null;
       for(var i = index ; i<medi.mainMedList.length; i++){
           if(sessionStorage.getItem("medi_kdCd") !== medi.mainMedList[i].kdCd){
               result = JSON.parse(JSON.stringify(medi.mainMedList[i]));
               checkIdx = i;
               break;
           }else{
               if(i == 49){
                   result = JSON.parse(JSON.stringify(medi.mainMedList[49]));
                   checkIdx = 49;
                   break;
               }
           }
       }
       sessionStorage.setItem("medi_kdCd", result.kdCd);
       sessionStorage.setItem("medi_index", checkIdx);
       location.href = "/medDetailPage?kdCd="+ result.kdCd + "&mapVer=0";
   });

    /**
     * kcd 상세화면 이전버튼 이벤트
     * sessionStorage 정보를 이용하여 medi리스트에서 MEDI코드로 다음것을 찾음.
     */
   $('#mediList_prev').on('click', function(){
       if(parseInt(sessionStorage.getItem("medi_index")) === 0){
           if(sessionStorage.getItem("medi_offset") > 0){
               sessionStorage.setItem(
                   'medi_offset', parseInt(sessionStorage.getItem("medi_offset")) - parseInt(sessionStorage.getItem("medi_limit"))
               );
               mediDetail_prevBtn_ajaxReq();
               sessionStorage.setItem("medi_index", medi.mainMedList.length-1);
           }else{
              //인덱스가 0이고 offset이 0일때 첫번째 인덱스이므로 동작 X.
               $(this).attr('disabled', true);
               return;
           }
       }
       var index = parseInt(sessionStorage.getItem("medi_index"));
       var result = null;
       var checkIdx = null;
       for(var i = index ; i>=0; i--){
           if(sessionStorage.getItem("medi_kdCd") !== medi.mainMedList[i].kdCd){
               result = JSON.parse(JSON.stringify(medi.mainMedList[i]));
               checkIdx = i;
               break;
           }else{
               if(i == 0){
                   result = JSON.parse(JSON.stringify(medi.mainMedList[0]));
                   checkIdx = 0;
                   break;
               }
           }
       }
       sessionStorage.setItem("medi_kdCd", result.kdCd);
       sessionStorage.setItem("medi_index", checkIdx);
       location.href = "/medDetailPage?kdCd="+ result.kdCd + "&mapVer=0";
   })

}

function medi_detail_dynamic_func(){
    $('input[name="medi_sctListCheck"]').on('change', function(e){
        if($('input[name="medi_sctListCheck"]:checked').length > 0){
            $('#mediRemoveBtn').prop('disabled', false);
        }else{
            $('#mediRemoveBtn').prop('disabled', true);
        }
    });

    $('input[name="searchResultSaveCheckbox"]').on('change', function(e){
        if($('input[name="searchResultSaveCheckbox"]:checked').length > 0){
            $('#saveBtn').prop('disabled', false);
        }else{
            $('#saveBtn').prop('disabled', true);
        }
    });

    $('.medi-sctIdDetail').on('click', function(e){
        e.stopPropagation();
        window.open(
            'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1='+$(this).text(),
            'Detail',
            'width=1200,height=800,left=200,'
        );
        $('#sctId').val($(this).text());
    });

    $('input[name="mediSearchResultSaveCheckbox"]').on('change', function(){
        if($('input[name="mediSearchResultSaveCheckbox"]:checked').length > 0){
            $('#mediSaveBtn').attr('disabled', false);
        }else{
            $('#mediSaveBtn').attr('disabled', true);
        }
    });
}

/**
 * medi info
 */
function get_mediObject_req(){
    $.ajax({
        url:'/getMediInfo',
        type:'post',
        async:false,
        data:{
            kdCd:$('#kdCd').text()
        },
        dataType:'json',
        success:function(data){
            if(data){
                medi.currentMediInfo = JSON.parse(JSON.stringify(data));
                $('#mediKor').text(data.drugNmKor);
                $('#mediEng').text(data.drugNmEng);
                $('#mediTerm').val(data.drugNmEng);
            }
        }
    });
}

/**
 * medi 목록 이벤트
 */
function get_mediDetail_list(){
    $.ajax({
        url:'/getMediDetailList',
        type:'post',
        async:false,
        data:{
            kdCd:$('#kdCd').text()
        },
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.length > 0){
                medi.mediDetailList = JSON.parse(JSON.stringify(data));
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
            }else{
                console.log('자료 없음 처리.');
                medi.mediDetailList = JSON.parse(JSON.stringify(data));
                $('#mediDetailTable tbody').empty();
                $('#mediAllSelect').prop("checked", false);
                var $tr = $('<tr>').append(
                    $('<td>',{
                        colspan:8,
                        text:'조회 결과가 없습니다.',
                        style:'text-align:center;'
                    })
                );
                $('#mediDetailTable tbody').append($tr);
            }
        }
    })
}

/**
 * 검색 이벤트
 */
function medi_search_req(){
    var param = new Object();
    param.term = $('#mediTerm').val();

    if($('input[name="mediDefaultRule"]:checked').val()){
        param.ecl = $('input[name="mediDefaultRule"]:checked').val();
    }else{
        param.ecl = $('#mediEcl').val();
    }

    $.ajax({
        url:'/search',
        type:'post',
        data:param,
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data['items'].length > 0){
                var items = data['items'];

                //중복제거.
                for(var i = 0; i<medi.mediDetailList.length; i++){
                    items = items.filter(function(item, idx, arr){
                        if(item.conceptId != medi.mediDetailList[i].sctId){
                            return item;
                        }
                    });
                }
                medi.searchList = JSON.parse(JSON.stringify(items));
                $('#mediSearchResultTable tbody').empty();
                $('#mediSearchResultAllSelect').prop("checked", false);
                for(var i = 0; i<items.length; i++){
                    var $tr = $('<tr>',{id:items[i].conceptId}).append(
                        //conceptId
                        $('<td>',{
                            class:'sctIdDetail',
                            text:items[i].conceptId
                        }),
                       
                        //term
                        $('<td>',{
                            text: items[i]['fsn']['term']

                        }),
                       
                        //checkBox
                        $('<td>').append(
                            $('<input>',{
                                type:'checkbox',
                                name:'searchResultSaveCheckbox',
                                value:items[i].conceptId
                            })
                        )
                    );
                    $('#mediSearchResultTable tbody').append($tr);
                }
                $('#saveBtnDiv').removeClass('displayNone');
                $('.autoRuleCol').addClass('displayNone');
                medi_detail_dynamic_func();
            }else{
                console.log("자료 없음 처리.");
            }
        }
    })
}

/*
 * 저장 버튼 이벤트
 */
function medi_saveBtn_req(){
    var currentSelected = $('input[name="mediSearchResultSaveCheckbox"]:checked');
    var sctIdArr = [];

    //기존 검색된 리스트에서 선택된(currentSelected) sctId로 검색.
    for(var i  = 0; i<currentSelected.length; i++){
        if(medi.searchList){
            for(var j = 0; j<medi.searchList.length; j++){
                if(medi.searchList[j].conceptId == currentSelected[i].value){
                    sctIdArr.push(medi.searchList[j].conceptId);
                }
            }
        }else{
            sctIdArr.push(currentSelected[i].value);
        }
    }

    sctIdArr = sctIdArr.filter(function(item, idx, arr){
        return arr.indexOf(item) == idx;
    })

    $.ajax({
        url:'/mediInsertSearchList',
        type:'post',
        data:{
            oriCd : $('#kdCd').text(),
            mapVer : medi.mapVer,
            mapStatCd:5,
            oriTpCd:'medi',
            sctId:sctIdArr.join(",")
        },
        success:function(){
            get_mediDetail_list();
            for(var i = 0; i<sctIdArr.length; i++){
                var sctId = sctIdArr[i];
                medi.searchList = medi.searchList.filter(function(item, idx, arr){
                    if(item.conceptId != sctId){
                        return item;
                    }
                });
                $('#' + sctId).remove();
            }
            $('#mediSaveBtn').attr('disabled', true);
            alert_timeout();
        }
    });
}

/**
 * medi 목록 삭제 이벤트.
 */
function deleteMediList_req(){
    var currentSelected = $('input[name="medi_sctListCheck"]:checked');
    var sctIdArr = [];
    for(var i = 0; i<currentSelected.length; i++){
        sctIdArr.push(currentSelected[i].value);
    }
   $.ajax({
        url:'/deleteMediList',
        type:'post',
        data:{
            oriCd: $('#kdCd').text(),
            sctId: sctIdArr.join(',')
        },
        success:function(){
            get_mediDetail_list();
            //search_req();
            medi_autoRuleSet();
        }
    });
}

/**
 * 유사동의어리스트 req
 */
function termSynonym(){
    $.ajax({
        url:'/getTermSynonymList',
        type:'post',
        data:{
            kcdCd: $('#kcdCd').text()
        },
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.length > 0){
                $('#synonym').empty();
                for(var i = 0; i<data.length; i++){
                    var $option = $('<option>',{
                       text:data[i].kcdEngSyn,
                       title:data[i].kcdKorSyn
                    });
                    $('#synonym').append($option);
                }
            }else{
                $('#synonym').empty();
                console.log('데이터 없음 처리.');
            }
        }
    })
}

//search term selectbox setting
function mediSearchTerm_setting(){
    $('#mediSearchTermSelect').empty();
    for(var i = 0; i<medi.autoRuleLog.length; i++){
        var logInfo = medi.autoRuleLog[i];
        if(logInfo.status == 'true' || logInfo.status == 'false'){
            var $option = $('<option>',{
                text:logInfo.searchTerm
            });
            $('#mediSearchTermSelect').append($option);
        }
    }
}

/**
 * 룰기반 검색 버튼.
 * 상세화면에 최초 진입시에 실행되는 자동룰과 룰기반검색 버튼을 통해 실행되는 자동룰이 다름.
 * 버튼이벤트로 실행되는 자동 룰은 kcd와 동일한 룰이 실행됨.
 */
function button_medi_autoRuleSet(){
    var param = new Object();
    param.term = $('#mediTerm').val();
    // param.rules = medi.rules.join(',');
    param = JSON.parse(JSON.stringify(medi.currentMediInfo));
    if ($('input[name="mediDefaultRule"]:checked').val()) {
        param.ecl = $('input[name="mediDefaultRule"]:checked').val();
    } else {
        param.ecl = $('#mediEcl').val();
    }
    $.ajax({
        url: '/mediAutoRuleSet',
        type: 'post',
        async: false,
        data: param,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            medi.autoRuleLog = JSON.parse(JSON.stringify(data));
            $('#mediSearchResultTable tbody').empty();
            for(var q = 0; q<data.length; q++){

                if(data[q].status === "true"){
                    var res = JSON.parse(data[q].result);
                    if(res['items'].length > 0){
                        var items = res['items'];

                        //중복제거.
                        if(medi.mediDetailList){
                            for(var i = 0; i<medi.mediDetailList.length; i++){
                                items = items.filter(function(item, idx, arr){
                                    if(item.conceptId != medi.mediDetailList[i].sctId){
                                        return item;
                                    }
                                });
                            }
                        }
                        medi.searchList = JSON.parse(JSON.stringify(items));

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
                                            name:'searchResultSaveCheckbox',
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
                    }else{
                        //status false;
                        console.log(data[q].status);
                    }
                }
            }
        }
    });
}
/*

/!**
 * 자동룰 반영
 *!/
function medi_autoRuleSet(){
    var param = new Object();
    param = JSON.parse(JSON.stringify(medi.currentMediInfo));

    if($('input[name="mediDefaultRule"]:checked').val()){
        param.ecl = $('input[name="mediDefaultRule"]:checked').val();
    }else{
        param.ecl = $('#mediEcl').val();
    }
      $.ajax({
        url:'/mediAutoRuleSet',
        //url:'/autoRuleSet',
        type:'post',
        async:false,
        data:param,
        dataType:'json',
        success:function(data){
            console.log(data);
            medi.autoRuleLog = JSON.parse(JSON.stringify(data));
            $('#mediSearchResultTable tbody').empty();
            for(var q = 0; q<data.length; q++){

                if(data[q].status === "true"){
                    var res = JSON.parse(data[q].result);
                    if(res['items'].length > 0){
                        var items = res['items'];

                        //중복제거.
                        if(medi.mediDetailList){
                            for(var i = 0; i<medi.mediDetailList.length; i++){
                                items = items.filter(function(item, idx, arr){
                                    if(item.conceptId != medi.mediDetailList[i].sctId){
                                        return item;
                                    }
                                });
                            }
                        }
                        medi.searchList = JSON.parse(JSON.stringify(items));

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
                                            name:'searchResultSaveCheckbox',
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
                  }else{
                        //status false;
                        console.log(data[q].status);
                    }
                }
            }
        }
    });
}
*/

/**
 * 유사도기준 검색 ajax
 */
function medi_similaritySearch(){
    var param = new Object();
    param.term = $('#mediTerm').val().replace("/", "");

    if($('input[name="mediDefaultRule"]:checked').val()){
        param.ecl = $('input[name="mediDefaultRule"]:checked').val();
    }else{
        param.ecl = $('#mediEcl').val();
    }

    $.ajax({
        url:'/similaritySearch',
        type:'post',
        data:param,
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.status === "true"){
                var obj = JSON.parse(data.result);
                var items = obj['items'];
                //중복제거.
                if(medi.mediDetailList){
                    for(var i = 0; i<medi.mediDetailList.length; i++){
                        items = items.filter(function(item, idx, arr){
                            if(item.conceptId != medi.mediDetailList[i].sctId){
                                return item;
                            }
                        });
                    }
                }
                medi.searchList = JSON.parse(JSON.stringify(items));

                $('#mediSearchResultTable tbody').empty();
                for(var i = 0; i<items.length; i++){
                    var itemVal = items[i];
                    if($('#mediSearchResultTable tbody').children('#' + itemVal.conceptId).length > 0){
                        
                    }else{
                        var $tr = $('<tr>', {id:itemVal.conceptId}).append(
                            $('<td>',{
                                class:'sctIdDetail',
                                text:itemVal.conceptId
                            }),
                            $('<td>',{
                                text:itemVal.fsn.term
                            }),
                            $('<td>',{
                                text:data.ruleCode
                            }),
                            //checkBox
                            $('<td>').append(
                                $('<input>',{
                                    type:'checkbox',
                                    name:'mediSearchResultSaveCheckbox',
                                    value:itemVal.conceptId
                                })
                            )
                        );
                    }

                    $('#mediSearchResultTable tbody').append($tr);
                }
                medi_detail_dynamic_func();
            }else{
                console.log("데이터 없음");
                console.log(data);
            }
        }
    });
}

/**
 * 속성추가 모달 세팅하는 펑션.
 * 추가되어있는 속성/값 리스트를 조회 >>> 조회된 리스트가 잇으면 수정버튼 show 없으면 저장버튼 show
 *
 * @param sctId
 */
function attr_val_modalSetting(sctId){
    $('#modal_sctId').text(sctId);
    $('#modal_kcdKor').text($('#mediKor').text());
    $('#modal_kcdEng').text($('#mediEng').text());
    $('.attrRemove').hide();
    $('.selectPickerDiv').empty();
    var infoList = null;
    var ajax_res = getMapAttrValList_ajax();
    ajax_res.done(function(attrValList){
        console.log(attrValList);
        infoList = JSON.parse(JSON.stringify(attrValList));
         $.ajax({
            url:'/getKcdAttrList',
            type:'post',
            data:{
                sctId:sctId,
                oriTpCd:'MEDI'
            },
            dataType:'json',
            async:false,
            success:function(data){
                console.log(data);
                $('.attrSelect, .valSelect').empty();
                $('.valSelect').attr('disabled', true);

                $('.attrSelect').append(
                    $('<option>',{
                        value:'',
                        text:'속성을 선택하세요.',
                        disabled:true,
                        selected:true
                    }),
                );

                if(data.length>0){
                    for(var i = 0; i<data.length; i++){
                        var $option = $('<option>', {
                            text:data[i].cmSctTerm2,
                            value:data[i].attSctId
                        });
                        $('.attrSelect').append($option);
                    }
                }else{
                    console.log("attr 목록 없음.");
                }
            }
        });
    });

    if (infoList.length > 0) {
        $('#attrSaveBtn').hide();
        $('#attrUpdateBtn').show();
    } else {
        $('#attrSaveBtn').show();
        $('#attrUpdateBtn').hide();
    }

    for (var i = 0; i < infoList.length; i++) {
        $('#attr_select'+(i+1)).val(infoList[i].attSctId);
        getValueList(i+1);
        textSearchForm_setting(i+1);
        $('#val_select'+(i+1)).val(infoList[i].valgrpSctId);
        $('#text_select'+(i+1)).data('conceptid', JSON.parse(infoList[i].valSctIdInfo).conceptId);
        $('#div'+ (i+1) +' .textSelect .filter-option-inner-inner').text(JSON.parse(infoList[i].valSctIdInfo).fsn.term);
        $('#attr_remove'+(i+1)).show();
    }
}

function getValueList(currentNum){
    $.ajax({
        url:'/getKcdValList',
        type:'post',
        data:{
            sctId:$('#attr_select'+ currentNum +' option:selected').val()
        },
        dataType:'json',
        async:false,
        success:function(data){
            $('#val_select' + currentNum).empty();
            $('#val_select' + currentNum).append(
                $('<option>',{
                    text:"값을 선택하세요.",
                    value:''
                })
            );
            if(data.length > 0){
                for(var i = 0; i<data.length; i++){
                    var $option = $('<option>',{
                        text:data[i].cmSctTerm,
                        value:data[i].attSctId,
                        'data-tokens':data[i].cmSctTerm
                    });
                    $('#val_select' + currentNum).append($option);
                }
                $('#val_select' + currentNum).attr('disabled', false);
            }else{
                console.log("리스트 없음.")
            }
        }
    })
}

function textSearchForm_setting(currentNum){
    $('#div' + currentNum).empty().append(
        $('<select>',{
            class:"textSelect",
            'data-live-search':"true",
            id:"text_select"+currentNum
        }).on('change', function(){
            $('#attrSaveBtn').attr('disabled', false)
        })
    );
    $('#text_select' + currentNum).selectpicker();

    $('.textSelect input[type="text"].form-control').on('keydown', function(key){
        if(key.keyCode == 13){
            console.log('enter');
            attrValTextSearch_request(currentNum);
        }
    })
}

function attrValTextSearch_request(currentNum){
     $.ajax({
         url: '/getTextSearchResult',
         type: 'post',
         data: {
             term: $('#div'+ currentNum +' .textSelect input[type="text"].form-control').val(),
             ecl: '<' + $('#val_select' + currentNum + ' option:selected').val()
         },
         dataType: 'json',
         success: function (data) {
             console.log(data);
             $('#text_select'+currentNum).empty();
             if(data.items.length > 0){
                 for(var i = 0; i<data.items.length; i++){
                     var item = data.items[i];
                     var $option = $('<option>',{
                         text:item.fsn.term,
                         value:item.conceptId
                     });
                     $('#text_select'+currentNum).append($option);
                 }
                 //$('#text_select'+currentNum).selectpicker('render');
                 $('#text_select'+currentNum).selectpicker('refresh');
             }
         }
     })
}

function attr_val_save(){
    var attrOptList = $('.attrSelect option:selected');
    var attrParam = [];
    var valParam = [];
    var valGrpSctIdParam = [];
    for(var i = 0; i<attrOptList.length; i++){
        if(attrOptList[i].value){
            if($('#val_select' + (i+1) + " option:selected").val()){
                attrParam.push(attrOptList[i].value);
                valGrpSctIdParam.push($('#val_select' + (i+1) + " option:selected").val());
                valParam.push($('#text_select' + (i+1) + ' option:selected').val());
            }
        }
    }

    $.ajax({
        url:'/attrValSave',
        type:'post',
        data:{
            oriTpCd:'KCD',
            oriCd:$('#modal_kcdCd').text(),
            mapVer:$('#mapVer').val(),
            sctId:$('#modal_sctId').text(),
            mapStatCd:'80',
            attrParam:attrParam.join(','),
            valParam:valParam.join(','),
            valGrpSctIdParam:valGrpSctIdParam.join(',')
        },
        success:function(){
            console.log("save");
            get_mediDetail_list();
        }
    })
}

function attr_val_update(){
    var attrOptList = $('.attrSelect option:selected');
    var attrParam = [];
    var valParam = [];
    var valGrpSctIdParam = [];
    for(var i = 0; i<attrOptList.length; i++){
        if(attrOptList[i].value){
            if($('#val_select' + (i+1) + " option:selected").val()){
                if($('#text_select'+ (i+1) + ' option:selected').val()){
                    attrParam.push(attrOptList[i].value);
                    valParam.push($('#text_select' + (i+1) + " option:selected").val());
                    valGrpSctIdParam.push($('#val_select' + (i+1) + " option:selected").val());
                }else if($('#text_select' + (i+1)).data('conceptid')){
                    attrParam.push(attrOptList[i].value);
                    valParam.push($('#text_select' + (i+1)).data('conceptid'));
                    valGrpSctIdParam.push($('#val_select' + (i+1) + " option:selected").val());
                }
            }
        }
    }
    if(attrParam.length > 0) {
        $.ajax({
            url: '/attrValUpdate',
            type: 'post',
            data: {
                oriTpCd: 'KCD',
                oriCd: $('#modal_kcdCd').text(),
                mapVer: $('#mapVer').val(),
                sctId: $('#modal_sctId').text(),
                // attSctId:$('#attr_select option:selected').val(),
                // valSctId:$('#val_select option:selected').val(),
                mapStatCd: '80',
                attrParam: attrParam.join(','),
                valParam: valParam.join(','),
                valGrpSctIdParam: valGrpSctIdParam.join(",")
            },
            success: function () {
                console.log("update");
            }
        })
    }
}

function getMapAttrValList_ajax(){
    var ajax = $.ajax({
        url:'/getMapAttrValList',
        type:'post',
        data:{
            oriCd:$('#modal_kcdCd').text(),
            sctId:$('#modal_sctId').text(),
        },
        dataType:'json',
        async:false
        /*success:function(data){
            console.log(data)
        }*/
    });
    return ajax;
}

function deleteAttrVal(num){
    $.ajax({
        url:'/deleteAttrVal',
        type:'post',
        data:{
            oriCd:$('#modal_kcdCd').text(),
            sctId:$('#modal_sctId').text(),
            attSctId:$('#attr_select'+num+' option:selected').val(),
            valSctId:$('#text_select'+ num).data('conceptid'),
            valgrpSctId:$('#val_select'+num+ ' option:selected').val()
        },
        success:function(){
            $('#attr_select'+num).val('');
            $('#val_select' + num).empty().attr('disabled', true);
            $('#div'+num).empty();
            $('#attr_remove' + num).hide();
            console.log('delete');
        }
    })
}

/**
 *
 * @param str 원본문자열
 * @param matchStr 매칭시킬 문자열
 * @returns {string|*}
 * @constructor
 */
function StringMatch_func(str, matchStr){
    var matchRes = str.toUpperCase().match(matchStr.trim().toUpperCase());
    if(matchRes){
        return str.slice(0, matchRes.index) + "<span class='matchStrCss'>"
                                            + str.slice(matchRes.index, matchRes.index+matchRes[0].length)
                                            + '</span>' + str.slice(matchRes.index+matchRes[0].length);
    }else{
        return str;
    }
}

//MEDI목록 조회.
function mediDetail_prevBtn_ajaxReq(){
    $.ajax({
        url: "/medicine/select/"+sessionStorage.getItem("medi_listOption"),
        type:'get',
        data:{
            mapVer:sessionStorage.getItem("medi_mapVer"),
            mapStatCd:sessionStorage.getItem("medi_mapStatCd"),
            kcdCd:sessionStorage.getItem("medi_searchToKdCd").toUpperCase(),
            limit:sessionStorage.getItem("medi_limit"),
            offset:parseInt(sessionStorage.getItem("medi_offset"))
        },
        dataType:'json',
        async:false,
        success:function(data){
            medi.mainMedList = JSON.parse(JSON.stringify(data));
        }
    })
}

function alert_timeout(){
    $('#saveAlert').removeClass('displayNone');
    var timer = setTimeout(function(){
        if(!$('#saveAlert').hasClass('displayNone')){
            $('#saveAlert').addClass('displayNone');
        }
    }, 500);
}