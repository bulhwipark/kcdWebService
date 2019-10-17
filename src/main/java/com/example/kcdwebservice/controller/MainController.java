package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.*;
import com.example.kcdwebservice.util.HttpClientSearch;
import com.example.kcdwebservice.util.HttpRestCall;
import com.example.kcdwebservice.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CmKcdService cmKcdService;
    @Autowired
    private DescriptionService descriptionService;
    @Autowired
    private MapKcdSctService mapKcdSctService;
    @Autowired
    private RuleMapService ruleMapService;
    @Autowired
    private SearchService searchService;
    /**
     * 메인화면.
     * @return
     */
    @RequestMapping(value="/")
    public String main(){
        return "/index";
    }

    /**
     * KCD 전체 목록
     * @param cmKcdVo
     * @return
     */
    @RequestMapping(value="/selectAll")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdSelectAll(CmKcdVo cmKcdVo){
        List<CmKcdVo> list = cmKcdService.selectAll(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
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
     * kcd 매핑상태별 total count
     * @param mappingStatus
     * @return
     */
    @RequestMapping(value="/getTotalCount")
    @ResponseBody
    public ResponseEntity<String> getTotalCount(@RequestParam("mappingStatus")String mappingStatus, @RequestParam("mapVer")String mapVer){
        String totalCnt = cmKcdService.mappingStatTotalCnt(mappingStatus, mapVer);
        return new ResponseEntity<>(totalCnt, HttpStatus.OK);
    }


    /**
     * kcd코드 상세화면
     * @return
     */
    @RequestMapping(value="/kcdDetailPage")
    public ModelAndView kcdDetailPage(@RequestParam("kcdCd")String kcdCd, @RequestParam("mapVer")String mapVer){
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
    public ResponseEntity<List<DescriptionVo>> sctIdDetailList(@RequestParam("sctId")String sctId){
        List<DescriptionVo> descriptionList = descriptionService.getDescriptionList(sctId);
        return new ResponseEntity<>(descriptionList, HttpStatus.OK);
    }


    /**
     * sctId detailList
     * @param sctId
     * @return
     */
 
    @GetMapping(value="/kcdRule1")
    @ResponseBody
    public String autoMapKcdRule1(){
        ruleMapService.automap1("<64572001"); //disease (discorder)
       return "/index";
    }

    @GetMapping(value="/kcdRule2")
    @ResponseBody
    public String autoMapKcdRule2(){
        ruleMapService.automap1("<404684003"); //Clinical finding (finding)
       return "/index";
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
        System.out.println(result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value="/insertSearchList")
    @ResponseBody
    public void insertSearchObject(MapKcdSctVo mapKcdSctVo){
        mapKcdSctService.insertMapKcdSctInfo(mapKcdSctVo);
    }

    @RequestMapping(value="/deleteKcdList")
    @ResponseBody
    public void deleteKcdList(MapKcdSctVo mapKcdSctVo){
        mapKcdSctService.deleteMapKcdSctInfo(mapKcdSctVo);
    }

}
