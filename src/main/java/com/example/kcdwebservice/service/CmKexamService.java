package com.example.kcdwebservice.service;

import com.example.kcdwebservice.dao.CmKexamDao;
import com.example.kcdwebservice.vo.CmKcdVo;
import com.example.kcdwebservice.vo.CmKexamVo;
import com.example.kcdwebservice.vo.MapKcdSctVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CmKexamService {

	@Autowired
	private CmKexamDao cmKexamDao;
	@Autowired
	private RuleMapService ruleMapService;

	public List<CmKexamVo> selectAll() {
		return cmKexamDao.selectAll();
	}

	public List<CmKexamVo> kexam_selectAll(CmKexamVo cmKexamVo) {
		return cmKexamDao.kexam_selectAll(cmKexamVo);
	}

	public CmKexamVo selectKexCdInfo(String kexCd) {
		return cmKexamDao.selectKexCdInfo(kexCd);
	}

	public List<CmKexamVo> kexam_selectMapping(CmKexamVo cmKexamVo) {
		return cmKexamDao.kexam_selectMapping(cmKexamVo);
	}

	public List<CmKexamVo> kexam_selectNoMapping(CmKexamVo cmKexamVo) {
		return cmKexamDao.kexam_selectNoMapping(cmKexamVo);
	}
	
	public List<CmKexamVo> getMediCheckInfoList(String kexCd){
		return cmKexamDao.getMediCheckInfoList(kexCd);
	}
	
	public List<CmKexamVo> mediCheckDetailList(String kexCd){
		return cmKexamDao.mediCheckDetailList(kexCd);
	}

	public String kexam_totalCnt(String mappingStatus, CmKexamVo cmKexamVo) {
		//검사 총 건수 리턴
		String totalCnt = null;
		if (mappingStatus.equals("All")) {
			totalCnt = cmKexamDao.kexam_totalCnt(cmKexamVo);
		} else if (mappingStatus.equals("Mapping")) {
			totalCnt = cmKexamDao.kexam_mappingTotalCnt(cmKexamVo);
		} else if (mappingStatus.equals("NoMapping")) {
			totalCnt = cmKexamDao.kexam_noMappingTotalCnt(cmKexamVo);
		}
		return totalCnt;
	}

	public String kexam_mappingStatusTotalCnt(String mappingStatus, CmKexamVo cmKexamVo) {
		// 매핑된 검사 건수만 리턴
		String totalCnt = null;
		if (mappingStatus.equals("All")) {
			totalCnt = cmKexamDao.kexam_kexCdTotalCnt(cmKexamVo);
		} else if (mappingStatus.equals("Mapping")) {
			totalCnt = cmKexamDao.kexam_kexCdMappingTotalCnt(cmKexamVo);
		} else if (mappingStatus.equals("NoMapping")) {
			totalCnt = cmKexamDao.kexam_kexCdNoMappingTotalCnt(cmKexamVo);
		}
		return totalCnt;
	}
	
	public String convertRomanToChar(String term, String roman) {
		if(roman.equals("\u2160")) term = term.replace(roman, "I");
		else if(roman.equals("\u2161")) term = term.replace(roman, "II");
		else if(roman.equals("\u2162")) term = term.replace(roman, "III");
		else if(roman.equals("\u2163")) term = term.replace(roman, "IV");
		else if(roman.equals("\u2164")) term = term.replace(roman, "V");
		else if(roman.equals("\u2165")) term = term.replace(roman, "VI");
		else if(roman.equals("\u2166")) term = term.replace(roman, "VII");
		else if(roman.equals("\u2167")) term = term.replace(roman, "VIII");
		else if(roman.equals("\u2168")) term = term.replace(roman, "IX");
		else if(roman.equals("\u2169")) term = term.replace(roman, "X");
		else if(roman.equals("\u216A")) term = term.replace(roman, "XI");
		else if(roman.equals("\u216B")) term = term.replace(roman, "XII");
		else if(roman.equals("\u216C")) term = term.replace(roman, "L");
		else if(roman.equals("\u216D")) term = term.replace(roman, "C");
		else if(roman.equals("\u216E")) term = term.replace(roman, "D");
		else if(roman.equals("\u216F")) term = term.replace(roman, "M");
		else if(roman.equals("\u2170")) term = term.replace(roman, "I");
		else if(roman.equals("\u2171")) term = term.replace(roman, "II");
		else if(roman.equals("\u2172")) term = term.replace(roman, "III");
		else if(roman.equals("\u2173")) term = term.replace(roman, "IV");
		else if(roman.equals("\u2174")) term = term.replace(roman, "V");
		else if(roman.equals("\u2175")) term = term.replace(roman, "VI");
		else if(roman.equals("\u2176")) term = term.replace(roman, "VII");
		else if(roman.equals("\u2177")) term = term.replace(roman, "VIII");
		else if(roman.equals("\u2178")) term = term.replace(roman, "IX");
		else if(roman.equals("\u2179")) term = term.replace(roman, "X");
		else if(roman.equals("\u217A")) term = term.replace(roman, "XI");
		else if(roman.equals("\u217B")) term = term.replace(roman, "XII");
		else if(roman.equals("\u217C")) term = term.replace(roman, "L");
		else if(roman.equals("\u217D")) term = term.replace(roman, "C");
		else if(roman.equals("\u217E")) term = term.replace(roman, "D");
		else if(roman.equals("\u217F")) term = term.replace(roman, "M");
		
		return term;
	}
	
	public JSONObject searchTermObject(String term, String ecl) {
		String strUrl = "http://1.224.169.78:8095/MAIN/concepts?";
		
		Pattern pat = Pattern.compile("[\\u2160-\\u217B]");
		Matcher match = pat.matcher(term);
		while(match.find()) {
			String roman = match.group();
			term = convertRomanToChar(term, roman);
			match = pat.matcher(term);
		}

		Map<String, String> hm = new HashMap<String, String>();
		hm.put("activeFilter", "true");
		hm.put("termActive", "true");
		hm.put("statedEcl", ecl);
		hm.put("term", term.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " "));

		try {
			JSONObject jobj = new JSONObject(com.example.kcdwebservice.util.HttpRestCall.callGet(strUrl, hm));
			System.out.println("out:" + jobj);
			JSONArray ja = jobj.getJSONArray("items");

			for (int x = 0; x < ja.length(); x++) {
				// db에서 conceptid에 해당하는 모든 동의어를 lowercase로 변경하여 term 과 일치하는지 확인
				JSONObject jo = ja.getJSONObject(x);
				String sctId = jo.getString("id");

				// 유사어도 모두 확인해야함. gun
				List<String> lstSCTSynon = cmKexamDao.selectSynonym(sctId);

				for (String strTerm : lstSCTSynon) {
					if (term.replace(" ", "").toLowerCase().equals(strTerm.replace(" ", "").toLowerCase())) {
						return jo;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<JSONObject> searchTermList(String term, String ecl){
		ArrayList<JSONObject> arrSctid = new ArrayList<JSONObject>();

	    String strUrl = "http://1.224.169.78:8095/MAIN/concepts?";
	    
	    Pattern pat = Pattern.compile("[\\u2160-\\u217B]");
		Matcher match = pat.matcher(term);
		while(match.find()) {
			String roman = match.group();
			term = convertRomanToChar(term, roman);
			match = pat.matcher(term);
		}

	    Map<String, String> hm = new HashMap<String, String>();

	    hm.put("activeFilter", "true");
	    hm.put("termActive", "true");
	    hm.put("statedEcl", ecl);
	    hm.put("term", term.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " "));

	    try {
	      JSONObject jobj = new JSONObject(com.example.kcdwebservice.util.HttpRestCall.callGet(strUrl, hm));
	      JSONArray ja = jobj.getJSONArray("items");

	      for (int x = 0; x < ja.length(); x++) {
	        JSONObject jo = ja.getJSONObject(x);
	        String sctId = jo.getString("id");
	        // 유사어도 모두 확인해야함. gun
			List<String> lstSCTSynon = cmKexamDao.selectSynonym(sctId);

			for (String strTerm : lstSCTSynon) {
				if (term.replace(" ", "").toLowerCase().equals(strTerm.replace(" ", "").toLowerCase())) {
					arrSctid.add(jo);
				}
			}
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return arrSctid;
	}

	public String searchTerm(String term, String ecl) {
		String strSctid = "";
		String strUrl = "http://1.224.169.78:8095/MAIN/concepts?";
		
		Pattern pat = Pattern.compile("[\\u2160-\\u217B]");
		Matcher match = pat.matcher(term);
		while(match.find()) {
			String roman = match.group();
			term = convertRomanToChar(term, roman);
			match = pat.matcher(term);
		}

		Map<String, String> hm = new HashMap<String, String>();
		hm.put("activeFilter", "true");
		hm.put("termActive", "true");
		hm.put("statedEcl", ecl);
		hm.put("term", term.replaceAll("[\\{\\}\\[\\]\\/?.,;:|\\)*~`!^\\-_+<>@\\#$%&\\\\\\=\\(\\'\\\"]", " "));

		try {
			JSONObject jobj = new JSONObject(com.example.kcdwebservice.util.HttpRestCall.callGet(strUrl, hm));

			System.out.println("out:" + jobj);

			JSONArray ja = jobj.getJSONArray("items");

			for (int x = 0; x < ja.length(); x++) {
				// 나중엔 id 리스트로 가져다가 jo.getString("conceptId")
				// db에서 conceptid에 해당하는 모든 동의어를 lowercase로 변경하여 term 과 일치하는지 확인
				JSONObject jo = ja.getJSONObject(x);
				// String pt_term = jo.getJSONObject("pt").getString("term").toLowerCase();
				String sctId = jo.getString("id");

				// 유사어도 모두 확인해야함. gun
				List<String> lstSCTSynon = cmKexamDao.selectSynonym(sctId);

				for (String strTerm : lstSCTSynon) {

					if (term.replace(" ", "").toLowerCase().equals(strTerm.replace(" ", "").toLowerCase())) {
						return sctId;
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strSctid;
	}

	public Map<Integer, String> getPatternMap() {
		Map<Integer, String> hm = new LinkedHashMap<Integer, String>();
		hm.put(1, ".*");
		hm.put(21, "\\(.*?\\)");
		hm.put(22, "-.+");
		hm.put(31, "\\'s");
		hm.put(32, "\\,");
		hm.put(33, "\\-");
		hm.put(34, "\\/");
		hm.put(35, "\\(.*?\\)");
		hm.put(36, "\\[.*?\\]");
		hm.put(41, "and ");
		hm.put(42, "with ");
		hm.put(43, "( others|other )");
		hm.put(44, "of  ");
		hm.put(45, "by ");
		hm.put(46, "(special | special)");
		hm.put(5, "(es|s)\\b");
		hm.put(61, "{} level");
		hm.put(62, "{} measurement");
		hm.put(63, "measurement of {}");
		hm.put(64, "{} test");
		hm.put(65, "{} analysis");
		hm.put(66, "{} culture");
		hm.put(67, "{} count");
		hm.put(68, "{} assay");
		hm.put(69, "{} method");
		hm.put(610, "{} study");
		hm.put(71, "{} level");
		hm.put(72, "{} measurement");
		hm.put(73, "measurement of {}");
		hm.put(74, "{} test");
		hm.put(75, "{} analysis");
		hm.put(76, "{} culture");
		hm.put(77, "{} count");
		hm.put(78, "{} assay");
		hm.put(79, "{} method");
		hm.put(710, "{} study");
		//
		hm.put(80, "");

		return hm;
	}

	public void domapping(CmKexamVo kexam) {
		String targetSentence = kexam.getKexEng();
		Map<Integer, String> hm = getPatternMap();
		Collection<Integer> keySet = hm.keySet();
		Iterator<Integer> itr = keySet.iterator();
		boolean isMatched = false;
		while (itr.hasNext()) {
			int key = itr.next();
			if (key == 1 || key == 21 || key == 22) {
				Pattern pat = Pattern.compile(hm.get(key));
				Matcher match = pat.matcher(targetSentence);
				while (match.find()) {
					// 정규식 적용했으니까 key 값에 따라 결과 정제하고 term 찾기
					String normalizeWord = match.group();
					if (normalizeWord.isEmpty())
						continue;
					if (key == 21)
						normalizeWord = normalizeWord.replace("(", "").replace(")", "");
					else if (key == 22) {
//						normalizeWord = normalizeWord.substring(1);
						normalizeWord = normalizeWord.split("-")[normalizeWord.split("-").length-1];
					}
						

					String searchResult = searchTerm(normalizeWord, "<71388002");
					if (!searchResult.isEmpty()) {
						System.out.println(normalizeWord + "-" + kexam.getKexCd() + " : " + searchResult);
						// insert db
						MapKcdSctVo kcdSct = new MapKcdSctVo();
						kcdSct.setOriCd(kexam.getKexCd());
						kcdSct.setSctId(searchResult);
						kcdSct.setMapVer("0");
						kcdSct.setMapStatCd(Integer.toString(key));
						if (cmKexamDao.insertAutoMap3(kcdSct) > 0) {
							// flag true로 설정
							isMatched = true;
							break;
						}
					}
				}
			} else if (key == 31 || key == 32 || key == 33 || key == 34 || key == 35 || key == 36 || key == 41
					|| key == 42 || key == 43 || key == 44 || key == 45 || key == 46 || key == 5) {
				Pattern pat = Pattern.compile(hm.get(key));
				Matcher match = pat.matcher(targetSentence);
				if (match.find()) {
					String normalizeWord = targetSentence.replaceAll(hm.get(key), "");

					String searchResult = searchTerm(normalizeWord, "<71388002");
					if (!searchResult.isEmpty()) {
						System.out.println(normalizeWord + "-" + kexam.getKexCd() + " : " + searchResult);
						// insert db
						MapKcdSctVo kcdSct = new MapKcdSctVo();
						kcdSct.setOriCd(kexam.getKexCd());
						kcdSct.setSctId(searchResult);
						kcdSct.setMapVer("0");
						kcdSct.setMapStatCd(Integer.toString(key));
						if (cmKexamDao.insertAutoMap3(kcdSct) > 0) {
							// flag true로 설정
							isMatched = true;
							break;
						}
					}
				}

			} else if (key == 61 || key == 62 || key == 63 || key == 64 || key == 65 || key == 66 || key == 67
					|| key == 68 || key == 69 || key == 610) {
				String normalizeWord = hm.get(key).replace("{}", targetSentence);

				String searchResult = searchTerm(normalizeWord, "<71388002");
				if (!searchResult.isEmpty()) {
					System.out.println(normalizeWord + "-" + kexam.getKexCd() + " : " + searchResult);
					// insert db
					MapKcdSctVo kcdSct = new MapKcdSctVo();
					kcdSct.setOriCd(kexam.getKexCd());
					kcdSct.setSctId(searchResult);
					kcdSct.setMapVer("0");
					kcdSct.setMapStatCd(Integer.toString(key));
					if (cmKexamDao.insertAutoMap3(kcdSct) > 0) {
						// flag true로 설정
						isMatched = true;
						break;
					}
				}

			} else if (key == 71 || key == 72 || key == 73 || key == 74 || key == 75 || key == 76 || key == 77
					|| key == 78 || key == 79 || key == 710) {
				Pattern pat = Pattern.compile("-.+");
				Matcher match = pat.matcher(targetSentence);
				
				if (!match.find()) {
					pat = Pattern.compile("_.+"); // 추가
					match = pat.matcher(targetSentence);
					if(match.find()) {
						String normalizeWord = hm.get(key).replace("{}", match.group().substring(1));
						String searchResult = searchTerm(normalizeWord, "<71388002");
						if (!searchResult.isEmpty()) {
							System.out.println(normalizeWord + "-" + kexam.getKexCd() + " : " + searchResult);
							// insert db
							MapKcdSctVo kcdSct = new MapKcdSctVo();
							kcdSct.setOriCd(kexam.getKexCd());
							kcdSct.setSctId(searchResult);
							kcdSct.setMapVer("0");
							kcdSct.setMapStatCd(Integer.toString(key));
							if (cmKexamDao.insertAutoMap3(kcdSct) > 0) {
								// flag true로 설정
								isMatched = true;
								break;
							}
						}
					}
					
				}else {
					String normalizeWord = hm.get(key).replace("{}", match.group().split("-")[match.group().split("-").length-1]);
					String searchResult = searchTerm(normalizeWord, "<71388002");
					if (!searchResult.isEmpty()) {
						System.out.println(normalizeWord + "-" + kexam.getKexCd() + " : " + searchResult);
						// insert db
						MapKcdSctVo kcdSct = new MapKcdSctVo();
						kcdSct.setOriCd(kexam.getKexCd());
						kcdSct.setSctId(searchResult);
						kcdSct.setMapVer("0");
						kcdSct.setMapStatCd(Integer.toString(key));
						if (cmKexamDao.insertAutoMap3(kcdSct) > 0) {
							// flag true로 설정
							isMatched = true;
							break;
						}
					}
				}
				
			} else if (key == 80) {   //80 룰 추가
				targetSentence = kexam.getPreTerm().trim();
				if(!targetSentence.isEmpty()){
					List<String> searchResult = ruleMapService.searchTerm(targetSentence, "<71388002");
					for (String sctId : searchResult) {
						System.out.println(targetSentence + "-" + kexam.getKexCd() + " : " + sctId);
						// insert db
						MapKcdSctVo kcdSct = new MapKcdSctVo();
						kcdSct.setOriCd(kexam.getKexCd());
						kcdSct.setSctId(sctId);
						kcdSct.setMapVer("0");
						kcdSct.setMapStatCd(Integer.toString(key));
						//후보 모두 저장
						int insertResult = cmKexamDao.insertAutoMap3(kcdSct);
						if(insertResult > 0) isMatched = true;
					}
				}
			} else if (key == 81) {   //81 룰 추가
				targetSentence = kexam.getPreTerm2().trim();
				if(!targetSentence.isEmpty()){
					List<String> searchResult = ruleMapService.searchTerm(targetSentence, "<71388002");
					for (String sctId : searchResult) {
						System.out.println(targetSentence + "-" + kexam.getKexCd() + " : " + sctId);
						// insert db
						MapKcdSctVo kcdSct = new MapKcdSctVo();
						kcdSct.setOriCd(kexam.getKexCd());
						kcdSct.setSctId(sctId);
						kcdSct.setMapVer("0");
						kcdSct.setMapStatCd(Integer.toString(key));
						//후보 모두 저장
						int insertResult = cmKexamDao.insertAutoMap3(kcdSct);
						if(insertResult > 0) isMatched = true;
					}
				}
			}
			if (isMatched)
				break;
		}
	}
	
	public List<JSONObject> autoRuleMapping(List<CmKexamVo> mediCheckInfoList) {
		List<JSONObject> resultList = new ArrayList<JSONObject>();
		String targetSentence = mediCheckInfoList.get(0).getKexEng();
		Map<Integer, String> hm = getPatternMap();
		Collection<Integer> keySet = hm.keySet();
		Iterator<Integer> itr = keySet.iterator();
		boolean isMatched = false;
		while (itr.hasNext()) {
			int key = itr.next();
			if (key == 1 || key == 21 || key == 22) {
				Pattern pat = Pattern.compile(hm.get(key));
				Matcher match = pat.matcher(targetSentence);
				while (match.find()) {
					// 정규식 적용했으니까 key 값에 따라 결과 정제하고 term 찾기
					String normalizeWord = match.group();
					if (normalizeWord.isEmpty())
						continue;
					if (key == 21)
						normalizeWord = normalizeWord.replace("(", "").replace(")", "");
					else if (key == 22) {
//						normalizeWord = normalizeWord.substring(1);
						normalizeWord = normalizeWord.split("-")[normalizeWord.split("-").length-1];
					}
						
					JSONObject searchResult = searchTermObject(normalizeWord, "<71388002");
					if (searchResult!=null) {
						// flag true로 설정
						isMatched = true;
						resultList.add(searchResult);
						break;
					}
				}
			} else if (key == 31 || key == 32 || key == 33 || key == 34 || key == 35 || key == 36 || key == 41
					|| key == 42 || key == 43 || key == 44 || key == 45 || key == 46 || key == 5) {
				Pattern pat = Pattern.compile(hm.get(key));
				Matcher match = pat.matcher(targetSentence);
				if (match.find()) {
					String normalizeWord = targetSentence.replaceAll(hm.get(key), "");

					JSONObject searchResult = searchTermObject(normalizeWord, "<71388002");
					if (searchResult != null) {
						// flag true로 설정
						isMatched = true;
						resultList.add(searchResult);
						break;
					}
				}

			} else if (key == 61 || key == 62 || key == 63 || key == 64 || key == 65 || key == 66 || key == 67
					|| key == 68 || key == 69 || key == 610) {
				String normalizeWord = hm.get(key).replace("{}", targetSentence);

				JSONObject searchResult = searchTermObject(normalizeWord, "<71388002");
				if (searchResult != null) {
					// flag true로 설정
					isMatched = true;
					resultList.add(searchResult);
					break;
				}

			} else if (key == 71 || key == 72 || key == 73 || key == 74 || key == 75 || key == 76 || key == 77
					|| key == 78 || key == 79 || key == 710) {
				Pattern pat = Pattern.compile("-.+");
				Matcher match = pat.matcher(targetSentence);
				
				if (!match.find()) {
					pat = Pattern.compile("_.+"); // 추가
					match = pat.matcher(targetSentence);
					if(match.find()) {
						String normalizeWord = hm.get(key).replace("{}", match.group().substring(1));
						JSONObject searchResult = searchTermObject(normalizeWord, "<71388002");
						if (searchResult != null) {
							// flag true로 설정
							isMatched = true;
							resultList.add(searchResult);
							break;
						}
					}
					
				}else {
					String normalizeWord = hm.get(key).replace("{}", match.group().split("-")[match.group().split("-").length-1]);
					JSONObject searchResult = searchTermObject(normalizeWord, "<71388002");
					if (searchResult != null) {
						// flag true로 설정
						isMatched = true;
						resultList.add(searchResult);
						break;
					}
				}
				
			} else if (key == 80) {   //80 룰 추가
				targetSentence = mediCheckInfoList.get(0).getPreTerm().trim();
				if(!targetSentence.isEmpty()){
					List<JSONObject> searchResult = searchTermList(targetSentence, "<71388002");
					for (JSONObject obj : searchResult) {
						//후보 모두 저장
						resultList.add(obj);
						isMatched = true;
					}
				}
			} else if (key == 81) {   //80 룰 추가
				targetSentence = mediCheckInfoList.get(0).getPreTerm2().trim();
				if(!targetSentence.isEmpty()){
					List<JSONObject> searchResult = searchTermList(targetSentence, "<71388002");
					for (JSONObject obj : searchResult) {
						//후보 모두 저장
						resultList.add(obj);
						isMatched = true;
					}
				}
			}
			if (isMatched)
				break;
		}
		return resultList;
	}
	
	public List<JSONObject> mediCheck_autoRuleRequest(List<CmKexamVo> mediCheckInfoList){
		List<JSONObject> list = new ArrayList<>();
		
		if(mediCheckInfoList.size() > 0){
			list = autoRuleMapping(mediCheckInfoList);
		}
		
		return list;
	}

}
