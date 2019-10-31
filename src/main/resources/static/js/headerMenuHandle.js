function static_function(){
    $('.headerMenu').on('click',function(){
        $('.content').addClass('displayNone');
        $('#' + $(this).data('menu')).removeClass('displayNone');
    })
}
