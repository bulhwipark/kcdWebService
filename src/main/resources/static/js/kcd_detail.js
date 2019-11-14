function kcd_detail_static_func() {
    //초기 실행.
    get_kcdCdObject_req();
    get_kcdDetail_list();
    termSynonym();
    autoRuleSet();
    kcdDetail_prevBtn_ajaxReq();

    //ecl 클릭시 disorder, clinicalFinding 체크해제.
    $('#ecl').on('click', function () {
        $('#disorder').prop('checked', false);
        $('#clinicalFinding').prop('checked', false);
    });

    //disorder, clinicalfinding 클릭시 좌측 ecl 인풋에 텍스트는 삭제.
    $('input[name="defaultRule"]').on('click', function () {
        $('#ecl').val('');
    });

    //kcd 상세리스트 쪽 전체 선택.
    $('#allSelect').on('click', function () {
        if ($(this).prop('checked')) {
            for (var i = 0; i < $('input[name="sctListCheck"]').length; i++) {
                if (!$('input[name="sctListCheck"]')[i].disabled) {
                    $($('input[name="sctListCheck"]')[i]).prop("checked", true);
                }
            }
        } else {
            $('input[name="sctListCheck"]').prop("checked", false);
        }

        if ($('input[name="sctListCheck"]:checked').length > 0) {
            $('#removeBtn').prop('disabled', false);
        } else {
            $('#removeBtn').prop('disabled', true);
        }
    });

    //검색목록 쪽 전체선택
    $('#searchResultAllSelect').on('click', function () {
        if ($(this).prop('checked')) {
            $('input[name="searchResultSaveCheckbox"]').prop('checked', true);
        } else {
            $('input[name="searchResultSaveCheckbox"]').prop('checked', false);
        }

        if ($('input[name="searchResultSaveCheckbox"]:checked').length > 0) {
            $('#saveBtn').prop('disabled', false);
        } else {
            $('#saveBtn').prop('disabled', true);
        }
    });

    //동의어 이벤트
    $('#synonym').on('change', function () {
        $('#term').val($('#synonym option:selected').val());
    })
    .on('click', function () {
        $('#term').val($('#synonym option:selected').val());
    });

    //후조합 속성선택시.
    $('.attrSelect').on('change', function () {
        getValueList($(this).data('num'));
    });

    //후조합 삭제 이벤트
    $('.attrRemove').on('click', function () {
        deleteAttrVal($(this).data('num'));
    });

    //후조합 속성 - 밸류 선택시 검색이벤트
    $('.valSelect').on('change', function () {
        textSearchForm_setting($(this).data('num'));
    });

    /**
     * kcd 상세화면 다음버튼 이벤트
     * sessionStorage 정보를 이용하여 kcd리스트에서 KCD코드로 다음것을 찾음.
     */
    $('#kcdList_next').on('click', function () {
        if (parseInt(sessionStorage.getItem("index")) === 49) {
            sessionStorage.setItem(
                'offset', parseInt(sessionStorage.getItem("offset")) + parseInt(sessionStorage.getItem("limit"))
            );
            kcdDetail_prevBtn_ajaxReq();
            sessionStorage.setItem("index", 0);
        }
        var index = parseInt(sessionStorage.getItem("index"));
        var result = null;
        var checkIdx = null;
        for (var i = index; i < kcd.mainKcdList.length; i++) {
            if (sessionStorage.getItem("kcdCd") !== kcd.mainKcdList[i].kcdCd) {
                result = JSON.parse(JSON.stringify(kcd.mainKcdList[i]));
                checkIdx = i;
                break;
            } else {
                if (i == 49) {
                    result = JSON.parse(JSON.stringify(kcd.mainKcdList[49]));
                    checkIdx = 49;
                    break;
                }
            }
        }
        sessionStorage.setItem("kcdCd", result.kcdCd);
        sessionStorage.setItem("index", checkIdx);
        location.href = "/kcdDetailPage?kcdCd=" + result.kcdCd + "&mapVer=0";
    });

    /**
     * kcd 상세화면 이전버튼 이벤트
     * sessionStorage 정보를 이용하여 kcd리스트에서 KCD코드로 다음것을 찾음.
     */
    $('#kcdList_prev').on('click', function () {
        if (parseInt(sessionStorage.getItem("index")) === 0) {
            if (sessionStorage.getItem("offset") > 0) {
                sessionStorage.setItem(
                    'offset', parseInt(sessionStorage.getItem("offset")) - parseInt(sessionStorage.getItem("limit"))
                );
                kcdDetail_prevBtn_ajaxReq();
                sessionStorage.setItem("index", kcd.mainKcdList.length - 1);
            } else {
                //인덱스가 0이고 offset이 0일때 첫번째 인덱스이므로 동작 X.
                $(this).attr('disabled', true);
                return;
            }
        }
        var index = parseInt(sessionStorage.getItem("index"));
        var result = null;
        var checkIdx = null;
        for (var i = index; i >= 0; i--) {
            if (sessionStorage.getItem("kcdCd") !== kcd.mainKcdList[i].kcdCd) {
                result = JSON.parse(JSON.stringify(kcd.mainKcdList[i]));
                checkIdx = i;
                break;
            } else {
                if (i == 0) {
                    result = JSON.parse(JSON.stringify(kcd.mainKcdList[0]));
                    checkIdx = 0;
                    break;
                }
            }
        }
        sessionStorage.setItem("kcdCd", result.kcdCd);
        sessionStorage.setItem("index", checkIdx);
        location.href = "/kcdDetailPage?kcdCd=" + result.kcdCd + "&mapVer=0";
    })
}

