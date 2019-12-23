function menu_medicalCheckList_staticFunc(){
	//초기 실행
    if(sessionStorage.getItem("mediCheck_storageCheck")){
        $('#medListOption').val(sessionStorage.getItem("mediCheck_listOption"));
        $('#mediCheck_searchToKexCd').val(sessionStorage.getItem("mediCheck_searchToKexCd"));
        medi_check.limit = parseInt(sessionStorage.getItem("mediCheck_limit"));
        medi_check.currentOffset = parseInt(sessionStorage.getItem("mediCheck_offset"));
        sessionStorage.clear();
        sessionStorage.setItem('mainPage', 'medicalCheckPage');
    }
    
    medicalCheck_totalCnt_req();
    medicalCheckList_req();
    
    //매핑상태 이벤트.
    $('#mediCheck_listOption').on('change', function(){
        medi_check.limit = 50;
        medi_check.currentOffset = 0;
        medicalCheckList_req();
    });

    $('#mediCheck_searchToKexCd').on('change', function(){
        medi_check.limit = 50;
        medi_check.currentOffset = 0;
        medicalCheckList_req();
    })
    .on('keyup', function(){
        medi_check.limit = 50;
        medi_check.currentOffset = 0;
        medicalCheckList_req();
    });
    
    //다음 버튼
    $('#mediCheck_next').on('click', function (e) {
        e.preventDefault();
        if ((medi_check.currentOffset + medi_check.limit) >= medi_check.totalCnt) {
        	medi_check.currentOffset = (medi_check.totalCnt - 1);
        } else {
        	medi_check.currentOffset = medi_check.currentOffset + medi_check.limit;
        }
        medicalCheckList_req();
    });

    //이전 버튼
    $('#mediCheck_prev').on('click', function (e) {
        e.preventDefault();
        if ((medi_check.currentOffset - medi_check.limit) <= 0) {
        	medi_check.currentOffset = 0
        } else {
        	medi_check.currentOffset = medi_check.currentOffset - medi_check.limit
        }
        medicalCheckList_req();
    });

    $('#mediCheck_excelDownloadBtn').on('click', function(){
      $('#mediCheck_searchForm')[0].submit();
    });

};

function menu_mediKexamList_dynamicFunc(){
    $('.mediCheckDetail').on('click', function(){
        console.log($(this));
        //상세화면에서의 페이징을 위해 세팅.
        sessionStorage.setItem("mediCheck_storageCheck", true);
        sessionStorage.setItem("mediCheck_limit", medi_check.limit);
        sessionStorage.setItem("mediCheck_offset", medi_check.currentOffset);
        sessionStorage.setItem("mediCheck_totalCnt", medi_check.totalCnt);
        sessionStorage.setItem("mediCheck_listOption", $('#mediCheck_listOption option:selected').val());
        sessionStorage.setItem("mediCheck_sctId", $(this).data('sctid'));
        sessionStorage.setItem("mediCheck_kdCd", $(this).text());
        sessionStorage.setItem("mediCheck_index", $(this).data('index'));
        sessionStorage.setItem("mediCheck_searchToKexCd", $('#mediCheck_searchToKexCd').val());
        sessionStorage.setItem("mediCheck_mapVer", $('#mediCheck_mapVer').val());
        sessionStorage.setItem("mediCheck_mapStatCd", $('#mediCheck_mapStatCd').val());
        location.href = '/mediCheckDetailPage?kexCd=' + $(this).text() + '&mapVer=' + $('#mediCheck_mapVer').val();
    });
};

function medicalCheck_totalCnt_req(){
    $.ajax({
        url:'/getMedicalCheckTotalCnt',
        type:'post',
        data:{
            mappingStatus:$('#mediCheck_listOption option:selected').val(),
            mapVer:$('#mediCheck_mapVer').val(),
            mapStatCd: $('#mediCheck_mapStatCd').val()
        },
        dataType:'json',
        async:false,
        success:function(data){
        	medi_check.mediTotalCnt = data.kexTotalCnt;
        	medi_check.totalCnt = data.totalCnt;
            $('#kexTotalCnt').text(data.kexTotalCnt?data.kexTotalCnt:'-');
            $('#mediCheck_totalCnt').text(data.totalCnt?data.totalCnt:'-');
        }
    })
}

function medicalCheckList_req(){
    $.ajax({
        url:'/kexam/select/' + $('#mediCheck_listOption option:selected').val(),
        type:'post',
        data:{
            kexCd:$('#mediCheck_searchToKexCd').val(),
            limit: medi_check.limit,
            offset: medi_check.currentOffset
        },
        dataType:'json',
        success:function(data){
            medi_check.mainMediCheckList = JSON.parse(JSON.stringify(data));
            if (data.length > 0) {
                $('#mediCheck_ListTable tbody').empty();
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
                                text: 'ko : ' + (data[i].kexKor?data[i].kexKor:'-')
                            }),
                            $('<div>', {
                                text: 'en : ' + (data[i].kexEng?data[i].kexEng:'-')
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
                    $('#mediCheck_ListTable tbody').append($tr);
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
                $('#mediCheck_currentPage').text((medi_check.currentOffset+1) + '/' + medi_check.totalCnt);
            }
        }
    })
}