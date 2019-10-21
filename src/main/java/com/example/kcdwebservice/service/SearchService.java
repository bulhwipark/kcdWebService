package com.example.kcdwebservice.service;

import com.example.kcdwebservice.util.AutoRules;
import com.example.kcdwebservice.util.HttpRestCall;
import com.example.kcdwebservice.vo.SearchVo;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchService {
    public String searchReqeust(SearchVo searchVo) throws JSONException {
        String result = null;
        String URL = "http://1.224.169.78:8095/MAIN/concepts?";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("activeFilter", "true");
        paramMap.put("termActive", "true");
       // paramMap.put("limit", "50");
       // paramMap.put("offset", "0");
        paramMap.put("term", searchVo.getTerm());

        for(int i = 0; i<searchVo.getEcl().size(); i++){
            paramMap.put("ecl", searchVo.getEcl().get(i));
            result = HttpRestCall.callGet(URL, paramMap);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getJSONArray("items").length() > 0) {
                break;
            }
        }
        return result;
    }

    public JSONObject autoRuleRequest(SearchVo searchVo) {
        AutoRules autoRules = new AutoRules();
        JSONObject result = null;
        try {
           result = autoRules.autoRule_1(searchVo);
           if(result.get("status").equals("false")) {
               result = autoRules.autoRule_2(searchVo);
               if(result.get("status").equals("false")){
                   result = autoRules.autoRule_3(searchVo);
                   if(result.get("status").equals("false")){
                       result = autoRules.autoRule_4(searchVo);
                       if(result.get("status").equals("false")){
                           result = autoRules.autoRule_6(searchVo);
                       }
                   }
               }
           }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}

