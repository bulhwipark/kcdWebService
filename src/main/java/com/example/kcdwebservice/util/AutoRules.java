package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.SearchVo;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.HashMap;

public class AutoRules {

    public String autoRuleRequest(SearchVo searchVo) {
        String result = null;
        String URL = "http://1.224.169.78:8095/MAIN/concepts?";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("activeFilter", "true");
        paramMap.put("termActive", "true");
        paramMap.put("term", searchVo.getTerm());
        result = HttpRestCall.callGet(URL, paramMap);
        return result;
    }
    public String autoRule_1(SearchVo searchVo) throws JSONException {

        String result = null;
        String term = null;
        String[] strSet = new String[] {
            "and",
            "with",
            "other",
            "unspecified",
            "alone",
            "without complication",
            "single"
        };

        for(int i = 0; i<strSet.length; i++){
            searchVo.setTerm(
               searchVo.getTerm().replace(strSet[i]+ " ", "")
            );
            result = autoRuleRequest(searchVo);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getJSONArray("items").length() > 0) {
                term = searchVo.getTerm();
                break;
            }
        }

        JSONObject checkObject = new JSONObject(result);
        JSONObject returnJSON = new JSONObject();
        if(checkObject.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", term);
            returnJSON.put("ruleCode", "91");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("ruleCode", "91");
        }

        System.out.println("-------------------------------------------");
        System.out.println("rule_1 : " + result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");

        return returnJSON.toString();
   }


}
