package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmMediService;
import com.example.kcdwebservice.vo.CmMedicineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MedicineController {
    @Autowired
    private CmMediService cmMediService;

    @RequestMapping(value="/medicine/select/{option}")
    @ResponseBody
    public ResponseEntity<List<CmMedicineVo>> mediSelect(@PathVariable("option")String option, CmMedicineVo cmMedicineVo){
        List<CmMedicineVo> list = null;
        if(option.equals("All")){
            list = cmMediService.medi_selectAll(cmMedicineVo);
        }else if(option.equals("Mapping")){
            list = cmMediService.medi_selectMapping(cmMedicineVo);
        }else{
            list = cmMediService.medi_selectNoMapping(cmMedicineVo);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value="/getMediTotalCount")
    @ResponseBody
    public ResponseEntity<String> getMediTotalCount(@RequestParam("mappingStatus")String mappingStatus, CmMedicineVo cmMedicineVo){
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


    /*
    @PostMapping(value="/mediExcelDownload.xlsx")
    public String mediExcelDownload(CmMedicineVo cmMedicineVo, Model model){
        System.out.println(cmMedicineVo.getKdCd());
        return "";
    }
    */


}
