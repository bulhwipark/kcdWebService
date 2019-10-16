package com.example.kcdwebservice.service;

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

        //disorder, clinical finding, 사용자ECL 순으로 req를 보내고 items에 length가 0 이상이면 종료.
        for(int i = 0; i<searchVo.getEcl().size(); i++){
            paramMap.put("ecl", searchVo.getEcl().get(i));
            result = HttpRestCall.callGet(URL, paramMap);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.getJSONArray("items").length() > 0) {
                break;
            }
        }


        // 위에 루프 종료 후에도 null일때
//        JSONObject jsonObject = new JSONObject(result);
//        if(jsonObject.getJSONArray("items").length() > 0){
//           while(result == null){
//               paramMap.put("term", paramMap.get("term").replace("and ", ""));
//               result = HttpRestCall.callGet(URL, paramMap);
//               if(result != null){
//                   break;
//               }
//               paramMap.put("term", paramMap.get("term").replace("with ", ""));
//               result = HttpRestCall.callGet(URL, paramMap);
//               if(result != null){
//                   break;
//               }
//           }
//        }

        return result;
    }
}

