package com.example.kcdwebservice.controller;

import java.util.List;

import com.example.kcdwebservice.service.CmKcdService;
import com.example.kcdwebservice.service.CmKexamService;
import com.example.kcdwebservice.service.CmSnomedCtService;
import com.example.kcdwebservice.service.DicKcdSynonymService;
import com.example.kcdwebservice.service.DicSnomedctAttValService;
import com.example.kcdwebservice.service.MCodeService;
import com.example.kcdwebservice.service.MapKcdSctAftCatService;
import com.example.kcdwebservice.service.MapKcdSctService;
import com.example.kcdwebservice.service.RuleMapService;
import com.example.kcdwebservice.service.SearchService;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.CmSnomedCtVo;
import com.example.kcdwebservice.vo.DicKcdSynonymVo;
import com.example.kcdwebservice.vo.DicSnomedctAttValVo;
import com.example.kcdwebservice.vo.MCodeVo;
import com.example.kcdwebservice.vo.MapKcdSctAftCatVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;
import com.example.kcdwebservice.vo.SearchVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @Autowired
    private CmKcdService cmKcdService;
    @Autowired
    private MapKcdSctService mapKcdSctService;
    @Autowired
    private RuleMapService ruleMapService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private CmSnomedCtService cmSnomedCtService;
    @Autowired
    private DicKcdSynonymService dicKcdSynonymService;
    @Autowired
    private MCodeService mCodeService;
    @Autowired
    private DicSnomedctAttValService dicSnomedctAttValService;
    @Autowired
    private MapKcdSctAftCatService mapKcdSctAftCatService;
    @Autowired
    private CmKexamService cmKexamService;

    /**
     * 메인화면.
     * 
     * @return
     */
    @RequestMapping(value = "/")
    public String main(@RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {
        return "/index";
    }

    /**
     * KCD 전체 목록
     * 
     * @param cmKcdVo
     * @return
     */
    @RequestMapping(value = "/selectAll")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdSelectAll(CmKcdVo cmKcdVo) {
        System.out.println("cmKcdVo" + cmKcdVo);
        List<CmKcdVo> list = cmKcdService.selectAll(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String kcdSelectAll() {
        for (int i = 1; i < 1000; i++) {
            System.out.println("test:" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "test all";
    }

    /**
     *  kcd 매핑 목록
     * @param cmKcdVo
     * @return
     */
    @RequestMapping(value="/selectMapping")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdSelectMapping(CmKcdVo cmKcdVo){
        List<CmKcdVo> list = cmKcdService.selectMapping(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * kcd 미매핑 목록
     * @param cmKcdVo
     * @return
     */
    @RequestMapping(value="/selectNotMapping")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdSelectNotMapping(CmKcdVo cmKcdVo){
        List<CmKcdVo> list = cmKcdService.selectNotMapping(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * kcd - Icd 비매핑 목록
     * @param cmKcdVo
     */
    @RequestMapping(value="/selectIcdNotMapping")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdSelectIcdNotMapping(CmKcdVo cmKcdVo){
        List<CmKcdVo> list = cmKcdService.selectIcdNotMapping(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * kcd 매핑상태별 total count
     * @param mappingStatus
     * @return
     */
    @RequestMapping(value="/getTotalCount")
    @ResponseBody
    public ResponseEntity<String> getTotalCount(@RequestParam("mappingStatus")String mappingStatus, CmKcdVo mapKcdSctVo){
        JSONObject jsonObject = new JSONObject();
        String kcdTotalCnt = cmKcdService.kcdTotalCnt(mappingStatus, mapKcdSctVo);
        String totalCnt = cmKcdService.mappingStatTotalCnt(mappingStatus, mapKcdSctVo);
        try {
            jsonObject.put("kcdTotalCnt", kcdTotalCnt);
            jsonObject.put("totalCnt", totalCnt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    /**
     * kcd코드 상세화면
     * @return
     */
    @RequestMapping(value="/kcdDetailPage")
    public ModelAndView kcdDetailPage(
            @RequestParam("kcdCd")String kcdCd
            , @RequestParam("mapVer")String mapVer){
        ModelAndView mav = new ModelAndView();
        mav.addObject("kcdCd", kcdCd);
        mav.addObject("mapVer", mapVer);
        mav.setViewName("/DiagnosisNamePage/kcdDetail");
        return mav;
    }

    /**
     * kcdCd 정보
     * @param kcdCd
     * @return
     */
    @RequestMapping(value="/getKcdCdInfo")
    @ResponseBody
    public ResponseEntity<CmKcdVo> kcdCdInfo(@RequestParam("kcdCd")String kcdCd){
        CmKcdVo kcdInfo = cmKcdService.selectKcdCdInfo(kcdCd);
        return new ResponseEntity<>(kcdInfo, HttpStatus.OK);
    }

    @RequestMapping(value="/getkcdDetailList")
    @ResponseBody
    public ResponseEntity<List<MapKcdSctVo>> getKcdDetailList(@RequestParam("kcdCd")String kcdCd){
        List<MapKcdSctVo> list = mapKcdSctService.selectKcdCdList(kcdCd);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * sctId 상세화면.
     *
     * @return
     */
    @RequestMapping(value="/sctIdDetail")
    public String sctIdDetail(){
        return "/detailPage/sctIdDetail";
    }

    /**
     * sctId detailList
     * @param sctId
     * @return
     */
    @GetMapping(value="/detailList")
    @ResponseBody
    public ResponseEntity<List<CmSnomedCtVo>> sctIdDetailList(@RequestParam("sctId")String sctId){
        List<CmSnomedCtVo> cmSnomedCtVoList = cmSnomedCtService.getList(sctId);
        return new ResponseEntity<>(cmSnomedCtVoList, HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @GetMapping(value="/kcdRule1")
    @ResponseBody
    public String autoMapKcdRule1(){
        ruleMapService.automap1("<64572001"); //disease (discorder)
       return "/index";
    }

    @GetMapping(value="/mediRule/{id}")
    @ResponseBody
    public String autoMapMediRule1(@PathVariable("id") String ruleTp) {
        long during=System.currentTimeMillis();
        ruleMapService.selectMediList(ruleTp);

    //     ruleMapService.selectMediList("10");
    //     ruleMapService.selectMediList("11");
    //     ruleMapService.selectMediList("12");
    //      ruleMapService.selectMediList("13");
    //     ruleMapService.selectMediList("20");
    //     ruleMapService.selectMediList("21");
    //     ruleMapService.selectMediList("22");
    //     ruleMapService.selectMediList("23");
    //     ruleMapService.selectMediList("30");
    //     ruleMapService.selectMediList("31");
    //     ruleMapService.selectMediList("32");
    //     ruleMapService.selectMediList("33");
    //     ruleMapService.selectMediList("40");
    //     ruleMapService.selectMediList("41");
    //     ruleMapService.selectMediList("42");
    //     ruleMapService.selectMediList("43");
    //    ruleMapService.selectMediList("5");
    //    ruleMapService.selectMediList("6");

    during=(System.currentTimeMillis()-during)/1000/60;
       return "자동룰매핑종료 매핑시간:"+during;
    }

    @GetMapping(value="/mediRule2")
    @ResponseBody
    public String autoMapMediRule2() {
      // ruleMapService.selectMediList(ruleTp);
        System.out.println("rule 10 ");
        
        ruleMapService.selectMediList("10");
        System.out.println("rule 11 ");
        ruleMapService.selectMediList("11");
        System.out.println("rule 12 ");
        ruleMapService.selectMediList("12");
        System.out.println("rule 20 ");
        ruleMapService.selectMediList("20");
        System.out.println("rule 21 ");
        ruleMapService.selectMediList("21");
        System.out.println("rule 22 ");
        ruleMapService.selectMediList("22");
        System.out.println("rule 30 ");
        ruleMapService.selectMediList("30");
        System.out.println("rule 31 ");
        ruleMapService.selectMediList("31");
        System.out.println("rule 32 ");
        ruleMapService.selectMediList("32");
        System.out.println("rule 40 ");
        ruleMapService.selectMediList("40");
        System.out.println("rule 41 ");
        ruleMapService.selectMediList("41");
        System.out.println("rule 42 ");
        ruleMapService.selectMediList("42");
        System.out.println("rule 50 ");
        ruleMapService.selectMediList("50");
        System.out.println("rule B0 ");
        ruleMapService.selectMediList("B0");
        System.out.println("rule 60 ");
        ruleMapService.selectMediList("60");
        System.out.println("rule C0 ");
        ruleMapService.selectMediList("C0");
        System.out.println("rule 70 ");
        ruleMapService.selectMediList("70");
        System.out.println("rule D0 ");
        ruleMapService.selectMediList("D0");
        System.out.println("rule 80 ");
        ruleMapService.selectMediList("80");
        System.out.println("rule E0 ");
        ruleMapService.selectMediList("E0");
        System.out.println("rule 90 ");
        ruleMapService.selectMediList("90");
        System.out.println("rule F0 ");
        ruleMapService.selectMediList("F0");
        System.out.println("rule A0 ");
        ruleMapService.selectMediList("A0");
        System.out.println("rule G0 ");
        ruleMapService.selectMediList("G0");

       return "자동룰매핑종료"+System.currentTimeMillis();
    }

    
    @GetMapping(value="/mediRuleC")
    @ResponseBody
    public String autoMapMediRuleC() {
        System.out.println("rule X10 ");
        ruleMapService.selectMediListC("10");
        ruleMapService.selectMediListC("11");
        ruleMapService.selectMediListC("12");
        System.out.println("rule X20 ");
        ruleMapService.selectMediListC("20");
        ruleMapService.selectMediListC("21");
        ruleMapService.selectMediListC("22");
        System.out.println("rule X30 ");
        ruleMapService.selectMediListC("30");
        ruleMapService.selectMediListC("31");
        ruleMapService.selectMediListC("32");
        System.out.println("rule X40 ");
        ruleMapService.selectMediListC("40");
        ruleMapService.selectMediListC("41");
        ruleMapService.selectMediListC("42");
        System.out.println("rule X50 ");
        ruleMapService.selectMediListC("50");
        System.out.println("rule X60 ");
        ruleMapService.selectMediListC("60");
        System.out.println("rule X70 ");
        ruleMapService.selectMediListC("70");
        System.out.println("rule X80 ");
        ruleMapService.selectMediListC("80");
        
        return "자동룰매핑종료"+System.currentTimeMillis();
    }

    @GetMapping(value="/mediRuleC2")
    @ResponseBody
    public String autoMapMediRuleC2() {
        System.out.println("rule 90X ");
        ruleMapService.selectMediListC2("90X");
        System.out.println("rule A0X ");
        ruleMapService.selectMediListC2("A0X");
        return "자동룰매핑종료"+System.currentTimeMillis();
    }

    @RequestMapping(value="/search")
    @ResponseBody
    public ResponseEntity<String> search(SearchVo searchVo) {
        String result = null;
        try {
            result = searchService.searchReqeust(searchVo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * kcd 저장.
     * @param mapKcdSctVo
     */
    @RequestMapping(value="/insertSearchList")
    @ResponseBody
    public void insertSearchObject(MapKcdSctVo mapKcdSctVo){
        mapKcdSctService.insertMapKcdSctInfo(mapKcdSctVo);
    }

    /**
     * kcd 삭제
     * @param mapKcdSctVo
     */
    @RequestMapping(value="/deleteKcdList")
    @ResponseBody
    public void deleteKcdList(MapKcdSctVo mapKcdSctVo){
        mapKcdSctService.deleteMapKcdSctInfo(mapKcdSctVo);
    }

    /**
     * 유사동의어
     * @param dicKcdSynonymVo
     * @return
     */
    @RequestMapping(value="/getTermSynonymList")
    @ResponseBody
    public ResponseEntity<List<DicKcdSynonymVo>> getTermSynonymList(DicKcdSynonymVo dicKcdSynonymVo){
        List<DicKcdSynonymVo> list = dicKcdSynonymService.getList(dicKcdSynonymVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 매핑상태코드
     * @return
     */
    @RequestMapping(value="/getMappingStatusCd")
    @ResponseBody
    public ResponseEntity<List<MCodeVo>> getMappingStatusCd(){
        List<MCodeVo> list = mCodeService.getMappingTypeList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 자동룰
     * @param searchVo
     * @return
     */
    @RequestMapping(value="/autoRuleSet")
    @ResponseBody
    public ResponseEntity<String> ruleSet(SearchVo searchVo){
        List<JSONObject> result = searchService.autoRuleRequest(searchVo);
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    /**
     * Excel 다운로드
     * @param cmKcdVo
     * @param model
     * @return
     */
    @PostMapping("/excelDownload.xlsx")
    public String excelDownload(CmKcdVo cmKcdVo, Model model){
        List<CmKcdVo> list = null;
        if(cmKcdVo.getListOption().equals("All")){
            list = cmKcdService.selectAll(cmKcdVo);
        }else if(cmKcdVo.getListOption().equals("Mapping")){
            list = cmKcdService.selectMapping(cmKcdVo);
        }else if(cmKcdVo.getListOption().equals("NotMapping")){
            list = cmKcdService.selectNotMapping(cmKcdVo);
        }else if(cmKcdVo.getListOption().equals("IcdNotMapping")){
            list = cmKcdService.selectIcdNotMapping(cmKcdVo);
        }

        model.addAttribute("list", list);
        model.addAttribute("sheetNm", "KCD 목록");
        model.addAttribute("headerNmArr", new String[]{"KCD코드", "한글명/영문명", "SCTID", "Snomed CT Term", "매핑상태", "매핑일자" });
        return "ExcelDownload";
    }

    /**
     * 유사도 기준조회.
     * @param searchVo
     * @return
     */
    @RequestMapping(value="/similaritySearch")
    @ResponseBody
    public ResponseEntity<String> similaritySearch(SearchVo searchVo){
        JSONObject result = searchService.similarityRequest(searchVo);
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    /**
     * Attribute 목록 조회.
     * @param sctId
     *
     **/
    @RequestMapping(value="/getKcdAttrList")
    @ResponseBody
    public ResponseEntity<List<DicSnomedctAttValVo>> getKcdAttrList(@RequestParam("sctId")String sctId, @RequestParam("oriTpCd")String oriTpCd){
        List<DicSnomedctAttValVo> list = dicSnomedctAttValService.getAttrList(oriTpCd);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * val 목록 조회
     * @param sctId
     * @return
     */
    @RequestMapping(value="/getKcdValList")
    public ResponseEntity<List<DicSnomedctAttValVo>> getKcdValList(@RequestParam("sctId") String sctId){
        List<DicSnomedctAttValVo> list = dicSnomedctAttValService.getValList(sctId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 속성/밸류 신규저장.
     * @param mapKcdSctAftCatVo
     * @param attrParam
     * @param valParam
     */
    @RequestMapping(value="/attrValSave")
    @ResponseBody
    public void attrValSave(
            MapKcdSctAftCatVo mapKcdSctAftCatVo,
            @RequestParam("attrParam")String attrParam,
            @RequestParam("valParam")String valParam,
            @RequestParam("valGrpSctIdParam")String valGrpSctIdParam
    ){
        mapKcdSctAftCatService.attValInsert(mapKcdSctAftCatVo, attrParam, valParam, valGrpSctIdParam);
    }

    /**
     * 속성/밸류 수정
     * delete후 insert하는 방식으로 해둠.
     * @param mapKcdSctAftCatVo
     * @param attrParam
     * @param valParam
     */
    @RequestMapping(value="/attrValUpdate")
    @ResponseBody
    public void attrValUpdate(
            MapKcdSctAftCatVo mapKcdSctAftCatVo,
            @RequestParam("attrParam")String attrParam,
            @RequestParam("valParam")String valParam,
            @RequestParam("valGrpSctIdParam")String valGrpSctIdParam
    ){
        mapKcdSctAftCatService.attValUpdate(mapKcdSctAftCatVo, attrParam, valParam,valGrpSctIdParam);
    }

    /**
     * 속성/밸류 목록 조회
     * @param mapKcdSctAftCatVo
     * @return
     */
    @RequestMapping(value="/getMapAttrValList")
    @ResponseBody
    public ResponseEntity<List<MapKcdSctAftCatVo>> getMapAttrValList(MapKcdSctAftCatVo mapKcdSctAftCatVo){
        List<MapKcdSctAftCatVo> list = mapKcdSctAftCatService.getList(mapKcdSctAftCatVo);

        for(MapKcdSctAftCatVo vo : list){
            vo.setValSctIdInfo(searchService.searchConceptId(vo.getValSctId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    /**
     * 속성, 밸류 삭제.
     * @param mapKcdSctAftCatVo
     */
    @RequestMapping(value="/deleteAttrVal")
    @ResponseBody
    public void deleteAttrVal(MapKcdSctAftCatVo mapKcdSctAftCatVo){
        mapKcdSctAftCatService.deleteAttrVal(mapKcdSctAftCatVo);
    }

    @RequestMapping(value="/getTextSearchResult")
    @ResponseBody
    public ResponseEntity<String> getTextSearchResult(SearchVo searchVo){
        String result = null;
        try {
            result = searchService.searchReqeust(searchVo);
            JSONObject jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
