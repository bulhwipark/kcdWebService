function menu_medicineList_staticFunc() {
    //초기 실행
    if(sessionStorage.getItem("medi_storageCheck")){
        $('#medListOption').val(sessionStorage.getItem("medi_listOption"));
        $('#searchToKdCd').val(sessionStorage.getItem("medi_searchToKcdCd"));
        medi.limit = parseInt(sessionStorage.getItem("medi_limit"));
        medi.currentOffset = parseInt(sessionStorage.getItem("medi_offset"));
        sessionStorage.clear();
        sessionStorage.setItem('mainPage', 'medicinePage');
    }
    medicineTotalCnt_req();
    medicineList_req();

    $('#medListOption').on('change', function () {
        medicineList_req();
    });

    $('#searchToKdCd').on('keyup', function () {
        medicineList_req();
    });

    //다음 버튼
    $('#medi_next').on('click', function (e) {
        e.preventDefault();
        if ((medi.currentOffset + medi.limit) >= medi.totalCnt) {
            medi.currentOffset = (medi.totalCnt - 1);
        } else {
            medi.currentOffset = medi.currentOffset + medi.limit;
        }
        medicineList_req();
    });

    //이전 버튼
    $('#medi_prev').on('click', function (e) {
        e.preventDefault();
        if ((medi.currentOffset - medi.limit) <= 0) {
            medi.currentOffset = 0
        } else {
            medi.currentOffset = medi.currentOffset - medi.limit
        }
        medicineList_req();
    });

    //excel download
    $('#medExcelDownloadBtn').on('click', function(e){
        e.preventDefault();
        $('#mediSearchForm')[0].submit();
    });

}

function menu_medicineList_dynamicFunc() {
    $('.mediSctIdDetail').on('click', function () {
        window.open(
            'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1=' + $(this).text(),
            'Detail',
            'width=1200,height=800,left=200,'
        );
        $('#sctId').val($(this).text());
    });

    $('.medDetail').on('click', function(){
        console.log($(this));
        //상세화면에서의 페이징을 위해 세팅.
        sessionStorage.setItem("medi_storageCheck", true);
        sessionStorage.setItem("medi_limit", medi.limit);
        sessionStorage.setItem("medi_offset", medi.currentOffset);
        sessionStorage.setItem("medi_totalCnt", medi.totalCnt);
        sessionStorage.setItem("medi_listOption", $('#medListOption option:selected').val());
        sessionStorage.setItem("medi_sctId", $(this).data('sctid'));
        sessionStorage.setItem("medi_kdCd", $(this).text());
        sessionStorage.setItem("medi_index", $(this).data('index'));
        sessionStorage.setItem("medi_searchToKdCd", $('#searchToKdCd').val());
        sessionStorage.setItem("medi_mapVer", $('#medVersion').val());
        sessionStorage.setItem("medi_mapStatCd", $('#medMapStatCd').val());
        location.href = '/medDetailPage?kdCd=' + $(this).text() + '&mapVer=' + $('#medVersion').val();
    });

}

function medicineTotalCnt_req() {
    $.ajax({
        url: '/getMediTotalCount',
        type: 'post',
        data: {
            mappingStatus: $('#medListOption option:selected').val(),
            mapVer: $('#version').val(),
            mapStatCd: $('#mapStatCd').val()
        },
        dataType: 'json',
        async:false,
        success: function (data) {
            console.log(data);
            medi.mediTotalCnt = data.mediTotalCnt;
            medi.totalCnt = data.totalCnt;
            $('#medi_kdTotalCnt').text(data.mediTotalCnt ? data.mediTotalCnt : '-');
            $('#medi_totalCnt').text(data.totalCnt ? data.totalCnt : '-');
        }
    });
}

function medicineList_req() {
    $.ajax({
        url: '/medicine/select/' + $('#medListOption option:selected').val(),
        type: 'post',
        data: {
            kdCd: $('#searchToKdCd').val(),
            limit: medi.limit,
            offset: medi.currentOffset
        },
        dataType: 'json',
        success: function (data) {
            medi.mainMedList = JSON.parse(JSON.stringify(data));
            if (data.length > 0) {
                $('#medListTable tbody').empty();
                for (var i = 0; i < data.length; i++) {
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class: 'medDetail',
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
                    $('#medListTable tbody').append($tr);
                }
                menu_medicineList_dynamicFunc();

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
            }
        }
    })
}