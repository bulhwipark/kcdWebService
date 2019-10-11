package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmKcdService;
import com.example.kcdwebservice.service.TempKcd7Service;
import com.example.kcdwebservice.service.TempmapicdsctService;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.TempKcd7Vo;
import com.example.kcdwebservice.vo.TempmapicdsctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CmKcdService cmKcdService;

    @RequestMapping(value="/")
    public String main(){
        return "/index";
    }

    @RequestMapping(value="/getKcdList")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdList(CmKcdVo cmKcdVo){
        List<CmKcdVo> list = cmKcdService.select(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value="/kcdDetailPage")
    public String kcdDetail(){
        return "/DiagnosisNamePage/kcdDetail";
    }


}
