package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmMediService;
import com.example.kcdwebservice.service.MapKcdSctService;
import com.example.kcdwebservice.service.RuleMapService;
import com.example.kcdwebservice.util.AutoRules;
import com.example.kcdwebservice.vo.CmMedicineVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;
import com.example.kcdwebservice.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MedicineController {
    @Autowired
    private CmMediService cmMediService;
    @Autowired
    private MapKcdSctService mapKcdSctService;
    @Autowired
    private RuleMapService ruleMapService;

    /**
     * 리스트 조회.
     * 전체 :  All
     * 매핑 : Mapping
     * 비매핑 : NoMapping
     *
     * @param option
     * @param cmMedicineVo
     * @return
     */
    @RequestMapping(value = "/medicine/select/{option}")
    @ResponseBody
    public ResponseEntity<List<CmMedicineVo>> mediSelect(@PathVariable("option") String option, CmMedicineVo cmMedicineVo) {
        List<CmMedicineVo> list = null;
        if (option.equals("All")) {
            list = cmMediService.medi_selectAll(cmMedicineVo);
        } else if (option.equals("Mapping")) {
            list = cmMediService.medi_selectMapping(cmMedicineVo);
        } else {
            list = cmMediService.medi_selectNoMapping(cmMedicineVo);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * 코드기준 카운트, 매핑리스트기준 카운트
     *
     * @param mappingStatus
     * @param cmMedicineVo
     * @return
     */
    @RequestMapping(value = "/getMediTotalCount")
    @ResponseBody
    public ResponseEntity<String> getMediTotalCount(@RequestParam("mappingStatus") String mappingStatus, CmMedicineVo cmMedicineVo) {
        String mediTotalCnt = cmMediService.medi_totalCnt(mappingStatus, cmMedicineVo);
        String totalCnt = cmMediService.medi_mappingStatusTotalCnt(mappingStatus, cmMedicineVo);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mediTotalCnt", mediTotalCnt);
            jsonObject.put("totalCnt", totalCnt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    /**
     * 약제 상세 화면.
     *
     * @param cmMedicineVo
     * @return
     */
    @RequestMapping(value = "/medDetailPage")
    public ModelAndView medDetailPage(CmMedicineVo cmMedicineVo) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("kdCd", cmMedicineVo.getKdCd());
        mav.addObject("mapVer", cmMedicineVo.getMapVer());
        mav.setViewName("/medicinePage/medDetail");
        return mav;
    }

    /**
     * 약제 정보 조회.
     *
     * @param kdCd
     * @return
     */
    @RequestMapping(value = "/getMediInfo")
    @ResponseBody
    public ResponseEntity<CmMedicineVo> getMediInfo(@RequestParam("kdCd") String kdCd) {
        CmMedicineVo cmMedicineVo = cmMediService.getMediInfo(kdCd);
        return new ResponseEntity<>(cmMedicineVo, HttpStatus.OK);
    }

    /**
     * 상세화면 리스트 조회.
     *
     * @param kdCd
     * @return
     */
    @RequestMapping(value = "/getMediDetailList")
    @ResponseBody
    public ResponseEntity<List<CmMedicineVo>> getMediDetailList(@RequestParam("kdCd") String kdCd) {
        List<CmMedicineVo> list = cmMediService.mediDetailList(kdCd);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * medi 삭제
     *
     * @param mapKcdSctVo
     */
    @RequestMapping(value = "/deleteMediList")
    @ResponseBody
    public void deleteMediList(MapKcdSctVo mapKcdSctVo) {
        mapKcdSctService.deleteMapKcdSctInfo_medi(mapKcdSctVo);
    }

    /**
     * 룰
     *
     * @param searchVo
     */
    @RequestMapping(value = "/mediAutoRuleSet")
    @ResponseBody
    public void mediAutoRuleSet(SearchVo searchVo) {
        System.out.println(searchVo.getEcl());
//        List<String> list = ruleMapService.searchTerm(searchVo.getTerm());
//        List<CmMedicineVo> list = ruleMapService.selectMediList("20");
//        System.out.println(list.toString());
    }

    /*
    @PostMapping(value="/mediExcelDownload.xlsx")
    public String mediExcelDownload(CmMedicineVo cmMedicineVo, Model model){
        System.out.println(cmMedicineVo.getKdCd());
        return "";
    }
    */

}
