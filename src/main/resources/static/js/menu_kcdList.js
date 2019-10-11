function menu_kcdList_staticFunc(){
    $.ajax({
        url:"/getKcdList",
        type:'get',
        data:{
            limit:10,
            offset:0
        },
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.length > 0){
                $('#kcdListTable tbody').empty();
                for(var i = 0; i<data.length; i++){
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class:'kcdDetail',
                            text:data[i].kcdCd,
                            'data-kcdCd':data[i].kcdCd
                        }),
                        $('<td>', {
                            text:data[i].kcdKor
                        }),
                        $('<td>', {
                            text:data[i].kcdEng
                        }),
                        $('<td>', {
                            text:!data[i].sctId?'-':data[i].sctId
                        }),
                        $('<td>', {
                            text:'-'
                        }),
                        $('<td>', {
                            text:'-'
                        })
                    );
                    $('#kcdListTable tbody').append($tr);
                }
                dynamic_event_func();
            }else{
                console.log("자료 없음 처리.")
            }
        }
    });
}

function dynamic_event_func(){

    $('.kcdDetail').on('click', function(){
        console.log($(this));
        location.href = '/kcdDetailPage';
    });

}
