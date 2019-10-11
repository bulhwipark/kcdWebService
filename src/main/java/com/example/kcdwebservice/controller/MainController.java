package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmKcdService;
import com.example.kcdwebservice.service.TempKcd7Service;
import com.example.kcdwebservice.service.TempmapicdsctService;
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
    private TempKcd7Service tempKcd7Service;

    @Autowired
    private TempmapicdsctService tempmapicdsctService;

    @RequestMapping(value="/")
    public String main(){
        return "/index";
    }

    @GetMapping(value="/selectTemp")
    @ResponseBody
    public ResponseEntity<List<TempKcd7Vo>> kcdList(){
        System.out.println("commit test");
        List<TempKcd7Vo> list = tempKcd7Service.select();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value="/selectTemp2")
    @ResponseBody
    public ResponseEntity<List<TempmapicdsctVo>> tempList(){
        List<TempmapicdsctVo> list = tempmapicdsctService.select();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
