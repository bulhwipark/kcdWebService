function kcd_detail_static_func(){
    get_kcdCdObject_req();
    get_kcdDetail_list();

    //ecl 클릭시 disorder, clinicalFinding 체크해제.
    $('#ecl').on('click',function(){
        $('#disorder').prop('checked', false);
        $('#clinicalFinding').prop('checked', false);
    })


}

function get_kcdCdObject_req(){
    $.ajax({
        url:'getKcdCdInfo',
        type:'post',
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

function get_kcdDetail_list(){
    $.ajax({
        url:'getkcdDetailList',
        type:'post',
        data:{
            kcdCd:$('#kcdCd').text()
        },
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.length > 0){
                $('#kcdDetailTable tbody').empty();
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
                    );
                    $('#kcdDetailTable tbody').append($tr);
                }
            }else{
                console.log('자료 없음 처리.');
            }
        }
    })
}

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
    console.log(param);

    $.ajax({
        url:'/search',
        type:'post',
        data:param,
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data['items'].length > 0){
                var items = data['items'];
                $('#searchResultTable tbody').empty();
                for(var i = 0; i<items.length; i++){
                    var $tr = $('<tr>').append(
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
                                name:'saveCheckbox',
                                value:JSON.stringify(items[i])
                            })
                        )
                    );
                    $('#searchResultTable tbody').append($tr);
                }
                $('#saveBtnDiv').removeClass('displayNone');
            }else{
                console.log("자료 없음 처리.");
            }
        }
    })
}