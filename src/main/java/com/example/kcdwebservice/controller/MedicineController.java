package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmMediService;
import com.example.kcdwebservice.vo.CmMedicineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MedicineController {
    @Autowired
    private CmMediService cmMediService;

    @RequestMapping(value="/medicine/select/{option}")
    @ResponseBody
    public ResponseEntity<List<CmMedicineVo>> mediSelect(@PathVariable("option")String option, CmMedicineVo cmMedicineVo){
        List<CmMedicineVo> list = null;
        if(option.equals("all")){
            list = cmMediService.selectAll(cmMedicineVo);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
