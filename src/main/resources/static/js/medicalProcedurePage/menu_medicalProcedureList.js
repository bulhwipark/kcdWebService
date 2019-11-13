function menu_medicalProcedureList_staticFunc() {
    //초기 실행
    mediProcList_req();


    $('#mediProc_listOption').on('change', function(){
        mediProcList_req();
    });
}

function menu_medicalProcedureList_dynamicFunc() {
    $('.mediProcSctIdDetail').on('click', function () {
        window.open(
            'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1=' + $(this).text(),
            'Detail',
            'width=1200,height=800,left=200,'
        );
        $('#sctId').val($(this).text());
    });

    $('.mediProcDetail').on('click', function(){
        console.log($(this));
        //상세화면에서의 페이징을 위해 세팅.
        sessionStorage.setItem("mediProc_storageCheck", true);
        sessionStorage.setItem("mediProc_limit", medi.limit);
        sessionStorage.setItem("mediProc_offset", medi.currentOffset);
        sessionStorage.setItem("mediProc_totalCnt", medi.totalCnt);
        sessionStorage.setItem("mediProc_listOption", $('#medListOption option:selected').val());
        sessionStorage.setItem("mediProc_sctId", $(this).data('sctid'));
        sessionStorage.setItem("mediProc_kdCd", $(this).text());
        sessionStorage.setItem("mediProc_index", $(this).data('index'));
        sessionStorage.setItem("mediProc_searchToKdCd", $('#searchToKdCd').val());
        sessionStorage.setItem("mediProc_mapVer", $('#medVersion').val());
        sessionStorage.setItem("mediProc_mapStatCd", $('#medMapStatCd').val());
        location.href = '/medDetailPage?kdCd=' + $(this).text() + '&mapVer=' + $('#medVersion').val();
    });

}


function mediProcList_req() {
    $.ajax({
        url: '/medicine/select/' + $('#mediProc_listOption option:selected').val(),
        type: 'post',
        data: {
            kdCd: $('#searchToKdCd').val(),
            limit: medi_proc.limit,
            offset: medi_proc.currentOffset
        },
        dataType: 'json',
        success: function (data) {
            medi_proc.mainMedList = JSON.parse(JSON.stringify(data));
            if (data.length > 0) {
                $('#mediProc_procListTable tbody').empty();
                for (var i = 0; i < data.length; i++) {
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class: 'mediProcDetail',
                            text: data[i].kdCd,
                            'data-kdCd': data[i].kdCd,
                            //'data-sctid':!data[i].sctId?'-':data[i].sctId,
                            'data-index': i
                        }),
                        $('<td>').append(
                            $('<div>', {
                                text: 'ko : ' + data[i].drugNmKor
                            }),
                            $('<div>', {
                                text: 'en : ' + data[i].drugNmEng
                            })
                        ),
                        $('<td>', {
                            class: 'mediProcSctIdDetail',
                            text: data[i].sctId ? data[i].sctId : '-'
                        }),
                        $('<td>', {
                            text: data[i].sctTerm ? data[i].sctTerm : '-'
                        }),
                        $('<td>', {
                            text: data[i].mapStatCd ? data[i].mapStatCd : '-'
                        }),
                        $('<td>', {
                            text: data[i].udtDt
                        }),
                    );
                    $('#mediProc_procListTable tbody').append($tr);
                }
               // menu_medicineList_dynamicFunc();

                if(medi_proc.currentOffset == 0){
                    $('#mediProc_prev').addClass('displayNone');
                }else{
                    $('#mediProc_prev').removeClass('displayNone');
                }

                if(medi_proc.currentOffset == medi_proc.totalCnt){
                    $('#mediProc_next').addClass('displayNone');
                }else{
                    $('#mediProc_next').removeClass('displayNone');
                }

                $('#mediProc_currentPage').text((medi_proc.currentOffset+1) + '/' + medi_proc.totalCnt);
                menu_medicalProcedureList_dynamicFunc();
            }
        }
    })
}
