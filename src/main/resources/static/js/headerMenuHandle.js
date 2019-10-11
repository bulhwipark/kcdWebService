function static_function(){
    $('.headerMenu').on('click',function(){
        console.log($(this).data("menu"));
        $('.content').addClass('displayNone');
        $('#' + $(this).data('menu')).removeClass('displayNone');
    })
}