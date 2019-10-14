function sctId_detail_static_func(){
    detailList_req(window.opener.document.getElementById('sctId').value);

}

function detailList_req(sctId){
    $('#sctIdSpan').text("'"+sctId+"'");
    $.ajax({
        url:'/detailList',
        type:'get',
        data:{sctId:sctId},
        dataType:'json',
        success:function(data){
            console.log(data);
            if(data.length > 0){
                $('#detailListTable tbody').empty();
                for(var i = 0; i<data.length; i++){
                    var $tr = $('<tr>').append(
                        $('<td>',{
                            text:data[i].conceptId
                        }),
                        $('<td>',{
                            text:data[i].term
                        }),
                    );
                    $('#detailListTable').append($tr);
                }
            }
        }
    });
}