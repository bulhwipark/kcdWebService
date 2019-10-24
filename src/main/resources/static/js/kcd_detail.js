function kcd_detail_static_func(){
    get_kcdCdObject_req();
    get_kcdDetail_list();
    termSynonym();
    autoRuleSet();

    //ecl 클릭시 disorder, clinicalFinding 체크해제.
    $('#ecl').on('click',function(){
        $('#disorder').prop('checked', false);
        $('#clinicalFinding').prop('checked', false);
    });

    //disorder, clinicalfinding 클릭시 좌측 ecl 인풋에 텍스트는 삭제.
    $('input[name="defaultRule"]').on('click', function(){
        $('#ecl').val('');
    });

    //kcd 상세리스트 쪽 전체 선택.
    $('#allSelect').on('click',function(){
        if($(this).prop('checked')){
            for(var i = 0; i<$('input[name="sctListCheck"]').length; i++){
                if(!$('input[name="sctListCheck"]')[i].disabled){
                    $($('input[name="sctListCheck"]')[i]).prop("checked", true);
                }
            }
        }else{
            $('input[name="sctListCheck"]').prop("checked", false);
        }

        if($('input[name="sctListCheck"]:checked').length > 0){
            $('#removeBtn').prop('disabled', false);
        }else{
            $('#removeBtn').prop('disabled', true);
        }
    });

    //검색목록 쪽 전체선택
    $('#searchResultAllSelect').on('click', function(){
        if($(this).prop('checked')){
            $('input[name="searchResultSaveCheckbox"]').prop('checked', true);
        }else{
            $('input[name="searchResultSaveCheckbox"]').prop('checked', false);
        }

        if($('input[name="searchResultSaveCheckbox"]:checked').length > 0){
            $('#saveBtn').prop('disabled', false);
        }else{
            $('#saveBtn').prop('disabled', true);
        }
    });

    //동의어 이벤트
   $('#synonym').on('change', function(){
        $('#term').val($('#synonym option:selected').val());
    });

   $('.attrSelect').on('change', function(){
       getValueList($(this).data('num'));
   });

   $('.valSelect').on('change', function(){
       $('#attrSaveBtn').attr('disabled', false);
   });

   $('.attrRemove').on('click', function(){
       deleteAttrVal($(this).data('num'));
   })
}

function kcd_detail_dynamic_func(){
    $('input[name="sctListCheck"]').on('change', function(e){
        if($('input[name="sctListCheck"]:checked').length > 0){
            $('#removeBtn').prop('disabled', false);
        }else{
            $('#removeBtn').prop('disabled', true);
        }
    });

    $('input[name="searchResultSaveCheckbox"]').on('change', function(e){
        if($('input[name="searchResultSaveCheckbox"]:checked').length > 0){
            $('#saveBtn').prop('disabled', false);
        }else{
            $('#saveBtn').prop('disabled', true);
        }
    });

    $('.sctIdDetail').on('click', function(){
        window.open(
            'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1='+$(this).text(),
            'Detail',
            'width=1200,height=800,left=200,'
        );
        $('#sctId').val($(this).text());
    });

    /*$('.addAttrBtn').on('click', function(){
        $('#modal_kcdKor').text($('#kcdKor').text());
        $('#modal_kcdEng').text($('#kcdEng').text());
        kcdAttrList_ajax($(this).val());
    })*/
}

/**
 * kcd info
 */
function get_kcdCdObject_req(){
    $.ajax({
        url:'getKcdCdInfo',
        type:'post',
        async:false,
        data:{
            kcdCd:$('#kcdCd').text()
        },
        dataType:'json',
        success:function(data){
            if(data){
                $('#kcdKor').text(data.kcdKor);
                $('#kcdEng').text(data.kcdEng);
                $('#term').val(data.kcdEng);
            }
        }
    });
}


/**
 * kcd 목록 이벤트
 */
