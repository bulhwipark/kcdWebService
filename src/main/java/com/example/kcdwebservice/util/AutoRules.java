package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.SearchVo;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.HashMap;

/**
 *
 */
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

    /**
     * 룰 1
     * "and", "with", "other", "unspecified", "alone", "without complication", "single" 를 제거 후 검색.
     * @param searchVo
     * @return
     * 검색결과 있을때 : {"status":"false","result":[],"searchTerm":"", "ruleCode":"91"}
     * 없을때 : {"status":"false","ruleCode":"91"}
     * @throws JSONException
     */
    public JSONObject autoRule_1(SearchVo searchVo) throws JSONException {

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

        return returnJSON;
   }

    /**
     * s, es 제거
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_2(SearchVo searchVo) throws JSONException {
        String result = null;
        String[] term = searchVo.getTerm().split(" ");

        for (int i = 0; i < term.length; i++) {
            if (term[i].lastIndexOf("s") > -1) {
                term[i] = term[i].substring(0, term[i].lastIndexOf("s"));
            } else if (term[i].lastIndexOf("es") > -1) {
                term[i] = term[i].substring(0, term[i].lastIndexOf("es"));
            }
        }
        searchVo.setTerm(String.join(" ", term));
        result = autoRuleRequest(searchVo);

        JSONObject returnJSON = new JSONObject();
        JSONObject checkJSON = new JSONObject(result);
        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "92");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("ruleCode", "92");
        }
        return returnJSON;
    }

    /**
     * 각 단어에서 마지막 문자를 제거.
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_3(SearchVo searchVo) throws JSONException {
        System.out.println(searchVo.getTerm());
        JSONObject returnJSON = new JSONObject();
        returnJSON.put("status", "false");
        returnJSON.put("ruleCode", "93");
        return returnJSON;
    }

    /**
     * s, ',' , '-' 제거     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_4(SearchVo searchVo) throws JSONException {
        searchVo.setTerm(searchVo.getTerm().replaceAll("[s,-]", ""));
        String result = autoRuleRequest(searchVo);
        JSONObject checkJSON = new JSONObject(result);
        JSONObject returnJSON = new JSONObject();
        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "94");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("ruleCode", "94");
        }
        return returnJSON;
    }

    /**
     * '(', '[' 사이 단어까지 모두 제거.
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_6(SearchVo searchVo) throws JSONException {
        searchVo.setTerm(searchVo.getTerm().replaceAll("\\((.*?)\\)", ""));
        searchVo.setTerm(searchVo.getTerm().replaceAll("\\[(.*?)\\]", ""));
        String result = autoRuleRequest(searchVo);
        JSONObject checkJSON = new JSONObject(result);
        JSONObject returnJSON = new JSONObject();
        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "96");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("ruleCode", "96");
        }
        return returnJSON;
    }

    /**
     * 앞 단어부터 하나씩 제거.
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_7(SearchVo searchVo) throws JSONException {
        System.out.println(searchVo.getTerm());
        JSONObject returnJSON = new JSONObject();
        returnJSON.put("status", "false");
        returnJSON.put("ruleCode", "97");
        return returnJSON;
    }
}
