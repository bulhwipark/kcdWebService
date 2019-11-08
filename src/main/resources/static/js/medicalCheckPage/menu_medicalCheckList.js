function menu_medicalCheckList_staticFunc(){
    medicalCheckList_req();
};

function menu_mediKexamList_dynamicFunc(){
    $('.mediCheckDetail').on('click', function(){
        console.log($(this));
        //상세화면에서의 페이징을 위해 세팅.
        // sessionStorage.setItem("mediCheck_storageCheck", true);
        // sessionStorage.setItem("mediCheck_limit", medi.limit);
        // sessionStorage.setItem("mediCheck_offset", medi.currentOffset);
        // sessionStorage.setItem("mediCheck_totalCnt", medi.totalCnt);
        // sessionStorage.setItem("mediCheck_listOption", $('#medListOption option:selected').val());
        // sessionStorage.setItem("mediCheck_sctId", $(this).data('sctid'));
        // sessionStorage.setItem("mediCheck_kdCd", $(this).text());
        // sessionStorage.setItem("mediCheck_index", $(this).data('index'));
        // sessionStorage.setItem("mediCheck_searchToKdCd", $('#searchToKdCd').val());
        // sessionStorage.setItem("mediCheck_mapVer", $('#medVersion').val());
        // sessionStorage.setItem("mediCheck_mapStatCd", $('#medMapStatCd').val());
        location.href = '/mediCheckDetailPage?kexCd=' + $(this).text() + '&mapVer=' + $('#kexMapVer').val();
    });
}

function medicalCheckList_req(){
    $.ajax({
        url:'/kexam/select/' + $('#medicalCheck_listOption option:selected').val(),
        type:'post',
        data:{
            kexamCd:'',
            limit: medi_check.limit,
            offset: medi_check.currentOffset
        },
        dataType:'json',
        success:function(data){
            medi_check.mainMediCheckList = JSON.parse(JSON.stringify(data));
            if (data.length > 0) {
                $('#mediCheckListTable tbody').empty();
                for (var i = 0; i < data.length; i++) {
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class: 'mediCheckDetail',
                            text: data[i].kexCd,
                            'data-kexCd': data[i].kexCd,
                            //'data-sctid':!data[i].sctId?'-':data[i].sctId,
                            'data-index': i
                        }),
                        $('<td>').append(
                            $('<div>', {
                                text: 'ko : ' + data[i].kexKor
                            }),
                            $('<div>', {
                                text: 'en : ' + data[i].kexEng
                            })
                        ),
                        $('<td>', {
                            class: 'mediSctIdDetail',
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
                    $('#mediCheckListTable tbody').append($tr);
                }
                menu_mediKexamList_dynamicFunc();

                /*
                if(medi.currentOffset == 0){
                    $('#medi_prev').addClass('displayNone');
                }else{
                    $('#medi_prev').removeClass('displayNone');
                }

                if(medi.currentOffset == medi.totalCnt){
                    $('#medi_next').addClass('displayNone');
                }else{
                    $('#medi_next').removeClass('displayNone');
                }

                $('#medi_currentPage').text((medi.currentOffset+1) + '/' + medi.totalCnt);
                */
            }
        }
    })
}