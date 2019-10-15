function menu_kcdList_staticFunc(){
    kcdList_req("/selectAll");

    $('.kcdSearchOption').on('change', function(){
        kcdList_req();
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

function kcdList_req(){
    $.ajax({
        url: $('#listOption option:selected').val(),
        type:'get',
        data:{
            mapVer:$('#version option:selected').val(),
            limit:100,
            offset:0
        },
        dataType:'json',
        success:function(data){
            if(data.length > 0){
                $('#kcdListTable tbody').empty();
                $('#totalCnt').text(data.length?data.length:'-');
                for(var i = 0; i<data.length; i++){
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class:'kcdDetail',
                            text:data[i].kcdCd,
                            'data-kcdCd':data[i].kcdCd
                        }),
                        $('<td>').append(
                            $('<div>',{
                                text:'ko : ' + data[i].kcdKor
                            }),
                            $('<div>',{
                                text:'en : ' + data[i].kcdEng
                            }),
                        ),
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