function kcd_detail_dynamic_func() {

    $('input[name="sctListCheck"]').on('change', function (e) {
        if ($('input[name="sctListCheck"]:checked').length > 0) {
            $('#removeBtn').prop('disabled', false);
        } else {
            $('#removeBtn').prop('disabled', true);
        }
    });

    $('input[name="searchResultSaveCheckbox"]').on('change', function (e) {
        if ($('input[name="searchResultSaveCheckbox"]:checked').length > 0) {
            $('#saveBtn').prop('disabled', false);
        } else {
            $('#saveBtn').prop('disabled', true);
        }
    });

    $('.sctIdDetail').on('click', function (e) {
        e.stopPropagation();
        window.open(
            'https://browser.ihtsdotools.org/?perspective=full&edition=MAIN/2019-07-31&release=&languages=en&conceptId1=' + $(this).text(),
            'Detail',
            'width=1200,height=800,left=200,'
        );
        $('#sctId').val($(this).text());
    });
}

/**
 * kcd info
 */
function get_kcdCdObject_req() {
    $.ajax({
        url: 'getKcdCdInfo',
        type: 'post',
        async: false,
        data: {
            kcdCd: $('#kcdCd').text()
        },
        dataType: 'json',
        success: function (data) {
            if (data) {
                $('#kcdKor').text(data.kcdKor);
                $('#kcdEng').text(data.kcdEng);
                $('#term').val(data.kcdEng);
            }
        }
    });
}

/**
 * kcd 목록 이벤트
 */
function get_kcdDetail_list() {
    $.ajax({
        url: 'getkcdDetailList',
        type: 'post',
        async: false,
        data: {
            kcdCd: $('#kcdCd').text()
        },
        dataType: 'json',
        success: function (data) {
            if (data.length > 0) {
                kcd.kcdDetailList = JSON.parse(JSON.stringify(data));
                $('#kcdDetailTable tbody').empty();
                $('#allSelect').prop("checked", false);
                for (var i = 0; i < data.length; i++) {
                    var $tr = $('<tr>').append(
                        $('<td>', {
                            class: 'sctIdDetail',
                            text: data[i].sctId,
                            'data-sctid': data[i].sctId
                        }),
                        $('<td>', {
                            text: data[i].sctTerm
                        }),
                        $('<td>', {
                            text: data[i].mapStatNm + "(" + data[i].mapStatCd + ")"
                        }),
                        $('<td>', {
                            text: data[i].afterMap
                        }),
                        $('<td>', {
                            text: data[i].udtDt
                        }),
                        $('<td>').append(
                            $('<input>', {
                                type: 'checkbox',
                                name: 'sctListCheck',
                                id: "delCheckbox_" + data[i].sctId,
                                value: data[i].sctId,
                                disabled: data[i].mapStatCd === '0' || data[i].mapStatCd === '1' ? true : false
                            })
                        ),
                        $('<td>').append(
                            $('<button>', {
                                value: data[i].sctId,
                                class: 'btn btn-sm btn-info addAttrBtn',
                                text: '속성추가',
                                'data-toggle': 'modal',
                                'data-target': '#attrValSetting_modal',
                                onclick: 'attr_val_modalSetting(' + data[i].sctId + ')'
                            })
                        )
                    );
                    $('#kcdDetailTable tbody').append($tr);
                }
                kcd_detail_dynamic_func();
            } else {
                kcd.kcdDetailList = JSON.parse(JSON.stringify(data));
                $('#kcdDetailTable tbody').empty();
                $('#allSelect').prop("checked", false);
                var $tr = $('<tr>').append(
                    $('<td>', {
                        colspan: 8,
                        text: '조회 결과가 없습니다.',
                        style: 'text-align:center;'
                    })
                );
                $('#kcdDetailTable tbody').append($tr);
            }
        }
    })
}

