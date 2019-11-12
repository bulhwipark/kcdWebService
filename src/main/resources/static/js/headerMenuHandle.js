function static_function(){
    $('.headerMenu').on('click',function(){
        $('.menuLi').removeClass('currentSelectedMenu');
        $(this).parent().addClass('currentSelectedMenu');
        $('.content').addClass('displayNone');
        $('#' + $(this).data('menu')).removeClass('displayNone');

        sessionStorage.setItem("mainPage", $(this).data('menu'));

        pageRuting($(this).data('menu'));
    });
}

function pageRuting(pageNm){
    if(pageNm === 'kcdListPage'){
        menu_kcdList_staticFunc();
    }else if(pageNm === 'medicinePage'){
        menu_medicineList_staticFunc();
    }else if(pageNm === 'medicalProcedurePage'){
        // menu_medicalProcedureList_staticFunc();
    }else if(pageNm === 'medicalCheckPage'){
        menu_medicalCheckList_staticFunc();
    }else {
        console.log('page not found');
    }

    $('.menuLi').removeClass('currentSelectedMenu');
    $('#'+pageNm + '_a').parent().addClass('currentSelectedMenu');
    $('.content').addClass('displayNone');
    $('#' + pageNm).removeClass('displayNone');

}
