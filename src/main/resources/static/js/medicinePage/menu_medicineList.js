function menu_medicineList_staticFunc() {
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