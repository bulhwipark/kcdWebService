/*
저장, 삭제 이벤트시 화면 싱크를 위해 동일하게 유지해야됨.
 */

//KCD 전역변수
var kcd = {
    //KCD 리스트 변수
    mainKcdList: null,
    //kcd detail 리스트 변수
    kcdDetailList: null,
    //search 결과 리스트
    searchList: null,
    mapVer: null,
    kcdTotalCnt: 0,
    totalCnt: 0,
    limit: 50,
    currentOffset: 0
};

//약제 전역변수
var medi = {
    mediTotalCnt: 0,
    totalCnt: 0,
    limit: 50,
    currentOffset: 0,
    mainMedList: null,
    mediDetailList:[],
    currentMediInfo:null
};
