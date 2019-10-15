function menu_kcdList_staticFunc(){
    kcdList_req("/selectAll");

    $('#listOption').on('change', function(){
        kcdList_req($(this).val());
    });
}

function dynamic_event_func(){
    $('.kcdDetail').on('click', function(){
        location.href = '/kcdDetailPage?kcdCd=' + $(this).text();
    });

    $('.sctIdDetail').on('click', function(){
      window.open(
           '/sctIdDetail',
           'Detail',
           'width=1200,height=800,left=200,'
       );
      $('#sctId').val($(this).text());
    });
}

function kcdList_req(url){
    $.ajax({
        url: url,
        type:'get',
        data:{
            limit:100,
            offset:0
        },
        dataType:'json',
        success:function(data){
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
                            class:'sctIdDetail',
                            text:!data[i].sctId?'-':data[i].sctId,
                            'data-sctId':!data[i].sctId?'-':data[i].sctId,
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