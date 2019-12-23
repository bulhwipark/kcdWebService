select
MED.KD_CD '제품코드',
MED.STD_CD '표준코드',
MED.DRUG_NM_KOR '제품한글명',
MED.DRUG_NM_ENG '제품영문명',
MED.DRUG_MANUF '업체명',
AI.amount as '약품규격',
AI.unit as '단위',
AI.frm as '투여경로',
MED.HIRA_ATC_CD 'ATC코드',
MED.HIRA_ATC_NM 'ATC영문명',
'' as 'Mapping type',

 M.SCT_ID  'Precoordinated concept SCTID',
 S.SCT_TERM2  'Precoordinated concept FSN',
 REGEXP_REPLACE(REGEXP_SUBSTR(S.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Precoordinated concept Hierarchy',

	concat( M.SCT_ID,'|',S.SCT_TERM,'|') 'SNOMED CT Expression'
 
from EMR.CM_MEDICINE_FOR_PRINT MED

left outer join rv_medi_addinfo_20191212 AI
on MED.KD_CD=AI.KD_CD
left outer join EMR.MAP_KCD_SCT2 M
on MED.KD_CD=M.ORI_CD
and M.ORI_TP_CD='MEDI'
left outer join EMR.CM_SNOMEDCT S
on S.SCT_ID=M.SCT_ID 
#where MED.kd_cd='641605440'



#24138