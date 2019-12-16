function mediCheck_detail_static_func() {
    get_mediCheckObject_req();
    get_mediCheckDetail_list();
//    termSynonym();
//    medicalDetail_autoRuleSet();
    mediCheckDetail_prevBtn_ajaxReq();
//    mediCheckSearchTerm_setting();
    
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
            $('#mediRemoveBtn').prop('disabled', false);
        }else{
            $('#mediRemoveBtn').prop('disabled', true);
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

    //serach Term 이벤트.
    $('#mediSearchTermSelect').on('change', function () {
        $('#mediCheck_term').val($('#mediSearchTermSelect option:selected').val());
    })
    .on('click', function () {
        //$('#mediCheck_term').val($('#mediSearchTermSelect option:selected').val());
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

       if(parseInt(sessionStorage.getItem("medi_index")) === sessionStorage.getItem("medi_totalCnt")){
           $(this).attr('disabled', true);
            return;
       }

       if(parseInt(sessionStorage.getItem("medi_index")) === medi.mainMedList.length){
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
       sessionStorage.setItem("medi_subAltKey", result.subAltKey);
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
               $('#mediCheck_term').val(data.kexEng?data.kexEng:"");
               $('#mediSearchTermSelect').empty();
               
               if(data.kexEng != null && data.kexEng.trim().length > 0){
            	   $('#mediSearchTermSelect').append(
                   		$('<option>',{
                   			text:data.kexEng.trim(),
                   			value:data.kexEng.trim()
                   		})   
                      );
               }
               if(data.preTerm != null && data.preTerm.trim().length > 0){
            	   $('#mediSearchTermSelect').append(
                   		$('<option>',{
                   			text:data.preTerm.trim(),
                   			value:data.preTerm.trim()
                   		})   
                      );
               }
               if(data.preTerm2 != null && data.preTerm2.trim().length > 0){
            	   $('#mediSearchTermSelect').append(
                   		$('<option>',{
                   			text:data.preTerm2.trim(),
                   			value:data.preTerm2.trim()
                   		})   
                      );
               }
           }
        }
    });
}

//kexam 리스트 조회
function get_mediCheckDetail_list() {
    $.ajax({
        url:'/getMediCheckDetailList',
        type:'post',
        data:{
        	kexCd:$('#kexCd').text()
        },
        dataType:'json',
        success:function(data){
        	medi_check.mediCheckDetailList = JSON.parse(JSON.stringify(data));
        	medicalDetail_autoRuleSet();
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
            medi_check_detail_dynamic_func();
        }
    })
}

/**
 * 유사동의어리스트 req
 */
//function termSynonym(){
//    $.ajax({
//        url:'/getTermSynonymList',
//        type:'post',
//        data:{
//            kcdCd: $('#kexCd').text()
//        },
//        dataType:'json',
//        success:function(data){
//            console.log(data);
//            if(data.length > 0){
//                $('#mediSearchTermSelect').empty();
//                for(var i = 0; i<data.length; i++){
//                    var $option = $('<option>',{
//                       text:data[i].kcdEngSyn,
//                       title:data[i].kcdKorSyn
//                    });
//                    $('#synonym').append($option);
//                }
//            }else{
//                $('#synonym').empty();
//                console.log('데이터 없음 처리.');
//            }
//        }
//    })
//}

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
            //for(var q = 0; q<items.length; q++){

        	//중복제거.
        	if(medi_check.mediCheckDetailList){
                for(var i = 0; i<medi_check.mediCheckDetailList.length; i++){
                    items = items.filter(function(item, idx, arr){
                        if(item.conceptId != medi_check.mediCheckDetailList[i].sctId){
                            return item;
                        }
                    });
                }
            }
            medi_check.searchList = JSON.parse(JSON.stringify(items));
            
            $('#mediSearchResultTable').prop("checked", false);
            
            if(items.length > 0){
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
                                html:StringMatch_func(items[i]['fsn']['term'], $('#mediCheck_term').val())
                            }),
                            $('<td>',{
                                class:'autoRuleCol',
                                id:items[i].conceptId + "_ruleCode",
                                text:items[i].ruleCode
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
            }else{
            	console.log("자료 없음 처리.");
            	$('#mediSearchResultTable tbody').empty();
                $('#mediSearchResultAllSelect').prop("checked", false);
                var $tr = $('<tr>').append(
                        //conceptId
                        $('<td>',{
                            text:"검색 결과가 없습니다.",
                            colspan:"4",
                            align: "center"
                        })
                    );
                $('#mediSearchResultTable tbody').append($tr);
            }
            
            $('#saveBtnDiv').removeClass('displayNone');
            $('.autoRuleCol').removeClass('displayNone');
            medi_check_detail_dynamic_func();
            //}
            alert_timeout();
        }
    });
}

