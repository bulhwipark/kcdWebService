package com.example.kcdwebservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kcdwebservice.vo.CmKcdVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
public class RuleMapService {
  @Autowired
  private CmKcdService cmKcdService;

  public void automap(){

  List<CmKcdVo> lck= selectKcdList();
  for(CmKcdVo ck : lck){
    System.out.println(searchTerm(ck.getKcdEng()).toString());
  }


}


  public List<String> searchTerm(String term) {
    ArrayList<String> arrSctid = new ArrayList<String>();

    String strUrl = "http://1.224.169.78:8095/MAIN/concepts?";

    Map<String, String> hm = new HashMap<String, String>();

    hm.put("activeFilter", "true");
    hm.put("termActive", "true");
    hm.put("statedEcl", "<64572001");
    hm.put("term", term);

    try {

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

  public List<CmKcdVo> selectKcdList(){
    
    CmKcdVo ck = new CmKcdVo();
    ck.setLimit("50");
    ck.setMapVer("0");
    ck.setOffset("0");

    List<CmKcdVo> list = cmKcdService.selectNotMapping(ck);
    

    return list;


  } 


  // http://localhost:8080/RESTfulExample/json/product/get
  public static void main(String[] args) {

    RuleMapService rs = new RuleMapService();
    
    System.out.println("test"+rs.selectKcdList().toString());

    System.out.println(rs.searchTerm("Heart Attack").toString());

  }

}