function get_kcdDetail_list(){
    $.ajax({
        url:'getkcdDetailList',
        type:'post',
        async:false,
        data:{
            kcdCd:$('#kcdCd').text()
        },
        dataType:'json',
        success:function(data){
            if(data.length > 0){
                kcdDetailList = JSON.parse(JSON.stringify(data));
                $('#kcdDetailTable tbody').empty();
                $('#allSelect').prop("checked", false);
                for(var i = 0; i<data.length; i++){
                    var $tr = $('<tr>').append(
                        $('<td>',{
                            class:'sctIdDetail',
                            text:data[i].sctId,
                            'data-sctid':data[i].sctId
                        }),
                        $('<td>',{
                            text:data[i].sctTerm
                        }),
                        $('<td>',{
                            text:data[i].mapStatNm +"("+data[i].mapStatCd+")"
                        }),
                        $('<td>',{
                            text:data[i].udtDt
                        }),
                        $('<td>').append(
                            $('<input>',{
                                type:'checkbox',
                                name:'sctListCheck',
                                id:"delCheckbox_"+ data[i].sctId,
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
                    $('#kcdDetailTable tbody').append($tr);
                }
                kcd_detail_dynamic_func();
            }else{
                console.log('자료 없음 처리.');
                kcdDetailList = JSON.parse(JSON.stringify(data));
                $('#kcdDetailTable tbody').empty();
                $('#allSelect').prop("checked", false);
                var $tr = $('<tr>').append(
                    $('<td>',{
                        colspan:8,
                        text:'조회 결과가 없습니다.',
                        style:'text-align:center;'
                    })
                );
                $('#kcdDetailTable tbody').append($tr);
            }
        }
    })
}

/**
 * 검색 이벤트
 */
function search_req(){
    var param = new Object();
    param.term = $('#term').val();

    if($('input[name="defaultRule"]:checked').val()){
        param.ecl = $('input[name="defaultRule"]:checked').val();
    }else{
        param.ecl = $('#ecl').val();
    }

    //disorder 체크해제되어있고, clinicalFinding 체크해제되어있을때만. ecl 세팅.
    // if(!$('#disorder').prop('checked') && !$('#clinicalFinding').prop('checked')){
    //     if($('#ecl').val().length > 0){
    //         param.ecl.push($('#ecl').val());
    //     }
    // }
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
                for(var i = 0; i<kcdDetailList.length; i++){
                    items = items.filter(function(item, idx, arr){
                        if(item.conceptId != kcdDetailList[i].sctId){
                            return item;
                        }
                    });
                }
                searchList = JSON.parse(JSON.stringify(items));
                $('#searchResultTable tbody').empty();
                $('#searchResultAllSelect').prop("checked", false);
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
                    $('#searchResultTable tbody').append($tr);
                }
                $('#saveBtnDiv').removeClass('displayNone');
                $('.autoRuleCol').addClass('displayNone');
                kcd_detail_dynamic_func();
            }else{
                console.log("자료 없음 처리.");
            }
        }
    })
}

/**
 * 저장 버튼 이벤트
 */
function saveBtn_req(){
    var currentSelected = $('input[name="searchResultSaveCheckbox"]:checked');
    var sctIdArr = [];

    //기존 검색된 리스트에서 선택된(currentSelected) sctId로 검색.
    for(var i  = 0; i<currentSelected.length; i++){
        if(searchList){
            for(var j = 0; j<searchList.length; j++){
                if(searchList[j].conceptId == currentSelected[i].value){
                    sctIdArr.push(searchList[j].conceptId);
                }
            }
        }else{
            sctIdArr.push(currentSelected[i].value);
        }
    }
    $.ajax({
        url:'/insertSearchList',
        type:'post',
        data:{
            oriCd : $('#kcdCd').text(),
            mapVer : mapVer,
            mapStatCd:5,
            oriTpCd:'kcd',
            sctId:sctIdArr.join(",")
        },
        success:function(){
            get_kcdDetail_list();
            for(var i = 0; i<sctIdArr.length; i++){
                var sctId = sctIdArr[i];
                searchList = searchList.filter(function(item, idx, arr){
                    if(item.conceptId != sctId){
                        return item;
                    }
                });
                $('#' + sctId).remove();
            }
            alert_timeout();
        }
    })
}

/**
 * kcd 목록 삭제 이벤트.
 */
