package com.example.kcdwebservice.util;

import com.example.kcdwebservice.vo.CmMedicineVo;
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

/**
 * 'medi_' : 약제 쪽 룰.
 */
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

    public String medi_autoRuleRequest(SearchVo searchVo) {
        String result = null;
        String URL = "http://1.224.169.78:8095/MAIN/concepts?";
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("activeFilter", "true");
        paramMap.put("termActive", "true");
//        paramMap.put("ecl", searchVo.getEcl());
        paramMap.put("term", searchVo.getTerm());
        result = HttpRestCall.callGet(URL, paramMap);
        return result;
    }

    public String autoRuleRequest(String conceptId){
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
     * malignant neoplasm -> malignant tumor
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_1(SearchVo searchVo) throws JSONException {
        String result = null;
        String[] strArr = new String[]{
                "malignant neoplasm",
                "Malignant neoplasm"
        };

        for(int i = 0; i<strArr.length; i++){
            if(searchVo.getTerm().indexOf(strArr[i]) > -1){
                searchVo.setTerm(
                        searchVo.getTerm().replace(strArr[i], "malignant tumor")
                );
            }
            result = autoRuleRequest(searchVo);
        }

        JSONObject checkJSON = new JSONObject(result);
        JSONObject returnJSON = new JSONObject();
        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "91");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "91");
        }
        System.out.println("-------------rule_1-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");
        return returnJSON;

    }

    /**
     * 's, ',' , '-' 제거
     * * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_2(SearchVo searchVo) throws JSONException {
        searchVo.setTerm(searchVo.getTerm().replaceAll("'s|[,-/]", ""));
        String result = autoRuleRequest(searchVo);
        JSONObject checkJSON = new JSONObject(result);
        JSONObject returnJSON = new JSONObject();
        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "92");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
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
     * '(', '[' 사이 단어까지 모두 제거.
     * @param searchVo
     * @return
     * @throws JSONException
     */
    public JSONObject autoRule_3(SearchVo searchVo) throws JSONException {
        searchVo.setTerm(searchVo.getTerm().replaceAll("\\((.*?)\\)", ""));
        searchVo.setTerm(searchVo.getTerm().replaceAll("\\[(.*?)\\]", ""));
        String result = autoRuleRequest(searchVo);
        JSONObject checkJSON = new JSONObject(result);
        JSONObject returnJSON = new JSONObject();
        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "93");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "93");
        }
        System.out.println("-------------rule_3-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");
        return returnJSON;
    }

    /**
     * 룰 4
     * @param searchVo
     * @return
     * 검색결과 있을때 : {"status":"false","result":[],"searchTerm":"", "ruleCode":"91"}
     * 없을때 : {"status":"false","ruleCode":"91"}
     * @throws JSONException
     */
    public JSONObject autoRule_4(SearchVo searchVo) throws JSONException {

        String result = null;
        String term = null;
        String[] strSet = new String[] {
            "without complication",
            "with other complication",
            "and",
            "with",
            "other",
            "Other",
            "unspecified",
            "Unspecified",
            "alone",
            "Alone",
            "single",
            "Single",
            "side",
            "Side",
            "right",
            "Right",
            "left",
            "Left",
            "multiple",
            "Multiple"
        };

        for(int i = 0; i<strSet.length; i++){
            searchVo.setTerm(
                    searchVo.getTerm().replace(strSet[i], "")
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
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "94");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "94");
        }

        System.out.println("-------------rule_4-------------------");
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
    public JSONObject autoRule_5(SearchVo searchVo) throws JSONException {
        String result = null;
        String[] term = searchVo.getTerm().split(" ");

        for(int i = 0; i<term.length; i++){
            if(term[i].length() > 0){
                if(term[i].substring((term[i].length()-2), term[i].length()).equals("es")){
                    term[i] = term[i].substring(0, (term[i].length()-2));
                }else if(term[i].substring((term[i].length()-1), term[i].length()).equals("s")){
                    term[i] = term[i].substring(0, (term[i].length()-1));
                }
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
            returnJSON.put("ruleCode", "95");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "95");
        }
        
        System.out.println("-------------rule_5-------------------------");
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
    public JSONObject autoRule_6(SearchVo searchVo) throws JSONException {
        JSONObject returnJSON = new JSONObject();
        String[] term = searchVo.getTerm().trim().split(" ");
        for(int i = 0; i<term.length; i++){
            if(term[i].length() != 0){
                term[i] = term[i].substring(0, term[i].length()-1);
            }
        }

        searchVo.setTerm(
                String.join(" ", term)
        );
        String result = autoRuleRequest(searchVo);
        JSONObject checkJSON = new JSONObject(result);

        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "96");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
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
     * Elasticsearch api 호출.
     * @param searchVo
     * @return \"Benign neoplasm of breast, unspecifiedt\"\n"
     */
    public JSONObject autoRule_7(SearchVo searchVo) throws JSONException {
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
                String res = autoRuleRequest(String.valueOf(conceptIdList.toArray()[i]));
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
                returnJSON.put("ruleCode", "97");
            }else{
                returnJSON.put("status", "false");
                returnJSON.put("searchTerm", searchVo.getTerm());
                returnJSON.put("ruleCode", "97");
            }

            System.out.println("-------------rule_7-------------------------");
            System.out.println(result);
            System.out.println(returnJSON);
            System.out.println(returnJSON.toString());
            System.out.println("-------------------------------------------");
        }catch(IOException e){
            e.printStackTrace();
        }
       return returnJSON;
    }

    /**
     * 약제 룰 1
     * @param cmMedicineVo
     * @return
     * @throws JSONException
     */
    public JSONObject medi_autoRule_1(CmMedicineVo cmMedicineVo) throws JSONException {
        JSONObject returnJSON = new JSONObject();
        String result = null;

        SearchVo searchVo = new SearchVo();
        searchVo.setEcl(cmMedicineVo.getEcl());
        searchVo.setTerm(
                cmMedicineVo.getSubstanceNm() + " " + cmMedicineVo.getAmount1() + " " + cmMedicineVo.getUnit1() + " " + cmMedicineVo.getRtOfAdmin().trim() + " " + cmMedicineVo.getMedDoseFrm()
        );
        result = medi_autoRuleRequest(searchVo);

        JSONObject checkJSON = new JSONObject(result);

        if(checkJSON.getJSONArray("items").length() == 0){
            if(cmMedicineVo.getUnit1().equals("g") && cmMedicineVo.getAmount1() < 1){
                cmMedicineVo.setUnit1("mg");
                cmMedicineVo.setAmount1(
                        cmMedicineVo.getAmount1() * 1000
                );
                cmMedicineVo.setStrAmount(String.format("%.0f", cmMedicineVo.getAmount1()));
            }else if(cmMedicineVo.getUnit1().equals("mg") && cmMedicineVo.getAmount1() < 1){
                cmMedicineVo.setUnit1("mcg");
                cmMedicineVo.setAmount1(
                        cmMedicineVo.getAmount1() * 1000
                );
                cmMedicineVo.setStrAmount(String.format("%.0f", cmMedicineVo.getAmount1()));
            }else if(cmMedicineVo.getUnit1().equals("mcg") && cmMedicineVo.getAmount1() < 1){
                cmMedicineVo.setUnit1("nanogram");
                cmMedicineVo.setAmount1(
                        cmMedicineVo.getAmount1() * 1000
                );
                cmMedicineVo.setStrAmount(String.format("%.0f", cmMedicineVo.getAmount1()));
            }
            searchVo.setEcl(cmMedicineVo.getEcl());
            searchVo.setTerm(
                    cmMedicineVo.getSubstanceNm() + " " + cmMedicineVo.getStrAmount() + " " + cmMedicineVo.getUnit1() + " " + cmMedicineVo.getRtOfAdmin().trim() + " " + cmMedicineVo.getMedDoseFrm()
            );
            System.out.println("term : " + searchVo.getTerm());
            System.out.println("ecl : " + searchVo.getEcl());
            result = medi_autoRuleRequest(searchVo);
        }

        checkJSON = new JSONObject(result);

        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "1");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "1");
        }
        System.out.println("-------------medi rule_1-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");

        return returnJSON;
    }

    /**
     * 약제 룰 2
     * @param cmMedicineVo
     * @return
     */
    public JSONObject medi_autoRule_2(CmMedicineVo cmMedicineVo) throws JSONException {
        JSONObject returnJSON = new JSONObject();
        String result = null;
        SearchVo searchVo = new SearchVo();
        searchVo.setEcl(cmMedicineVo.getEcl());
        searchVo.setTerm(
           cmMedicineVo.getSubstanceNm() + " " + cmMedicineVo.getAmount3() + " " + cmMedicineVo.getUnit3() + " " + cmMedicineVo.getRtOfAdmin().trim() + " " + cmMedicineVo.getMedDoseFrm()
        );
        result = medi_autoRuleRequest(searchVo);

        JSONObject checkJSON = new JSONObject(result);

        if(checkJSON.getJSONArray("items").length() == 0){
            if(cmMedicineVo.getUnit3().equals("mg")){
                cmMedicineVo.setUnit3("milligram");
            }else if(cmMedicineVo.getUnit3().equals("g")){
                cmMedicineVo.setUnit3("gram");
            }else if(cmMedicineVo.getUnit3().equals("μg")){
                cmMedicineVo.setUnit3("microgram");
            }else if(cmMedicineVo.getUnit3().equals("KI.U")){
                cmMedicineVo.setUnit3("KIU");
            }else if(cmMedicineVo.getUnit3().equals("MI.U")){
                cmMedicineVo.setUnit3("MIU");
            }else if(cmMedicineVo.getUnit3().equals("L")){
                cmMedicineVo.setUnit3("liter");
            }else if(cmMedicineVo.getUnit3().equals("cm2")){
                cmMedicineVo.setUnit3("square centimeter");
            }else if(cmMedicineVo.getUnit3().equals("mm")){
                cmMedicineVo.setUnit3("milimeter");
            }else if(cmMedicineVo.getUnit3().equals("mL/g")){
                cmMedicineVo.setUnit3("milliliter/gram");
            }else if(cmMedicineVo.getUnit3().equals("mL/mL")){
                cmMedicineVo.setUnit3("milliliter/milliliter");
            }else if(cmMedicineVo.getUnit3().equals("mL")){
                cmMedicineVo.setUnit3("milimeter");
            }else if(cmMedicineVo.getUnit3().equals("mg/mL")){
                cmMedicineVo.setUnit3("milligram/1 milliliter");
            }
            searchVo.setEcl(cmMedicineVo.getEcl());
            searchVo.setTerm(
                    cmMedicineVo.getSubstanceNm() + " " + cmMedicineVo.getAmount3() + " " + cmMedicineVo.getUnit3() + " " + cmMedicineVo.getRtOfAdmin().trim() + " " + cmMedicineVo.getMedDoseFrm()
            );
            result = medi_autoRuleRequest(searchVo);
        }

        checkJSON = new JSONObject(result);

        if(checkJSON.getJSONArray("items").length() > 0){
            returnJSON.put("status", "true");
            returnJSON.put("result", result);
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "2");
        }else{
            returnJSON.put("status", "false");
            returnJSON.put("searchTerm", searchVo.getTerm());
            returnJSON.put("ruleCode", "2");
        }
        System.out.println("-------------medi rule_2-------------------------");
        System.out.println(result);
        System.out.println(returnJSON);
        System.out.println(returnJSON.toString());
        System.out.println("-------------------------------------------");

        return returnJSON;
    }
}