/**
 * 검색 이벤트
 */
function search_req() {
    var param = new Object();
    param.term = $('#term').val();

    if ($('input[name="defaultRule"]:checked').val()) {
        param.ecl = $('input[name="defaultRule"]:checked').val();
    } else {
        param.ecl = $('#ecl').val();
    }

    $.ajax({
        url: '/search',
        type: 'post',
        data: param,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            if (data['items'].length > 0) {
                var items = data['items'];

                //중복제거.
                for (var i = 0; i < kcd.kcdDetailList.length; i++) {
                    items = items.filter(function (item, idx, arr) {
                        if (item.conceptId != kcd.kcdDetailList[i].sctId) {
                            return item;
                        }
                    });
                }
                kcd.searchList = JSON.parse(JSON.stringify(items));
                $('#searchResultTable tbody').empty();
                $('#searchResultAllSelect').prop("checked", false);
                for (var i = 0; i < items.length; i++) {
                    var $tr = $('<tr>', {id: items[i].conceptId}).append(
                        //conceptId
                        $('<td>', {
                            class: 'sctIdDetail',
                            text: items[i].conceptId
                        }),

                        //term
                        $('<td>', {
                            text: items[i]['fsn']['term']

                        }),

                        //checkBox
                        $('<td>').append(
                            $('<input>', {
                                type: 'checkbox',
                                name: 'searchResultSaveCheckbox',
                                value: items[i].conceptId
                            })
                        )
                    );
                    $('#searchResultTable tbody').append($tr);
                }
                $('#saveBtnDiv').removeClass('displayNone');
                $('.autoRuleCol').addClass('displayNone');
                kcd_detail_dynamic_func();
            } else {
                console.log("자료 없음 처리.");
            }
            alert_timeout();
        }
    })
}

/**
 * 저장 버튼 이벤트
 */
function saveBtn_req() {
    var currentSelected = $('input[name="searchResultSaveCheckbox"]:checked');
    var sctIdArr = [];
    var ruleCodeArr = [];
    //기존 검색된 리스트에서 선택된(currentSelected) sctId로 검색.
    for (var i = 0; i < currentSelected.length; i++) {
        if (kcd.searchList) {
            for (var j = 0; j < kcd.searchList.length; j++) {
                if (kcd.searchList[j].conceptId == currentSelected[i].value) {
                    sctIdArr.push(kcd.searchList[j].conceptId);
                    ruleCodeArr.push(
                        $('#' + kcd.searchList[j].conceptId + '_ruleCode').text()
                    )
                }
            }
        } else {
            sctIdArr.push(currentSelected[i].value);
            ruleCodeArr.push(
                $('#' + currentSelected[i].value + '_ruleCode').text()
            )
        }
    }

    sctIdArr = sctIdArr.filter(function (item, idx, arr) {
        return arr.indexOf(item) == idx;
    });

    $.ajax({
        url: '/insertSearchList',
        type: 'post',
        data: {
            oriCd: $('#kcdCd').text(),
            mapVer: kcd.mapVer,
            mapStatCd: 5,
            oriTpCd: 'kcd',
            sctId: sctIdArr.join(","),
            mapMemo: ruleCodeArr.join("_")
        },
        success: function () {
            get_kcdDetail_list();
            for (var i = 0; i < sctIdArr.length; i++) {
                var sctId = sctIdArr[i];
                kcd.searchList = kcd.searchList.filter(function (item, idx, arr) {
                    if (item.conceptId != sctId) {
                        return item;
                    }
                });
                $('#' + sctId).remove();
            }
            $('#saveBtn').attr('disabled', true);
            alert_timeout();
        }
    })
}