function deleteKcdList_req(){
    var currentSelected = $('input[name="sctListCheck"]:checked');
    var sctIdArr = [];
    for(var i = 0; i<currentSelected.length; i++){
        sctIdArr.push(currentSelected[0].value);
    }
   $.ajax({
        url:'/deleteKcdList',
        type:'post',
        data:{
            oriCd: $('#kcdCd').text(),
            sctId: sctIdArr.join(',')
        },
        success:function(){
            get_kcdDetail_list();
            search_req();
            autoRuleSet();
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

/**
 * 자동룰 반영
 */
function autoRuleSet(){
    var param = new Object();
    param.term = $('#term').val();

    if($('input[name="defaultRule"]:checked').val()){
        param.ecl = $('input[name="defaultRule"]:checked').val();
    }else{
        param.ecl = $('#ecl').val();
    }

    //disorder 체크해제되어있고, clinicalFinding 체크해제되어있을때만. ecl 세팅.
    // if(!$('#disorder').prop('checked') && !$('#clinicalFinding').prop('checked')){
    //     if($('#ecl').val().length > 0){
    //         param.ecl.push($('#ecl').val());
    //     }
    // }
    // param.ecl = param.ecl.join(",");

    $.ajax({
        url:'/autoRuleSet',
        type:'post',
        async:false,
        data:param,
        dataType:'json',
        success:function(data){
            console.log(data);
            $('#searchResultTable tbody').empty();
            for(var q = 0; q<data.length; q++){

                if(data[q].status === "true"){
                    var res = JSON.parse(data[q].result);
                    if(res['items'].length > 0){
                        var items = res['items'];

                        //중복제거.
                        if(kcdDetailList){
                            for(var i = 0; i<kcdDetailList.length; i++){
                                items = items.filter(function(item, idx, arr){
                                    if(item.conceptId != kcdDetailList[i].sctId){
                                        return item;
                                    }
                                });
                            }
                        }
                        searchList = JSON.parse(JSON.stringify(items));

                        $('#searchResultAllSelect').prop("checked", false);
                        for(var i = 0; i<items.length; i++){
                            if($('#searchResultTable tbody').children('#' + items[i].conceptId).length > 0){
                                $('#searchResultTable tbody tr').children('#'+items[i].conceptId+'_ruleCode').text(
                                    $('#searchResultTable tbody tr').children('#'+items[i].conceptId+'_ruleCode').text()+ ', ' + data[q].ruleCode
                                )
                            }else{
                                var $tr = $('<tr>',{id:items[i].conceptId}).append(
                                    //conceptId
                                    $('<td>',{
                                        text:items[i].conceptId
                                    }),
                                    //term
                                    $('<td>',{
                                        text:items[i]['fsn']['term']
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
                                $('#searchResultTable tbody').append($tr);
                            }

                        }
                        $('#saveBtnDiv').removeClass('displayNone');
                        $('.autoRuleCol').removeClass('displayNone');
                        kcd_detail_dynamic_func();
                  }else{
                        //status false;
                        console.log(data[q].status);
                    }
                }
            }
        }
    });
}

/**
 * 유사도기준 검색 ajax
 */
function similaritySearch(){
    var param = new Object();
    param.term = $('#term').val();

    if($('input[name="defaultRule"]:checked').val()){
        param.ecl = $('input[name="defaultRule"]:checked').val();
    }else{
        param.ecl = $('#ecl').val();
    }

    /*//disorder 체크해제되어있고, clinicalFinding 체크해제되어있을때만. ecl 세팅.
    if(!$('#disorder').prop('checked') && !$('#clinicalFinding').prop('checked')){
        if($('#ecl').val().length > 0){
            param.ecl.push($('#ecl').val());
        }
    }
    param.ecl = param.ecl.join(",");*/
    $.ajax({
        url:'/similaritySearch',
        type:'post',
        data:param,
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.status === "true"){
                $('#searchResultTable tbody').empty();
                var obj = JSON.parse(data.result);
                for(var i = 0; i<obj.items.length; i++){
                    var item = obj.items[i];
                    var $tr = $('<tr>', {id:item.conceptId}).append(
                        $('<td>',{
                            text:item.conceptId
                        }),
                        $('<td>',{
                            text:item.fsn.term
                        }),
                        $('<td>',{
                            text:data.ruleCode
                        }),
                        //checkBox
                        $('<td>').append(
                            $('<input>',{
                                type:'checkbox',
                                name:'searchResultSaveCheckbox',
                                value:item.conceptId
                            })
                        )
                    );

                    $('#searchResultTable tbody').append($tr);
                }

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
    $('#modal_kcdKor').text($('#kcdKor').text());
    $('#modal_kcdEng').text($('#kcdEng').text());
    $('.attrRemove').hide();
    var infoList = null;
    var ajax_res = getMapAttrValList_ajax();
    ajax_res.done(function(attrValList){
        console.log(attrValList);
        infoList = JSON.parse(JSON.stringify(attrValList));
         $.ajax({
            url:'/getKcdAttrList',
            type:'post',
            data:{
                sctId:sctId
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
                            text:data[i].cmSctTerm,
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
        $('#val_select'+(i+1)).val(infoList[i].valSctId);
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
            console.log(data);
            $('#val_select' + currentNum).empty();
            $('#val_select' + currentNum).append(
                $('<option>',{
                    value:'',
                    text:' 값을 선택하세요.',
                    disabled:true,
                    selected:true
                })
            );
            if(data.length > 0){
                for(var i = 0; i<data.length; i++){
                    var $option = $('<option>',{
                        text:data[i].cmSctTerm,
                        value:data[i].attSctId
                    });
                    $('#val_select'+currentNum).append($option);
                }
                $('#val_select'+currentNum).attr('disabled', false);
            }else{
                console.log("리스트 없음.")
            }
        }
    })
}

function attr_val_save(){
    var attrOptList = $('.attrSelect option:selected');
    var attrParam = [];
    var valParam = [];
    for(var i = 0; i<attrOptList.length; i++){
        if(attrOptList[i].value){
            if($('#val_select' + (i+1) + " option:selected").val()){
                attrParam.push(attrOptList[i].value);
                valParam.push($('#val_select' + (i+1) + " option:selected").val());
            }
        }
    }

    $.ajax({
        url:'/attrValSave',
        type:'post',
        data:{
            oriTpCd:'D',
            oriCd:$('#modal_kcdCd').text(),
            mapVer:$('#mapVer').val(),
            sctId:$('#modal_sctId').text(),
            // attSctId:$('#attr_select option:selected').val(),
            // valSctId:$('#val_select option:selected').val(),
            mapStatCd:'80',
            attrParam:attrParam.join(','),
            valParam:valParam.join(',')
        },
        success:function(){
            console.log("save")
        }
    })
}

function attr_val_update(){
    var attrOptList = $('.attrSelect option:selected');
    var attrParam = [];
    var valParam = [];
    for(var i = 0; i<attrOptList.length; i++){
        if(attrOptList[i].value){
            if($('#val_select' + (i+1) + " option:selected").val()){
                attrParam.push(attrOptList[i].value);
                valParam.push($('#val_select' + (i+1) + " option:selected").val());
            }
        }
    }

    $.ajax({
        url:'/attrValUpdate',
        type:'post',
        data:{
            oriTpCd:'D',
            oriCd:$('#modal_kcdCd').text(),
            mapVer:$('#mapVer').val(),
            sctId:$('#modal_sctId').text(),
            // attSctId:$('#attr_select option:selected').val(),
            // valSctId:$('#val_select option:selected').val(),
            mapStatCd:'80',
            attrParam:attrParam.join(','),
            valParam:valParam.join(',')
        },
        success:function(){
            console.log("update");
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

function deleteAttrVal(num){
    $.ajax({
        url:'/deleteAttrVal',
        type:'post',
        data:{
            oriCd:$('#modal_kcdCd').text(),
            sctId:$('#modal_sctId').text(),
            attSctId:$('#attr_select'+num+' option:selected').val(),
            valSctId:$('#val_select'+num+ ' option:selected').val()
        },
        success:function(){
            $('#attr_select'+num).val('');
            $('#val_select'+num).empty().attr('disabled', true);
            console.log('delete');
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