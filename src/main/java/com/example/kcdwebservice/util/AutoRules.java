package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.SearchVo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class AutoRules {
    /**
     * snowstrom api 호출.
      * @param searchVo
     * @return
     */
    public String autoRuleRequest(SearchVo searchVo) {
        String result = null;
        String URL = "http://1.224.169.78:8095/MAIN/concepts?";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("activeFilter", "true");
        paramMap.put("termActive", "true");
        paramMap.put("ecl", searchVo.getEcl());
        paramMap.put("term", searchVo.getTerm());
        result = HttpRestCall.callGet(URL, paramMap);
        return result;
    }

    public String autoRuleRequest(SearchVo searchVo, String conceptId){
        String result = null;
        String URL = "http://1.224.169.78:8095/browser/MAIN/concepts/"+ conceptId +"?";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("branch", "MAIN");
        paramMap.put("conceptId", conceptId);
        //paramMap.put("form", "inferred");
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
            "Other",
            "unspecified",
            "alone",
            "alone",
            "without complication",
            "single",
            "side",
            "Side"
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

        System.out.println("-------------rule_1-------------------");
        System.out.println(result);
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
        System.out.println("-------------rule_2-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");

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
     * s, ',' , '-' 제거
     * * @param searchVo
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
        System.out.println("-------------rule_4-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");
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
        System.out.println("-------------rule_6-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");
        return returnJSON;
    }

    /**
     * 앞 단어부터 하나씩 제거.
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_7(SearchVo searchVo) throws JSONException {
        JSONObject returnJSON = new JSONObject();
        returnJSON.put("status", "false");
        returnJSON.put("ruleCode", "97");
        return returnJSON;
    }

    /**
     * Elasticsearch api 호출.
     * @param searchVo
     * @return \"Benign neoplasm of breast, unspecifiedt\"\n"
     */
    public JSONObject autoRule_8(SearchVo searchVo) throws JSONException {
        Set<String> conceptIdList = new HashSet<>();
        JSONObject returnJSON = new JSONObject();
        try{
            String jsonStr = "{\"query\": {\"query_string\" : {\"query\" : \""+ searchVo.getTerm() +"\"}},\"_source\": [\"conceptId\",\"term\"]}";
            RestClient restClient = RestClient.builder(
                    new HttpHost("1.224.169.78", 9200, "http")
            ).build();

            Map<String, String> params = Collections.EMPTY_MAP;
            HttpEntity httpEntity = new NStringEntity(jsonStr, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest("GET", "/description/_search", params, httpEntity );

            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(result);
            JSONObject hitsObj = new JSONObject(String.valueOf(jsonObject.get("hits")));
            JSONArray jsonArray = hitsObj.getJSONArray("hits");
            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
                obj = new JSONObject(String.valueOf(obj.get("_source")));
                conceptIdList.add(String.valueOf(obj.get("conceptId")));
            }
            System.out.println(conceptIdList.toString());
            List<JSONObject> items = new ArrayList<>();
            for(int i = 0; i<conceptIdList.size(); i++){
                String res = autoRuleRequest(searchVo, String.valueOf(conceptIdList.toArray()[i]));
                JSONArray resList = new JSONObject(res).getJSONArray("classAxioms");

                for(int k = 0; k<resList.length(); k++){
                    JSONArray relationships = resList.getJSONObject(k).getJSONArray("relationships");

                    for(int h = 0; h < relationships.length(); h++){
                        JSONObject target = relationships.getJSONObject(h).getJSONObject("target");
                        /*if(target.get("conceptId").equals(searchVo.getEcl().replace("<", ""))){
                            items.add((JSONObject) new JSONObject(res));
                        }*/
                        items.add((JSONObject) new JSONObject(res));
                    }
                }
            }
            Map<String, Object> checkMap = new HashMap<>();
            checkMap.put("items", items);
            JSONObject checkJSON = new JSONObject(checkMap);

            if(items.size() > 0){
                returnJSON.put("status", "true");
                returnJSON.put("result", checkJSON.toString());
                returnJSON.put("searchTerm", searchVo.getTerm());
                returnJSON.put("ruleCode", "98");
            }else{
                returnJSON.put("status", "false");
                returnJSON.put("ruleCode", "98");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
       return returnJSON;
    }
}
