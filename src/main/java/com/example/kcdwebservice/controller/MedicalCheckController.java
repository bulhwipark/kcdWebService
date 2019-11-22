package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmKexamService;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.CmKexamVo;
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
public class MedicalCheckController {
    @Autowired
    private CmKexamService kexamService;

    @RequestMapping(value="/mediCheckDetailPage")
    public ModelAndView mediCheckDetailPage(
            @RequestParam("kexCd")String kexCd,
            @RequestParam("mapVer")String mapVer
    ){
        ModelAndView mav = new ModelAndView();
        mav.addObject("kexCd", kexCd);
        mav.addObject("mapVer", mapVer);
        mav.setViewName("/medicalCheckPage/medicalCheckDetail");
        return mav;
    }

    @RequestMapping(value="/getMediCheckInfo")
    @ResponseBody
    public ResponseEntity<CmKexamVo> getMediCheckInfo(@RequestParam("kexCd")String kexCd){
        CmKexamVo cmKexamVo = kexamService.selectKexCdInfo(kexCd);
        return new ResponseEntity<>(cmKexamVo, HttpStatus.OK);
    }

    @RequestMapping(value="/getMedicalCheckTotalCnt")
    @ResponseBody
    public ResponseEntity<String> getMedicalCheckTotalCnt(@RequestParam("mappingStatus")String mappingStatus, CmKexamVo cmKexamVo){
        String kexTotalCnt = kexamService.kexam_totalCnt(mappingStatus, cmKexamVo);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("kexTotalCnt", kexTotalCnt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }

    @PostMapping("/mediCheckExcelDownload.xlsx")
    public String mediCheckExcelDownload(CmKexamVo cmKexamVo, Model model){
        List<CmKexamVo> list = null;
        if(cmKexamVo.getListOption().equals("All")){
            list = kexamService.kexam_selectAll(cmKexamVo);
        }

        model.addAttribute("list", list);
        model.addAttribute("sheetNm", "검사 목록");
        model.addAttribute("headerNmArr", new String[]{"KEX코드", "한글명/영문명", "SCTID", "Snomed CT Term", "매핑상태", "매핑일자" });
        return "mediCheckExcelDownload";
    }

}