/**
 * kcd 목록 삭제 이벤트.
 */
function deleteKcdList_req() {
    var currentSelected = $('input[name="sctListCheck"]:checked');
    var sctIdArr = [];
    for (var i = 0; i < currentSelected.length; i++) {
        sctIdArr.push(currentSelected[i].value);
    }

    $.ajax({
        url: '/deleteKcdList',
        type: 'post',
        data: {
            oriCd: $('#kcdCd').text(),
            sctId: sctIdArr.join(',')
        },
        success: function () {
            get_kcdDetail_list();
            search_req();
            autoRuleSet();
        }
    });
}

/**
 * 유사동의어리스트 req
 **/
function termSynonym() {
    $.ajax({
        url: '/getTermSynonymList',
        type: 'post',
        data: {
            kcdCd: $('#kcdCd').text()
        },
        dataType: 'json',
        success: function (data) {
            console.log(data);
            if (data.length > 0) {
                $('#synonym').empty();
                for (var i = 0; i < data.length; i++) {
                    var $option = $('<option>', {
                        text: data[i].kcdEngSyn,
                        title: data[i].kcdKorSyn
                    });
                    $('#synonym').append($option);
                }
            } else {
                $('#synonym').empty();
                console.log('데이터 없음 처리.');
            }
        }
    })
}

/**
 * 자동룰 반영
 */
function autoRuleSet() {
    var param = new Object();
    param.term = $('#term').val();
    param.rules = kcd.rules.join(',');

    if ($('input[name="defaultRule"]:checked').val()) {
        param.ecl = $('input[name="defaultRule"]:checked').val();
    } else {
        param.ecl = $('#ecl').val();
    }
    $.ajax({
        url: '/autoRuleSet',
        type: 'post',
        async: false,
        data: param,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            kcd.autoRuleLog = JSON.parse(JSON.stringify(data));
            $('#searchResultTable tbody').empty();
            for (var q = 0; q < data.length; q++) {

                if (data[q].status === "true") {
                    var res = JSON.parse(data[q].result);
                    if (res['items'].length > 0) {
                        var items = res['items'];

                        //중복제거.
                        if (kcd.kcdDetailList) {
                            for (var i = 0; i < kcd.kcdDetailList.length; i++) {
                                items = items.filter(function (item, idx, arr) {
                                    if (item.conceptId != kcd.kcdDetailList[i].sctId) {
                                        return item;
                                    }
                                });
                            }
                        }
                        kcd.searchList = JSON.parse(JSON.stringify(items));

                        $('#searchResultAllSelect').prop("checked", false);
                        for (var i = 0; i < items.length; i++) {
                            if ($('#searchResultTable tbody').children('#' + items[i].conceptId).length > 0) {
                                $('#searchResultTable tbody tr').children('#' + items[i].conceptId + '_ruleCode').text(
                                    $('#searchResultTable tbody tr').children('#' + items[i].conceptId + '_ruleCode').text() + ', ' + data[q].ruleCode
                                )
                            } else {
                                var $tr = $('<tr>', {id: items[i].conceptId}).append(
                                    //conceptId
                                    $('<td>', {
                                        class: "sctIdDetail",
                                        text: items[i].conceptId
                                    }),
                                    //term
                                    $('<td>', {
                                        // text:items[i]['fsn']['term']
                                        html: StringMatch_func(items[i]['fsn']['term'], $('#term').val())
                                    }),
                                    $('<td>', {
                                        class: 'autoRuleCol',
                                        id: items[i].conceptId + "_ruleCode",
                                        text: data[q].ruleCode
                                    }),
                                    //checkBox
                                    $('<td>').append(
                                        $('<input>', {
                                            type: 'checkbox',
                                            name: 'searchResultSaveCheckbox',
                                            value: items[i].conceptId
                                        })
                                    )
                                );
                                $('#searchResultTable tbody').append($tr);
                            }
                        }
                        $('#saveBtnDiv').removeClass('displayNone');
                        $('.autoRuleCol').removeClass('displayNone');
                        kcd_detail_dynamic_func();
                        alert_timeout();
                    } else {
                        //status false;
                        console.log(data[q].status);
                    }
                }
            }
        }
    });
}

