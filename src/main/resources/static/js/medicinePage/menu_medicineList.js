function menu_medicineList_staticFunc(){
    medicineTotalCnt_req();
    medicineList_req();

    $('#medListOption').on('change', function(){
        medicineList_req();
    });

    $('#searchToKdCd').on('keyup', function(){
        medicineList_req();
    });

    //excel download
    /*
    $('#medExcelDownloadBtn').on('click', function(e){
        e.preventDefault();
        $('#searchForm')[0].submit();
    });
    */

}

function menu_medicineList_dynamicFunc(){
    $('.mediSctIdDetail').on('click', function(){
        window.open(
            'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1='+$(this).text(),
            'Detail',
            'width=1200,height=800,left=200,'
        );
        $('#sctId').val($(this).text());
    });
}

function medicineTotalCnt_req(){
    $.ajax({
        url:'/getMediTotalCount',
        type:'post',
        data:{
            mappingStatus:$('#medListOption option:selected').val(),
            mapVer:$('#version').val(),
            mapStatCd: $('#mapStatCd').val()
        },
        dataType:'json',
        success:function(data){
            console.log(data);
        }
    });
}

function medicineList_req(){
    $.ajax({
        url:'/medicine/select/'+$('#medListOption option:selected').val(),
        type:'post',
        data: {
            kdCd: $('#searchToKdCd').val(),
            limit: medi.limit,
            offset: medi.currentOffset
        },
        dataType:'json',
        success:function(data){
            medi.mainMedList = JSON.parse(JSON.stringify(data));
            if(data.length > 0){
                $('#medListTable tbody').empty();
                for(var i = 0; i<data.length; i++){
                    var $tr = $('<tr>').append(
                        $('<td>',{
                            class:'medDetail',
                            text:data[i].kdCd,
                            'data-kdCd':data[i].kdCd,
                            //'data-sctid':!data[i].sctId?'-':data[i].sctId,
                            'data-index':i
                        }),
                        $('<td>').append(
                            $('<div>',{
                                text:'ko : ' + data[i].drugNmKor
                            }),
                            $('<div>',{
                                text:'en : ' + data[i].drugNmEng
                            })
                        ),
                        $('<td>',{
                            class:'mediSctIdDetail',
                            text:data[i].sctId?data[i].sctId:'-'
                        }),
                        $('<td>',{
                            text:data[i].sctTerm?data[i].sctTerm:'-'
                        }),
                        $('<td>',{
                            text:data[i].mapStatCd?data[i].mapStatCd:'-'
                        }),
                        $('<td>',{
                            text:data[i].udtDt
                        }),
                    );
                    $('#medListTable tbody').append($tr);
                }
                menu_medicineList_dynamicFunc();
            }
        }
    })
}