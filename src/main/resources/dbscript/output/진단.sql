use EMR;

select
K.KCD_CD 'KCD CODE',
K.KCD_KOR 'KCD_KOREAN',
K.KCD_ENG 'KCD_ENGLISH',
K.ICD_CD 'ICD CODE',
K.ICD_ENG 'ICD',
MT1.maptp as 'Mapping type',
case when ATT.ATT_SCT_ID is  null then M.SCT_ID else '' end 'Precoordinated concept SCTID',
case when ATT.ATT_SCT_ID is  null then S.SCT_TERM2 else ''  end 'Precoordinated concept FSN',
case when ATT.ATT_SCT_ID is  null then REGEXP_REPLACE(REGEXP_SUBSTR(S.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','') else '' end 'Precoordinated concept Hierarchy',


case when ATT.ATT_SCT_ID is NOT null then M.SCT_ID else '' end 'Postcoordinated Core concept SCTID',
case when ATT.ATT_SCT_ID is NOT null then S.SCT_TERM2 else ''  end 'Postcoordinated Core concept FSN',
case when ATT.ATT_SCT_ID is NOT null then REGEXP_REPLACE(REGEXP_SUBSTR(S.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','') else '' end 'Postcoordinated Core concept Hierarchy',

ATT.ATT_SCT_ID 'Attribute SCTID',
ATM.SCT_TERM 'Attribute FSN',
REGEXP_REPLACE(REGEXP_SUBSTR(ATM.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Attribute Hierarchy',

ATT.VAL_SCT_ID 'Value SCTID',
VTM.SCT_TERM  'Value FSN',
REGEXP_REPLACE(REGEXP_SUBSTR(VTM.SCT_TERM,'\\([^()]+\\)(?=[^()]*$)'),'[()]','')  'Value Hierarchy',

case when ATT.ATT_SCT_ID is null  then 
	concat( M.SCT_ID,'|',S.SCT_TERM,'|')
else 
	(
    select concat( M.SCT_ID,'|',S.SCT_TERM,'|:(' ,GROUP_CONCAT(CONCAT(MA.ATT_SCT_ID,'|',A.SCT_TERM,' |= ', MA.VAL_SCT_ID,'|',V.SCT_TERM,'|') SEPARATOR ' , ' ),')')
	from EMR.MAP_KCD_SCT_AFT_CAT MA
	inner join EMR.CM_SNOMEDCT A
	on MA.ATT_SCT_ID=A.SCT_ID
	inner join EMR.CM_SNOMEDCT V
	on MA.VAL_SCT_ID=V.SCT_ID
	where M.ORI_TP_CD=MA.ORI_TP_CD
	and M.MAP_VER=MA.MAP_VER
	and M.ORI_CD=MA.ORI_CD
	and M.SCT_ID=MA.SCT_ID
	) 
end 'SNOMED CT Expression'
 
from EMR.CM_KCD K
left outer join EMR.MAP_KCD_SCT M
on K.KCD_CD=M.ORI_CD
and M.ORI_TP_CD='KCD'
left outer join EMR.CM_SNOMEDCT S
on S.SCT_ID=M.SCT_ID 
left outer join EMR.MAP_KCD_SCT_AFT_CAT ATT
on M.ORI_TP_CD=ATT.ORI_TP_CD
and M.MAP_VER=ATT.MAP_VER
and M.ORI_CD=ATT.ORI_CD
and M.SCT_ID=ATT.SCT_ID
left outer join EMR.CM_SNOMEDCT ATM
on ATT.ATT_SCT_ID=ATM.SCT_ID 
left outer join EMR.CM_SNOMEDCT VTM
on ATT.VAL_SCT_ID=VTM.SCT_ID 
left outer join temp_kcd_maptype MT1
on K.KCD_CD=MT1.KCD_CD
;

