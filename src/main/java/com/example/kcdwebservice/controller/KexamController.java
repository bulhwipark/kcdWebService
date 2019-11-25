package com.example.kcdwebservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.kcdwebservice.service.CmKexamService;
import com.example.kcdwebservice.service.SearchService;
import com.example.kcdwebservice.vo.CmKexamVo;
import com.example.kcdwebservice.vo.CmMedicineVo;

@Controller
public class KexamController {
	@Autowired
    SearchService searchService;
	
	@Autowired
	CmKexamService cmKexamService;
	
	
	/**
	 * 
	 * CM_KEXAM 테이블에서 kexEng 값이 있는 row 들을 읽어와서 정규화 과정을 거쳐 api를 호출한다.
	 * api 호출 결과가 정규화한 값과 완전 일치하면 MAP_KCD_SCT3 테이블에 값을 기록한다.
	 * ORI_TP_CD : KEXAM
	 * MAP_VER : 추후에 확장성을 위해 만들어 놓은 필드
	 * ORI_CD : KEX_CD
	 * SCT_ID : SNOMEDCT에서 찾은 id 값
	 * MAP_STAT_CD : 정규화매핑코드(대분류번호-NO 와 소분류번호-기본룰 을 합쳐 생성한 코드)
	 * 
	 */
	@RequestMapping(value = "/kexam/domapping")
	public void domapping () {
		List<CmKexamVo> list = cmKexamService.selectAll();
		for(CmKexamVo kexam : list) {
            
			//service 호출 (지금은 이렇게 만들어 놓고 추후에 service.domapping 추가해서 정규화 로직 따라가게 만들기)
			cmKexamService.domapping(kexam);
		}
	}
	
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
    public ResponseEntity<List<CmKexamVo>> kexamSelect(@PathVariable("option") String option, CmKexamVo cmKexamVo) {
        List<CmKexamVo> list = null;
        if (option.equals("All")) {
            list = cmKexamService.kexam_selectAll(cmKexamVo);
        } else if (option.equals("Mapping")) {
            list = cmKexamService.kexam_selectMapping(cmKexamVo);
        } else {
            list = cmKexamService.kexam_selectNoMapping(cmKexamVo);
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
    @RequestMapping(value = "/getKexamTotalCount")
    @ResponseBody
    public ResponseEntity<String> getKexamTotalCount(@RequestParam("mappingStatus") String mappingStatus, CmKexamVo cmKexamVo) {
        String kexamTotalCnt = cmKexamService.kexam_totalCnt(mappingStatus, cmKexamVo);
        String totalCnt = cmKexamService.kexam_mappingStatusTotalCnt(mappingStatus, cmKexamVo);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mediTotalCnt", kexamTotalCnt);
            jsonObject.put("totalCnt", totalCnt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
    }
}
