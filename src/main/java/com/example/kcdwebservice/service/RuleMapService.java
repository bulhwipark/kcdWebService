package com.example.kcdwebservice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kcdwebservice.util.ParameterStringBuilder;

import com.example.kcdwebservice.vo.CmKcdVo;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class RuleMapService {

  List<String> searchTerm(String term) {
    ArrayList<String> arrSctid = new ArrayList<String>();

    String strUrl = "http://1.224.169.78:8095/MAIN/concepts?";

    Map<String, String> hm = new HashMap<String, String>();

    hm.put("activeFilter", "true");
    hm.put("termActive", "true");
    hm.put("statedEcl", "<64572001");
    hm.put("term", term);

    try {
      CmKcdVo ck =new CmKcdVo();

      JSONObject jobj = new JSONObject(com.example.kcdwebservice.util.HttpRestCall.callGet(strUrl, hm));

      System.out.println("out:" + jobj);

      JSONArray ja = jobj.getJSONArray("items");

      for (int x = 0; x < ja.length(); x++) {
        JSONObject jo = ja.getJSONObject(x);
        System.out.println("idx:" + x + " body: " + jo.toString());
        arrSctid.add(jo.getString("conceptId"));

      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return arrSctid;
  }

  // http://localhost:8080/RESTfulExample/json/product/get
  public static void main(String[] args) {
    RuleMapService rs = new RuleMapService();
    System.out.println(rs.searchTerm("Heart Attack").toString());

  }

}