/**
 * 유사도기준 검색 ajax
 */
function similaritySearch() {
    var param = new Object();
    param.term = $('#term').val();

    if ($('input[name="defaultRule"]:checked').val()) {
        param.ecl = $('input[name="defaultRule"]:checked').val();
    } else {
        param.ecl = $('#ecl').val();
    }

    $.ajax({
        url: '/similaritySearch',
        type: 'post',
        data: param,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            if (data.status === "true") {
                var obj = JSON.parse(data.result);
                var items = obj['items'];
                //중복제거.
                if (kcd.kcdDetailList) {
                    for (var i = 0; i < kcd.kcdDetailList.length; i++) {
                        items = items.filter(function (item, idx, arr) {
                            if (item.conceptId != kcd.kcdDetailList[i].sctId) {
                                return item;
                            }
                        });
                    }
                }
                kcd.searchList = JSON.parse(JSON.stringify(items));

                $('#searchResultTable tbody').empty();
                for (var i = 0; i < items.length; i++) {
                    var itemVal = items[i];
                    if ($('#searchResultTable tbody').children('#' + itemVal.conceptId).length == 0) {
                        var $tr = $('<tr>', {id: itemVal.conceptId}).append(
                            $('<td>', {
                                class: 'sctIdDetail',
                                text: itemVal.conceptId
                            }),
                            $('<td>', {
                                text: itemVal.fsn.term
                            }),
                            $('<td>', {
                                text: data.ruleCode
                            }),
                            //checkBox
                            $('<td>').append(
                                $('<input>', {
                                    type: 'checkbox',
                                    name: 'searchResultSaveCheckbox',
                                    value: itemVal.conceptId
                                })
                            )
                        );
                    }

                    $('#searchResultTable tbody').append($tr);
                }
                kcd_detail_dynamic_func();
            } else {
                console.log("데이터 없음");
                console.log(data);
            }
            alert_timeout();
        }
    });
}

/**
 * 속성추가 모달 세팅하는 펑션.
 * 추가되어있는 속성/값 리스트를 조회 >>> 조회된 리스트가 잇으면 수정버튼 show 없으면 저장버튼 show
 *
 * @param sctId
 */
function attr_val_modalSetting(sctId) {
    $('#modal_sctId').text(sctId);
    $('#modal_kcdKor').text($('#kcdKor').text());
    $('#modal_kcdEng').text($('#kcdEng').text());
    $('.attrRemove').hide();
    $('.selectPickerDiv').empty();
    var infoList = null;
    var ajax_res = getMapAttrValList_ajax();
    ajax_res.done(function (attrValList) {
        console.log(attrValList);
        infoList = JSON.parse(JSON.stringify(attrValList));
        $.ajax({
            url: '/getKcdAttrList',
            type: 'post',
            data: {
                sctId: sctId,
                oriTpCd:'KCD'
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                console.log(data);
                $('.attrSelect, .valSelect').empty();
                $('.valSelect').attr('disabled', true);

                $('.attrSelect').append(
                    $('<option>', {
                        value: '',
                        text: '속성을 선택하세요.',
                        disabled: true,
                        selected: true
                    }),
                );

                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        var $option = $('<option>', {
                            text: data[i].cmSctTerm2,
                            value: data[i].attSctId
                        });
                        $('.attrSelect').append($option);
                    }
                } else {
                    console.log("attr 목록 없음.");
                }
            }
        });
    });

    if (infoList.length > 0) {
        $('#attrSaveBtn').hide();
        $('#attrUpdateBtn').show();
    } else {
        $('#attrSaveBtn').show();
        $('#attrUpdateBtn').hide();
    }

    for (var i = 0; i < infoList.length; i++) {
        $('#attr_select' + (i + 1)).val(infoList[i].attSctId);
        getValueList(i + 1);
        textSearchForm_setting(i + 1);
        $('#val_select' + (i + 1)).val(infoList[i].valgrpSctId);
        $('#text_select' + (i + 1)).data('conceptid', JSON.parse(infoList[i].valSctIdInfo).conceptId);
        $('#div' + (i + 1) + ' .textSelect .filter-option-inner-inner').text(JSON.parse(infoList[i].valSctIdInfo).fsn.term);
        $('#attr_remove' + (i + 1)).show();
    }

}

