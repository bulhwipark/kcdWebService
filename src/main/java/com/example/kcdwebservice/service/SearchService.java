package com.example.kcdwebservice.service;

import com.example.kcdwebservice.util.HttpRestCall;
import com.example.kcdwebservice.vo.SearchVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SearchService {
    public String searchReqeust(SearchVo searchVo) {
        String result = null;
        String URL = "http://1.224.169.78:8095/MAIN/concepts?";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("activeFilter", "true");
        paramMap.put("termActive", "true");
        paramMap.put("limit", "50");
        paramMap.put("offset", "0");
        paramMap.put("term", searchVo.getTerm());

        if(searchVo.getDisorder() != null){
            paramMap.put("ecl", searchVo.getDisorder());
            result = HttpRestCall.callGet(URL, paramMap);
            if(result.length() > 0){
                return result;
            }
        }else if(searchVo.getClinicalFinding() != null){
            paramMap.put("ecl", searchVo.getClinicalFinding());
            result = HttpRestCall.callGet(URL, paramMap);
            if(result.length() > 0){
                return result;
            }
        }else if(searchVo.getEcl() != null){
            paramMap.put("ecl", searchVo.getEcl());
            result = HttpRestCall.callGet(URL, paramMap);
            if(result.length() > 0){
                return result;
            }
        }
        return "tetetetet";
    }
}

