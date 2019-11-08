function menu_medicalProcedureList_staticFunc(){
    console.log("처치 및 시술.");
}

function medicalProcedureList_req(){
    $.ajax({
        url:'',
        type:'',
        data:'',
        dataType:'',
        success:function(data){
            console.log(data);
        }
    })
}