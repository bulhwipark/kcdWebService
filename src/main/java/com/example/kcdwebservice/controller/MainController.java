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
     * kcd코드 상세화면
     * @return
     */
    @RequestMapping(value="/kcdDetailPage")
    public String kcdDetailPage(){
        return "/DiagnosisNamePage/kcdDetail";
    }

    @RequestMapping(value="/kcdDetail")
    public String kcdDetail(){
        return "";
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



}
