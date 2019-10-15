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