function getValueList(currentNum) {
    $.ajax({
        url: '/getKcdValList',
        type: 'post',
        data: {
            sctId: $('#attr_select' + currentNum + ' option:selected').val()
        },
        dataType: 'json',
        async: false,
        success: function (data) {
            $('#val_select' + currentNum).empty();
            $('#val_select' + currentNum).append(
                $('<option>', {
                    text: "값을 선택하세요.",
                    value: ''
                })
            );
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    var $option = $('<option>', {
                        text: data[i].cmSctTerm,
                        value: data[i].attSctId,
                        'data-tokens': data[i].cmSctTerm
                    });
                    $('#val_select' + currentNum).append($option);
                }
                $('#val_select' + currentNum).attr('disabled', false);
            } else {
                console.log("리스트 없음.")
            }
        }
    })
}

function textSearchForm_setting(currentNum) {
    $('#div' + currentNum).empty().append(
        $('<select>', {
            class: "textSelect",
            'data-live-search': "true",
            id: "text_select" + currentNum
        }).on('change', function () {
            $('#attrSaveBtn').attr('disabled', false)
        })
    );

    $('#text_select' + currentNum).selectpicker();

    $('.textSelect input[type="text"].form-control').on('keydown', function (key) {
        if (key.keyCode == 13) {
            console.log('enter');
            attrValTextSearch_request(currentNum);
        }
    });

    $('.textSelect').on('change', function () {
        console.log($(this));
        if ($(this).children('select').val()) {
            $('#attrSaveBtn').attr('disabled', false);
        }
    });

}

function attrValTextSearch_request(currentNum) {
    $.ajax({
        url: '/getTextSearchResult',
        type: 'post',
        data: {
            term: $('#div' + currentNum + ' .textSelect input[type="text"].form-control').val(),
            ecl: '<' + $('#val_select' + currentNum + ' option:selected').val()
        },
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $('#text_select' + currentNum).empty();
            if (data.items.length > 0) {
                for (var i = 0; i < data.items.length; i++) {
                    var item = data.items[i];
                    var $option = $('<option>', {
                        text: item.fsn.term,
                        value: item.conceptId
                    });
                    $('#text_select' + currentNum).append($option);
                }
                //$('#text_select'+currentNum).selectpicker('render');
                $('#text_select' + currentNum).selectpicker('refresh');
            }
        }
    })
}

function attr_val_save() {
    var attrOptList = $('.attrSelect option:selected');
    var attrParam = [];
    var valParam = [];
    var valGrpSctIdParam = [];
    for (var i = 0; i < attrOptList.length; i++) {
        if (attrOptList[i].value) {
            if ($('#val_select' + (i + 1) + " option:selected").val()) {
                attrParam.push(attrOptList[i].value);
                valGrpSctIdParam.push($('#val_select' + (i + 1) + " option:selected").val());
                valParam.push($('#text_select' + (i + 1) + ' option:selected').val());
            }
        }
    }

    $.ajax({
        url: '/attrValSave',
        type: 'post',
        data: {
            oriTpCd: 'KCD',
            oriCd: $('#modal_kcdCd').text(),
            mapVer: $('#mapVer').val(),
            sctId: $('#modal_sctId').text(),
            mapStatCd: '80',
            attrParam: attrParam.join(','),
            valParam: valParam.join(','),
            valGrpSctIdParam: valGrpSctIdParam.join(',')
        },
        success: function () {
            console.log("save");
            get_kcdDetail_list();
        }
    })
}

