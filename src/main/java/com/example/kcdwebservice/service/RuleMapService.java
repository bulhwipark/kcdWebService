package com.example.kcdwebservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kcdwebservice.dao.CmMediDao;
import com.example.kcdwebservice.dao.MapKcdSctDao;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.CmMedicineVo;
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

  public void serarchAndInsert(CmMedicineVo cm, String ruleTp) {

    String strUnit = "";
    double dblAmount = 0.0;
    String strAmount = "";

    if (ruleTp.substring(0, 1).equals("2")) {
      if (cm.getAmount3() == 0)
        return;
      strUnit = cm.getUnit3();
      dblAmount = cm.getAmount3();
    }else  if (ruleTp.substring(0, 1).equals("3")) {
      if (cm.getAmount2() == 0)
        return;
      strUnit = cm.getUnit2();
      dblAmount = cm.getAmount2();
    } else if (ruleTp.substring(0, 1).equals("4")) {
      if (cm.getAmount3() == 0)
        return;
      strUnit = cm.getUnit3();
      dblAmount = cm.getAmount3();
    } else {
      strUnit = cm.getUnit1();
      dblAmount = cm.getAmount1();
    }

    if (ruleTp.substring(1, 2).equals("1")) {
      if (strUnit.equals("g") && cm.getAmount1() < 1) {
        strUnit = "mg";
        dblAmount = dblAmount * 1000;
        strAmount = String.format("%.0f", dblAmount);
      } else if (strUnit.equals("mg") && cm.getAmount1() < 1) {
        strUnit = "mcg";
        dblAmount = dblAmount * 1000;
        strAmount = String.format("%.0f", dblAmount);
      } else if (strUnit.equals("mcg") && cm.getAmount1() < 1) {
        strUnit = "nanogram";
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
      }
    }

    if (dblAmount * 10 % 10 == 0) {
      strAmount = String.format("%.0f", dblAmount);
    }

    String strQuery = "";

    String strMedDoseFrm = cm.getMedDoseFrm();
    if (strMedDoseFrm.indexOf("tablet") >= 0) {
      strMedDoseFrm = "tablet";
    } else if (strMedDoseFrm.indexOf("syrup") >= 0) {
      strMedDoseFrm = "oral suspension";
    } else if (strMedDoseFrm.indexOf("capsule") >= 0) {
      strMedDoseFrm = "capsule";
    } else if (strMedDoseFrm.indexOf("gastro-resistant capsule") >= 0) {
      strMedDoseFrm = "gastro-resistant capsule";
    } else if (strMedDoseFrm.indexOf("prolonged-release capsule") >= 0) {
      strMedDoseFrm = "prolonged-release capsule";
    }

    if (ruleTp.substring(0, 1).equals("1") || ruleTp.substring(0, 1).equals("2")) {
      strQuery = cm.getSubstanceNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    } else if (ruleTp.substring(0, 1).equals("3") || ruleTp.substring(0, 1).equals("4")) {
      if (cm.getEftSubstNm().equals(""))
        return;
      strQuery = cm.getEftSubstNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    } else if (ruleTp.substring(0, 1).equals("5")) {
      if (cm.getSubstanceNm().equals(""))
        return;
      strQuery = "only " + cm.getSubstanceNm();
    } else if (ruleTp.substring(0, 1).equals("6")) {
      if (cm.getEftSubstNm().equals(""))
        return;
      strQuery = "only " + cm.getEftSubstNm();
    } else {
      strQuery = cm.getSubstanceNm() + " " + strAmount + " " + strUnit + " " + strMedDoseFrm;
    }

    if (!ruleTp.substring(1, 2).equals("3")) {
      if ((ruleTp.substring(0, 1).equals("6") || ruleTp.substring(0, 1).equals("5")) && cm.getRtOfAdmin().equals(""))
        return;
      strQuery += " " + cm.getRtOfAdmin();
    }

    strQuery = strQuery.replace(",", "");
    strQuery = strQuery.replace("/", " ");

    System.out.println("Term query : " + strQuery);
    String ecl = "<763158003";
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



  public void selectMediList(String ruleTp) {

    List<CmMedicineVo> list = cmMediDao.selectAll();

    for (CmMedicineVo cm : list) {
      serarchAndInsert(cm, ruleTp);
    }
    return;

  }

  // http://localhost:8080/RESTfulExample/json/product/get
  public static void main(String[] args) {

    RuleMapService rs = new RuleMapService();

    System.out.println(rs.searchTerm("Heart Attack").toString());

  }

}