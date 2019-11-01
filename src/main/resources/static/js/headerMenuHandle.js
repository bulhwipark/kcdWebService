function static_function(){
    $('.headerMenu').on('click',function(){
        $('.menuLi').removeClass('currentSelectedMenu');
        $(this).parent().addClass('currentSelectedMenu');
        $('.content').addClass('displayNone');
        $('#' + $(this).data('menu')).removeClass('displayNone');
    })
}
