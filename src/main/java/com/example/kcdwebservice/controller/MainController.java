package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmKcdService;
import com.example.kcdwebservice.service.DescriptionService;
import com.example.kcdwebservice.service.TempKcd7Service;
import com.example.kcdwebservice.service.TempmapicdsctService;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.DescriptionVo;
import com.example.kcdwebservice.vo.TempKcd7Vo;
import com.example.kcdwebservice.vo.TempmapicdsctVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CmKcdService cmKcdService;
    @Autowired
    private DescriptionService descriptionService;

    /**
     * 메인화면.
     * @return
     */
    @RequestMapping(value="/")
    public String main(){
        return "/index";
    }

    /**
     * KCD목록
     * @param cmKcdVo
     * @return
     */
    @RequestMapping(value="/getKcdList")
    @ResponseBody
    public ResponseEntity<List<CmKcdVo>> kcdList(CmKcdVo cmKcdVo){
        List<CmKcdVo> list = cmKcdService.select(cmKcdVo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * kcd코드 상세화면
     * @return
     */
    @RequestMapping(value="/kcdDetailPage")
    public String kcdDetail(){
        return "/DiagnosisNamePage/kcdDetail";
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

    @GetMapping(value="/detailList")
    @ResponseBody
    public ResponseEntity<List<DescriptionVo>> sctIdDetailList(@RequestParam("sctId")String sctId){
        List<DescriptionVo> descriptionList = descriptionService.getDescriptionList(sctId);
        return new ResponseEntity<>(descriptionList, HttpStatus.OK);
    }

}