function attr_val_update() {
    var attrOptList = $('.attrSelect option:selected');
    var attrParam = [];
    var valParam = [];
    var valGrpSctIdParam = [];
    for (var i = 0; i < attrOptList.length; i++) {
        if (attrOptList[i].value) {
            if ($('#val_select' + (i + 1) + " option:selected").val()) {
                if ($('#text_select' + (i + 1) + ' option:selected').val()) {
                    attrParam.push(attrOptList[i].value);
                    valParam.push($('#text_select' + (i + 1) + " option:selected").val());
                    valGrpSctIdParam.push($('#val_select' + (i + 1) + " option:selected").val());
                } else if ($('#text_select' + (i + 1)).data('conceptid')) {
                    attrParam.push(attrOptList[i].value);
                    valParam.push($('#text_select' + (i + 1)).data('conceptid'));
                    valGrpSctIdParam.push($('#val_select' + (i + 1) + " option:selected").val());
                }
            }
        }
    }
    if (attrParam.length > 0) {
        $.ajax({
            url: '/attrValUpdate',
            type: 'post',
            data: {
                oriTpCd: 'KCD',
                oriCd: $('#modal_kcdCd').text(),
                mapVer: $('#mapVer').val(),
                sctId: $('#modal_sctId').text(),
                // attSctId:$('#attr_select option:selected').val(),
                // valSctId:$('#val_select option:selected').val(),
                mapStatCd: '80',
                attrParam: attrParam.join(','),
                valParam: valParam.join(','),
                valGrpSctIdParam: valGrpSctIdParam.join(",")
            },
            success: function () {
                console.log("update");
            }
        })
    }
}

function getMapAttrValList_ajax() {
    var ajax = $.ajax({
        url: '/getMapAttrValList',
        type: 'post',
        data: {
            oriCd: $('#modal_kcdCd').text(),
            sctId: $('#modal_sctId').text(),
        },
        dataType: 'json',
        async: false
        /*success:function(data){
            console.log(data)
        }*/
    });
    return ajax;
}

function deleteAttrVal(num) {
    $.ajax({
        url: '/deleteAttrVal',
        type: 'post',
        data: {
            oriCd: $('#modal_kcdCd').text(),
            sctId: $('#modal_sctId').text(),
            attSctId: $('#attr_select' + num + ' option:selected').val(),
            valSctId: $('#text_select' + num).data('conceptid'),
            valgrpSctId: $('#val_select' + num + ' option:selected').val()
        },
        success: function () {
            $('#attr_select' + num).val('');
            $('#val_select' + num).empty().attr('disabled', true);
            $('#div' + num).empty();
            $('#attr_remove' + num).hide();
            console.log('delete');
        }
    })
}

/**
 *
 * @param str 원본문자열
 * @param matchStr 매칭시킬 문자열
 * @returns {string|*}
 * @constructor
 */
function StringMatch_func(str, matchStr) {
    var matchRes = str.toUpperCase().match(matchStr.trim().toUpperCase());
    if (matchRes) {
        return str.slice(0, matchRes.index) + "<span class='matchStrCss'>"
            + str.slice(matchRes.index, matchRes.index + matchRes[0].length)
            + '</span>' + str.slice(matchRes.index + matchRes[0].length);
    } else {
        return str;
    }
}

//KCD목록 조회.
function kcdDetail_prevBtn_ajaxReq() {
    $.ajax({
        url: "/select" + sessionStorage.getItem("listOption"),
        type: 'get',
        data: {
            mapVer: sessionStorage.getItem("mapVer"),
            mapStatCd: sessionStorage.getItem("mapStatCd"),
            kcdCd: sessionStorage.getItem("searchToKcdCd").toUpperCase(),
            limit: sessionStorage.getItem("limit"),
            offset: parseInt(sessionStorage.getItem("offset"))
        },
        dataType: 'json',
        async: false,
        success: function (data) {
            kcd.mainKcdList = JSON.parse(JSON.stringify(data));
        }
    })
}

/**
 * 검색, 룰기반검색, 유사도 기반검색 완료알림 관련 함수.
 */
function alert_timeout() {
    $('.searchAlert').removeClass('displayNone');
    var timer = setTimeout(function () {
        if (!$('.searchAlert').hasClass('displayNone')) {
            $('.searchAlert').addClass('displayNone');
        }
    }, 1000);
}