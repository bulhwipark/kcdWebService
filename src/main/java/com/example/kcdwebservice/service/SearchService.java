package com.example.kcdwebservice.service;

import com.example.kcdwebservice.util.AutoRules;
import com.example.kcdwebservice.util.HttpRestCall;
import com.example.kcdwebservice.vo.CmMedicineVo;
import com.example.kcdwebservice.vo.SearchVo;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        paramMap.put("ecl", searchVo.getEcl());
        result = HttpRestCall.callGet(URL, paramMap);
        return result;
    }

    public List<JSONObject> autoRuleRequest(SearchVo searchVo) {
        AutoRules autoRules = new AutoRules();
        List<JSONObject> list = new ArrayList<>();
        try {
           /*
           result = autoRules.autoRule_1(searchVo);
            if (result.get("status").equals("false")) {
                result = autoRules.autoRule_2(searchVo);
                if (result.get("status").equals("false")) {
                    result = autoRules.autoRule_3(searchVo);
                    if (result.get("status").equals("false")) {
                        result = autoRules.autoRule_4(searchVo);
                        if (result.get("status").equals("false")) {
                            result = autoRules.autoRule_6(searchVo);
                            if (result.get("status").equals("false")) {
                                result = autoRules.autoRule_8(searchVo);
                            }
                        }
                    }
                }
            }
            */
           list.add(autoRules.autoRule_1(searchVo));
           list.add(autoRules.autoRule_2(searchVo));
           list.add(autoRules.autoRule_3(searchVo));
           list.add(autoRules.autoRule_4(searchVo));
           list.add(autoRules.autoRule_5(searchVo));
           //list.add(autoRules.autoRule_6(searchVo));
           /*list.add(autoRules.autoRule_7(searchVo));*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 유사도 기준 검색 룰만 별도로 실행.
     * @param searchVo
     * @return
     */
    public JSONObject similarityRequest(SearchVo searchVo) {
        AutoRules autoRules = new AutoRules();
        JSONObject result = null;
        try {
            result =  autoRules.autoRule_7(searchVo);
            System.out.println(result.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String searchConceptId(String valSctId) {
        return new AutoRules().autoRuleRequest(valSctId);
    }

    public List<JSONObject> medi_autoRuleRequest(CmMedicineVo cmMedicineVo) {
        AutoRules autoRules = new AutoRules();
        List<JSONObject> list = new ArrayList<>();
        try {
            list.add(autoRules.medi_autoRule_1(cmMedicineVo));
            list.add(autoRules.medi_autoRule_2(cmMedicineVo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}

