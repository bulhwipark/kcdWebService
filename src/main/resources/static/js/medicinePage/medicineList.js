function menu_medicineList_staticFunc(){
    medicineList_req();
}

function medicineList_req(){
    $.ajax({
        url:'/medicine/select/all',
        type:'post',
        data:{
          limit:medicine.limit,
          offset:medicine.currentOffset
        },
        dataType:'json',
        success:function(data){
            medicine.mainMedList = JSON.parse(JSON.stringify(data));
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
            }
        }
    })
}