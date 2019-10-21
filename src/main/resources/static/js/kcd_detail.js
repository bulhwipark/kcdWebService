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

    //kcd 상세리스트 쪽 전체 선택.
    $('#allSelect').on('click',function(){
        if($(this).prop('checked')){
            $('input[name="sctListCheck"]').prop("checked", true);
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

    //동의어 이벤트트
   $('#synonym').on('change', function(){
        $('#term').val($('#synonym option:selected').val());
    });

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
                            text:data[i].sctId
                        }),
                        $('<td>',{
                            text:data[i].mapVer
                        }),
                        $('<td>',{
                            text:data[i].mapStatCd
                        }),
                        $('<td>',{
                            text:data[i].rvStatCd
                        }),
                        $('<td>',{
                            text:data[i].dispOdr
                        }),
                        $('<td>',{
                            text:data[i].udtDt
                        }),
                        $('<td>').append(
                            $('<input>',{
                                type:'checkbox',
                                name:'sctListCheck',
                                value:data[i].sctId
                            })
                        ),
                        $('<td>').append(
                            $('<button>',{
                                value:data[i].sctId,
                                class:'btn btn-sm btn-info',
                                text:'속성추가',
                                'data-toggle':'modal',
                                'data-target':'#attrValSetting_modal'
                            })
                        )
                    );
                    $('#kcdDetailTable tbody').append($tr);
                }
                kcd_detail_dynamic_func();
            }else{
                console.log('자료 없음 처리.');
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

    param.ecl = [];

    if($('#disorder').prop('checked')){
        param.ecl.push($('#disorder').val());
    }

    if($('#clinicalFinding').prop('checked')){
        param.ecl.push($('#clinicalFinding').val());
    }

    //disorder 체크해제되어있고, clinicalFinding 체크해제되어있을때만. ecl 세팅.
    if(!$('#disorder').prop('checked') && !$('#clinicalFinding').prop('checked')){
        if($('#ecl').val().length > 0){
            param.ecl.push($('#ecl').val());
        }
    }
    param.ecl = param.ecl.join(",");

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
                            text:items[i].conceptId
                        }),
                        //active
                        $('<td>',{
                            text:items[i].active
                        }),
                        //term
                        $('<td>',{
                            text:items[i]['fsn']['term']
                        }),
                        //moduleId
                        $('<td>',{
                            text:items[i].moduleId
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
        for(var j = 0; j<searchList.length; j++){
            if(searchList[j].conceptId == currentSelected[i].value){
                sctIdArr.push(searchList[j].conceptId);
            }
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

    param.ecl = [];

    if($('#disorder').prop('checked')){
        param.ecl.push($('#disorder').val());
    }

    if($('#clinicalFinding').prop('checked')){
        param.ecl.push($('#clinicalFinding').val());
    }

    //disorder 체크해제되어있고, clinicalFinding 체크해제되어있을때만. ecl 세팅.
    if(!$('#disorder').prop('checked') && !$('#clinicalFinding').prop('checked')){
        if($('#ecl').val().length > 0){
            param.ecl.push($('#ecl').val());
        }
    }
    param.ecl = param.ecl.join(",");
    console.log(param.ecl);
    $.ajax({
        url:'/autoRuleSet',
        type:'post',
        async:false,
        data:param,
        dataType:'json',
        success:function(data){
            if(data.status === "true"){
                var res = JSON.parse(data.result);
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
                        searchList = JSON.parse(JSON.stringify(items));
                    }
                    $('#searchResultTable tbody').empty();
                    $('#searchResultAllSelect').prop("checked", false);
                    for(var i = 0; i<items.length; i++){
                        var $tr = $('<tr>',{id:items[i].conceptId}).append(
                            //conceptId
                            $('<td>',{
                                text:items[i].conceptId
                            }),
                            //active
                            $('<td>',{
                                text:items[i].active
                            }),
                            //term
                            $('<td>',{
                                text:items[i]['fsn']['term']
                            }),
                            //moduleId
                            $('<td>',{
                                text:items[i].moduleId
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
                    kcd_detail_dynamic_func();
              }else{
                    //status false;
                    console.log(data.status);
                }
            }else{
                console.log("자료 없음 처리.");
            }
        }
    });
}

function alert_timeout(){
    $('#saveAlert').removeClass('displayNone');
    var timer = setTimeout(function(){
        if(!$('#saveAlert').hasClass('displayNone')){
            $('#saveAlert').addClass('displayNone');
        }
    }, 500);
}