package com.example.kcdwebservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.example.kcdwebservice.dao.CmMediDao;
import com.example.kcdwebservice.dao.MapKcdSctDao;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.CmMedicineVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleMapService {
  @Autowired
  private CmKcdService cmKcdService;

  @Autowired
  CmMediDao cmMediDao;
  @Autowired
  MapKcdSctDao mapKcdSctDao;

  public void automap1(String ecl) {

    List<CmKcdVo> lck = selectKcdList();
    MapKcdSctVo mvo = null;
    for (CmKcdVo ck : lck) {

      List<String> lsctcd = searchTerm(ck.getKcdEng(), ecl);

      for (String sctcd : lsctcd) {
        mvo = new MapKcdSctVo();
        mvo.setOriCd(ck.getKcdCd());
        mvo.setSctId(sctcd);
        mvo.setMapVer("0");
        mapKcdSctDao.insertAutoMap1(mvo);
      }

    }

  }

  public List<String> searchTerm(String term) {
    String ecl = "<64572001"; // Disease (disorder)
    return searchTerm(term, ecl);
    // String ecl="<404684003"; //clinical finding(finding)
  }

  public List<String> searchTerm(String term, String ecl) {
    ArrayList<String> arrSctid = new ArrayList<String>();

    String strUrl = "http://1.224.169.78:8095/MAIN/concepts?";

    Map<String, String> hm = new HashMap<String, String>();

    hm.put("activeFilter", "true");
    hm.put("termActive", "true");
    hm.put("statedEcl", ecl);
    hm.put("term", term);

    try {
      CmKcdVo ck = new CmKcdVo();

      JSONObject jobj = new JSONObject(com.example.kcdwebservice.util.HttpRestCall.callGet(strUrl, hm));

      System.out.println("out:" + jobj);

      JSONArray ja = jobj.getJSONArray("items");

      for (int x = 0; x < ja.length(); x++) {
        JSONObject jo = ja.getJSONObject(x);
        System.out.println("idx:" + x + " body: " + jo.toString());
        arrSctid.add(jo.getString("conceptId"));

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return arrSctid;
  }


  public List<CmKcdVo> selectKcdList() {

    CmKcdVo ck = new CmKcdVo();
    ck.setLimit("100000");
    ck.setMapVer("0");
    ck.setOffset("0");

    List<CmKcdVo> list = cmKcdService.selectNotMapping(ck);

    return list;

  }
  public void serarchSnowAndInsert(String kmCd, String strQuery, String ruleTp) {

   
    strQuery = strQuery.replace(",", "");
    strQuery = strQuery.replace("/", " ");

    System.out.println("Term query : " +kmCd+" :" + strQuery);
   
    String ecl = "<763158003";
    List<String> lsctcd = searchTerm(strQuery, ecl);
    MapKcdSctVo mvo = new MapKcdSctVo();
    for (String sctcd : lsctcd) {
      mvo = new MapKcdSctVo();
      mvo.setOriCd(kmCd);
      mvo.setSctId(sctcd);
      mvo.setMapVer("0");
      mvo.setMapStatCd(ruleTp);
      cmMediDao.insertAutoMap2(mvo);
    }
    return;
  }
 
  public void serarchAndInsert2(String kmCd, String strQuery) {

   
    strQuery = strQuery.replace(",", "");
    strQuery = strQuery.replace("/", " ");

    System.out.println("Term query : " +kmCd+" :" + strQuery);
    
    try {
      JSONObject jsonObject = searchElastic(strQuery);
      JSONObject hitsObj = new JSONObject(String.valueOf(jsonObject.get("hits")));
      JSONArray jsonArray = hitsObj.getJSONArray("hits");

      for(int i = 0; i<jsonArray.length(); i++){
        JSONObject obj = new JSONObject(String.valueOf(jsonArray.get(i)));
        obj = new JSONObject(String.valueOf(obj.get("_source")));
        //conceptIdList.add(String.valueOf(obj.get("conceptId")));
        MapKcdSctVo mvo = new MapKcdSctVo();
        mvo.setOriCd(kmCd);
        mvo.setSctId(String.valueOf(obj.get("conceptId")));
        mvo.setMapVer("0");
        mvo.setMapStatCd("A1");

        cmMediDao.insertAutoMap2(mvo);
      }

    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return;
  }

  public JSONObject searchElastic(String term) throws JSONException {
    Set<String> conceptIdList = new HashSet<>();
    JSONObject returnJSON = new JSONObject();
    try{
        String jsonStr = "{\"query\": {\"query_string\" : {\"query\" : \""+ term +"\"}},\"_source\": [\"conceptId\",\"term\"]}";
        RestClient restClient = RestClient.builder(
                new HttpHost("1.224.169.78", 9200, "http")
        ).build();

        Map<String, String> params = Collections.EMPTY_MAP;
        HttpEntity httpEntity = new NStringEntity(jsonStr, ContentType.APPLICATION_JSON);
        Response response = restClient.performRequest("GET", "/description/_search", params, httpEntity );

        String result = EntityUtils.toString(response.getEntity());
        returnJSON = new JSONObject(result);
        
    }catch(IOException e){
        e.printStackTrace();
    }
   return returnJSON;
}

 
  public void serarchAndInsert(CmMedicineVo cm, String ruleTp) {

    String strUnit = "";
    double dblAmount = 0.0;
    String strAmount = "";

    if (ruleTp.substring(0, 1).equals("2")) {
      if (cm.getAmount3() == 0)
        return;
      strUnit = cm.getUnit3();
      dblAmount = cm.getAmount3();
    }else  if (ruleTp.substring(0, 1).equals("3") ) {
      if (cm.getAmount2() == 0|| cm.getUnit2().equals(""))
        return;
      strUnit = cm.getUnit2();
      dblAmount = cm.getAmount2();
    } else if (ruleTp.substring(0, 1).equals("4")) {
      if (cm.getAmount3() == 0 || cm.getUnit3().equals(""))
        return;
      strUnit = cm.getUnit3();
      dblAmount = cm.getAmount3();
    } else {
      strUnit = cm.getUnit1();
      dblAmount = cm.getAmount1();
    }

    if (ruleTp.substring(1, 2).equals("1") || ruleTp.substring(1, 2).equals("2")) {
      if (strUnit.equals("g") && cm.getAmount1() < 1) {
        strUnit = "mg";
        dblAmount = dblAmount * 1000;
        strAmount = String.format("%.0f", dblAmount);
      } else if (strUnit.equals("mg") && cm.getAmount1() < 1) {
        strUnit = "microgram";
        dblAmount = dblAmount * 1000;
        strAmount = String.format("%.0f", dblAmount);
      } else if (strUnit.equals("μg") && cm.getAmount1() < 1) {
        strUnit = "nanogram";
        dblAmount = dblAmount * 1000;
        strAmount = String.format("%.0f", dblAmount);
      } if (strUnit.equals("KI.U") && cm.getAmount1() < 1) {
        strUnit = "I.U";
        dblAmount = dblAmount * 1000;
        strAmount = String.format("%.0f", dblAmount);
      } else {

        strAmount = dblAmount + "";
      }
    } else {
      strAmount = dblAmount + "";
    }

    if (ruleTp.substring(1, 2).equals("2")) {
      if (strUnit.equals("mg")) {
        strUnit = "milligram";
      } else if (strUnit.equals("g")) {
        strUnit = "gram";
      } else if (strUnit.equals("μg")) {
        strUnit = "microgram";
      } else if (strUnit.equals("KI.U")) {
        strUnit = "KIU";
      } else if (strUnit.equals("MI.U")) {
        strUnit = "MIU";
      } else if (strUnit.equals("L")) {
        strUnit = "liter";
      } else if (strUnit.equals("cm2")) {
        strUnit = "square centimeter";
      } else if (strUnit.equals("mm")) {
        strUnit = "milimeter";
      } else if (strUnit.equals("mL/g")) {
        strUnit = "milliliter/gram";
      } else if (strUnit.equals("mL/mL")) {
        strUnit = "milliliter/milliliter";
      } else if (strUnit.equals("mL")) {
        strUnit = "milimeter";
      } else if (strUnit.equals("mg/mL")) {
        strUnit = "milligram/1 milliliter";
      } else if (strUnit.equals("I.U")) {
        strUnit = "unit";
      }else if (strUnit.equals("mg/정")) {
        strUnit = "milligram/1 each";
      }
    }



    if (dblAmount * 10 % 10 == 0) {
      strAmount = String.format("%.0f", dblAmount);
    }

    String strQuery = "";

    String strMedDoseFrm = cm.getMedDoseFrm();
    
    if ( cm.getMedDoseFrm3().length()>2){
      strMedDoseFrm=cm.getMedDoseFrm3();
    } else if(cm.getMedDoseFrm2().length()>2){
      strMedDoseFrm=cm.getMedDoseFrm2();
    } else {
      strMedDoseFrm=cm.getMedDoseFrm();
    }
    // if (strMedDoseFrm.indexOf("tablet") >= 0) {
    //   strMedDoseFrm = "tablet";
    // } else if (strMedDoseFrm.indexOf("syrup") >= 0) {
    //   strMedDoseFrm = "oral suspension";
    // } else if (strMedDoseFrm.indexOf("capsule") >= 0) {
    //   strMedDoseFrm = "capsule";
    // } else if (strMedDoseFrm.indexOf("gastro-resistant capsule") >= 0) {
    //   strMedDoseFrm = "gastro-resistant capsule";
    // } else if (strMedDoseFrm.indexOf("prolonged-release capsule") >= 0) {
    //   strMedDoseFrm = "prolonged-release capsule";
    // }
    String ecl = "<763158003";
    if (ruleTp.substring(0, 1).equals("1") ) {
      if (cm.getEftSubstNm().length()<2 || strMedDoseFrm.length()<2)
        return ;
      strQuery = cm.getSubstanceNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    } else if ( ruleTp.substring(0, 1).equals("2")) {
      if (cm.getEftSubstNm().length()<2 || strMedDoseFrm.length()<2)
        return ;
      strQuery = cm.getSubstanceNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    } else if (ruleTp.substring(0, 1).equals("3") ) {
      if (cm.getEftSubstNm().length()<2 || strMedDoseFrm.length()<2)
        return;
      strQuery = cm.getEftSubstNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    } else if ( ruleTp.substring(0, 1).equals("4")) {
      if (cm.getEftSubstNm().length()<2 || strMedDoseFrm.length()<2)
        return;
      strQuery = cm.getEftSubstNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    } else if (ruleTp.substring(0, 1).equals("5") ) {
      if (cm.getSubstanceNm().length()<2 || cm.getRtOfAdmin().length()<2)
        return;
      strQuery = "only " + cm.getSubstanceNm()+" " + cm.getRtOfAdmin();
    }else if (ruleTp.substring(0, 1).equals("B") ) {
      
      if (cm.getSubstanceNm2().length()<2 || cm.getRtOfAdmin().length()<2)
        return;
      strQuery = "only " + cm.getSubstanceNm2()+" " + cm.getRtOfAdmin();;
    } else if (ruleTp.substring(0, 1).equals("6") ) {
      if (cm.getEftSubstNm().length()<2 || cm.getRtOfAdmin().length()<2)
          return;
      strQuery =  cm.getEftSubstNm() + " " + cm.getRtOfAdmin();
    } else if (ruleTp.substring(0, 1).equals("C")) {
      if (cm.getEftSubstNm2().length()<2 || cm.getRtOfAdmin().length()<2)
        return;
      strQuery = "only " + cm.getEftSubstNm2()+" " + cm.getRtOfAdmin();
    } else if (ruleTp.substring(0, 1).equals("7")) {
      if (cm.getSubstanceNm().length()<2)
        return;
      strQuery = "only " + cm.getSubstanceNm()+" " ;
    }  else if (ruleTp.substring(0, 1).equals("D") ) {
      if (cm.getSubstanceNm2().length()<2)
        return;
      strQuery =  cm.getSubstanceNm2();
    }else if ( ruleTp.substring(0, 1).equals("8")) {
      if (cm.getEftSubstNm().length()<2)
        return;
      strQuery =  cm.getEftSubstNm();
    } else if ( ruleTp.substring(0, 1).equals("E")) {
      if (cm.getEftSubstNm2().length()<2)
        return;
      strQuery =  cm.getEftSubstNm2();
    } else if (ruleTp.substring(0, 1).equals("9")) {
      if (cm.getSubstanceNm().length()<2)
        return;
      strQuery = cm.getSubstanceNm();
      ecl = "<105590001";
    }else if (ruleTp.substring(0, 1).equals("F")) {
      if (cm.getSubstanceNm2().length()<2)
        return;
      strQuery = cm.getSubstanceNm2();
      ecl = "<105590001";
    } else if (ruleTp.substring(0, 1).equals("A") ) {
      if (cm.getEftSubstNm().length()<2)
        return;
      strQuery =  cm.getEftSubstNm();
      ecl = "<105590001";
    } else if (ruleTp.substring(0, 1).equals("G") ) {
      if (cm.getEftSubstNm2().length()<2)
        return;
      strQuery =  cm.getEftSubstNm2();
      ecl = "<105590001";
    }   else {
      strQuery = cm.getSubstanceNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    }

    
    // if (!ruleTp.substring(1, 2).equals("3")) {
    //   if ( cm.getRtOfAdmin().equals(""))
    //     return;
    //   strQuery += " " + cm.getRtOfAdmin();
    // }

    strQuery = strQuery.replace(",", "");
    strQuery = strQuery.replace("/", " ");

    System.out.println("Term query : " + strQuery);
    
    List<String> lsctcd = searchTerm(strQuery, ecl);
    MapKcdSctVo mvo = new MapKcdSctVo();
    for (String sctcd : lsctcd) {
      mvo = new MapKcdSctVo();
      mvo.setOriCd(cm.getKdCd());
      mvo.setSctId(sctcd);
      mvo.setMapVer("0");
      mvo.setMapStatCd(ruleTp);
      cmMediDao.insertAutoMap2(mvo);
    }

    return;

  }

  public void selectMediListC(String ruleTp) {

    List<CmMedicineVo> list = cmMediDao.selectCAll();
    
    String chkFlag="";
    List<CmMedicineVo> list2 = new ArrayList<CmMedicineVo>();
    String qryStr="";

    for (CmMedicineVo cm : list) {
      System.out.println("cm:"+ cm.getKdCd());
      
      if (!chkFlag.equals(cm.getKdCd()) && !chkFlag.equals("")){

        System.out.println("test:"+ qryStr);
        //serarchAndInsert2(chkFlag,qryStr);
        serarchSnowAndInsert(chkFlag,qryStr,ruleTp);
        
        qryStr="";
        
      }

      //list2.add(cm);
      if ( ruleTp.equals("A5")&& cm.getSubstanceNm().length()>2){
        
        qryStr+=cm.getSubstanceNm()+ " " + cm.getRtOfAdmin() + " ";
      }else if ( ruleTp.equals("A6")&& cm.getEftSubstNm().length()>2){
        qryStr+=cm.getEftSubstNm()+ " " + cm.getRtOfAdmin() + " " ;

      }else if ( ruleTp.equals("A7") && cm.getSubstanceNm().length()>2){
        qryStr+=cm.getSubstanceNm()+ " " ;

      }else if ( ruleTp.equals("A8") && cm.getEftSubstNm().length()>2){
        qryStr+=cm.getEftSubstNm()+ " " ;

      }

      chkFlag=cm.getKdCd();
    }

    System.out.println("test:"+ qryStr);

    return;

  }

  public void selectMediList(String ruleTp) {

    List<CmMedicineVo> list = cmMediDao.selectAll();

    for (CmMedicineVo cm : list) {
      serarchAndInsert(cm, ruleTp);
    }
    return;

  }



}