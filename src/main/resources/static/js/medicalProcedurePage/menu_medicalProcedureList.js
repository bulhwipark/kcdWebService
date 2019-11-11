function menu_medicalProcedureList_staticFunc(){
    //검사쪽 리스트를 가져옴 후에 수정해야됨.
    medicalProcList_req();
}

function medicalProcList_req(){
    $.ajax({
        url:'/kexam/select/' + $('#mediCheck_listOption option:selected').val(),
        type:'post',
        data:{
            //kexamCd:'',
            limit: medi_procedure.limit,
            offset: medi_procedure.currentOffset
        },
        dataType:'json',
        success:function(data){
            medi_procedure.mainProcList = JSON.parse(JSON.stringify(data));
            if (data.length > 0) {
                $('#mediProc_procListTable tbody').empty();
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
                    $('#mediProc_procListTable tbody').append($tr);
                }
                //menu_mediKexamList_dynamicFunc();

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