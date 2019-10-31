function menu_kcdList_staticFunc(){
    //초기 실행
    if(sessionStorage.getItem("storageCheck")){
        $('#listOption').val(sessionStorage.getItem("listOption"));
        $('#searchToKcdCd').val(sessionStorage.getItem("searchToKcdCd"));
        limit = parseInt(sessionStorage.getItem("limit"));
        currentOffset = parseInt(sessionStorage.getItem("offset"));
        sessionStorage.clear();
    }
    kcdList_getMappingStatusCd();
    kcdList_totalCount_req();
    kcdList_req();

    //이벤트바인딩.
    $('.kcdSearchOption').on('change', function(){
        currentOffset = 0;
        totalCnt = 0;
        kcdList_totalCount_req();
        kcdList_req();
    });

    $('#searchToKcdCd').on('keyup', function(){
        currentOffset = 0;
        totalCnt = 0;
        kcdList_totalCount_req();
        kcdList_req();
    });

    //다음 버튼
    $('#next').on('click', function(e){
        e.preventDefault();
        if((currentOffset+limit) >= totalCnt ){
            currentOffset = (totalCnt-1);
        }else{
            currentOffset = currentOffset+limit;
        }
        kcdList_req();
    });

    //이전 버튼
    $('#prev').on('click', function(e){
        e.preventDefault();
        if((currentOffset-limit) <= 0){
            currentOffset = 0
        }else{
            currentOffset = currentOffset-limit
        }
        kcdList_req();
    });

    //excel download
    $('#excelDownloadBtn').on('click', function(e){
        e.preventDefault();
        $('#searchForm')[0].submit();
    });
}

function dynamic_event_func(){
    $('.kcdDetail').on('click', function(){
        //상세화면에서의 페이징을 위해 세팅.
        sessionStorage.setItem("storageCheck", true);
        sessionStorage.setItem("limit", limit);
        sessionStorage.setItem("offset", currentOffset);
        sessionStorage.setItem("totalCnt", totalCnt);
        sessionStorage.setItem("listOption", $('#listOption option:selected').val());
        sessionStorage.setItem("sctId", $(this).data('sctid'));
        sessionStorage.setItem("kcdCd", $(this).text());
        sessionStorage.setItem("index", $(this).data('index'));
        sessionStorage.setItem("searchToKcdCd", $('#searchToKcdCd').val());
        sessionStorage.setItem("mapVer", $('#version').val());
        sessionStorage.setItem("mapStatCd", $('#mapStatCd').val());

        location.href = '/kcdDetailPage?kcdCd=' + $(this).text() + '&mapVer=' + $('#version').val();
    });

    $('.sctIdDetail').on('click', function(){
      window.open(
           'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1='+$(this).text(),
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
            mapVer:$('#version').val(),
            mapStatCd:$('#mapStatCd').val(),
            kcdCd:$('#searchToKcdCd').val().toUpperCase(),
            limit:limit,
            offset:currentOffset
        },
        dataType:'json',
        success:function(data){
            if(data.length > 0){
                $('#kcdListTable tbody').empty();
                mainKcdList = JSON.parse(JSON.stringify(data));
                sessionStorage.setItem("mainKcdList", JSON.stringify(data));
                for(var i = 0; i<data.length; i++){
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class:'kcdDetail',
                            text:data[i].kcdCd,
                            'data-kcdCd':data[i].kcdCd,
                            'data-sctid':!data[i].sctId?'-':data[i].sctId,
                            'data-index':i
                        }),
                        $('<td>').append(
                            $('<div>',{
                                text:'ko : ' + data[i].kcdKor
                            }),
                            $('<div>',{
                                text:'en : ' + data[i].kcdEng
                            })
                        ),
                        $('<td>', {
                            class: !data[i].sctId?'':'sctIdDetail',
                            text:!data[i].sctId?'-':data[i].sctId,
                            'data-sctId':!data[i].sctId?'-':data[i].sctId,
                        }),
                        $('<td>', {
                            text: data[i].sctTerm
                        }),
                        $('<td>', {
                            
                            text: (!data[i].mapStatCd? '': data[i].mapStatNm +"("+data[i].mapStatCd+")")
                        }),
                        $('<td>', {
                            text: data[i].udtDt
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
            mapVer:$('#version').val(),
            mapStatCd: $('#mapStatCd').val()

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

/**
 * 매핑상태코드.
 */
function kcdList_getMappingStatusCd(){
    $.ajax({
        url:'/getMappingStatusCd',
        type:'post',
        async:false,
        dataType:'json',
        success:function(data){
            console.log(data);
            $('#mapStatCd').empty();
            if(data.length > 0){
                $('#mapStatCd').append(
                    $('<option>',{
                        text:'전체',
                        value:'All'
                    })
                );

                for(var i = 0; i<data.length; i++){
                    var $option = $('<option>',{
                       text:data[i].cdId + ' : ' + data[i].cdDscrt,
                       value: data[i].cdId
                    });
                    $('#mapStatCd').append($option);
                }
            }else{
                console.log('데이터 없음 처리');
            }
        }
    })
}
