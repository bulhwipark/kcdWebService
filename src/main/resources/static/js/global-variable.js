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
    currentOffset: 0,
    autoRuleLog:null,
    rules:[1,2,3,4,5]
};

//약제 전역변수
var medi = {
    mediTotalCnt: 0,
    totalCnt: 0,
    limit: 50,
    currentOffset: 0,
    mainMedList: null,
    mediDetailList:[],
    currentMediInfo:null,
    autoRuleLog:null,
    rules:[2,3,4]
};

//처치및시술 전역변수
var medi_procedure = {
    procTotalCnt: 0,
    totalCnt: 0,
    limit: 50,
    currentOffset: 0,
    mainProcList: null,
    procDetailList:[],
    currentProcInfo:null,
    autoRuleLog:null,
    rules:[2,3,4]
};

//검사 전역변수
var medi_check = {
    mediCheckTotalCnt: 0,
    totalCnt: 0,
    limit: 50,
    currentOffset: 0,
    mainMediCheckList: null,
    mediCheckDetailList:[],
    currentMediCheckInfo:null,
    autoRuleLog:null,
    rules:[2,3,4]
}
