function mediCheck_detail_static_func() {
    get_mediCheckObject_req();
}

/**
 * mediCheck info
 */
function get_mediCheckObject_req() {
    $.ajax({
        url: '/getMediCheckInfo',
        type: 'post',
        async: false,
        data: {
            kexCd: $('#kexCd').text()
        },
        dataType: 'json',
        success: function (data) {
           if (data) {
               $('#mediCheck_kor').text(data.kexKor?data.kexKor:"-");
               $('#mediCheck_eng').text(data.kexEng?data.kexEng:"-");
               $('#mediCheck_term').val(data.kexEng);
           }
        }
    });
}