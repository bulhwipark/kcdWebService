package com.example.kcdwebservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kcdwebservice.dao.MapKcdSctDao;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
@Service
public class RuleMapService {
  @Autowired
  private CmKcdService cmKcdService;

  @Autowired
  MapKcdSctDao mapKcdSctDao;

  public void automap(){

  List<CmKcdVo> lck= selectKcdList();
  MapKcdSctVo mvo =null;
  for(CmKcdVo ck : lck){

    List <String> lsctcd = searchTerm(ck.getKcdEng());

    for(String sctcd : lsctcd){
      mvo =new MapKcdSctVo();
      mvo.setOriCd(ck.getKcdCd());
      mvo.setSctId(sctcd);
      mvo.setMapVer("0");
      mapKcdSctDao.insertAutoMap1(mvo);
    }
    
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

  public List<CmKcdVo> selectKcdList(){
    
    CmKcdVo ck = new CmKcdVo();
    ck.setLimit("100000");
    ck.setMapVer("0");
    ck.setOffset("0");

    List<CmKcdVo> list = cmKcdService.selectNotMapping(ck);
    

    return list;


  } 


  // http://localhost:8080/RESTfulExample/json/product/get
  public static void main(String[] args) {

    RuleMapService rs = new RuleMapService();
    

    System.out.println(rs.searchTerm("Heart Attack").toString());

  }

}