//search term selectbox setting
//function mediCheckSearchTerm_setting(){
//    $('#mediSearchTermSelect').empty();
//    if(medi_check.autoRuleLog != null) {
//    	for(var i = 0; i<medi_check.autoRuleLog.length; i++){
//            var logInfo = medi_check.autoRuleLog[i];
//            if(logInfo.status == 'true' || logInfo.status == 'false'){
//                if(logInfo.searchTerm.trim().length > 0){
//                    var $option = $('<option>',{
//                        text:logInfo.searchTerm
//                    });
//                    $('#mediSearchTermSelect').append($option);
//                }
//            }
//        }
//    }
//}

//MEDICHECK목록 조회.
function mediCheckDetail_prevBtn_ajaxReq(){
    $.ajax({
        url: "/kexam/select/"+sessionStorage.getItem("medi_listOption"),
        type:'get',
        data:{
            mapVer:sessionStorage.getItem("mediCheck_mapVer"),
            mapStatCd:sessionStorage.getItem("mediCheck_mapStatCd"),
            kcdCd:sessionStorage.getItem("mediCheck_searchToKdCd").toUpperCase(),
            limit:sessionStorage.getItem("mediCheck_limit"),
            offset:parseInt(sessionStorage.getItem("mediCheck_offset"))
        },
        dataType:'json',
        async:false,
        success:function(data){
        	medi_check.mainMediCheckList = JSON.parse(JSON.stringify(data));
        }
    });
}

function medi_check_detail_dynamic_func(){
    $('input[name="medi_sctListCheck"]').on('change', function(e){
        if($('input[name="medi_sctListCheck"]:checked').length > 0){
            $('#mediRemoveBtn').prop('disabled', false);
        }else{
            $('#mediRemoveBtn').prop('disabled', true);
        }
    });

    $('input[name="mediSearchResultSaveCheckbox"]').on('change', function(e){
        if($('input[name="mediSearchResultSaveCheckbox"]:checked').length > 0){
            $('#mediSaveBtn').prop('disabled', false);
        }else{
            $('#mediSaveBtn').prop('disabled', true);
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

/**
 * 검색, 룰기반검색, 유사도 기반검색 완료알림 관련 함수.
 */
function alert_timeout() {
    $('.searchAlert').removeClass('displayNone');
    var timer = setTimeout(function () {
        if (!$('.searchAlert').hasClass('displayNone')) {
            $('.searchAlert').addClass('displayNone');
        }
    }, 1000);
}

/**
 * 속성추가 모달 세팅하는 펑션.
 * 추가되어있는 속성/값 리스트를 조회 >>> 조회된 리스트가 잇으면 수정버튼 show 없으면 저장버튼 show
 *
 * @param sctId
 */
function attr_val_modalSetting(sctId){
    $('#modal_sctId').text(sctId);
    $('#modal_kcdCd').text($('#kexCd').text());
    $('#modal_kcdKor').text($('#mediCheck_kor').text());
    $('#modal_kcdEng').text($('#mediCheck_eng').text());
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
                oriTpCd:'PROC'
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

/**
 * 검색 이벤트
 */
function medi_search_req(){
    var param = new Object();
    param.term = $('#mediCheck_term').val();

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
                                name:'mediSearchResultSaveCheckbox',
                                value:items[i].conceptId
                            })
                        )
                    );
                    $('#mediSearchResultTable tbody').append($tr);
                }
                $('#saveBtnDiv').removeClass('displayNone');
                $('.autoRuleCol').addClass('displayNone');
                medi_check_detail_dynamic_func();
            }else{
            	console.log("자료 없음 처리.");
            	$('#mediSearchResultTable tbody').empty();
                $('#mediSearchResultAllSelect').prop("checked", false);
                var $tr = $('<tr>').append(
                        //conceptId
                        $('<td>',{
                            text:"검색 결과가 없습니다.",
                            colspan:"4",
                            align: "center"
                        })
                    );
                $('#mediSearchResultTable tbody').append($tr);
            }
            alert_timeout();
        }
    })
}

/**
 * 유사도기준 검색 ajax
 */
function medi_similaritySearch(){
    var param = new Object();
    param.term = $('#mediCheck_term').val().replace("/", "");

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
                medi_check_detail_dynamic_func();
            }else{
                console.log("자료 없음 처리.");
                console.log(data);
                $('#mediSearchResultTable tbody').empty();
                $('#mediSearchResultAllSelect').prop("checked", false);
                var $tr = $('<tr>').append(
                        //conceptId
                        $('<td>',{
                            text:"검색 결과가 없습니다.",
                            colspan:"4",
                            align: "center"
                        })
                    );
                $('#mediSearchResultTable tbody').append($tr);
            }
            alert_timeout();
        }
    });
}