package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmMediService;
import com.example.kcdwebservice.service.MapKcdSctService;
import com.example.kcdwebservice.service.SearchService;
import com.example.kcdwebservice.vo.CmMedicineVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    SearchService searchService;

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
     * medi 저장
     * @param mapKcdSctVo
     */
    @RequestMapping(value="/mediInsertSearchList")
    @ResponseBody
    public void mediInsertSearchList(MapKcdSctVo mapKcdSctVo){
        mapKcdSctService.insertMapKcdSctInfo_medi(mapKcdSctVo);
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
     * medi AUTO RULE
     * @param cmMedicineVo
     */
    @RequestMapping(value = "/mediAutoRuleSet")
    @ResponseBody
    public ResponseEntity<String> mediAutoRuleSet(CmMedicineVo cmMedicineVo) {
        List<JSONObject> list = searchService.medi_autoRuleRequest(cmMedicineVo);
        return new ResponseEntity<>(list.toString(), HttpStatus.OK);
    }

    @PostMapping(value="/mediExcelDownload.xlsx")
    public String mediExcelDownload(CmMedicineVo cmMedicineVo, Model model){
        List<CmMedicineVo> list = null;
        if (cmMedicineVo.getMedListOption().equals("All")) {
            list = cmMediService.medi_selectAll(cmMedicineVo);
        } else if (cmMedicineVo.getMedListOption().equals("Mapping")) {
            list = cmMediService.medi_selectMapping(cmMedicineVo);
        } else {
            list = cmMediService.medi_selectNoMapping(cmMedicineVo);
        }

        model.addAttribute("list", list);
        model.addAttribute("sheetNm", "제약 목록");
        model.addAttribute("headerNmArr", new String[]{"KDCD코드", "한글명/영문명", "SCTID", "Snomed CT Term", "매핑상태", "매핑일자" });

        return "MediExcelDownload";
    }

}
