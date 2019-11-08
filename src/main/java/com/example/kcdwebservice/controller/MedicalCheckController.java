package com.example.kcdwebservice.controller;

import com.example.kcdwebservice.service.CmKexamService;
import com.example.kcdwebservice.vo.CmKexamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MedicalCheckController {
    @Autowired
    private CmKexamService kexamService;

    /**
     * 리스트 조회.
     * 전체 :  All
     * 매핑 : Mapping
     * 비매핑 : NoMapping
     *
     * @param option
     * @param cmKexamVo
     * @return
     */
    @RequestMapping(value = "/kexam/select/{option}")
    @ResponseBody
    public ResponseEntity<List<CmKexamVo>> mediSelect(@PathVariable("option") String option, CmKexamVo cmKexamVo) {
        List<CmKexamVo> list = null;
        if (option.equals("All")) {
            list = kexamService.kexam_selectAll(cmKexamVo);
        } else if (option.equals("Mapping")) {
//            list = kexamService.medi_selectMapping(cmMedicineVo);
        } else {
//            list = kexamService.medi_selectNoMapping(cmMedicineVo);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

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

}
