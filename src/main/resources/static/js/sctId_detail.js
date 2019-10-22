function sctId_detail_static_func(){
    detailList_req(window.opener.document.getElementById('sctId').value);

}

function detailList_req(sctId){
    $('#sctIdSpan').text("'"+sctId+"'");
    var linkurl="https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1="+window.opener.document.getElementById('sctId').value;
    $('#link1').attr('href',linkurl);
    
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
                            text:data[i].sctId
                        }),
                        $('<td>',{
                            text:data[i].sctTerm
                        }),
                        $('<td>',{
                            text:data[i].udtDt
                        })
                    );
                    $('#detailListTable').append($tr);
                }
            }
        }
    });
}