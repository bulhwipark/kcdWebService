function kcd_detail_static_func(){
    get_kcdCdObject_req();
    get_kcdDetail_list();
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
                        })
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
    $.ajax({
        url:'/search',
        type:'post',
        data:{
            ecl:$('#ecl').val(),
            term:$('#term').val()
        },
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
                                name:'testing'
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