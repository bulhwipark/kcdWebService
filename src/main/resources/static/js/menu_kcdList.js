function menu_kcdList_staticFunc(){
    //초기 실행
    kcdList_totalCount_req();
    kcdList_req();

    //이벤트바인딩.
    $('.kcdSearchOption').on('change', function(){
        currentOffset = 0;
        totalCnt = 0;
        kcdList_totalCount_req();
        kcdList_req();
    });

    $('#next').on('click', function(){
        if((currentOffset+limit) >= totalCnt ){
            currentOffset = (totalCnt-1);
        }else{
            currentOffset = currentOffset+limit;
        }

        kcdList_req();
    });

    $('#prev').on('click', function(){
        if(currentOffset !== 0){
            currentOffset = currentOffset-limit
        }
        kcdList_req();
    });
}

function dynamic_event_func(){
    $('.kcdDetail').on('click', function(){
        location.href = '/kcdDetailPage?kcdCd=' + $(this).text() + '&mapVer=' + $('#version option:selected').val();
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

/**
 * kcd 목록
 */
function kcdList_req(){
    $.ajax({
        url: "/select"+$('#listOption option:selected').val(),
        type:'get',
        data:{
            mapVer:$('#version option:selected').val(),
            limit:limit,
            offset:currentOffset
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

                if(currentOffset == 0){
                    $('#prev').addClass('displayNone');
                }else{
                    $('#prev').removeClass('displayNone');
                }

                if(currentOffset == totalCnt){
                    $('#next').addClass('displayNone');
                }else{
                    $('#next').removeClass('displayNone');
                }

                $('#currentPage').text((currentOffset+1) + '/' + totalCnt);
            }else{
                console.log("자료 없음 처리.")
            }
        }
    });
}

/**
 * 토탈카운트
 */
function kcdList_totalCount_req(){
    $.ajax({
        url:'/getTotalCount',
        type:'post',
        async:false,
        data:{
            mappingStatus:$('#listOption option:selected').val(),
            mapVer:$('#version option:selected').val(),
        },
        dataType:'json',
        success:function(data){
            console.log(data);
            kcdTotalCnt = data.kcdTotalCnt;
            totalCnt = data.totalCnt;
            $('#kcdTotalCnt').text(data.kcdTotalCnt?data.kcdTotalCnt:'-');
            $('#totalCnt').text(data.totalCnt?data.totalCnt:'-');
        }
    })
}